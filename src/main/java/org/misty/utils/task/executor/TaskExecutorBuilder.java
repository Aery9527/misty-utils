package org.misty.utils.task.executor;

import org.misty.utils.Tracked;
import org.misty.utils.task.TaskErrorPolicy;
import org.misty.utils.verify.Verifier;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

public class TaskExecutorBuilder {

    public static TaskExecutorBuilder create(Tracked tracked) {
        return new TaskExecutorBuilder(tracked);
    }

    private final Tracked tracked;

    private TaskBaseExecutorBuilder<?, ?> baseBuilder;

    public TaskExecutorBuilder(Tracked tracked) {
        Verifier.refuseNull("tracked", tracked);
        this.tracked = tracked;
        withSerial();
    }

    public TaskCountExecutor buildCountExecutor() {
        return new TaskCountExecutor(build());
    }

    public TaskExecutor build() {
        return this.baseBuilder.build();
    }

    public TaskExecutorBuilder giveDefaultErrorHandler(Function<Exception, TaskErrorPolicy> defaultErrorHandler) {
        this.baseBuilder.giveDefaultErrorHandler(defaultErrorHandler);
        return this;
    }

    /**
     * 使用串行模式(單執行緒)
     */
    public TaskExecutorBuilder withSerial() {
        this.baseBuilder = new TaskSerialExecutorBuilder()
                .giveTracked(this.tracked);
        return this;
    }

    /**
     * 同{@link #withParallel()}, 只是執行緒數量自動使用CPU核心數
     */
    public TaskExecutorBuilder withParallel() {
        return withParallel(Runtime.getRuntime().availableProcessors());
    }

    /**
     * 同{@link #withParallel(ExecutorService)}, 只是指定執行緒數量
     */
    public TaskExecutorBuilder withParallel(int threadNumber) {
        return withParallel(Executors.newFixedThreadPool(threadNumber, r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        }));
    }

    /**
     * 使用併發模式(多執行緒)
     */
    public TaskExecutorBuilder withParallel(ExecutorService executorService) {
        Verifier.refuseNull("executorService", executorService);
        this.baseBuilder = new TaskParallelExecutorBuilder()
                .giveTracked(this.tracked)
                .giveExecutorService(executorService);
        return this;
    }

}
