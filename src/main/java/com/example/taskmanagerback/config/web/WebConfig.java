package com.example.taskmanagerback.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Разрешаем доступ ко всем ресурсам для всех клиентов (*)
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // разрешённые адреса происхождения запросов
                .allowedMethods("*");                     // разрешенные HTTP-методы (GET, POST, PUT, DELETE и др.)
    }
}
