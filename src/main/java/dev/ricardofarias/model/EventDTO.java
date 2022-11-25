package dev.ricardofarias.model;

import lombok.Getter;
import org.keycloak.events.Event;
import org.keycloak.events.EventType;

import java.util.Map;

@Getter
public class EventDTO {
    private final String id;
    private final long time;
    private final EventType type;
    private final String realmId;
    private final String clientId;
    private final String userId;
    private final String sessionId;
    private final String ipAddress;
    private final String error;
    private final Map<String, String> details;

    public EventDTO(Event event) {
        this.id = event.getId();
        this.time = event.getTime();
        this.type = event.getType();
        this.realmId = event.getRealmId();
        this.clientId = event.getClientId();
        this.userId = event.getUserId();
        this.sessionId = event.getSessionId();
        this.ipAddress = event.getIpAddress();
        this.error = event.getError();
        this.details = event.getDetails();
    }
}
