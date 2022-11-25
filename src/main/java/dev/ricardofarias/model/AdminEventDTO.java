package dev.ricardofarias.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.events.admin.AuthDetails;
import org.keycloak.events.admin.OperationType;

@Getter
public class AdminEventDTO {
    private final String id;
    private final long time;
    private final String realmId;
    private final AuthDetails authDetails;
    private final String resourceType;
    private final OperationType operationType;
    private final String resourcePath;
    private final Object representation;
    private final String error;

    public AdminEventDTO(AdminEvent event) {
        this.id = event.getId();
        this.time = event.getTime();
        this.realmId = event.getRealmId();
        this.authDetails = event.getAuthDetails();
        this.resourceType = event.getResourceType().name();
        this.operationType = event.getOperationType();
        this.resourcePath = event.getResourcePath();
        this.error = event.getError();
        this.representation = parseRepresentation(event.getRepresentation());
    }

    private Object parseRepresentation(String representation) {
        try {
            return new ObjectMapper().readValue(representation, Object.class);
        } catch (Exception e) {
            return null;
        }
    }
}
