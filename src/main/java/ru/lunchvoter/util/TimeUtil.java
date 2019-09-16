package ru.lunchvoter.util;

import java.time.LocalDate;
import java.time.LocalTime;

public class TimeUtil {

    public static LocalTime CHANGE_MIND_TIME = LocalTime.of(11, 0);

    public static LocalDate getMenuDate() {
        return LocalDate.now();
    }
}
