## Дипломный проект по профессии «Тестировщик»

Задача данного проекта — автоматизация тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.
Перед нами веб-сервис, предлагающий купить тур двумя способами:

1. Оплата по дебетовой карте.
2. Уникальная технология: выдача кредита по данным банковской карты.

Само приложение не обрабатывает данные по картам, а пересылает их банковским сервисам:
* сервису платежей - Payment Gate;
* кредитному сервису - Credit Gate.

Приложение в собственной СУБД сохраняет информацию о том, успешно ли был совершён платёж и каким способом. Данные карт при этом не сохраняются.

### Подготовка к работе с проектом

Для подготовки работы с проектом понадобятся следующие инструменты:

* [Git](https://git-scm.com/downloads) - система контроля версий, понадобится для клонирования репозитория данного проекта.
* [Intellij IDEA](https://www.jetbrains.com/ru-ru/idea/) - Среда разработки, понадобится для открытия проекта и запуска автотестов.
* [Docker](https://www.docker.com/) - необходим для запуска базы данных, ознакомиться с инструкцией по установке можно пройдя по [ссылке](https://github.com/netology-code/aqa-homeworks/blob/master/docker/installation.md).
* [Google Chrome](https://www.google.com/intl/ru_ru/chrome/) - удобный и популярный браузер, понадобится для перехода по url адресу при тестировании веб-приложения.

### Запуск

1. Для того чтобы получить копию данного проекта для запуска на локальном ПК, необходимо создать клон [репозитория](https://github.com/M-Alex96/QA-Graduate-Work) с помощью команды `git clone`.
2. Открыть проект в Intellij IDEA.
3. Запустить Docker, в терминале Intellij IDEA вызвать команду `docker compose up` для взаимодействия с СУБД.
4. Для запуска сервиса (SUT) с указанием пути к базе данных использовать следующие команды:
* Для MySQL: `java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar`
* Для PostgreSQL: `java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar`
5. Выполнить запуск тестов указав путь к базе данных в командной строке:
* Для MySQL: `./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"`
* Для PostgreSQL: `./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"`
6. Для получения отчётов Allure выполнить команды: `./gradlew allurereport`, затем `./gradlew allureserve`.

### Документация

* [План автоматизации тестирования](https://github.com/M-Alex96/QA-Graduate-Work/blob/main/docs/plan.md)
* [Отчётные документы по итогам тестирования](https://github.com/M-Alex96/QA-Graduate-Work/blob/main/docs/Report.md)
* [Отчётные документы по итогам автоматизации](https://github.com/M-Alex96/QA-Graduate-Work/blob/main/docs/Summary.md)
