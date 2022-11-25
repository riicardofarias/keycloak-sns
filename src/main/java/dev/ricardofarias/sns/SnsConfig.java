package dev.ricardofarias.sns;

import lombok.Getter;
import org.keycloak.Config;

import java.util.Locale;

@Getter
public class SnsConfig {
    private final String arnUrl;

    private final String region;

    public SnsConfig(Config.Scope config) {
        this.arnUrl = getConfigVar(config, "arn");
        this.region = getConfigVar(config, "region");
    }

    private String getConfigVar(Config.Scope config, String key) {
        if(config != null && config.get(key) != null) {
            return config.get(key);
        } else {
            return System.getenv(String.format("KK_SNS_%s", key.toUpperCase(Locale.ENGLISH)));
        }
    }
}
