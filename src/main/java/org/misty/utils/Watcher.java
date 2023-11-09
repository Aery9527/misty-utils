package org.misty.utils;

public class Watcher {

    public static Watcher create() {
        return new Watcher();
    }

    private long startTime = System.currentTimeMillis();

    /**
     * @return 從實例建構開始計時到現在的時間
     */
    public long through() {
        return System.currentTimeMillis() - this.startTime;
    }

    /**
     * @return 從實例開始計時到現在的時間是否超過指定時間
     */
    public boolean over(long duration) {
        return through() > duration;
    }

    /**
     * 重置計時器
     */
    public Watcher reset() {
        this.startTime = System.currentTimeMillis();
        return this;
    }

    public String format() {
        return TimeFormat.auto(through());
    }

    public long getStartTime() {
        return startTime;
    }

}
