package org.misty.utils.task;

public enum TaskWaitResult {
    /**
     * 所有任務都已完成
     */
    FINISH(true),

    /**
     * 等待時間到, 仍有任務未完成
     */
    TIMEOUT(false),

    /**
     * 已中斷
     */
    INTERRUPTED(true),

    /**
     * 已關閉
     */
    CLOSE(true);

    private final boolean stopWait;

    TaskWaitResult(boolean stopWait) {
        this.stopWait = stopWait;
    }

    public boolean isFinish() {
        return this == FINISH;
    }

    public boolean isTimeout() {
        return this == TIMEOUT;
    }

    public boolean isInterrupted() {
        return this == INTERRUPTED;
    }

    public boolean isClose() {
        return this == CLOSE;
    }

    public boolean stopWait() {
        return stopWait;
    }

}
