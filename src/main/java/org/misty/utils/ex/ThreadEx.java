package org.misty.utils.ex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadEx extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadEx.class);

    public static boolean rest(long millis) {
        try {
            Thread.sleep(millis);
            return false;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // reset interrupt flag
            return true;
        }
    }

    public static boolean restRandom(long most) {
        return restRandom(0, most);
    }

    public static boolean restRandom(long least, long most) {
        if (most < 0) {
            throw new IllegalArgumentException("most(" + most + ") < 0");
        } else if (least < 0) {
            throw new IllegalArgumentException("least(" + least + ") < 0");
        } else if (most <= least) {
            throw new IllegalArgumentException("most(" + most + ") <= least(" + least + ")");
        }

        return rest((long) (Math.random() * (most - least)) + least);
    }

    public static void fork(Runnable action) {
        new Thread(action).start();
    }

    public static void fork(String threadName, Runnable action) {
        new Thread(() -> {
            try {
                LOGGER.info("[{}] in", threadName);
                action.run();
            } catch (Exception e) {
                LOGGER.info("[{}] error", threadName, e);
            } finally {
                LOGGER.info("[{}] out", threadName);
            }
        }, threadName).start();
    }

}
