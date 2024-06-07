# Java Spring Boot SQS Example
This project consists of a single Java Spring Boot application designed to demonstrate the use of Amazon Simple Queue Service (SQS) for sending and receiving messages. This application showcases how to integrate AWS SQS with Spring Boot, providing a scalable and reliable messaging solution.
## Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Setup](#setup)
    - [Prerequisites](#prerequisites)
    - [AWS Configuration](#aws-configuration)
    - [Create an SQS Queue with localstack](#create-an-sqs-queue-with-localstack)
- [Running the Applications](#running-the-applications)
- [License](#license)

## Overview

This project contains two main components:

1. **Sender**: Sends messages to an Amazon SQS queue.
2. **Receiver**: Receives messages from an Amazon SQS queue.

## Architecture

- **Sender Application**:
    - Exposes a REST endpoint to send messages to the SQS queue.
    - Utilizes AWS SDK for Java to interact with SQS.

- **Receiver Application**:
    - Continuously polls the SQS queue for new messages.
    - Processes received messages.

## Setup

### Prerequisites

- Java 11 or higher
- Maven
- AWS account with access to SQS
- AWS CLI configured with your credentials

### AWS Configuration

1. **Create an SQS Queue**:
    - Go to the [AWS Management Console](https://aws.amazon.com/console/).
    - Navigate to the SQS service.
    - Create a new queue (standard or FIFO).
    - Note the Queue URL for later use.

2. **Configure AWS Credentials**:
    - Ensure your AWS credentials are configured correctly. This can be done using the AWS CLI:
      ```sh
      aws configure
      ```

### Create an SQS Queue with localstack

Open another terminal and execute the following command to create an SQS queue:

**Start LocalStack:**

Run the following command to start LocalStack:

   ```bash
   docker-compose up localstack
   ```

This will start LocalStack and all services configured in the `docker-compose.yml` file.


```bash
aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name my-queue
```

This will create a queue named `my-queue` in LocalStack.

#### Send a message to the SQS Queue

To send a message to the SQS queue, you can use the following command:

```bash
aws --endpoint-url=http://localhost:4566 sqs send-message --queue-url http://localhost:4566/000000000000/my-queue --message-body "My test message"
```

#### Receive a message from the SQS Queue

You can receive messages from the SQS queue with the following command:

```bash
aws --endpoint-url=http://localhost:4566 sqs receive-message --queue-url http://localhost:4566/000000000000/my-queue
```

This will return any available messages in the `my-queue`.
      
## Running the Applications

1. **Sender Message**:
    - Use a tool like `curl` or Postman to send a POST request to the `api/v1/sqs` endpoint:
      ```sh
      curl -X POST "http://localhost:8080/api/v1/sqs" -d "message=Hello World"
      ```

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
