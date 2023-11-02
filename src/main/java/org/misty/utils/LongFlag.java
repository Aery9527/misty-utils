package org.misty.utils;

public class LongFlag {

    public static LongFlag create() {
        return new LongFlag();
    }

    public static LongFlag create(long flags) {
        return new LongFlag(flags);
    }

    private long flags = 0;

    public LongFlag() {
    }

    public LongFlag(long flags) {
        this.flags = flags;
    }

    public void add(long flag) {
        checkFlag(flag);
        this.flags |= flag;
    }

    public void remove(long flag) {
        checkFlag(flag);
        this.flags &= ~flag;
    }

    public boolean has(long flag) {
        checkFlag(flag);
        return (this.flags & flag) == flag;
    }

    public void tip() {
        this.flags = ~this.flags;
    }

    private void checkFlag(long flag) {
        if (flag <= 0) {
            String msg = Long.toBinaryString(flag) + "(" + flag + ")";
            throw new IllegalArgumentException(msg);
        }
    }

    public long getFlags() {
        return this.flags;
    }

}
