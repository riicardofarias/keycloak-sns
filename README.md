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
* S3_BUCKET: Name of your s3 bucket
* AWS_ACCESS_KEY_ID: AWS Access Key Id
* AWS_SECRET_ACCESS_KEY: AWS Secret Key
* AWS_REGION: AWS Region

## Getting Started
After fork/clone the repo, install nodejs dependencies.
```
npm install
```
Deploy to AWS
```
serverless deploy --verbose
```

You can start the server on localhost using:
```
serverless offline
```

## S3 Bucket
You need add policy permission on you S3 bucket.
```
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "git-webhook-lambda-s3",
            "Effect": "Allow",
            "Action": "s3:PutObject",
            "Resource": "[YOUR BUCKET/KEYS]"
        }
    ]
}
```

