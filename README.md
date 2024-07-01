# gRPC Demo Project

## Data analyzer gRPC microservice
gRPC сервер.
- Принимает данные датчиков по gRPC от сервиса "Data generator gRPC microservice" и сохраняет их в базу данных Postgresql.
- Читает данные датчиков из базы данных Postgresql и отправляет их по gRPC в ответ запросившему сервису ("Data store gRPC microservice"). 

## Data generator gRPC microservice
gRPC клиент.
- Принимает новые данные датчиков по REST и отправляет их по gRPC в сервис "Data analyzer gRPC microservice".

## Data store gRPC microservice
gRPC клиент.
- По расписанию (1 раз в 10 сек) запрашивает по gRPC ранее не забранные данные датчиков у "Data analyzer gRPC microservice" и сохраняет их в базу данных Redis.
- Предоставляет возможность запроса аналитики данных с датчиков по REST (из базы данных Redis).
