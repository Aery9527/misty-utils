package org.misty.utils;

import org.misty.utils.fi.IntConsumerEx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiPredicate;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ExecutorSwitch {

    public interface Action {

        void execute(int times) throws Exception;

        boolean errorHandler(int times, Exception e);

    }

    public static ExecutorSwitch create() {
        return new ExecutorSwitch(Tracked.create());
    }

    public static ExecutorSwitch create(BiPredicate<Integer, Exception> defaultErrorHandler) {
        return new ExecutorSwitch(Tracked.create(), defaultErrorHandler);
    }

    public static ExecutorSwitch create(String name) {
        return new ExecutorSwitch(Tracked.create(name));
    }

    public static ExecutorSwitch create(String name, BiPredicate<Integer, Exception> defaultErrorHandler) {
        return new ExecutorSwitch(Tracked.create(name), defaultErrorHandler);
    }

    public static ExecutorSwitch create(Tracked tracked) {
        return new ExecutorSwitch(tracked);
    }

    public static ExecutorSwitch create(Tracked tracked, BiPredicate<Integer, Exception> defaultErrorHandler) {
        return new ExecutorSwitch(tracked, defaultErrorHandler);
    }

    /**
     * 用來表達錯誤處理器的回傳值, 表示繼續執行
     */
    public static final boolean CONTINUE_WHEN_ERROR = false;

    /**
     * 同{@link #CONTINUE_WHEN_ERROR}, 表示中斷執行
     */
    public static final boolean BREAK_WHEN_ERROR = true;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 啟動鎖, 當第一次執行{@link #run(IntConsumerEx, BiPredicate)}開始就不得改變執行模式({@link #withParallel(ExecutorService)}或{@link #withSerial()})
     */
    private final AtomicBoolean startLock = new AtomicBoolean(false);

    /**
     * 中斷鎖, 當任務發生錯誤時, 這邊會被設定為true, 表示中斷執行
     */
    private final AtomicBoolean breakFlag = new AtomicBoolean(false);

    /**
     * 執行計數器, 當任務被執行時, 這邊會被加1
     */
    private final AtomicInteger executeCount = new AtomicInteger(0);

    /**
     * 追蹤碼, 方便用來追蹤哪個{@link ExecutorSwitch}實例執行錯誤
     */
    private final Tracked tracked;

    /**
     * 預設的錯誤處理器
     */
    private final BiPredicate<Integer, Exception> defaultErrorHandler;

    /**
     * 任務執行動作, 會根據{@link #withParallel(ExecutorService)}或{@link #withSerial()}來決定是同步或非同步執行
     */
    private Consumer<Runnable> runAction;

    /**
     * 任務執行完成檢查動作, 用於主thread等待使用
     */
    private Predicate<Duration> finishCheckAction;

    private boolean withSerial;

    public ExecutorSwitch(Tracked tracked) {
        this.tracked = tracked;
        this.defaultErrorHandler = ((times, e) -> {
            this.logger.error(this.tracked.say("error when times({})"), times, e);
            return CONTINUE_WHEN_ERROR;
        });

        withSerial();
        reset();
    }

    public ExecutorSwitch(Tracked tracked, BiPredicate<Integer, Exception> defaultErrorHandler) {
        this.tracked = tracked;
        this.defaultErrorHandler = defaultErrorHandler;

        withSerial();
        reset();
    }

    /**
     * {@link #withParallel(ExecutorService)}, threadNumber自動使用CPU核心數
     */
    public ExecutorSwitch withParallel() {
        int availableCores = Runtime.getRuntime().availableProcessors();
        return withParallel(availableCores);
    }

    /**
     * {@link #withParallel(ExecutorService)}
     */
    public ExecutorSwitch withParallel(int threadNumber) {
        withParallel(Executors.newFixedThreadPool(threadNumber));
        return this;
    }

    /**
     * 使用多執行緒模式
     */
    public ExecutorSwitch withParallel(ExecutorService executorService) {
        checkStartLock();

        AtomicInteger finishCount = new AtomicInteger(0);
        Object lock = new Object();

        BooleanSupplier checkFinish = () -> finishCount.get() == this.executeCount.get();

        this.runAction = action -> executorService.execute(() -> {
            try {
                action.run();
            } finally {
                finishCount.incrementAndGet();

                synchronized (lock) {
                    lock.notifyAll();
                }
            }
        });

        this.finishCheckAction = (waitTime) -> {
            long waitMs = waitTime.toMillis();

            Watcher watcher = Watcher.create();
            while (!watcher.over(waitMs)) {
                long through = watcher.through();
                long timeout = Math.min(1000, waitMs - through);

                try {
                    synchronized (lock) {
                        if (checkFinish.getAsBoolean()) {
                            return true;
                        }

                        lock.wait(timeout);

                        if (checkFinish.getAsBoolean()) {
                            return true;
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // reset interrupt flag
                    this.logger.error(this.tracked.say("wait job"), e);
                    return checkFinish.getAsBoolean();
                }
            }

            return checkFinish.getAsBoolean();
        };

        this.withSerial = false;

        return this;
    }

    /**
     * 使用單線程模式
     */
    public ExecutorSwitch withSerial() {
        checkStartLock();

        this.runAction = Runnable::run;
        this.finishCheckAction = waitTime -> true;
        this.withSerial = true;
        return this;
    }

    private void checkStartLock() {
        if (this.startLock.get()) {
            throw new IllegalStateException("please reset first before change mode.");
        }
    }

    /**
     * 會無限等待之前已送出的任務全部完成, 才會進行rest
     */
    public ExecutorSwitch reset() {
        waitFinish();

        this.startLock.set(false);
        this.breakFlag.set(false);
        this.executeCount.set(0);
        return this;
    }

    /**
     * 參考{@link #run(IntConsumerEx, BiPredicate)}
     */
    public boolean run(Action executeAction) {
        return run(executeAction::execute, executeAction::errorHandler);
    }

    /**
     * 參考{@link #run(IntConsumerEx, BiPredicate)}
     */
    public boolean run(IntConsumerEx executeAction) {
        return run(executeAction, this.defaultErrorHandler);
    }

    /**
     * 同步或非同步執行executeAction
     *
     * @param executeAction 欲工作的任務內容
     * @param errorHandler  錯誤處理器, 回傳true表示中斷執行, 回傳false表示繼續執行, 參考{@link ExecutorSwitch#CONTINUE_WHEN_ERROR}與{@link ExecutorSwitch#BREAK_WHEN_ERROR}
     * @return true表示該任務有執行, false表示任務沒有執行
     */
    public boolean run(IntConsumerEx executeAction, BiPredicate<Integer, Exception> errorHandler) {
        this.startLock.set(true);

        if (this.breakFlag.get()) {
            return false;
        }

        int times = this.executeCount.incrementAndGet();
        this.runAction.accept(() -> {
            try {
                executeAction.handle(times);
            } catch (Exception e) {
                boolean breakFlag = true;
                try {
                    breakFlag = errorHandler.test(times, e);
                } catch (Exception ex) {
                    this.logger.error(this.tracked.say("raw error when times({})"), times, e);
                    this.logger.error(this.tracked.say("errorHandler error when times({})"), times, ex);
                }
                this.breakFlag.compareAndSet(false, breakFlag);
            }
        });

        return true;
    }

    public void waitFinish() {
        waitFinish(Duration.ofMillis(Long.MAX_VALUE));
    }

    public boolean waitFinish(Duration waitTime) {
        return this.finishCheckAction.test(waitTime);
    }

    public Tracked getTracked() {
        return tracked;
    }

    public boolean isWithSerial() {
        return this.withSerial;
    }

    public boolean isWithParallel() {
        return !this.withSerial;
    }

}
