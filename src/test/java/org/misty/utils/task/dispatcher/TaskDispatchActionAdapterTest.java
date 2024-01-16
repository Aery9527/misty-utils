package org.misty.utils.task.dispatcher;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;
import org.misty.utils.Tracked;
import org.misty.utils.task.TaskErrorPolicy;

import java.util.concurrent.atomic.AtomicReference;

public class TaskDispatchActionAdapterTest {

    @Test
    public void getTracked() {
        Tracked tracked = Tracked.create();
        TaskDispatchActionAdapter<String> adapter = new TaskDispatchActionAdapter<>(tracked, task -> {
            return true;
        }, task -> {
        }, (task, e) -> {
            return TaskErrorPolicy.CONTINUE;
        });

        AssertionsEx.assertThat(adapter.getTracked() == tracked).isTrue();
    }

    @Test
    public void accept() {
        AtomicReference<String> check = new AtomicReference<>();
        TaskDispatchActionAdapter<String> adapter = new TaskDispatchActionAdapter<>(Tracked.create(), task -> {
            check.set(task);
            return true;
        }, task -> {
        }, (task, e) -> {
            return TaskErrorPolicy.CONTINUE;
        });

        adapter.accept("kerker");
        AssertionsEx.assertThat(check.get()).isEqualTo("kerker");
    }

    @Test
    public void receive() throws Exception {
        AtomicReference<String> check = new AtomicReference<>();
        TaskDispatchActionAdapter<String> adapter = new TaskDispatchActionAdapter<>(Tracked.create(), task -> {
            return true;
        }, task -> {
            check.set(task);
        }, (task, e) -> {
            return TaskErrorPolicy.CONTINUE;
        });

        adapter.receive("kerker");
        AssertionsEx.assertThat(check.get()).isEqualTo("kerker");
    }

    @Test
    public void name() throws Exception {
        AtomicReference<String> check = new AtomicReference<>();
        TaskDispatchActionAdapter<String> adapter = new TaskDispatchActionAdapter<>(Tracked.create(), task -> {
            return true;
        }, task -> {
        }, (task, e) -> {
            check.set(task);
            return TaskErrorPolicy.CONTINUE;
        });

        adapter.handleError("kerker", null);
        AssertionsEx.assertThat(check.get()).isEqualTo("kerker");
    }

}
