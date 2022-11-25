package dev.ricardofarias.sns;

import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SnsClient {
    private final SnsConfig cfg;

    public AmazonSNS getClient() {
        AWSCredentialsProviderChain credentials = new DefaultAWSCredentialsProviderChain();

        return AmazonSNSClientBuilder.standard()
            .withRegion(Regions.fromName(cfg.getRegion()))
            .withCredentials(credentials)
        .build();
    }
}
