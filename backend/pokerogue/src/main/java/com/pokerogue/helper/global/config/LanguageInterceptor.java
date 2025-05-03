package com.pokerogue.helper.global.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class LanguageInterceptor implements HandlerInterceptor {

    private static final String LANGUAGE_HEADER_NAME = "Accept-Language";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String acceptLanguage = request.getHeader(LANGUAGE_HEADER_NAME);
        if (acceptLanguage != null && acceptLanguage.toLowerCase().startsWith(LanguageSetter.KOREAN)) {
            LanguageSetter.setKorean();
        } else {
            LanguageSetter.setEnglish();
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        LanguageSetter.clear();
    }
}
