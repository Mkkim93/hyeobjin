package com.hyeobjin.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/files/item/**")
                .addResourceLocations("classpath:/static/files/item/");

        registry.addResourceHandler("/files/board/**")
                .addResourceLocations("classpath:/static/files/board/");
    }
}
