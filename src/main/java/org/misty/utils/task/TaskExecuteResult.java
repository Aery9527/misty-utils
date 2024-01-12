package org.misty.utils.task;

public enum TaskExecuteResult {

    /**
     * 任務已送出執行
     */
    EXECUTED,

    /**
     * 任務已被忽略, 因為先前有發生過error, 且該error返回{@link TaskErrorPolicy#INTERRUPT}表示中斷接下來所有任務
     */
    INTERRUPTED,

    /**
     * 任務已被忽略, 因為已關閉(意味著相關物件的{@link AutoCloseable#close()})已被調用
     */
    CLOSED;

    public boolean isExecuted() {
        return this == EXECUTED;
    }

    public boolean isInterrupt() {
        return this == INTERRUPTED;
    }

    public boolean isClosed() {
        return this == CLOSED;
    }

}
