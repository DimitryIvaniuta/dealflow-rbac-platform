package com.github.dimitryivaniuta.dealflow.service;

import java.util.Locale;

public final class TextNormalizer {

    private TextNormalizer() {}

    public static String normalize(String value) {
        if (value == null) {
            return null;
        }
        return value.trim().toLowerCase(Locale.ROOT);
    }
}
