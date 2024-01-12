package org.misty.utils.task;

public enum TaskGiveResult {

    /**
     * 任務已送出
     */
    SENTED,

    ;

    public boolean isSented() {
        return this == SENTED;
    }



}
