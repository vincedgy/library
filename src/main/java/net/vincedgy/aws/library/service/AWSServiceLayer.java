package net.vincedgy.aws.library.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by vincent on 02/06/2017.
 */
@Service
public class AWSServiceLayer {

    AWSCredentialsAdapter awsCredentialsAdapter;
    AWSDynamoDBAdapter awsDynamoDBAdapter;
    AWSS3Adapter awsS3Adapter;
    AWSSQSAdapter awssqsAdapter;
    AWSCognitoAdapter awsCognitoAdapter;


    @Autowired
    public AWSServiceLayer(AWSS3Adapter awss3Adapter,
                           AWSDynamoDBAdapter awsDynamoDBAdapter,
                           AWSCredentialsAdapter awsCredentialsAdapter,
                           AWSSQSAdapter awssqsAdapter,
                           AWSCognitoAdapter awsCognitoAdapter) {
        this.awsS3Adapter = awss3Adapter;
        this.awsDynamoDBAdapter = awsDynamoDBAdapter;
        this.awsCredentialsAdapter = awsCredentialsAdapter;
        this.awssqsAdapter = awssqsAdapter;
        this.awsCognitoAdapter = awsCognitoAdapter;
    }

    public AWSS3Adapter getAwsS3Adapter() {
        return awsS3Adapter;
    }

    public AWSDynamoDBAdapter getAwsDynamoDBAdapter() {
        return awsDynamoDBAdapter;
    }

    public AWSCredentialsAdapter getAwsCredentialsAdapter() {
        return awsCredentialsAdapter;
    }
}
