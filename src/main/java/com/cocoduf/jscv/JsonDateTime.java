package com.cocoduf.jscv;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by cocoduf on 17-06-03.
 */
public class JsonDateTime {

    private static Set<DateTimeFormatter> formatters = new HashSet<>();

    private static String[] rawFormats = new String[] {
            "yyyy-MM-dd",
            "yyyy-MM-dd HH:mm:ss"
    };

    private LocalDate date;

    JsonDateTime(String textDate) {
        if (formatters.isEmpty()) {
            generateFormatters();
        }
        parseTextDate(textDate);
    }

    public void addFormat(String format) {
        formatters.add(DateTimeFormatter.ofPattern(format));
    }

    public boolean isPriorTo(JsonDateTime target) {
        return date.isBefore(target.getDate());
    }

    public boolean isSubsequentTo(JsonDateTime target) {
        return date.isAfter(target.getDate());
    }

    public LocalDate getDate() {
        return this.date;
    }

    /******************************************************************************************************************/

    private void generateFormatters() {
        for (String format : rawFormats) {
            addFormat(format);
        }
    }

    private void parseTextDate(String textDate) {
        for (DateTimeFormatter dtf : formatters) {
            try {
                this.date = LocalDate.parse(textDate, dtf);
            } catch (Exception e) {}
        }
        if (date == null) {
            throw new IllegalArgumentException("Invalid date : " + textDate);
        }
    }
}