# keycloak-sns

keycloak-sns provides a solution by using a [AWS SNS](https://aws.amazon.com/en/sns/) and [SQS](https://aws.amazon.com/en/sqs/) to be deployed along with Gitea or any other git server for the Webhook API call integration. This service fetch the code repo from Gitea API and automatically zip and upload the source artifact to a configurable S3 bucket, to enable the integration between the git servers to AWS CodePipeline and AWS CodeBuild.

Additionally, the Amazon Linux image used in lambda currently doesn't have Git. So this layer should be added to the lambda function to have Git available:
```
arn:aws:lambda:us-east-1:553035198032:layer:git-lambda2:8
```

## Prerequisite
The following tools should be installed due to the project dependency.
* [Java](https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html)

## Setup
Here are all the environment variables that need to be defined.
* AWS_ACCESS_KEY_ID: AWS Access Key Id
* AWS_SECRET_ACCESS_KEY: AWS Secret Key
* AWS_REGION: AWS Region

## Getting Started

After fork/clone the repo, build the jar file using:
```
mvn package
```

Copy the jar to the Keycloak server:
`target/keycloak-sns-listener-1.0.jar` to `/opt/keycloak/providers` or `/opt/jboss/keycloak/standalone/deployments/`
