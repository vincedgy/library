package net.vincedgy.aws.library.service;

/*
 * Copyright 2010-2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

/**
 * This sample demonstrates how to make basic requests to Amazon SQS using the
 * AWS SDK for Java.
 * <p>
 * <b>Prerequisites:</b> You must have a valid Amazon Web
 * Services developer account, and be signed up to use Amazon SQS. For more
 * information on Amazon SQS, see http://aws.amazon.com/sqs.
 * <p>
 * Fill in your AWS access credentials in the provided credentials file
 * template, and be sure to move the file to the default location
 * (~/.aws/credentials) where the sample code will load the credentials from.
 * <p>
 * <b>WARNING:</b> To avoid accidental leakage of your credentials, DO NOT keep
 * the credentials file in your source directory.
 */
@Service
public class AWSSQSAdapter_0 {

    @Value("${aws.region}")
    String awsRegion;

    String QUEUE_NAME = "MyQueue";
    AWSCredentials credentials;
    AmazonSQS sqs;
    String queueUrl;


    /**
     * getCredentials() : Singleton that return current credentials
     *
     * @return
     * @throws Exception
     */
    AWSCredentials getCredentials() throws Exception {
        /*
         * The ProfileCredentialsProvider will return your [default]
         * credential profile by reading from the credentials file located at
         * (~/.aws/credentials).
         */
        if (this.credentials == null) {
            try {
                this.credentials = new ProfileCredentialsProvider().getCredentials();
                return this.credentials;
            } catch (Exception e) {
                throw new AmazonClientException(
                        "Cannot load the credentials from the credential profiles file. " +
                                "Please make sure that your credentials file is at the correct " +
                                "location (~/.aws/credentials), and is in valid format.",
                        e);
            }
        } else {
            return this.credentials;
        }
    }

    /**
     * getSqs : singleton for getting back current sqs
     *
     * @param credentials
     * @return
     * @throws Exception
     */
    AmazonSQS openSQS(AWSCredentials credentials) throws Exception {
        if (this.sqs == null) {
            try {
                this.sqs = new AmazonSQSClient(credentials);
                Region region = Region.getRegion(Regions.EU_WEST_1);
                sqs.setRegion(region);
                return this.sqs;
            } catch (Exception e) {
                throw new AmazonClientException(
                        "Cannot get SQS queue named " + QUEUE_NAME,
                        e);
            }
        } else {
            return this.sqs;
        }
    }

    /**
     * create a Queue (if it's not exists) and retrieve back it url
     *
     * @param queueName
     * @return
     * @throws Exception
     */
    String createQueue(String queueName) throws Exception {
        try {
            System.out.println("Creating a new SQS queue called " + queueName + "\n");
            CreateQueueRequest createQueueRequest = new CreateQueueRequest(queueName);
            this.queueUrl = this.sqs.createQueue(createQueueRequest).getQueueUrl();
            return this.queueUrl;
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot create SQS queue named " + queueName,
                    e);
        }

    }

    /**
     * getAllQueues : retrieve the list of all queues
     *
     * @return
     */
    List<String> getAllQueuesNames() throws Exception {
        List<String> queues;
        try {
            queues = new ArrayList<String>();
            for (String queueUrl : this.sqs.listQueues().getQueueUrls()) {
                queues.add(queueUrl);
            }
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot get url queues",
                    e);
        }
        return queues;
    }

    /**
     * sendMessage : simple send the message through the sqs, to the given queue
     *
     * @param message
     */
    void sendMessage(String message) throws Exception {
        try {
            if (queueUrl.length() > 0 && message.length() > 0) {
                this.sqs.sendMessage(new SendMessageRequest(this.queueUrl, message));
            }
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot send message to queue " + queueUrl,
                    e);
        }
    }

    /**
     * getMessages : retrieve messages from the given queue
     *
     * @return
     * @throws Exception
     */
    public List<Message> getMessages() throws Exception {
        List<Message> messages = null;
        if (this.queueUrl.length() > 0) {
            ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(this.queueUrl);
            messages = this.sqs.receiveMessage(receiveMessageRequest).getMessages();
        }
        return messages;
    }

    /**
     * deleteMessage : delete one message from the given
     * @param message
     */
    public void deleteMessage(Message message) {
        String messageReceiptHandle;
        try {
            messageReceiptHandle = message.getReceiptHandle();
            this.sqs.deleteMessage(new DeleteMessageRequest(this.queueUrl, messageReceiptHandle));
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot delete this message from the queue " + this.queueUrl,
                    e);
        }

    }

    /**
     * deleteMessages : delete all messages from the given list of messages
     * @param messages
     */
    public void deleteAllMessages(List<Message> messages) {
        String messageReceiptHandle;
        try {
            for (int i = 0; i < messages.size(); i++) {
                messageReceiptHandle = messages.get(i).getReceiptHandle();
                this.sqs.deleteMessage(new DeleteMessageRequest(this.queueUrl, messageReceiptHandle));
            }
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot deletes all messages from the queue " + this.queueUrl,
                    e);
        }

    }

    /**
     * Delete a queue
     * @return
     * @throws Exception
     */
    public void deleteQueue() throws Exception {
        try {
            sqs.deleteQueue(new DeleteQueueRequest(this.queueUrl));
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot delete queue ",
                    e);
        }
    }

    public void testQueue() throws Exception {
        openSQS(getCredentials());
        try {
            // Create a queue
            String myQueueUrl = createQueue(this.QUEUE_NAME);

            // Send a message
            System.out.println("Sending a message to MyQueue.\n");
            sendMessage("This is my message for now.");
            sendMessage("Second message.");
            sendMessage("Third message.");

            // Receive messages
            System.out.println("Receiving messages from MyQueue.\n");
            List<Message> messages = getMessages();

            for (Message message : messages) {
                System.out.println("  Message");
                System.out.println("    MessageId:     " + message.getMessageId());
                System.out.println("    ReceiptHandle: " + message.getReceiptHandle());
                System.out.println("    MD5OfBody:     " + message.getMD5OfBody());
                System.out.println("    Body:          " + message.getBody());
                for (Entry<String, String> entry : message.getAttributes().entrySet()) {
                    System.out.println("  Attribute");
                    System.out.println("    Name:  " + entry.getKey());
                    System.out.println("    Value: " + entry.getValue());
                }
            }
            System.out.println();

            // Delete a message
            System.out.println("Deleting one message.\n");
            deleteMessage(messages.get(0));

            // Delete all messages
            System.out.println("Deleting all messages.\n");
            deleteAllMessages(messages);

            // Delete a queue
            System.out.println("Deleting the test queue.\n");
            deleteQueue();

        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it " +
                    "to Amazon SQS, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered " +
                    "a serious internal problem while trying to communicate with SQS, such as not " +
                    "being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
    }

    public AWSSQSAdapter_0() throws Exception {

    }
}
