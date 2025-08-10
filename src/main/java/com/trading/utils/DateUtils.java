package com.trading.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 09-08-2025 18:09
 */
public class DateUtils {
    public static ZoneId getDefaultZoneId() {
        return ZoneId.of("UTC");
    }

    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now(getDefaultZoneId());
    }
}
