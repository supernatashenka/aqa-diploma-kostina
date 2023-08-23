# Дипломный проект по профессии «Тестировщик»
## Документация
* [Текст задания](https://github.com/netology-code/qa-diploma)
* [План автоматизации](https://github.com/supernatashenka/aqa-diploma-kostina/blob/main/Documents/Plan.md)
* [Отчёт о проведённом тестировании]()
* [Отчёт о проведённой автоматизации]()

## Перечень необходимого ПО 
1. IntelliJ IDEA 2022.3.2 (Community Edition)
2. Chrome Версия 116.0.5845.111 (64 бит)
3. Docker Desktop 4.22.0 (117440)
4. DBeaver 23.1.1.202306251800

## Инструкция для запуска автотестов
1. Клонировать проект: `https://github.com/supernatashenka/qa-diploma-kostina`
2. Открыть проект в IntelliJ IDEA
3. Запустить Docker Desktop
4. Выполнить команду `docker-compose up` в терминале IntelliJ IDEA  


### Подключение SUT к MySQL
1. В терминале 2 запустить приложение: ` java -jar artifacts/aqa-shop.jar --spring.profiles.active=mysql `
2. Проверить запуск приложение в браузере Chrome:`http://localhost:8080`
3. В терминале 3 запустить тесты: `./gradlew clean test -DdbUrl=jdbc:mysql://localhost:3306/mysql` 
4. Создать отчёт Allure и открыть в браузере `.\gradlew allureServe`
5. Закрыть отчёт в терминале 3: `CTRL + C` -> `y` -> `Enter`
6. Остановить приложение в терминале 2: `CTRL + C`
7. Остановить контейнеры в терминале 4:`docker-compose down`

### Подключение SUT к PostgreSQL
1. В терминале 2 запустить приложение: `java -jar artifacts/aqa-shop.jar --spring.profiles.active=postgresql`
2. Проверить запуск приложение в браузере Chrome:`http://localhost:8080`
3. В терминале 3 запустить тесты: `./gradlew clean test -DdbUrl=jdbc:postgresql://localhost:5432/postgresql`
4. Создать отчёт Allure и открыть в браузере `.\gradlew allureServe`
5. Закрыть отчёт в терминале 3: `CTRL + C` -> `y` -> `Enter`
6. Остановить приложение в терминале 2: `CTRL + C`
7. Остановить контейнеры в терминале 4:`docker-compose down`
