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
