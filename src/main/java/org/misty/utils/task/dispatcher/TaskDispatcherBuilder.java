package org.misty.utils.task.dispatcher;

import org.misty.utils.Tracked;
import org.misty.utils.fi.ConsumerEx;
import org.misty.utils.task.executor.TaskExecutor;
import org.misty.utils.task.executor.TaskExecutorBuilder;
import org.misty.utils.verify.Verifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class TaskDispatcherBuilder<Task> {

    public enum Type {
        /**
         * 串行模式, 也就是不會fork thread執行任務
         */
        SERIAL,

        /**
         * 有序模式, 會fork thread執行任務, 但每個action同一時間只會有一個thread在執行, 所以可以確保任務的執行順序 (也可以理解為以action為單位的串行模式)
         */
        ORDERED,

        /**
         * 無序模式, 會fork thread執行任務, 但每個action同一時間可能有多個thread在執行, 所以不能確保任務的執行順序
         */
        DISORDERED;
    }

    public static <Task> TaskDispatcherBuilder<Task> create(Tracked tracked) {
        return new TaskDispatcherBuilder<>(tracked);
    }

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final List<TaskDispatchAction<Task>> taskDispatchActionList = new ArrayList<>();

    private final Tracked tracked;

    private TaskDispatchErrorHandler<Task> defaultErrorHandler;

    private Supplier<TaskExecutor> taskExecutorBuilder;

    private Type type;

    private boolean buildFlag = false;

    public TaskDispatcherBuilder(Tracked tracked) {
        Verifier.refuseNull("tracked", tracked);
        this.tracked = tracked;

        withDisordered();

        Function<Task, String> errorWithClassInfo = task -> " " + task.getClass() + " : " + task;

        giveDefaultErrorHandler((actionTracked, task, e) -> this.logger.error(actionTracked.say("execute error with" + errorWithClassInfo.apply(task)), e));
    }

    /**
     * 同{@link #giveAction(Predicate, ConsumerEx, TaskDispatchErrorHandler)}, 只是accept固定為true(表示接收所有任務)跟採用{@link #defaultErrorHandler}
     */
    public TaskDispatcherBuilder<Task> giveAction(ConsumerEx<Task> taskReceiver) {
        return giveAction(buildAlwaysReceive(), taskReceiver, (actionTracked, task, e) -> this.defaultErrorHandler.handleError(actionTracked, task, e));
    }

    /**
     * 同{@link #giveAction(Predicate, ConsumerEx, TaskDispatchErrorHandler)}, 只是accept使用Class來判斷跟採用{@link #defaultErrorHandler}
     */
    public TaskDispatcherBuilder<Task> giveAction(Class<? extends Task> acceptClass, ConsumerEx<Task> taskReceiver) {
        return giveAction(buildClassAccept(acceptClass), taskReceiver, (actionTracked, task, e) -> this.defaultErrorHandler.handleError(actionTracked, task, e));
    }

    /**
     * 同{@link #giveAction(Predicate, ConsumerEx, TaskDispatchErrorHandler)}, 只是errorHandler採用{@link #defaultErrorHandler}
     */
    public TaskDispatcherBuilder<Task> giveAction(Predicate<Task> accept, ConsumerEx<Task> taskReceiver) {
        return giveAction(accept, taskReceiver, (actionTracked, task, e) -> this.defaultErrorHandler.handleError(actionTracked, task, e));
    }

    /**
     * 同{@link #giveAction(Predicate, ConsumerEx, TaskDispatchErrorHandler)}, 只是accept固定為true(表示接收所有任務)
     */
    public TaskDispatcherBuilder<Task> giveAction(ConsumerEx<Task> taskReceiver,
                                                  TaskDispatchErrorHandler<Task> errorHandler) {
        return giveAction(buildAlwaysReceive(), taskReceiver, errorHandler);
    }

    /**
     * 同{@link #giveAction(Predicate, ConsumerEx, TaskDispatchErrorHandler)}, 只是accept使用Class來判斷
     */
    public TaskDispatcherBuilder<Task> giveAction(Class<? extends Task> acceptClass,
                                                  ConsumerEx<Task> taskReceiver,
                                                  TaskDispatchErrorHandler<Task> errorHandler) {
        return giveAction(buildClassAccept(acceptClass), taskReceiver, errorHandler);
    }

    /**
     * 同{@link #giveAction(TaskDispatchAction)}, 只是將動作拆開使用Function來給定
     */
    public TaskDispatcherBuilder<Task> giveAction(Predicate<Task> accept,
                                                  ConsumerEx<Task> taskReceiver,
                                                  TaskDispatchErrorHandler<Task> errorHandler) {
        Verifier.refuseNull("accept", accept);
        Verifier.refuseNull("taskReceiver", taskReceiver);
        Verifier.refuseNull("errorHandler", errorHandler);
        checkBuildFlag();

        String trackedTitle = "[" + this.taskDispatchActionList.size() + "]";
        return giveAction(new TaskDispatchActionAdapter<>(this.tracked.link(trackedTitle), accept, taskReceiver, errorHandler));
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
     * 給定預設的錯誤處理器, 當任務接收動作發生錯誤時, 會使用此錯誤處理器來處理
     */
    public TaskDispatcherBuilder<Task> giveDefaultErrorHandler(TaskDispatchErrorHandler<Task> defaultErrorHandler) {
        Verifier.refuseNull("defaultErrorHandler", defaultErrorHandler);
        checkBuildFlag();

        this.defaultErrorHandler = defaultErrorHandler;
        return this;
    }

    /**
     * @see Type#SERIAL
     */
    public TaskDispatcherBuilder<Task> withSerial() {
        checkBuildFlag();

        this.taskExecutorBuilder = withSerialBuilder();
        this.type = Type.SERIAL;

        return this;
    }

    /**
     * @see Type#ORDERED
     */
    public TaskDispatcherBuilder<Task> withOrdered() {
        checkBuildFlag();

        this.taskExecutorBuilder = withParallelBuilder(this.taskDispatchActionList::size);
        this.type = Type.ORDERED;

        return this;
    }

    /**
     * @see Type#DISORDERED
     */
    public TaskDispatcherBuilder<Task> withDisordered() {
        checkBuildFlag();

        this.taskExecutorBuilder = withParallelBuilder();
        this.type = Type.DISORDERED;

        return this;
    }

    /**
     * @see Type#DISORDERED
     */
    public TaskDispatcherBuilder<Task> withDisordered(int threadNumber) {
        checkBuildFlag();
        Verifier.requireIntMoreThanExclusive("threadNumber", threadNumber, 0);

        this.taskExecutorBuilder = withParallelBuilder(() -> threadNumber);
        this.type = Type.DISORDERED;

        return this;
    }

    /**
     * @see TaskExecutorBuilder#withSerial()
     */
    private Supplier<TaskExecutor> withSerialBuilder() {
        return () -> TaskExecutor.builder().withSerial().build();
    }

    /**
     * @see TaskExecutorBuilder#withParallel()
     */
    private Supplier<TaskExecutor> withParallelBuilder() {
        return () -> TaskExecutor.builder().withParallel().build();
    }

    /**
     * @see TaskExecutorBuilder#withParallel(int)
     */
    private Supplier<TaskExecutor> withParallelBuilder(IntSupplier threadNumber) {
        return () -> TaskExecutor.builder().withParallel(threadNumber.getAsInt()).build();
    }

    public TaskDispatcher<Task> build() {
        Verifier.refuseNullOrEmpty("taskDispatchActionList", this.taskDispatchActionList, msg -> {
            throw new IllegalStateException("please give at least one taskDispatchAction");
        });

        checkBuildFlag();
        if (!this.buildFlag) {
            this.buildFlag = true;
        }

        if (this.type.equals(Type.SERIAL)) {
            return new TaskSerialDispatcher<>(this);
        } else if (this.type.equals(Type.ORDERED)) {
            return new TaskOrderedDispatcher<>(this);
        } else { // Type.DISORDERED
            return new TaskDisorderedDispatcher<>(this);
        }
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

    public Supplier<TaskExecutor> getTaskExecutorBuilder() {
        return taskExecutorBuilder;
    }

    public Type getType() {
        return type;
    }

}
