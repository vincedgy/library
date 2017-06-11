package net.vincedgy.aws.library;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.sqs.model.Message;
import net.vincedgy.aws.library.service.AWSCredentialsAdapter;
import net.vincedgy.aws.library.service.AWSSQSAdapter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;


/**
 * SimpleQueueService Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>mai 7, 2017</pre>
 */
public class AWSSQSAdapterTest {
    AWSCredentialsAdapter awsCredentialsAdapter;
    AWSSQSAdapter awssqsAdapter;
    String QUEUE_NAME = "TestQueue";

    @Before
    public void before() throws Exception {
        this.awsCredentialsAdapter = new AWSCredentialsAdapter();
    }

    @After
    public void after() throws Exception {
    }


    /**
     * Method: getCredentials()
     */
    @Test
    public void testConstructor() throws Exception {
        try {
            this.awssqsAdapter = new AWSSQSAdapter(this.awsCredentialsAdapter);

            //AWSSQSAdapter.testQueue();
            try {
                // Create a queue
                String myQueueUrl = this.awssqsAdapter.createQueue(this.QUEUE_NAME);

                // Send a message
                System.out.println("Sending a message to MyQueue.\n");
                this.awssqsAdapter.sendMessage("This is my message for now.");
                this.awssqsAdapter.sendMessage("Second message.");
                this.awssqsAdapter.sendMessage("Third message.");

                // Receive messages
                System.out.println("Receiving messages from MyQueue.\n");
                List<Message> messages = this.awssqsAdapter.getMessages();

                for (Message message : messages) {
                    System.out.println("  Message");
                    System.out.println("    MessageId:     " + message.getMessageId());
                    System.out.println("    ReceiptHandle: " + message.getReceiptHandle());
                    System.out.println("    MD5OfBody:     " + message.getMD5OfBody());
                    System.out.println("    Body:          " + message.getBody());
                    for (Map.Entry<String, String> entry : message.getAttributes().entrySet()) {
                        System.out.println("  Attribute");
                        System.out.println("    Name:  " + entry.getKey());
                        System.out.println("    Value: " + entry.getValue());
                    }
                }
                System.out.println();

                // Delete a message
                System.out.println("Deleting one message.\n");
                this.awssqsAdapter.deleteMessage(messages.get(0));

                // Delete all messages
                System.out.println("Deleting all messages.\n");
                this.awssqsAdapter.deleteAllMessages(messages);

                // Delete a queue
                System.out.println("Deleting the test queue.\n");
                this.awssqsAdapter.deleteQueue();

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

            Assert.assertNotNull(this.awssqsAdapter);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Method: openQueue()
     */
    //@Test
    public void testOpenQueue() throws Exception {
        Assert.fail("Echec");

    }

} 
