package org.misty.utils;

import org.junit.jupiter.api.Test;
import org.misty.utils.combinatorics.Combinations;

class TimeFormatTest {

    @Test
    public void auto() {
        Combinations.of(TimeFormat.YEAR_MS, TimeFormat.MONTH_MS, TimeFormat.DAY_MS, TimeFormat.HOUR_MS, TimeFormat.MINUTE_MS, TimeFormat.SECOND_MS, 10)
                .foreach(2, true, (times, combination) -> {
                    long ms = combination.stream().mapToLong(Number::longValue).sum();
                    System.out.println(TimeFormat.auto(ms));
                });
        System.out.println(TimeFormat.auto(0));
    }

}
