# Private File Sharing

**Private File Sharing** — это мощное и безопасное веб-приложение для управления файлами с авторизацией и гибким контролем доступа. Администраторы могут загружать файлы (PDF, Word и т.д.), назначать уровни доступа (`default`, `pro`, `admin`), а также управлять пользователями (создание, редактирование, удаление) с различными ролями (`admin`, `default`, `pro`). Сервис идеально подходит для организаций, которым нужна надёжная система хранения и разграничения доступа к документам.

## Технологии
- **Backend**: Java Spring Boot, Java Spring Security (для авторизации и JWT), Java Spring Data (для работы с базой данных).
- **Frontend**: HTML, CSS (Tailwind CSS), JavaScript (чистый, без фреймворков).
- **API**: RESTful API с поддержкой CORS.
- **Безопасность**: JWT-токены, хэширование паролей, ролевая модель доступа.

![Файлы](https://github.com/vnikolaenko-dev/PrivateFileSharing/raw/main/screenshots/files.jpg)
![Пользователи](https://github.com/vnikolaenko-dev/PrivateFileSharing/raw/main/screenshots/users.jpg)

## API Endpoints

### Авторизация
- `POST /auth/login`
  - Тело: `{ "login": "string", "password": "string" }`
  - Ответ: `{ "token": "string", "role": "string" }`
  - Коды: 200, 401

### Управление файлами
- `POST /files/upload`
  - Заголовки: `Authorization: Bearer <token>`
  - Тело: FormData (`file`, `accessLevel`)
  - Ответ: `{ "id": "string", "name": "string", "accessLevel": "string", "uploadDate": "string" }`
  - Коды: 201, 401, 400
- `GET /files`
  - Заголовки: `Authorization: Bearer <token>`
  - Ответ: `[{ "id": "string", "name": "string", "accessLevel": "string", "uploadDate": "string" }]`
  - Коды: 200, 401
- `GET /files/:id`
  - Заголовки: `Authorization: Bearer <token>`
  - Ответ: Файл (поток данных, `Content-Disposition: attachment`)
  - Коды: 200, 401, 404
- `DELETE /files/:id`
  - Заголовки: `Authorization: Bearer <token>`
  - Ответ: Пустое тело
  - Коды: 204, 401, 404
- `PATCH /files/:id`
  - Заголовки: `Authorization: Bearer <token>`
  - Тело: `{ "accessLevel": "string" }`
  - Ответ: `{ "id": "string", "name": "string", "accessLevel": "string", "uploadDate": "string" }`
  - Коды: 200, 401, 404

### Управление пользователями (только для `admin`)
- `GET /users`
  - Заголовки: `Authorization: Bearer <token>`
  - Ответ: `[{ "id": "string", "login": "string", "accessLevel": "string" }]`
  - Коды: 200, 401, 403
- `POST /users`
  - Заголовки: `Authorization: Bearer <token>`
  - Тело: `{ "login": "string", "password": "string", "accessLevel": "string" }`
  - Ответ: `{ "id": "string", "login": "string", "accessLevel": "string" }`
  - Коды: 201, 401, 403, 400
- `PATCH /users/:id`
  - Заголовки: `Authorization: Bearer <token>`
  - Тело: `{ "password": "string", "accessLevel": "string" }` (оба поля опциональны)
  - Ответ: `{ "id": "string", "login": "string", "accessLevel": "string" }`
  - Коды: 200, 401, 403, 404
- `DELETE /users/:id`
  - Заголовки: `Authorization: Bearer <token>`
  - Ответ: Пустое тело
  - Коды: 204, 401, 403, 404
