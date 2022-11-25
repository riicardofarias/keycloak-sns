import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Map;

public class SNSPublisherTest {
    private AmazonSNS amazonClient;

    @Before
    public void beforeTest() {
        amazonClient = AmazonSNSClientBuilder.standard()
            .withCredentials(new DefaultAWSCredentialsProviderChain())
        .build();
    }

    @Ignore
    @Test
    public void whenPublishSuccess() {
        MessageAttributeValue type = new MessageAttributeValue()
            .withDataType("String")
        .withStringValue("REGISTER");

        MessageAttributeValue status = new MessageAttributeValue()
            .withDataType("String")
        .withStringValue("FAILED");

        Map<String, MessageAttributeValue> attrs = Map.of(
            "KK_TYPE", type,
            "KK_STATUS", status
        );

        PublishRequest publishRequest = new PublishRequest()
            .withTopicArn("arn:aws:sns:us-east-1:745788135408:ReinvestKeycloakEvent")
            .withMessageAttributes(attrs)
        .withMessage("Hello word");

        amazonClient.publish(publishRequest);
    }
}
