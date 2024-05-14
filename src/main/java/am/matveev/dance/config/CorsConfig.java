package am.matveev.dance.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer{

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Применяем CORS ко всем эндпоинтам
                .allowedOrigins("*")     // Разрешаем запросы с любого источника
                .allowedMethods("*")     // Разрешаем все HTTP методы
                .allowedHeaders("*");    // Разрешаем все заголовки
    }
}

