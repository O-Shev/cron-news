package com.leshkins.cronnews.server.model.enums;

import java.sql.Timestamp;
import java.time.LocalTime;

public enum DayPart {
    MORNING(LocalTime.of(0, 0), LocalTime.of(10, 0)),
    MIDDAY(LocalTime.of(10, 0), LocalTime.of(18, 0)),
    EVENING(LocalTime.of(18, 0), LocalTime.of(23, 59, 59));

    private final LocalTime start;
    private final LocalTime end;

    DayPart(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }

    public LocalTime start() {
        return start;
    }

    public LocalTime end() {
        return end;
    }
}
