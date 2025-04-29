package com.pokerogue.helper.global.config;

public class LanguageSetter {

    public static final String KOREAN = "ko";
    private static final String ENGLISH = "en";
    public static final ThreadLocal<String> language = ThreadLocal.withInitial(() -> ENGLISH);

    private LanguageSetter() {}

    public static String getLanguage() {
        return language.get();
    }

    public static void setKorean() {
        language.set(KOREAN);
    }

    public static void setEnglish() {
        language.set(ENGLISH);
    }

    public static boolean isKorean() {
        return language.get().equals(KOREAN);
    }

    public static void clear() {
        language.remove();
    }
}
