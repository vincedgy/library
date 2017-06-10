package net.vincedgy.aws.library.service;


import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by vincent on 02/06/2017.
 */

@Service
public class AWSDynamoDBAdapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private AmazonDynamoDB amazonDynamoDB;
    private ListTablesResult tables = null;

    final AWSCredentialsAdapter awsCredentialsAdapter;

    @Autowired
    public AWSDynamoDBAdapter(AWSCredentialsAdapter awsCredentialsAdapter) {

        this.awsCredentialsAdapter = awsCredentialsAdapter;
    }

    /**
     * getDynamoDBClient
     *
     * @return AmazonDynamoDB
     */
    private AmazonDynamoDB getDynamoDBClient() {
        if (this.amazonDynamoDB == null) {
            try {
                this.amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
                        .withCredentials(awsCredentialsAdapter.getAwsCredentials())
                        .withRegion(awsCredentialsAdapter.getAwsRegion())
                        .build();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this.amazonDynamoDB;
    }

    /**
     * getTables
     *
     * @return ListTablesResult
     */
    public ListTablesResult getTables() {
        if (this.tables == null) {
            try {
                this.tables = new ListTablesResult();
                this.tables = this.getDynamoDBClient().listTables();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this.tables;
    }


}