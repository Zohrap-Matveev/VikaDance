package am.matveev.dance.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer{

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Применяем CORS ко всем эндпоинтам
                .allowedOrigins("*")     // Разрешаем запросы с любого источника
                .allowedMethods("OPTION", "GET", "POST", "PUT", "DELETE", "HEAD", "PATCH")     // Разрешаем все HTTP методы
                .allowedHeaders("*");    // Разрешаем все заголовки
    }
}

