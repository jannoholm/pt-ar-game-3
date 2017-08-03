package com.playtech.ptargame3.common.task.state;

import com.playtech.ptargame3.common.task.TaskState;


public enum TenStepState implements TaskState {
    INITIAL,
    STEP1,
    STEP2,
    STEP3,
    STEP4,
    STEP5,
    STEP6,
    STEP7,
    STEP8,
    STEP9,
    FINAL;

    @Override
    public TaskState next() {
        TaskState next = null;
        if ( ordinal()+1 < values().length ) {
            next = values()[ordinal()+1];
        }
        return next;
    }

    @Override
    public boolean isFinal() {
        return ordinal()+1 == values().length;
    }
}
