package com.aderugy.rugyengine3d.core.log;

public enum LogPriority {
    ERROR(3),
    WARNING(2),
    DEBUG(1),
    INFO(0);

    private int priority;

    LogPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return this.priority;
    }
}
