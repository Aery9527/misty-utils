package org.misty.utils.ex;

public class ThreadEx extends Thread {

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

}
