package org.misty.utils.task.dispatcher;

import org.misty.utils.Tracked;
import org.misty.utils.fi.ConsumerEx;
import org.misty.utils.task.TaskErrorPolicy;
import org.misty.utils.task.TaskGiveResult;
import org.misty.utils.task.executor.TaskExecutorBuilder;
import org.misty.utils.verify.Verifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class TaskDispatcherBuilder<Task> {

    public static <Task> TaskDispatcherBuilder<Task> create(Tracked tracked) {
        return new TaskDispatcherBuilder<>(tracked);
    }

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final List<TaskDispatchAction<Task>> taskDispatchActionList = new ArrayList<>();

    private final Tracked tracked;

    private final TaskExecutorBuilder taskExecutorBuilder;

    private ConsumerEx<TaskGiveResult<Task>> noMatchedAction;

    private BiFunction<Task, Exception, TaskErrorPolicy> defaultErrorHandler;

    private boolean buildFlag = false;

    public TaskDispatcherBuilder(Tracked tracked) {
        Verifier.refuseNull("tracked", tracked);
        this.tracked = tracked;
        this.taskExecutorBuilder = TaskExecutorBuilder.create(tracked);

        withParallel();

        Function<Task, String> errorWithClassInfo = task -> " " + task.getClass() + " : " + task;

        giveNoMatchedAction(result -> {
            this.logger.error(this.tracked.say("no match any action with" + errorWithClassInfo.apply(result.getTask())));
        });

        giveDefaultErrorHandler((task, e) -> {
            this.logger.error(this.tracked.say("execute error with" + errorWithClassInfo.apply(task)), e);
            return TaskErrorPolicy.CONTINUE;
        });
    }

    /**
     * 同{@link #giveAction(Predicate, ConsumerEx, BiFunction)}, 只是accept固定為true(表示接收所有任務)跟採用{@link #defaultErrorHandler}
     */
    public TaskDispatcherBuilder<Task> giveAction(ConsumerEx<Task> taskReceiver) {
        return giveAction(buildAlwaysReceive(), taskReceiver, (task, e) -> this.defaultErrorHandler.apply(task, e));
    }

    /**
     * 同{@link #giveAction(Predicate, ConsumerEx, BiFunction)}, 只是accept使用Class來判斷跟採用{@link #defaultErrorHandler}
     */
    public TaskDispatcherBuilder<Task> giveAction(Class<? extends Task> acceptClass, ConsumerEx<Task> dispatchAction) {
        return giveAction(buildClassAccept(acceptClass), dispatchAction, (task, e) -> this.defaultErrorHandler.apply(task, e));
    }

    /**
     * 同{@link #giveAction(Predicate, ConsumerEx, BiFunction)}, 只是errorHandler採用{@link #defaultErrorHandler}
     */
    public TaskDispatcherBuilder<Task> giveAction(Predicate<Task> accept, ConsumerEx<Task> taskReceiver) {
        return giveAction(accept, taskReceiver, (task, e) -> this.defaultErrorHandler.apply(task, e));
    }

    /**
     * 同{@link #giveAction(Predicate, ConsumerEx, BiFunction)}, 只是accept固定為true(表示接收所有任務)
     */
    public TaskDispatcherBuilder<Task> giveAction(ConsumerEx<Task> taskReceiver,
                                                  BiFunction<Task, Exception, TaskErrorPolicy> errorHandler) {
        return giveAction(buildAlwaysReceive(), taskReceiver, errorHandler);
    }

    /**
     * 同{@link #giveAction(Predicate, ConsumerEx, BiFunction)}, 只是accept使用Class來判斷
     */
    public TaskDispatcherBuilder<Task> giveAction(Class<? extends Task> acceptClass,
                                                  ConsumerEx<Task> taskReceiver,
                                                  BiFunction<Task, Exception, TaskErrorPolicy> errorHandler) {
        return giveAction(buildClassAccept(acceptClass), taskReceiver, errorHandler);
    }

    /**
     * 同{@link #giveAction(TaskDispatchAction)}, 只是將動作拆開使用Function來給定
     */
    public TaskDispatcherBuilder<Task> giveAction(Predicate<Task> accept,
                                                  ConsumerEx<Task> taskReceiver,
                                                  BiFunction<Task, Exception, TaskErrorPolicy> errorHandler) {
        Verifier.refuseNull("accept", accept);
        Verifier.refuseNull("taskReceiver", taskReceiver);
        Verifier.refuseNull("errorHandler", errorHandler);
        checkBuildFlag();

        return giveAction(new TaskDispatchActionAdapter<>(this.tracked, accept, taskReceiver, errorHandler));
    }

    /**
     * 給定任務接收動作
     */
    public TaskDispatcherBuilder<Task> giveAction(TaskDispatchAction<Task> taskDispatchAction) {
        Verifier.refuseNull("taskDispatchAction", taskDispatchAction);
        checkBuildFlag();

        this.taskDispatchActionList.add(taskDispatchAction);
        return this;
    }

    /**
     * 同{@link TaskExecutorBuilder#withSerial()}, 意即該任務調度器使用串行模式(單執行緒)
     */
    public TaskDispatcherBuilder<Task> withSerial() {
        checkBuildFlag();

        this.taskExecutorBuilder.withSerial();
        return this;
    }

    /**
     * 同{@link TaskExecutorBuilder#withParallel()}, 意即該任務調度器使用平行模式(多執行緒)
     */
    public TaskDispatcherBuilder<Task> withParallel() {
        checkBuildFlag();

        this.taskExecutorBuilder.withParallel();
        return this;
    }

    /**
     * 同{@link TaskExecutorBuilder#withParallel(int)}, 意即該任務調度器使用平行模式(多執行緒)
     */
    public TaskDispatcherBuilder<Task> withParallel(int threadNumber) {
        checkBuildFlag();

        this.taskExecutorBuilder.withParallel(threadNumber);
        return this;
    }

    /**
     * 同{@link TaskExecutorBuilder#withParallel(ExecutorService)}, 意即該任務調度器使用平行模式(多執行緒), 只是提供queue實作, threadNumber自動使用CPU核心數
     */
    public TaskDispatcherBuilder<Task> withParallel(BlockingQueue<Runnable> queue) {
        Verifier.refuseNull("queue", queue);
        checkBuildFlag();

        int threadNumber = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = new ThreadPoolExecutor(threadNumber, threadNumber, 0L, TimeUnit.MILLISECONDS, queue);
        this.taskExecutorBuilder.withParallel(executorService);
        return this;
    }

    /**
     * 同{@link TaskExecutorBuilder#withParallel(ExecutorService)}, 意即該任務調度器使用平行模式(多執行緒)
     */
    public TaskDispatcherBuilder<Task> withParallel(ExecutorService executorService) {
        Verifier.refuseNull("executorService", executorService);
        checkBuildFlag();

        this.taskExecutorBuilder.withParallel(executorService);
        return this;
    }

    public TaskDispatcherBuilder<Task> giveNoMatchedAction(ConsumerEx<TaskGiveResult<Task>> noMatchedAction) {
        Verifier.refuseNull("noMatchedAction", noMatchedAction);
        checkBuildFlag();

        this.noMatchedAction = noMatchedAction;
        return this;
    }

    /**
     * 給定預設的錯誤處理器, 當任務接收動作發生錯誤時, 會使用此錯誤處理器來處理
     */
    public TaskDispatcherBuilder<Task> giveDefaultErrorHandler(BiFunction<Task, Exception, TaskErrorPolicy> defaultErrorHandler) {
        Verifier.refuseNull("defaultErrorHandler", defaultErrorHandler);
        checkBuildFlag();

        this.defaultErrorHandler = defaultErrorHandler;
        return this;
    }

    public TaskDispatcher<Task> build() {
        Verifier.refuseNullOrEmpty("taskDispatchActionList", this.taskDispatchActionList, msg -> {
            throw new IllegalStateException("please give at least one taskDispatchAction");
        });

        checkBuildFlag();
        if (!this.buildFlag) {
            this.buildFlag = true;
        }

        return new TaskDispatcherPreset<>(this);
    }

    private void checkBuildFlag() {
        if (this.buildFlag) {
            throw new IllegalStateException("already build");
        }
    }

    private Predicate<Task> buildAlwaysReceive() {
        return task -> true;
    }

    private Predicate<Task> buildClassAccept(Class<? extends Task> acceptClass) {
        return task -> acceptClass.isAssignableFrom(task.getClass());
    }

    public Tracked getTracked() {
        return tracked;
    }

    public List<TaskDispatchAction<Task>> getTaskDispatchActionList() {
        return Collections.unmodifiableList(taskDispatchActionList);
    }

    public TaskExecutorBuilder getTaskExecutorBuilder() {
        return taskExecutorBuilder;
    }

    public ConsumerEx<TaskGiveResult<Task>> getNoMatchedAction() {
        return noMatchedAction;
    }

}
