package net.vincedgy.aws.library;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentity;
import com.amazonaws.services.sqs.model.Message;
import net.vincedgy.aws.library.service.AWSCognitoAdapter;
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
public class AWSCognitoAdapterTest {
    AWSCredentialsAdapter awsCredentialsAdapter;
    AWSCognitoAdapter awsCognitoAdapter;
    String COGNITO_IDENTITY_POOL_ID="eu-west-1_f3TDpEOqC";
    String AWS_ACCOUNT_ID="441481051737";

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
            this.awsCognitoAdapter = new AWSCognitoAdapter(this.awsCredentialsAdapter);

            Assert.assertNotNull(this.awsCognitoAdapter);
        } catch (Exception e) {
            throw e;
        }
    }

   @Test
    public void testCognitoIdentity() throws Exception {
       this.awsCognitoAdapter = new AWSCognitoAdapter(this.awsCredentialsAdapter);
       this.awsCognitoAdapter.setAWS_ACCOUNT_ID(AWS_ACCOUNT_ID);
       this.awsCognitoAdapter.setCOGNITO_IDENTITY_POOL_ID(COGNITO_IDENTITY_POOL_ID);
        AmazonCognitoIdentity amazonCognitoIdentity = this.awsCognitoAdapter.getAmazonCognito();

        Assert.assertNotNull(amazonCognitoIdentity);

    }

} 
