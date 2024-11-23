# Applikation
Die Applikation besteht aus 3 Services: Payment Service, Order Service und einem MessageBroker (RabbitMQ).\
Der Payment Service schreibt über einen Scheduler Nachrichten in die MessageQueue, welche vom Order Service konsumiert werden und anschließend wieder über den MessageBroker an den Payment Service übermittelt werden.\
In beiden Services (Payment & Order) wird die Abarbeitung der Messages über Konsolenausgaben dargestellt.

# Registry

Docker Login: `docker login --username <user> --password <token> ghcr.io`\
Build:
* cd to folder
* `docker build . -t ghcr.io/mcce2024/gr4-aktt1-payment`
* `docker push ghcr.io/mcce2024/gr4-aktt1-payment:latest`
* repeat for `ghcr.io/mcce2024/gr4-aktt1-order:latest`

# Kubernetes
* cd to configuration
* `kubectl apply -k .\overlays\DEV\rabbitmq`
* `kubectl apply -k .\overlays\DEV\orderservice`
* `kubectl apply -k .\overlays\DEV\paymentservice`
