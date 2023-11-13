package org.misty.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TrackedTest {

    @Test
    public void create_with_id() {
        Tracked tracked = Tracked.create("test", "123");
        System.out.println(tracked);
    }

    @Test
    public void create_with_link() {
        Tracked trackedA = Tracked.create("A");
        Tracked trackedB = trackedA.link("B");
        Tracked trackedC = trackedB.linkWithRandom("C");
        Tracked trackedD = trackedC.link("D", "123");
        System.out.println(trackedA);
        System.out.println(trackedB);
        System.out.println(trackedC);
        System.out.println(trackedD);

        Assertions.assertThat(trackedD.getParent().get()).isEqualTo(trackedC);
        Assertions.assertThat(trackedC.getParent().get()).isEqualTo(trackedB);
        Assertions.assertThat(trackedB.getParent().get()).isEqualTo(trackedA);
        Assertions.assertThat(trackedA.getParent()).isEmpty();
    }

    @Test
    public void create_without_name() throws InterruptedException {
        int createTimes = 5;
        int eachRestMs = 100;

        for (int i = 0; i < createTimes; i++) {
            Tracked tracked = Tracked.create();
            System.out.println(tracked);
            Thread.sleep(eachRestMs);
        }
    }

    @Test
    public void create_with_name() throws InterruptedException {
        int createTimes = 5;
        int eachRestMs = 100;

        for (int i = 0; i < createTimes; i++) {
            Tracked tracked = Tracked.create("test");
            System.out.println(tracked);
            Thread.sleep(eachRestMs);
        }
    }

    @Test
    public void say() {
        Tracked tracked = Tracked.create();
        System.out.println(tracked.say("kerker"));
    }

}
