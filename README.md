# gRPC Demo Project

## Data analyzer gRPC microservice
gRPC сервер.
- Принимает данные по gRPC с датчиков от сервиса "Data generator gRPC microservice" и сохраняет их в базу данных Postgresql.
- Читает данные датчиков из базы данных Postgresql и отправляет их по gRPC в ответ запросившему сервису ("Data store gRPC microservice"). 

## Data generator gRPC microservice
gRPC клиент.
- Принимает новые данные по REST и отправляет их по gRPC в сервис "Data analyzer gRPC microservice".

## Data store gRPC microservice
gRPC клиент.
- По расписанию (1 раз в 10 сек) запрашивает по gRPC новые данные у "Data analyzer gRPC microservice" и сохраняет их в базу данных Redis.
- Предоставляет возможность запроса аналитики с датчиков по REST (из базы данных Redis).
