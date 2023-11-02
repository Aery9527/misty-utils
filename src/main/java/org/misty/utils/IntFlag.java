package org.misty.utils;

public class IntFlag {

    public static IntFlag create() {
        return new IntFlag();
    }

    public static IntFlag create(int flags) {
        return new IntFlag(flags);
    }

    private int flags = 0;

    public IntFlag() {
    }

    public IntFlag(int flags) {
        this.flags = flags;
    }

    public void add(int flag) {
        checkFlag(flag);
        this.flags |= flag;
    }

    public void remove(int flag) {
        checkFlag(flag);
        this.flags &= ~flag;
    }

    public boolean has(int flag) {
        checkFlag(flag);
        return (this.flags & flag) == flag;
    }

    public void tip() {
        this.flags = ~this.flags;
    }

    private void checkFlag(int flag) {
        if (flag <= 0) {
            String msg = Integer.toBinaryString(flag) + "(" + flag + ")";
            throw new IllegalArgumentException(msg);
        }
    }

    public int getFlags() {
        return this.flags;
    }

}
