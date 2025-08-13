package com.acu.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * Configuration class for internationalization (i18n)
 * 
 * This class demonstrates:
 * - LocaleResolver configuration for session-based locale management
 * - LocaleChangeInterceptor for handling locale changes via URL parameters
 * - WebMvcConfigurer for registering interceptors
 */
@Configuration
public class LocaleConfig implements WebMvcConfigurer {

    /**
     * Configure the locale resolver to use session-based locale management
     * Default locale is set to English (US)
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(Locale.US);
        return resolver;
    }

    /**
     * Configure the locale change interceptor
     * This allows changing the locale via URL parameter 'lang'
     * Example: ?lang=en_US, ?lang=es_ES, ?lang=fr_FR
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }

    /**
     * Register the locale change interceptor
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
