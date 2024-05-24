package org.example;

import java.util.regex.Pattern;

public class Utils {
    private static final Pattern RUSSIAN_PATTERN = Pattern.compile("[ЁёА-я]");
    public static boolean containsRussianCharacters(String text) {
        return RUSSIAN_PATTERN.matcher(text).find();
    }
}
