package net.vincedgy.aws.library.service;


import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by vincent on 02/06/2017.
 */

@Service
public class AWSCredentialsAdapter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${aws.profile}")
    String awsProfile;

    @Value("${aws.region}")
    String awsRegion;


    private ProfileCredentialsProvider awsCredentials = null;

    public AWSCredentialsProvider getAwsCredentials() {
        if (this.awsCredentials == null) {
            try {
                this.awsCredentials = new ProfileCredentialsProvider(awsProfile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this.awsCredentials;
    }

    public String getAwsRegion() {
        return awsRegion;
    }
}