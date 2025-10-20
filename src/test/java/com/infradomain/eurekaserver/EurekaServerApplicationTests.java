package com.infradomain.eurekaserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EurekaServerApplicationTests {

  @Autowired
  TestRestTemplate restTemplate;

  @Test
  void contextLoads() {
    assertThat(restTemplate).isNotNull();
  }

  @Test
  @DisplayName("Actuator health should be up")
  void actuatorHealthShouldBeUp() {
    ResponseEntity<String> response = restTemplate.getForEntity("/actuator/health", String.class);
    assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(response.getBody()).contains("UP");
  }

  @Test
  @DisplayName("Actuator info should return data")
  void actuatorInfoShouldReturnData() {
    ResponseEntity<String> response = restTemplate.getForEntity("/actuator/info", String.class);
    assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
  }

  @Test
  @DisplayName("Should register and list fake instance")
  void shouldRegisterAndListFakeInstance() {
    // Simula o registro de uma instância fictícia no formato correto
    String registrationPayload = """
      {
        "instance": {
          "instanceId": "fake-instance:8080",
          "hostName": "fake-instance",
          "app": "FAKE-APP",
          "ipAddr": "127.0.0.1",
          "status": "UP",
          "port": { "$": 8080, "@enabled": true },
          "dataCenterInfo": {
            "@class": "com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo",
            "name": "MyOwn"
          }
        }
      }
      """;

    var headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

    HttpEntity<String> request = new HttpEntity<>(registrationPayload, headers);

    ResponseEntity<String> registrationResponse =
      restTemplate.postForEntity("/eureka/apps/FAKE-APP", request, String.class);

    // Deve retornar 204 No Content se registrado com sucesso
    assertThat(registrationResponse.getStatusCode().is2xxSuccessful())
      .as("Esperado status 2xx no registro")
      .isTrue();

    // Agora consulta a lista de apps
    ResponseEntity<String> appsResponse = restTemplate.getForEntity("/eureka/apps", String.class);
    assertThat(appsResponse.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(appsResponse.getBody()).contains("FAKE-APP");
  }

}