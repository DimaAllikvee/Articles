# Articles

Articles — это веб-приложение на базе Spring Boot, позволяющее пользователям:

Создавать и просматривать статьи.

Управлять тегами.

Управлять пользователями.

Просматривать статьи как гость (только чтение, без добавления).

## Структура проекта

![image](https://github.com/user-attachments/assets/292b7837-fed4-466a-ba69-028d9227ea44)

config: хранит конфигурационные классы (безопасность, инициализация БД).

Controller: контроллеры, обрабатывающие HTTP-запросы.

entity: классы-сущности JPA (таблицы в БД).

interfaces: абстрактные слои сервисов, описывающие бизнес-логику.

repository: взаимодействия с базой данных.

ServiceImpl: реализации интерфейсов сервисов.

## Как запустить проект

1. **Склонировать репозиторий**:
   ```bash
   git clone https://github.com/DimaAllikvee/Articles
   ```
2. **Импортировать проект** в IDE (IntelliJ IDEA / Eclipse / VS Code):


---

### 3. **Настроить БД**:

#### Использование MariaDB

Настройте файл `application.properties` следующим образом:

```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/db_articles
spring.datasource.username=articles
spring.datasource.password=articles

spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.devtools.restart.enabled=true
logging.level.org.springframework.security=DEBUG
```

#### Использование H2

Настройте файл `application.properties` следующим образом:

```properties
spring.application.name=Articles

spring.datasource.url=jdbc:h2:./data/articles
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=articles
spring.datasource.password=articles
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update

```


Выполните следующие SQL-запросы для создания базы данных и пользователя (если они ещё не существуют):

```sql
-- Создаём базу данных (если она ещё не существует)
CREATE DATABASE IF NOT EXISTS db_articles 
    CHARACTER SET utf8mb4 
    COLLATE utf8mb4_unicode_ci;

-- Создаём пользователя с именем 'articles' и паролем 'articles'
CREATE USER IF NOT EXISTS 'articles'@'localhost' IDENTIFIED BY 'articles';

-- Назначаем все привилегии на базу данных db_articles пользователю 'articles'
GRANT ALL PRIVILEGES ON db_articles.* TO 'articles'@'localhost';

-- Применяем изменения привилегий
FLUSH PRIVILEGES;
```

#### Импорт базы данных MySQL(Mariadb)

Если вы хотите импортировать готовую базу данных из дампа, выполните следующую команду в терминале (после установки MySQL):

```bash
mysql -u root -p clothing_store < "C:\dump.sql"
```

**Пояснение:**

- `-u root` — указывает имя пользователя MySQL (в данном случае `root`).
- `-p` — заставляет MySQL запросить пароль.
- `clothing_store` — имя базы данных, в которую будет импортирован дамп.
- `"C:\dump.sql"` — путь к файлу дампа базы данных.

---

4. **Запустить**:
    - Через IDE (класс `ArticlesApplication.java` → Run).
    - Или в консоли:
      ```bash
      mvn spring-boot:run
      ```
## 5. Открыть в браузере

Перейдите по адресу:  
[http://localhost:8080/login](http://localhost:8080/login)

Если при запуске приложения появляется ошибка:
> **Web server failed to start. Port 8080 was already in use.**

это означает, что порт 8080 уже занят другим процессом. Ниже приведены инструкции по освобождению порта:

### Освобождение порта 8080

#### Для Windows

1. Откройте командную строку от имени администратора.
2. Выполните следующую команду, чтобы узнать PID процесса, занимающего порт 8080:
   ```cmd
   netstat -ano | findstr :8080
   ```
3. Определите PID процесса и остановите его командой:
   ```cmd
   taskkill /PID <PID> /F
   ```
   *Замените `<PID>` на фактический номер процесса.*

#### Для Linux/Mac

1. Откройте терминал и выполните команду:
   ```bash
   sudo lsof -i :8080
   ```
2. Найдите PID процесса и завершите его командой:
   ```bash
   kill -9 <PID>
   ```
   *Замените `<PID>` на фактический номер процесса.*



## База данных (ER-диаграмма)

![ER диаграмма](https://github.com/user-attachments/assets/f6d8b4c5-a7cc-44a0-9ac7-19c68d6ac98f)

## Функциональность


- Показать список всех статей.
- Показать статью по ID.
- Добавить новую статью.
- Обновить статью.
- Удалить статью.
- Показать статьи по автору.
- Показать статьи по тегу.
- Поиск статей по заголовку или содержимому.


- Показать все теги.
- Добавить тег.
- Удалить тег.


- Показать всех пользователей.
- Показать пользователя по ID.
- Добавить нового пользователя.
- Обновить данные пользователя.
- Удалить пользователя.

---

## Роли пользователей


- **GUEST**:
    - Может только просматривать статьи и их детали.
    - Видит только публичные страницы и кнопку "Login".

- **USER (ROLE_USER)**:
    - Может создавать и редактировать свои статьи.
    - Может просматривать статьи и теги.


- **ADMIN (ROLE_ADMIN)**:
    - Имеет полный доступ ко всему: управление пользователями, статьями и тегами.
    - Может редактировать и удалять любые статьи, добавлять/удалять теги, управлять пользователями.

---
