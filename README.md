# 🎓 Система внутреннего рейтинга преподавателей

## 📘 Описание проекта

Это внутренняя система, позволяющая студентам **оценивать преподавателей**, а преподавателям и модераторам — **анализировать и модерировать отзывы**.  
Каждый пользователь входит в систему по логину/паролю.

---

## 👥 Типы пользователей и их роли

| Роль        | Назначение              | Доступные действия |
|-------------|--------------------------|---------------------|
| `STUDENT`   | Студент                 | - Авторизация; - Просмотр списка преподавателей; - Отправка 1 отзыва в семестр |
| `TEACHER`   | Преподаватель           | - Авторизация; - Просмотр отзывов о себе; - Просмотр средней оценки по критериям |
| `MODERATOR` | Модератор/Администратор | - Авторизация; - Просмотр всех отзывов; - Скрытие отзывов; - Управление критериями |

---

## 🧩 Архитектура проекта

### Технологии

- **Backend**: Java Spring Boot  Версия Java 21, Версия Java 21, Spring Boot 3.2.3
- **База данных**: PostgreSQL (или H2)  
- **Frontend**: React + TypeScript  
- **Авторизация**: Spring Security + JWT  
- **Документация**: Swagger

---

## 🗃️ Структура БД

### Таблица `users`
| Поле      | Тип    |
|-----------|--------|
| id        | UUID   |
| username  | String |
| email     | String |
| password  | String |
| full_name | String |
| role      | Enum (`STUDENT`, `TEACHER`, `MODERATOR`) |

### Таблица `teachers`
| Поле       | Тип    |
|------------|--------|
| id         | UUID   |
| full_name  | String |
| department | String |
| user_id    | UUID (FK → users) |

### Таблица `criteria`
| Поле | Тип   |
|------|--------|
| id   | UUID  |
| name | String |

### Таблица `reviews`
| Поле        | Тип     |
|-------------|----------|
| id          | UUID     |
| teacher_id  | UUID     |
| student_id  | UUID     |
| semester    | String   |
| scores      | JSON     | `{ "criteria_id": int, ... }`
| comment     | String   |
| is_hidden   | Boolean  |

> **Важно**: `student_id` используется только для ограничения "один отзыв на преподавателя в семестр". В интерфейсе и аналитике отзыв считается анонимным.

---

## 🔐 Авторизация и безопасность

- Все пользователи проходят логин по `username/password`.
- Получают JWT токен:  
  `Authorization: Bearer <token>`
- Spring Security ограничения:

```java
.antMatchers("/auth/**").permitAll()
.antMatchers("/reviews/**").hasRole("STUDENT")
.antMatchers("/teacher/**").hasRole("TEACHER")
.antMatchers("/moderation/**").hasRole("MODERATOR")
```

---

## 🧪 Бизнес-правила

- Один студент = один отзыв на одного преподавателя в семестр.
- Преподаватель не видит, кто оставил отзыв.
- Модератор может скрыть отзыв — `is_hidden = true`.

---

## 🔌 REST API

### 🔐 Auth
- `POST /auth/register` — регистрация пользователя  
- `POST /auth/login` — вход (JWT)

### 👨‍🎓 Student
- `GET /teachers` — список преподавателей  
- `GET /criteria` — список критериев  
- `POST /reviews` — отправка отзыва  

### 👨‍🏫 Teacher
- `GET /teacher/reviews` — отзывы о себе  
- `GET /teacher/averages` — средняя оценка  

### 🛡️ Moderator
- `GET /moderation/reviews` — все отзывы  
- `PATCH /moderation/reviews/{id}/hide` — скрыть отзыв  
- `POST /criteria` — добавить критерий  
- `DELETE /criteria/{id}` — удалить критерий  

---
## 🏗️ Структура проекта

Проект построен на Spring Boot с использованием многослойной архитектуры:
```
📁 com.university.teacherreviewsystem/
├── 📁 config/          # Конфигурационные файлы
├── 📁 controller/      # REST контроллеры (API endpoints)
├── 📁 dto/            # Data Transfer Objects
├── 📁 model/          # JPA Entity классы
├── 📁 repository/     # Spring Data JPA репозитории
├── 📁 service/        # Бизнес-логика и сервисы
├── 📁 util/           # Вспомогательные утилиты
└── TeacherReviewSystemApplication.java # Точка входа
```
---
## 🔄 Поток выполнения системы

1. Запуск приложения:
```
TeacherReviewSystemApplication.main()
    ↓
Spring Boot автоматически:
- Загружает конфигурации (SecurityConfig, OpenApiConfig)
- Инициализирует JPA репозитории  
- Запускает DataSeeder (создает тестовые данные)
- Регистрирует контроллеры и сервисы
- Запускает веб-сервер
```
2. Регистрация пользователя:
```
POST /auth/register
    ↓
AuthController.register()
    ↓
1. Валидация данных
2. Хеширование пароля (BCrypt)
3. Сохранение User в БД
4. Если TEACHER - создание Teacher
    ↓
Ответ: "User registered successfully"
```
3. Аутентификация:
```
POST /auth/login
    ↓
AuthController.login() 
    ↓
1. AuthenticationManager проверяет credentials
2. UserDetailsServiceImpl загружает пользователя
3. JwtService генерирует токен с ролью
    ↓
Ответ: {token, username, role}
```
4. Защищенный запрос:
```
Любой запрос с Authorization: Bearer {token}
    ↓
JwtAuthenticationFilter:
1. Извлекает токен из заголовка
2. Валидирует токен через JwtService
3. Загружает UserDetails
4. Устанавливает аутентификацию
    ↓
SecurityConfig проверяет права доступа
    ↓
Запрос передается в контроллер
```
5. Подача отзыва студентом:
```
POST /reviews (от STUDENT)
    ↓
JwtAuthenticationFilter → SecurityConfig → ReviewController
    ↓
1. Извлечение студента из Authentication
2. Проверка дублирования отзыва
3. Валидация критериев
4. Создание Review с JSON scores
5. Сохранение в БД
    ↓
Ответ: "Review submitted successfully"
```
6. Просмотр отзывов преподавателем:
```
GET /teacher/reviews (от TEACHER)
    ↓
TeacherController.getReviews()
    ↓
1. Поиск Teacher по текущему User
2. Получение всех видимых отзывов
3. ReviewUtils.mapCriteriaNames() - конвертация UUID в названия
4. Формирование ReviewResponse
    ↓
Ответ: List<ReviewResponse>
```
7. Модерация отзыва:
```
PATCH /moderation/reviews/{id}/hide (от MODERATOR)
    ↓
ModeratorController.hideReview()
    ↓
1. Поиск Review по ID
2. Установка hidden = true
3. Сохранение в БД
    ↓
Ответ: "Review hidden successfully"
```
---
## 🍀 Swagger UI

Расположен по следующей сслыке:
- http://localhost:8080/swagger-ui/index.html
Работает только при запущенном сервере
---

## 💡 Возможности расширения

- Экспорт отчетов в PDF  
- Динамика рейтингов по семестрам (графики)  
- Email-уведомления преподавателям  
- Поддержка кафедр и факультетов
