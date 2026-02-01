# Auth Service

Сервис аутентификации и авторизации для внутренних микросервисов.

## Возможности
- Регистрация и логин пользователей
- Stateless-аутентификация на основе JWT
- Ролевая модель доступа (USER / ADMIN)
- Админские эндпоинты для управления ролями

## Технологии
Java 17, Spring Boot, Spring Security, JWT, PostgreSQL, Flyway

## Запуск
```bash
export JWT_SECRET="your-jwt-secret"
docker compose up -d
mvn spring-boot:run
