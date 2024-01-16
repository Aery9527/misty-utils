package org.misty.utils.task;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskGiveResultTest {

    @Test
    public void isAllMatchedExecuted() {
        String task = "kerker";
        int allActionCount = 3;

        {
            Map<TaskExecuteResult, AtomicInteger> matchedExecuteResultCount = new HashMap<>();
            matchedExecuteResultCount.put(TaskExecuteResult.EXECUTED, new AtomicInteger(2));

            TaskGiveResult<String> giveResult = new TaskGiveResult<>(task, allActionCount, matchedExecuteResultCount);
            AssertionsEx.assertThat(giveResult.isAllMatchedExecuted()).isTrue();
        }

        {
            Map<TaskExecuteResult, AtomicInteger> matchedExecuteResultCount = new HashMap<>();
            matchedExecuteResultCount.put(TaskExecuteResult.EXECUTED, new AtomicInteger(2));
            matchedExecuteResultCount.put(TaskExecuteResult.INTERRUPTED, new AtomicInteger(1));

            TaskGiveResult<String> giveResult = new TaskGiveResult<>(task, allActionCount, matchedExecuteResultCount);
            AssertionsEx.assertThat(giveResult.isAllMatchedExecuted()).isFalse();
        }
    }

    @Test
    public void getTask() {
        String task = "kerker";
        int allActionCount = 3;

        Map<TaskExecuteResult, AtomicInteger> matchedExecuteResultCount = new HashMap<>();

        TaskGiveResult<String> giveResult = new TaskGiveResult<>(task, allActionCount, matchedExecuteResultCount);

        AssertionsEx.assertThat(giveResult.getTask()).isEqualTo(task);
    }

    @Test
    public void getAllActionCount() {
        String task = "kerker";
        int allActionCount = 3;

        Map<TaskExecuteResult, AtomicInteger> matchedExecuteResultCount = new HashMap<>();

        TaskGiveResult<String> giveResult = new TaskGiveResult<>(task, allActionCount, matchedExecuteResultCount);

        AssertionsEx.assertThat(giveResult.getAllActionCount()).isEqualTo(allActionCount);
    }

    @Test
    public void getMatchedCount() {
        String task = "kerker";
        int allActionCount = 3;

        Map<TaskExecuteResult, AtomicInteger> matchedExecuteResultCount = new HashMap<>();
        matchedExecuteResultCount.put(TaskExecuteResult.EXECUTED, new AtomicInteger(2));
        matchedExecuteResultCount.put(TaskExecuteResult.INTERRUPTED, new AtomicInteger(1));

        TaskGiveResult<String> giveResult = new TaskGiveResult<>(task, allActionCount, matchedExecuteResultCount);

        AssertionsEx.assertThat(giveResult.getMatchedCount()).isEqualTo(3);
    }

    @Test
    public void getResultCount() {
        String task = "kerker";
        int allActionCount = 3;

        Map<TaskExecuteResult, AtomicInteger> matchedExecuteResultCount = new HashMap<>();
        matchedExecuteResultCount.put(TaskExecuteResult.EXECUTED, new AtomicInteger(2));
        matchedExecuteResultCount.put(TaskExecuteResult.INTERRUPTED, new AtomicInteger(1));

        TaskGiveResult<String> giveResult = new TaskGiveResult<>(task, allActionCount, matchedExecuteResultCount);

        AssertionsEx.assertThat(giveResult.getResultCount(TaskExecuteResult.EXECUTED)).isEqualTo(2);
        AssertionsEx.assertThat(giveResult.getResultCount(TaskExecuteResult.INTERRUPTED)).isEqualTo(1);
        AssertionsEx.assertThat(giveResult.getResultCount(TaskExecuteResult.CLOSED)).isEqualTo(0);
    }
}
