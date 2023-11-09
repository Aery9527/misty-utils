package org.misty.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.ObjLongConsumer;

public class TimeFormat {

    public static final long SECOND_MS = 1000;

    public static final long MINUTE_MS = 60 * SECOND_MS;

    public static final long HOUR_MS = 60 * MINUTE_MS;

    public static final long DAY_MS = 24 * HOUR_MS;

    public static final long MONTH_MS = 30 * DAY_MS;

    public static final long YEAR_MS = 12 * MONTH_MS;

    public static String auto(long time) {
        long year = time / YEAR_MS;
        time %= YEAR_MS;

        long month = time / MONTH_MS;
        time %= MONTH_MS;

        long day = time / DAY_MS;
        time %= DAY_MS;

        long hours = time / HOUR_MS;
        time %= HOUR_MS;

        long minutes = time / MINUTE_MS;
        time %= MINUTE_MS;

        long seconds = time / SECOND_MS;
        long milliseconds = time % SECOND_MS;

        List<Long> formatArg = new ArrayList<>();
        AtomicReference<String> format = new AtomicReference<>("");

        ObjLongConsumer<String> join = (unit, value) -> {
            if (value != 0) {
                formatArg.add(value);
                format.updateAndGet(s -> (s.isEmpty() ? "%d" : (s + ":%02d")) + unit);
            }
        };

        join.accept("Y", year);
        join.accept("M", month);
        join.accept("D", day);
        join.accept("h", hours);
        join.accept("m", minutes);

        if (seconds != 0 || milliseconds != 0) {
            formatArg.add(seconds);
            formatArg.add(milliseconds);
            format.updateAndGet(s -> (s.isEmpty() ? "%d" : (s + ":%02d")) + ".%03ds");
        } else if (format.get().isEmpty()) {
            format.set("0.000s");
        }

        return String.format(format.get(), formatArg.toArray());
    }

}
