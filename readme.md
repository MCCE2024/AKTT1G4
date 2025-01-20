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

# Security Aspects
* `automountServiceAccountToken: false`
* Durch diese Einstellung wird verhindert, dass Kubernetes automatisch ein Service-Account-Token in den Pod einbindet. Dieser Token authentifiziert den Pod für die Kubernetes-API. Dadurch bekommt der Pod Zugriff auf Cluster-Ressourcen. In unserem Fall interagieren die Pods nicht mit der API, wodurch diese Einstellung allein reicht. Würden die Pods mit der API interagieren, dann müsste man den Token noch manuell einbinden. Ein kompromitierter Pod mit Token erlaubt Zugriff auf die API, wodurch ein Angreifer z.B. andere Pods auslesen könnte (Stichwort reconnaissance). Im worst-case kann er sogar Konfigurationsänderungen vornehmen.
* `allowPrivilegeEscalation: false`
* Diese Einstellung betrifft Prozesse in einem Container. Sie sorgt dafür, dass Prozesse keine zusätzlichen Rechte erlangen können, als ihnen initial zugewiesen wurden. Ein plakativer Angriffsvektor für diese Einstellung ist eine Schwachstelle in einer Bibliothek die einem Angreifer Zugriff auf setuid gibt, wodurch er sich Root-Rechter verschaffen kann.
* `privileged: false`
* Diese Einstellung betrifft einen ganzen Container und entzieht ihm den Zugriff auf Host-Ressourcen wie Geräte, Kernelmodule und andere Systemressourcen. Ohne diese Einstellung könnte ein Angreife das Dateisystem mounten.
* `readOnlyRootFilesystem: true`
* Diese Einstellung sorgt dafür, dass das Dateisystem des Containers schreibgeschützt ist. Damit wird z.B. verhindert, dass Systemdateien modifiziert, oder Viren installiert werden.
* `runAsNonRoot: true`
* Diese Einstellung betrifft ebenfalls einen Container, und sorgt dafür, dass er nicht als Root-User ausgeführt wird. Mit dieser Einstellung wird verhindert, dass Angreife z.B. sudo-Befehle ausführen können.
