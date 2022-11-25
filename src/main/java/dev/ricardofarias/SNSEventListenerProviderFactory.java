package dev.ricardofarias;

import dev.ricardofarias.sns.SnsConfig;
import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class SNSEventListenerProviderFactory implements EventListenerProviderFactory {
    private SnsConfig cfg;

    @Override
    public EventListenerProvider create(KeycloakSession keycloakSession) {
        return new SNSEventListenerProvider(cfg, keycloakSession);
    }

    @Override
    public void init(Config.Scope scope) {
        cfg = new SnsConfig(scope);
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return "sns-event-listener";
    }
}
