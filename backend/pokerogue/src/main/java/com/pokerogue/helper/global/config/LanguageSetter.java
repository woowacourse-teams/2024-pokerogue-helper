package com.pokerogue.helper.global.config;

public class LanguageSetter {

    private static final String ENGLISH = "en";
    public static final ThreadLocal<String> language = ThreadLocal.withInitial(() -> ENGLISH);

    private LanguageSetter() {}

    public static String getLanguage() {
        return language.get();
    }
}
