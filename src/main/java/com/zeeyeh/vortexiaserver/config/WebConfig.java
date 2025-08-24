package com.zeeyeh.vortexiaserver.config;

import com.zeeyeh.vortexiaserver.data.entity.UserStatus;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new Converter<LocalDateTime, Long>() {
            @Override
            public Long convert(LocalDateTime source) {
                return source.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            }
        });
    }
}
