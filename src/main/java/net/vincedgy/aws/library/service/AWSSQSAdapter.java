package net.vincedgy.aws.library.service;


import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vincent on 02/06/2017.
 */

@Service
public class AWSSQSAdapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    final AWSCredentialsAdapter awsCredentialsAdapter;
    private AmazonSQS amazonSQS = null;
    private String queueUrl = null;

    @Autowired
    public AWSSQSAdapter(final AWSCredentialsAdapter awsCredentialsAdapter) {
        this.awsCredentialsAdapter = awsCredentialsAdapter;
    }

    /**
     * getS3Client
     *
     * @return AmazonS3
     * @throws Exception
     */
    private AmazonSQS getAmazonSQS() throws Exception {
        if (this.amazonSQS == null) {
            try {
                this.amazonSQS = AmazonSQSClientBuilder
                        .standard()
                        .withCredentials(this.awsCredentialsAdapter.getAwsCredentials())
                        .withRegion(this.awsCredentialsAdapter.getAwsRegion())
                        .build();
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
        return this.amazonSQS;
    }

    /**
     * reset : reset current connection must be used through a chain
     *
     * @return
     */
    public AWSSQSAdapter reset() {
        this.amazonSQS = null;
        return this;
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
            this.queueUrl = this.getAmazonSQS().createQueue(createQueueRequest).getQueueUrl();
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
            for (String queueUrl : this.getAmazonSQS().listQueues().getQueueUrls()) {
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
                this.getAmazonSQS().sendMessage(new SendMessageRequest(this.queueUrl, message));
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
            messages = this.getAmazonSQS().receiveMessage(receiveMessageRequest).getMessages();
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
            this.getAmazonSQS().deleteMessage(new DeleteMessageRequest(this.queueUrl, messageReceiptHandle));
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
                this.getAmazonSQS().deleteMessage(new DeleteMessageRequest(this.queueUrl, messageReceiptHandle));
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
            this.getAmazonSQS().deleteQueue(new DeleteQueueRequest(this.queueUrl));
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot delete queue ",
                    e);
        }
    }


}