# Eureka Server

* **Serviço:** Eureka Server
* **Domínio:** Infra Domain
* **URL:** http://localhost:8761/

---

## Ferramentas e Tecnologias

* Java 25
* Spring Boot 3.5.6
* Maven 3.9.11
* Spring Cloud Eureka Server
* Spring Actuator
* Docker

---

## Executar a Aplicação

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


---

## Licença

* [Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0)

---

## Equipe Responsável

* Desenvolvedora e Mantenedora: Juliane Maran
* [Contato via e-mail](mailto:julianemaran@gmail.com)
* [GitHub](https://github.com/JuhMaran) 