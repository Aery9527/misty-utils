package org.misty.utils.task;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskGiveResult<Task> {

    private final Task task;

    private final int allActionCount;

    private final Map<TaskExecuteResult, AtomicInteger> matchedExecuteResultCount;

    private final int matchedCount;

    public TaskGiveResult(Task task, int allActionCount, Map<TaskExecuteResult, AtomicInteger> matchedExecuteResultCount) {
        this.task = task;
        this.allActionCount = allActionCount;
        this.matchedExecuteResultCount = Collections.unmodifiableMap(matchedExecuteResultCount);
        this.matchedCount = this.matchedExecuteResultCount.values().stream().mapToInt(AtomicInteger::get).sum();
    }

    public boolean isAllMatchedExecuted() {
        return this.matchedCount == getResultCount(TaskExecuteResult.EXECUTED);
    }

    public Task getTask() {
        return task;
    }

    public int getAllActionCount() {
        return allActionCount;
    }

    public int getMatchedCount() {
        return matchedCount;
    }

    public int getResultCount(TaskExecuteResult executeResult) {
        AtomicInteger count = this.matchedExecuteResultCount.get(executeResult);
        return count == null ? 0 : count.get();
    }

}
