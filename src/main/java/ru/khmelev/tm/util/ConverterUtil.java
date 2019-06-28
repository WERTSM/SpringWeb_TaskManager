package ru.khmelev.tm.util;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConverterUtil {

    @NotNull
    private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    @NotNull
    public static String convertDateFormat(@NotNull final Date date) {
        return DEFAULT_DATE_FORMAT.format(date);
    }
}