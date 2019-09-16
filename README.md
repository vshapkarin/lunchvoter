# Lunchvoter

*REST API системы голосования за рестораны*

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/daac9d71ccbe40158ab31f9c9551e9fc)](https://www.codacy.com?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=vshapkarin/lunchvoter&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/vshapkarin/lunchvoter.svg?branch=master)](https://travis-ci.org/vshapkarin/lunchvoter)

## Краткое описание
#### 2 типа пользователей:
* Пользователь
* Админ
#### Пользователь может:
* Смотреть список ресторанов
* Смотреть отдельные рестораны со списком блюд
* Голосовать за ресторан 1 раз в день (если уже проголосовал, поменять решение можно до 11:00)
* Изменить/удалить свой профиль
#### Админ может:
* Смотреть список пользователей
* Искать пользователей по id или email
* Создавать/изменять/удалять пользователей
* Создавать/изменять/удалять рестораны
* Смотреть рестораны с меню по id и дате
* Изменять меню ресторанов
## Запуск 
Приложение доступно по адресу [https://lunchvoter.herokuapp.com](https://lunchvoter.herokuapp.com)  
(Приложение работает по серверному времени UTC+0)

Для локального тестирования (необходимо JRE 11+ и Maven в системной переменной Path):
1. Клонируйте репозиторий
2. В папке проекта запустите в терминале `lunchvoter.bat`
3. Сервер доступен по адресу [http://localhost:8080](http://localhost:8080)
## Аутентификация
Каждый запрос к API (за исключением запроса на регистрацию) требует базовой авторизации (Basic authorization).
Пары логин/пароль для доступа к тестовым данным:
* Пользователи: `johnycowboy@email.com:password`, `builderbob@email.com:password`, `chrstn@email.com:password`
* Админ: `admin@email.com:admin`
## Конечные точки и примеры запросов
Доступны на [Postman](https://documenter.getpostman.com/view/8691721/SVmtzfk3)
## Используемые технологии
*	База данных: HSQLDB
*	ORM: JPA (Hibernate)
*	Репозиторий: Spring Data JPA
*	Контроллер: Spring Web MVC
*	Контейнер сервлетов: Tomcat
*	Аутентификация/Авторизация: Spring Security
*	Тесты: JUnit
*	Логирование: SLF4J/logback
*	Сборка проекта: Maven