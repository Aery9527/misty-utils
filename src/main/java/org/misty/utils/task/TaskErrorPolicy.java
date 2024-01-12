package org.misty.utils.task;

public enum TaskErrorPolicy {
    /**
     * 繼續執行接下來的任務
     */
    CONTINUE,

    /**
     * 中斷執行接下來的任務
     */
    INTERRUPT;

    public boolean isContinue() {
        return this == CONTINUE;
    }

    public boolean isInterrupt() {
        return this == INTERRUPT;
    }

}
