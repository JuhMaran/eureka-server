# ==========================================
# EUREKA SERVER - Dockerfile (LOCAL TESTS)
# ==========================================
# FROM eclipse-temurin:25-jdk-alpine
#WORKDIR /app

# Copia tudo (build local rápido e simples)
#COPY . .

# Compila e empacota
#RUN apk add --no-cache maven && mvn clean package -DskipTests

# Variáveis padrão
#ENV JAVA_OPTS="-Xms256m -Xmx512m"
#ENV APP_PORT=8761

#EXPOSE ${APP_PORT}
#CMD ["sh", "-c", "java $JAVA_OPTS -jar target/*.jar"]

# ==========================================
# EUREKA SERVER - Dockerfile (Produção)
# ==========================================

# ====== Build stage ======
FROM maven:3.9.11-eclipse-temurin-25 AS build
WORKDIR /app

# Copia pom e código fonte
COPY pom.xml .
COPY src ./src

# Build da aplicação (skipt testes)
RUN mvn clean package -DskipTests

# ====== Runtime stage ======
FROM eclipse-temurin:25-jdk-alpine
WORKDIR /app

# Copia o JAR gerado no build stage
COPY --from=build /app/target/*.jar app.jar

# Porta padrão do Eureka
ENV APP_PORT=8761

# Configurações de memória Java
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Credenciais básicas para acesso ao Eureka Server
ENV EUREKA_USER=admin
ENV EUREKA_PASS=admin123

# Configurações adicionais do Eureka (host/IP)
ENV EUREKA_HOST=localhost

# Expõe a porta configurável
EXPOSE ${APP_PORT}

# Comando de execução
CMD ["sh", "-c", "java $JAVA_OPTS -Deureka.instance.hostname=${EUREKA_HOST} -Deureka.client.register-with-eureka=false -Deureka.client.fetch-registry=false -jar app.jar"]