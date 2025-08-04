package com.pokerogue.helper.global.config;

public class LocaleContextHolder {

    private static final String DEFAULT_LANGUAGE = "en";
    private static final ThreadLocal<String> localeContext = new ThreadLocal<>();

    private LocaleContextHolder() {}

    public static void setLocale(String locale) {
        String language = locale.toLowerCase().substring(0, 2);
        localeContext.set(language);
    }

    public static void setDefault() {
        localeContext.set(DEFAULT_LANGUAGE);
    }

    public static String getCurrentLocale() {
        String locale = localeContext.get();
        return locale != null ? locale : DEFAULT_LANGUAGE;
    }

    public static boolean isDefault() {
        return localeContext.get().equals(DEFAULT_LANGUAGE);
    }

    public static void clear() {
        localeContext.remove();
    }
}
