package com.osj.dashboard;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class ResourceConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/").setCachePeriod(60 * 60 * 24 * 365);
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/").setCachePeriod(60 * 60 * 24 * 365);
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/").setCachePeriod(60 * 60 * 24 * 365);
        registry.addResourceHandler("/libs/**").addResourceLocations("classpath:/static/libs/").setCachePeriod(60 * 60 * 24 * 365);
        registry.addResourceHandler("/scss/**").addResourceLocations("classpath:/static/scss/").setCachePeriod(60 * 60 * 24 * 365);
    }
}
