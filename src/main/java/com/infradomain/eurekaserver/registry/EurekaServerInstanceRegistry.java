package com.infradomain.eurekaserver.registry;

import com.netflix.appinfo.InstanceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceCanceledEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRegisteredEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRenewedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * eureka-server
 *
 * @author Juliane Maran
 * @since 20/10/2025
 */
@Component
public class EurekaServerInstanceRegistry {

  private static final Logger log = LoggerFactory.getLogger(EurekaServerInstanceRegistry.class);

  @EventListener
  public void onInstanceRegistered(EurekaInstanceRegisteredEvent event) {
    InstanceInfo instance = event.getInstanceInfo();
    log.info("Instância registrada: {} - status: {}", instance.getAppName(), instance.getStatus());
  }

  @EventListener
  public void onInstanceCanceled(EurekaInstanceCanceledEvent event) {
    log.warn("Instância removida do registro: {} - ID: {}", event.getAppName(), event.getServerId());
  }

  @EventListener
  public void onInstanceRenewed(EurekaInstanceRenewedEvent event) {
    log.debug("Renovação recebida: {} - ID: {}", event.getAppName(), event.getServerId());
  }

}
