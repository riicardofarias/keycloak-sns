package dev.ricardofarias;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ricardofarias.model.AdminEventDTO;
import dev.ricardofarias.model.EventDTO;
import dev.ricardofarias.sns.SnsClient;
import dev.ricardofarias.sns.SnsConfig;
import org.jboss.logging.Logger;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerTransaction;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;

import java.util.Map;

public class SNSEventListenerProvider implements EventListenerProvider {
    private static final Logger log = Logger.getLogger(SNSEventListenerProvider.class);
    private final SnsConfig cfg;
    private final AmazonSNS snsClient;
    private final EventListenerTransaction tx = new EventListenerTransaction(this::publishAdminEvent, this::publishEvent);

    public SNSEventListenerProvider(SnsConfig cfg, KeycloakSession session) {
        this.cfg = cfg;
        this.snsClient = new SnsClient(cfg).getClient();
        session.getTransactionManager().enlistAfterCompletion(tx);
    }

    @Override
    public void onEvent(Event event) {
        tx.addEvent(event);
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean includeRepresentation) {
        tx.addAdminEvent(adminEvent, includeRepresentation);
    }

    @Override
    public void close() {}

    private void publishAdminEvent(AdminEvent event, boolean includeRepresentation) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String message = objectMapper.writeValueAsString(new AdminEventDTO(event));

            MessageAttributeValue type = new MessageAttributeValue()
                .withDataType("String")
            .withStringValue(event.getOperationType().name());

            MessageAttributeValue realm = new MessageAttributeValue()
                .withDataType("String")
            .withStringValue(event.getRealmId());

            MessageAttributeValue eventType = new MessageAttributeValue()
                .withDataType("String")
            .withStringValue("ADMIN");

            MessageAttributeValue status = new MessageAttributeValue()
                .withDataType("String")
            .withStringValue(event.getError() != null ? "ERROR": "SUCCESS");

            Map<String, MessageAttributeValue> attrs = Map.of(
                "KK_EVENT_TYPE", eventType,
                "KK_TYPE", type,
                "KK_REALM", realm,
                "KK_STATUS", status
            );

            PublishRequest request = new PublishRequest()
                .withMessage(message)
                .withMessageAttributes(attrs)
            .withTopicArn(cfg.getArnUrl());

            snsClient.publish(request);

            log.infof("sns-event-listener SUCCESS message published: %s", event.getOperationType());
        } catch (Exception ex) {
            log.errorf(ex, "sns-event-listener ERROR on publish message: %s", event.getOperationType());
        }
    }

    private void publishEvent(Event event) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String message = objectMapper.writeValueAsString(new EventDTO(event));

            MessageAttributeValue type = new MessageAttributeValue()
                .withDataType("String")
            .withStringValue(event.getType().name());

            MessageAttributeValue realm = new MessageAttributeValue()
                .withDataType("String")
            .withStringValue(event.getRealmId());

            MessageAttributeValue clientId = new MessageAttributeValue()
                .withDataType("String")
            .withStringValue(event.getClientId());

            MessageAttributeValue eventType = new MessageAttributeValue()
                .withDataType("String")
            .withStringValue("USER");

            MessageAttributeValue status = new MessageAttributeValue()
                .withDataType("String")
            .withStringValue(event.getError() != null ? "ERROR": "SUCCESS");

            Map<String, MessageAttributeValue> attrs = Map.of(
                "KK_EVENT_TYPE", eventType,
                "KK_TYPE", type,
                "KK_REALM", realm,
                "KK_CLIENT_ID", clientId,
                "KK_STATUS", status
            );

            PublishRequest request = new PublishRequest()
                .withMessage(message)
                .withMessageAttributes(attrs)
            .withTopicArn(cfg.getArnUrl());

            snsClient.publish(request);

            log.infof("sns-event-listener SUCCESS message published: %s", event.getType());
        } catch (Exception ex) {
            log.errorf(ex, "sns-event-listener ERROR on publish message: %s", event.getType());
        }
    }
}
