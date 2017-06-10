package net.vincedgy.aws.library.service;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
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
public class AWSS3Adapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    final AWSCredentialsAdapter awsCredentialsAdapter;
    private AmazonS3 s3Client = null;
    private List<Bucket> buckets = null;

    @Autowired
    public AWSS3Adapter(final AWSCredentialsAdapter awsCredentialsAdapter) {
        this.awsCredentialsAdapter = awsCredentialsAdapter;
    }

    /**
     * getS3Client
     *
     * @return AmazonS3
     * @throws Exception
     */
    private AmazonS3 getS3Client() throws Exception {
        if (this.s3Client == null) {
            try {
                this.s3Client = AmazonS3ClientBuilder.standard()
                        .withCredentials(this.awsCredentialsAdapter.getAwsCredentials())
                        .withRegion(this.awsCredentialsAdapter.getAwsRegion())
                        .build();
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
        return this.s3Client;
    }

    /**
     * reset : reset current connection must be used through a chain
     *
     * @return
     */
    public AWSS3Adapter reset() {
        this.s3Client = null;
        this.buckets = null;
        return this;
    }

    /**
     * getS3Buckets
     *
     * @return List<Bucket>
     * @throws Exception
     */
    public List<Bucket> getS3Buckets() throws Exception {
        if (this.buckets == null) {
            try {
                this.buckets = new ArrayList<Bucket>();
                this.getS3Client().listBuckets().stream()
                        .forEach(bucket -> buckets.add(bucket));
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
        return this.buckets;
    }

}