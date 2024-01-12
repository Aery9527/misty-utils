package org.misty.utils.lock;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;
import org.misty.utils.ex.ThreadEx;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class GroupWakeupLockTest {

    @Test
    public void lockFactory() {
        String key = "kerker";
        WakeupLock mockLock = Mockito.mock(WakeupLock.class);

        GroupWakeupLock groupWakeupLock = new GroupWakeupLock().create(key, (k) -> mockLock);

        { // wakeup
            AtomicBoolean check = new AtomicBoolean(false);
            Mockito.doAnswer(invocation -> {
                check.set(true);
                return null;
            }).when(mockLock).wakeup();
            groupWakeupLock.wakeup(key);

            AssertionsEx.assertThat(check.get()).isTrue();
        }

        { // wakeup + Runnable
            Runnable actionInSynchronized = () -> {
            };

            AtomicReference<Runnable> check = new AtomicReference<>();
            Mockito.doAnswer(invocation -> {
                check.set(invocation.getArgument(0));
                return null;
            }).when(mockLock).wakeup(actionInSynchronized);
            groupWakeupLock.wakeup(key, actionInSynchronized);

            AssertionsEx.assertThat(check.get() == actionInSynchronized).isTrue();
        }

        { // wakeup + Supplier
            Supplier<Void> actionInSynchronized = () -> null;

            AtomicReference<Supplier<Void>> check = new AtomicReference<>();
            Mockito.doAnswer(invocation -> {
                check.set(invocation.getArgument(0));
                return null;
            }).when(mockLock).wakeup(actionInSynchronized);
            groupWakeupLock.wakeup(key, actionInSynchronized);

            AssertionsEx.assertThat(check.get() == actionInSynchronized).isTrue();
        }

        { // waiting
            AtomicBoolean check = new AtomicBoolean(false);
            Mockito.doAnswer(invocation -> {
                check.set(true);
                return null;
            }).when(mockLock).waiting();
            groupWakeupLock.waiting(key);

            AssertionsEx.assertThat(check.get()).isTrue();
        }

        { // waitingAndAwareBefore
            Runnable actionBeforeWaitSynchronized = () -> {
            };

            AtomicReference<Runnable> check = new AtomicReference<>();
            Mockito.doAnswer(invocation -> {
                check.set(invocation.getArgument(0));
                return null;
            }).when(mockLock).waitingAndAwareBefore(actionBeforeWaitSynchronized);
            groupWakeupLock.waitingAndAwareBefore(key, actionBeforeWaitSynchronized);

            AssertionsEx.assertThat(check.get() == actionBeforeWaitSynchronized).isTrue();
        }

        { // waitingAndAwareAfter
            Runnable actionAfterWaitSynchronized = () -> {
            };

            AtomicReference<Runnable> check = new AtomicReference<>();
            Mockito.doAnswer(invocation -> {
                check.set(invocation.getArgument(0));
                return null;
            }).when(mockLock).waitingAndAwareAfter(actionAfterWaitSynchronized);
            groupWakeupLock.waitingAndAwareAfter(key, actionAfterWaitSynchronized);

            AssertionsEx.assertThat(check.get() == actionAfterWaitSynchronized).isTrue();
        }

        { // waiting + actionBeforeWaitSynchronized + actionAfterWaitSynchronized
            Runnable actionBeforeWaitSynchronized = () -> {
            };
            Runnable actionAfterWaitSynchronized = () -> {
            };

            AtomicReference<Runnable> check1 = new AtomicReference<>();
            AtomicReference<Runnable> check2 = new AtomicReference<>();
            Mockito.doAnswer(invocation -> {
                check1.set(invocation.getArgument(0));
                check2.set(invocation.getArgument(1));
                return null;
            }).when(mockLock).waiting(actionBeforeWaitSynchronized, actionAfterWaitSynchronized);
            groupWakeupLock.waiting(key, actionBeforeWaitSynchronized, actionAfterWaitSynchronized);

            AssertionsEx.assertThat(check1.get() == actionBeforeWaitSynchronized).isTrue();
            AssertionsEx.assertThat(check2.get() == actionAfterWaitSynchronized).isTrue();
        }

        { // waiting + interruptHandler
            Consumer<InterruptedException> interruptHandler = e -> {
            };

            AtomicReference<Consumer<InterruptedException>> check = new AtomicReference<>();
            Mockito.doAnswer(invocation -> {
                check.set(invocation.getArgument(0));
                return null;
            }).when(mockLock).waiting(interruptHandler);
            groupWakeupLock.waiting(key, interruptHandler);

            AssertionsEx.assertThat(check.get() == interruptHandler).isTrue();
        }

        { // waitingAndAwareBefore + interruptHandler + actionBeforeWaitSynchronized
            Consumer<InterruptedException> interruptHandler = e -> {
            };
            Runnable actionBeforeWaitSynchronized = () -> {
            };

            AtomicReference<Consumer<InterruptedException>> check1 = new AtomicReference<>();
            AtomicReference<Runnable> check2 = new AtomicReference<>();
            Mockito.doAnswer(invocation -> {
                check1.set(invocation.getArgument(0));
                check2.set(invocation.getArgument(1));
                return null;
            }).when(mockLock).waitingAndAwareBefore(interruptHandler, actionBeforeWaitSynchronized);
            groupWakeupLock.waitingAndAwareBefore(key, interruptHandler, actionBeforeWaitSynchronized);

            AssertionsEx.assertThat(check1.get() == interruptHandler).isTrue();
            AssertionsEx.assertThat(check2.get() == actionBeforeWaitSynchronized).isTrue();
        }

        { // waitingAndAwareAfter + interruptHandler + actionAfterWaitSynchronized
            Consumer<InterruptedException> interruptHandler = e -> {
            };
            Runnable actionAfterWaitSynchronized = () -> {
            };

            AtomicReference<Consumer<InterruptedException>> check1 = new AtomicReference<>();
            AtomicReference<Runnable> check2 = new AtomicReference<>();
            Mockito.doAnswer(invocation -> {
                check1.set(invocation.getArgument(0));
                check2.set(invocation.getArgument(1));
                return null;
            }).when(mockLock).waitingAndAwareAfter(interruptHandler, actionAfterWaitSynchronized);
            groupWakeupLock.waitingAndAwareAfter(key, interruptHandler, actionAfterWaitSynchronized);

            AssertionsEx.assertThat(check1.get() == interruptHandler).isTrue();
            AssertionsEx.assertThat(check2.get() == actionAfterWaitSynchronized).isTrue();
        }

        { // waiting + interruptHandler + actionBeforeWaitSynchronized + actionAfterWaitSynchronized
            Consumer<InterruptedException> interruptHandler = e -> {
            };
            Runnable actionBeforeWaitSynchronized = () -> {
            };
            Runnable actionAfterWaitSynchronized = () -> {
            };

            AtomicReference<Consumer<InterruptedException>> check1 = new AtomicReference<>();
            AtomicReference<Runnable> check2 = new AtomicReference<>();
            AtomicReference<Runnable> check3 = new AtomicReference<>();
            Mockito.doAnswer(invocation -> {
                check1.set(invocation.getArgument(0));
                check2.set(invocation.getArgument(1));
                check3.set(invocation.getArgument(2));
                return null;
            }).when(mockLock).waiting(interruptHandler, actionBeforeWaitSynchronized, actionAfterWaitSynchronized);
            groupWakeupLock.waiting(key, interruptHandler, actionBeforeWaitSynchronized, actionAfterWaitSynchronized);

            AssertionsEx.assertThat(check1.get() == interruptHandler).isTrue();
            AssertionsEx.assertThat(check2.get() == actionBeforeWaitSynchronized).isTrue();
            AssertionsEx.assertThat(check3.get() == actionAfterWaitSynchronized).isTrue();
        }

        { // waiting + timeout
            AtomicLong check = new AtomicLong(0);
            Mockito.doAnswer(invocation -> {
                check.set(invocation.getArgument(0));
                return null;
            }).when(mockLock).waiting(9527);
            groupWakeupLock.waiting(key, 9527);

            AssertionsEx.assertThat(check.get()).isEqualTo(9527);
        }

        { // waitingAndAwareBefore + timeout
            Runnable actionBeforeWaitSynchronized = () -> {
            };

            AtomicLong check1 = new AtomicLong(0);
            AtomicReference<Runnable> check2 = new AtomicReference<>();
            Mockito.doAnswer(invocation -> {
                check1.set(invocation.getArgument(0));
                check2.set(invocation.getArgument(1));
                return null;
            }).when(mockLock).waitingAndAwareBefore(9527, actionBeforeWaitSynchronized);
            groupWakeupLock.waitingAndAwareBefore(key, 9527, actionBeforeWaitSynchronized);

            AssertionsEx.assertThat(check1.get()).isEqualTo(9527);
            AssertionsEx.assertThat(check2.get() == actionBeforeWaitSynchronized).isTrue();
        }

        { // waitingAndAwareAfter + timeout
            Runnable actionAfterWaitSynchronized = () -> {
            };

            AtomicLong check1 = new AtomicLong(0);
            AtomicReference<Runnable> check2 = new AtomicReference<>();
            Mockito.doAnswer(invocation -> {
                check1.set(invocation.getArgument(0));
                check2.set(invocation.getArgument(1));
                return null;
            }).when(mockLock).waitingAndAwareAfter(9527, actionAfterWaitSynchronized);
            groupWakeupLock.waitingAndAwareAfter(key, 9527, actionAfterWaitSynchronized);

            AssertionsEx.assertThat(check1.get()).isEqualTo(9527);
            AssertionsEx.assertThat(check2.get() == actionAfterWaitSynchronized).isTrue();
        }

        { // waiting + timeout + actionBeforeWaitSynchronized + actionAfterWaitSynchronized
            Runnable actionBeforeWaitSynchronized = () -> {
            };
            Runnable actionAfterWaitSynchronized = () -> {
            };

            AtomicLong check1 = new AtomicLong(0);
            AtomicReference<Runnable> check2 = new AtomicReference<>();
            AtomicReference<Runnable> check3 = new AtomicReference<>();
            Mockito.doAnswer(invocation -> {
                check1.set(invocation.getArgument(0));
                check2.set(invocation.getArgument(1));
                check3.set(invocation.getArgument(2));
                return null;
            }).when(mockLock).waiting(9527, actionBeforeWaitSynchronized, actionAfterWaitSynchronized);
            groupWakeupLock.waiting(key, 9527, actionBeforeWaitSynchronized, actionAfterWaitSynchronized);

            AssertionsEx.assertThat(check1.get()).isEqualTo(9527);
            AssertionsEx.assertThat(check2.get() == actionBeforeWaitSynchronized).isTrue();
            AssertionsEx.assertThat(check3.get() == actionAfterWaitSynchronized).isTrue();
        }

        { // waiting + timeout + interruptHandler
            Consumer<InterruptedException> interruptHandler = e -> {
            };

            AtomicLong check1 = new AtomicLong(0);
            AtomicReference<Consumer<InterruptedException>> check2 = new AtomicReference<>();
            Mockito.doAnswer(invocation -> {
                check1.set(invocation.getArgument(0));
                check2.set(invocation.getArgument(1));
                return null;
            }).when(mockLock).waiting(9527, interruptHandler);
            groupWakeupLock.waiting(key, 9527, interruptHandler);

            AssertionsEx.assertThat(check1.get()).isEqualTo(9527);
            AssertionsEx.assertThat(check2.get() == interruptHandler).isTrue();
        }

        { // waitingAndAwareBefore + timeout + interruptHandler + actionBeforeWaitSynchronized
            Consumer<InterruptedException> interruptHandler = e -> {
            };
            Runnable actionBeforeWaitSynchronized = () -> {
            };

            AtomicLong check1 = new AtomicLong(0);
            AtomicReference<Consumer<InterruptedException>> check2 = new AtomicReference<>();
            AtomicReference<Runnable> check3 = new AtomicReference<>();
            Mockito.doAnswer(invocation -> {
                check1.set(invocation.getArgument(0));
                check2.set(invocation.getArgument(1));
                check3.set(invocation.getArgument(2));
                return null;
            }).when(mockLock).waitingAndAwareBefore(9527, interruptHandler, actionBeforeWaitSynchronized);
            groupWakeupLock.waitingAndAwareBefore(key, 9527, interruptHandler, actionBeforeWaitSynchronized);

            AssertionsEx.assertThat(check1.get()).isEqualTo(9527);
            AssertionsEx.assertThat(check2.get() == interruptHandler).isTrue();
            AssertionsEx.assertThat(check3.get() == actionBeforeWaitSynchronized).isTrue();
        }

        { // waitingAndAwareAfter + timeout + interruptHandler + actionAfterWaitSynchronized
            Consumer<InterruptedException> interruptHandler = e -> {
            };
            Runnable actionAfterWaitSynchronized = () -> {
            };

            AtomicLong check1 = new AtomicLong(0);
            AtomicReference<Consumer<InterruptedException>> check2 = new AtomicReference<>();
            AtomicReference<Runnable> check3 = new AtomicReference<>();
            Mockito.doAnswer(invocation -> {
                check1.set(invocation.getArgument(0));
                check2.set(invocation.getArgument(1));
                check3.set(invocation.getArgument(2));
                return null;
            }).when(mockLock).waitingAndAwareAfter(9527, interruptHandler, actionAfterWaitSynchronized);
            groupWakeupLock.waitingAndAwareAfter(key, 9527, interruptHandler, actionAfterWaitSynchronized);

            AssertionsEx.assertThat(check1.get()).isEqualTo(9527);
            AssertionsEx.assertThat(check2.get() == interruptHandler).isTrue();
            AssertionsEx.assertThat(check3.get() == actionAfterWaitSynchronized).isTrue();
        }

        { // waiting + timeout + interruptHandler + actionAfterWaitSynchronized
            Consumer<InterruptedException> interruptHandler = e -> {
            };
            Runnable actionBeforeWaitSynchronized = () -> {
            };
            Runnable actionAfterWaitSynchronized = () -> {
            };

            AtomicLong check1 = new AtomicLong(0);
            AtomicReference<Consumer<InterruptedException>> check2 = new AtomicReference<>();
            AtomicReference<Runnable> check3 = new AtomicReference<>();
            AtomicReference<Runnable> check4 = new AtomicReference<>();
            Mockito.doAnswer(invocation -> {
                check1.set(invocation.getArgument(0));
                check2.set(invocation.getArgument(1));
                check3.set(invocation.getArgument(2));
                check4.set(invocation.getArgument(3));
                return null;
            }).when(mockLock).waiting(9527, interruptHandler, actionBeforeWaitSynchronized, actionAfterWaitSynchronized);
            groupWakeupLock.waiting(key, 9527, interruptHandler, actionBeforeWaitSynchronized, actionAfterWaitSynchronized);

            AssertionsEx.assertThat(check1.get()).isEqualTo(9527);
            AssertionsEx.assertThat(check2.get() == interruptHandler).isTrue();
            AssertionsEx.assertThat(check3.get() == actionBeforeWaitSynchronized).isTrue();
            AssertionsEx.assertThat(check4.get() == actionAfterWaitSynchronized).isTrue();
        }
    }

    @Test
    public void create() {
        GroupWakeupLock groupLock = new GroupWakeupLock();

        groupLock.create("kerker", true);

        AssertionsEx.awareThrown(() -> groupLock.create("kerker", false))
                .hasMessageContaining("already exists")
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void waiting_wakeup() {
        GroupWakeupLock groupWakeupLock = new GroupWakeupLock();
        String waitingKey1 = "1";
        String waitingKey2 = "2";

        List<String> orderList = new ArrayList<>();

        ThreadEx.fork(() -> {
            groupWakeupLock.waiting(waitingKey1);
            orderList.add("1");
            groupWakeupLock.wakeup(waitingKey2);
        });

        ThreadEx.rest(50);
        orderList.add("2");
        groupWakeupLock.wakeup(waitingKey1);
        groupWakeupLock.waiting(waitingKey2);

        Assertions.assertThat(orderList).containsExactly("2", "1");
        Assertions.assertThat(groupWakeupLock.getKeys()).containsExactlyInAnyOrderElementsOf(List.of(waitingKey1, waitingKey2));
    }

    @Test
    public void waiting_timeout_wakeup() {
        for (int times = 0; times < 10; times++) {
            boolean wakeupOverTimeout = times % 2 == 0;

            GroupWakeupLock groupWakeupLock = new GroupWakeupLock();
            String waitingKey1 = "1";
            String waitingKey2 = "2";
            long timeout = 100;
            long buffer = (wakeupOverTimeout ? 1 : -1) * timeout / 2;

            List<String> orderList = new ArrayList<>();

            ThreadEx.fork(() -> {
                groupWakeupLock.waiting(waitingKey1, timeout);
                orderList.add("1");
                groupWakeupLock.wakeup(waitingKey2);
            });

            ThreadEx.rest(timeout + buffer);
            orderList.add("2");
            groupWakeupLock.wakeup(waitingKey1);
            groupWakeupLock.waiting(waitingKey2);

            String[] expectedOrderList = wakeupOverTimeout ? new String[]{"1", "2"} : new String[]{"2", "1"};
            Assertions.assertThat(orderList).containsExactly(expectedOrderList);
            Assertions.assertThat(groupWakeupLock.getKeys()).containsExactlyInAnyOrderElementsOf(List.of(waitingKey1, waitingKey2));
        }
    }

}
