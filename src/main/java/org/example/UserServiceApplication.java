package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * Основной класс приложения User Service.
 * <p>Конфигурирует и запускает контекст Spring Boot.</p>
 */
@OpenAPIDefinition(
        info = @Info(
                title = "User Service API",
                version = "1.0",
                description = "API для управления пользователями",
                contact = @Contact(name = "Ваша команда", email = "team@example.com")
        ),
        servers = @Server(url = "/", description = "Локальный сервер")
)
@SpringBootApplication
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
