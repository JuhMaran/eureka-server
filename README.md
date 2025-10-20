# 🛰️ Eureka Server

## 📘 Descrição

Este projeto implementa um **Eureka Server** utilizando **Spring Cloud Netflix**.  
Ele funciona como um **Service Registry** central para aplicações distribuídas, permitindo que microsserviços se
registrem e descubram uns aos outros dinamicamente, melhorando **resiliência**, **escalabilidade**
e **observabilidade**.

---

## ⚙️ Tecnologias Principais

* **Java 25**
* **Spring Boot 3.5**
* **Spring Cloud 2025.0.0**
* **Maven**
* **Docker (multi-stage)**
* **Spring Boot Actuator**
* **Spring Security (autenticação básica)**

---

## 🔐 Recursos Principais

* Registro e descoberta de serviços via **Eureka Dashboard**
* Autenticação básica configurável via variáveis de ambiente (`EUREKA_USER`, `EUREKA_PASS`)
* Health-check e métricas via **Spring Boot Actuator**
* Logs configurados com rotação diária e níveis ajustados
* Configuração otimizada para **Docker e produção**

---

## 🧱 Estrutura do Projeto

```
eureka-server/
├── src/
│   ├── main/
│   │   ├── java/com/infradomain/eurekaserver/EurekaServerApplication.java
│   │   └── resources/
│   │       ├── application.yml
│   │       └── logback-spring.xml
├── pom.xml
├── Dockerfile
└── README.md
```

---

## 🚀 Execução Local

### 1. Clonar o repositório

```bash
git clone https://github.com/JuhMaran/eureka-server.git
cd eureka-server
```

### 2. Compilar o projeto

```bash
mvn clean package -DskipTests
```

### 3. Executar a aplicação

```bash
java -jar target/eureka-server-1.0.0.jar
```

### 4. Acessar o dashboard

```
http://localhost:8761/
```

**Credenciais padrão:**

```
Usuário: admin
Senha: admin123
```

---

## 🐳 Execução via Docker

### 1. Build da imagem

```bash
docker build -t eureka-server .
```

### 2. Executar o container com variáveis seguras

```bash
docker run -d \
  -p 8761:8761 \
  -e APP_PORT=8761 \
  -e EUREKA_USER=meuUser \
  -e EUREKA_PASS=minhaSenhaSegura \
  -e EUREKA_HOST=eureka.local \
  --name eureka-server \
  eureka-server
```

### 3. Acessar o Eureka Dashboard

```
http://localhost:8761/
```

### 4. Autenticação

Use o usuário e senha definidos nas variáveis `EUREKA_USER` e `EUREKA_PASS`.

---

## 🧩 Variáveis de Ambiente Disponíveis

| Variável      | Descrição                                  | Valor padrão        |
|---------------|--------------------------------------------|---------------------|
| `APP_PORT`    | Porta do servidor Eureka                   | `8761`              |
| `JAVA_OPTS`   | Parâmetros de memória e performance da JVM | `-Xms256m -Xmx512m` |
| `EUREKA_USER` | Usuário para autenticação básica           | `admin`             |
| `EUREKA_PASS` | Senha do usuário do Eureka                 | `admin123`          |
| `EUREKA_HOST` | Hostname/IP da instância do Eureka         | `localhost`         |

---

## 🩺 Endpoints de Monitoramento

| Endpoint            | Descrição                    | Exemplo                                  |
|---------------------|------------------------------|------------------------------------------|
| `/actuator/health`  | Status da aplicação          | `http://localhost:8761/actuator/health`  |
| `/actuator/info`    | Informações gerais           | `http://localhost:8761/actuator/info`    |
| `/actuator/metrics` | Métricas da JVM e requests   | `http://localhost:8761/actuator/metrics` |
| `/actuator/env`     | Variáveis de ambiente ativas | `http://localhost:8761/actuator/env`     |

> ⚠️ Apenas usuários autenticados podem visualizar detalhes completos de health-check e métricas.

---

## 📒 Configuração do Eureka

Principais parâmetros definidos em `application.yml`:

```yml
eureka:
  client:
    fetch-registry: false
    register-with-eureka: false
  server:
    enable-self-preservation: true
    eviction-interval-timer-in-ms: 60000
  instance:
    hostname: ${EUREKA_HOST:localhost}
    prefer-ip-address: true
```

---

## 🪵 Logs

Logs são armazenados em:

```
logs/eureka-server.log
```

Configuração detalhada em `logback-spring.xml`:

* Log colorido no console
* Rotação diária de arquivos
* Histórico de 14 dias
* Níveis ajustados (`root: INFO`, `eureka: INFO`)

---

## 🤝 Contribuição

Contribuições são bem-vindas!
Abra uma **issue** para discutir melhorias, ou envie um **pull request** com suas alterações.

---

## 📜 Licença

Distribuído sob a licença [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0).

---

### ✨ Autor

**Juliane Maran**
Desenvolvedora e mantenedora do projeto
[GitHub](https://github.com/JuhMaran) · [Email](mailto:julianemaran@gmail.com)

---

## Observação sobre Execução

### Usando Docker

1. Acessar o `wsl`
2. Build Manual: `docker build -t eureka-server .`
3. Rodar container: `docker run -p 8761:8761 eureka-server`
4. Logs em tempo real: `docker logs -f <container-id>`

### Rede Docker

Se quiser que os serviços conversem entre si localmente

```bash
docker network create taptrack-net
docker run -d --network taptrack-net --name eureka eureka-server
docker run -d --network taptrack-net --name gateway api-gateway
docker run -d --network taptrack-net --name container-measure container-measure-service
docker run -d --network taptrack-net --name frontend -p 4200:4200 taptrack-frontend
```