# Private File Sharing

![Private File Sharing](https://github.com/vnikolaenko-dev/PrivateFileSharing/blob/main/screenshots/files.png)

Надежная, безопасная и масштабируемая платформа для приватного обмена файлами, разработанная для организаций, которым требуется детализированный контроль доступа, управление пользователями и бесшовные уведомления. Построена на основе микросервисной архитектуры для обеспечения высокой доступности, легкого масштабирования и удобной поддержки кода. Будь то работа с конфиденциальными документами или совместными файлами, приложение предоставляет ролевой доступ, аутентификацию через JWT и событийно-ориентированные уведомления.

## Основные возможности

- **Управление пользователями**: Регистрация, аутентификация и управление пользователями с ролями (admin, pro, default). Администраторы могут создавать, обновлять или удалять пользователей.
- **Управление файлами**: Загрузка, получение, изменение уровня доступа и удаление файлов (поддержка PDF, Word и т.д.). Доступ регулируется уровнями (default, pro, admin).
- **Безопасная аутентификация**: Логин на основе JWT с хешированием паролей и ролевым управлением доступом (RBAC).
- **Уведомления**: Уведомления пользователей в реальном времени через Kafka для событий, таких как передача файлов или обновления.
- **API-шлюз**: Централизованная точка входа для всех запросов, обеспечивающая маршрутизацию, CORS и балансировку нагрузки.
- **Масштабируемость**: Микросервисная архитектура позволяет независимо масштабировать сервисы для пользователей и файлов.

## Технологический стек

Проект использует современные технологии промышленного уровня для обеспечения производительности, безопасности и удобства для разработчиков. Основной акцент сделан на экосистему Java для надежности бэкенда и асинхронный обмен сообщениями для слабосвязанных сервисов.

### Бэкенд
<a href="https://www.java.com" target="_blank"><img src="https://img.shields.io/badge/Java-17%2B-ED8B00?logo=java" alt="Java"></a>
<a href="https://spring.io/projects/spring-boot" target="_blank"><img src="https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?logo=spring-boot" alt="Spring Boot"></a>
<a href="https://spring.io/projects/spring-security" target="_blank"><img src="https://img.shields.io/badge/Spring%20Security-Latest-6DB33F?logo=spring" alt="Spring Security"></a>
<a href="https://spring.io/projects/spring-data-jpa" target="_blank"><img src="https://img.shields.io/badge/Spring%20Data%20JPA-Latest-6DB33F?logo=spring" alt="Spring Data JPA"></a>
<a href="https://spring.io/projects/spring-cloud-gateway" target="_blank"><img src="https://img.shields.io/badge/Spring%20Cloud%20Gateway-Latest-6DB33F?logo=spring" alt="Spring Cloud Gateway"></a>
<a href="https://spring.io/projects/spring-kafka" target="_blank"><img src="https://img.shields.io/badge/Spring%20Kafka-Latest-6DB33F?logo=spring" alt="Spring Kafka"></a>
<a href="https://kafka.apache.org" target="_blank"><img src="https://img.shields.io/badge/Apache%20Kafka-Latest-231F20?logo=apache-kafka" alt="Apache Kafka"></a>
<a href="https://restfulapi.net" target="_blank"><img src="https://img.shields.io/badge/RESTful%20API-Latest-005571?logo=rest" alt="RESTful API"></a>

### База данных
<a href="https://www.postgresql.org" target="_blank"><img src="https://img.shields.io/badge/PostgreSQL-Latest-336791?logo=postgresql" alt="PostgreSQL"></a>

### Фронтенд
<a href="https://html.spec.whatwg.org" target="_blank"><img src="https://img.shields.io/badge/HTML5-Latest-E34F26?logo=html5" alt="HTML5"></a>
<a href="https://www.w3.org/Style/CSS" target="_blank"><img src="https://img.shields.io/badge/CSS3-Latest-1572B6?logo=css3" alt="CSS3"></a>
<a href="https://tailwindcss.com" target="_blank"><img src="https://img.shields.io/badge/Tailwind%20CSS-Latest-38B2AC?logo=tailwind-css" alt="Tailwind CSS"></a>
<a href="https://developer.mozilla.org/docs/Web/JavaScript" target="_blank"><img src="https://img.shields.io/badge/JavaScript-Vanilla-F7DF1E?logo=javascript" alt="JavaScript"></a>

### Сборка и деплой
<a href="https://maven.apache.org" target="_blank"><img src="https://img.shields.io/badge/Maven-Latest-C71A36?logo=apache-maven" alt="Maven"></a>
<a href="https://www.docker.com" target="_blank"><img src="https://img.shields.io/badge/Docker-Latest-2496ED?logo=docker" alt="Docker"></a>
<a href="https://projectlombok.org" target="_blank"><img src="https://img.shields.io/badge/Lombok-Latest-FF6F61?logo=lombok" alt="Lombok"></a>

## Архитектура проекта

Проект построен на микросервисной архитектуре для модульности и изоляции ошибок. Фронтенд взаимодействует только с API-шлюзом, который маршрутизирует запросы к специализированным сервисам. Операции, связанные с пользователями, могут инициировать уведомления через Kafka для разделения ответственности.

### Поток обработки запросов
1. Фронтенд отправляет HTTP-запросы (например, логин, загрузка файла) на API-шлюз.
2. API-шлюз аутентифицирует (через JWT) и перенаправляет запрос к соответствующему микросервису.
3. Микросервис пользователей обрабатывает аутентификацию, CRUD-операции и отправляет события в Kafka для уведомлений.
4. Микросервис файлов управляет операциями с файлами и их хранением.
5. Сервис уведомлений потребляет Kafka-топики для отправки email или уведомлений в приложении.

### Схема архитектуры

```mermaid
graph TD
    A[Фронтенд] -->|HTTP-запросы| B[API-шлюз]
    B -->|Маршруты /users/*| C[Микросервис пользователей]
    B -->|Маршруты /files/*| D[Микросервис файлов]
    C -->|Отправляет события| E[Kafka-брокер]
    E -->|Потребляет топики| F[Сервис уведомлений]
    C <-->|Запросы к БД| G[База данных]
    D <-->|Запросы к БД| G
```

Эта схема показывает поток запросов и событийно-ориентированное взаимодействие. API-шлюз выступает единой точкой входа, повышая безопасность и упрощая интеграцию с клиентом.

## Структура проекта

Репозиторий организован как мультимодульный Maven-проект для удобного управления микросервисами:

- **api-gateway/**: Содержит конфигурацию Spring Cloud Gateway, правила маршрутизации и фильтры безопасности.
  - `src/main/java`: Контроллеры/фильтры для обработки запросов.
  - `application.yml`: Конфигурация маршрутов (например, к сервисам пользователей и файлов).

## Установка и запуск

1. **Клонируйте репозиторий**:
   ```
   git clone https://github.com/vnikolaenko-dev/PrivateFileSharing.git
   cd PrivateFileSharing
   git checkout api-gateway
   ```

2. **Установите зависимости**:
   ```
   mvn clean install
   ```

3. **Настройте окружение**:
   - Обновите файлы `application.properties` или `.yml` в каждом модуле (например, URL базы данных, серверы Kafka).
   - Пример для микросервиса пользователей:
     ```
     spring.datasource.url=jdbc:postgresql://localhost:5432/privatefiles
     spring.kafka.bootstrap-servers=localhost:9092
     ```

4. **Запустите сервисы**:
   - Запустите Kafka/Zookeeper (например, через Docker: `docker run -p 9092:9092 apache/kafka`).
   - Запустите каждый микросервис: `mvn spring-boot:run` в соответствующих директориях.
   - Или используйте Docker Compose для всех сервисов.

5. **Доступ к приложению**:
   - Фронтенд: Откройте `index.html` или запустите локальный сервер.
   - API: Тестируйте конечные точки через Postman (например, POST /auth/login).
