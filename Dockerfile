# ==========================================
# EUREKA SERVER - Dockerfile (LOCAL TESTS)
# ==========================================
FROM eclipse-temurin:25-jdk-alpine

WORKDIR /app

# Copia tudo (build local rápido e simples)
COPY . .

# Compila e empacota
RUN apk add --no-cache maven && mvn clean package -DskipTests

# Variáveis padrão
ENV JAVA_OPTS="-Xms256m -Xmx512m"
ENV APP_PORT=8761

EXPOSE ${APP_PORT}
CMD ["sh", "-c", "java $JAVA_OPTS -jar target/*.jar"]