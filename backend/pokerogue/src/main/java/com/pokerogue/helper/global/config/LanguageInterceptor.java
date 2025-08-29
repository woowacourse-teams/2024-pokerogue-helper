package com.pokerogue.helper.global.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LanguageInterceptor implements HandlerInterceptor {

    private static final String LANGUAGE_HEADER_NAME = "Accept-Language";

    private final LanguageChecker languageChecker;

    public LanguageInterceptor(LanguageChecker languageChecker) {
        this.languageChecker = languageChecker;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String acceptLanguage = request.getHeader(LANGUAGE_HEADER_NAME);
        if (languageChecker.existsByLanguage(acceptLanguage)) {
            LocaleContextHolder.setLocale(acceptLanguage);
            return true;
        }
        LocaleContextHolder.setDefault();
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        LocaleContextHolder.clear();
    }
}
