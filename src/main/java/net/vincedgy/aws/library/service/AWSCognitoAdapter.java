package net.vincedgy.aws.library.service;


// import the required packages from the AWS SDK for Java

import com.amazonaws.services.cognitoidentity.*;
import com.amazonaws.services.cognitoidentity.model.*;
import com.amazonaws.services.securitytoken.*;
import com.amazonaws.services.securitytoken.model.*;
import com.amazonaws.auth.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vincent on 02/06/2017.
 */

@Service
public class AWSCognitoAdapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    private AmazonCognitoIdentity amazonCognito = null;

    private GetIdRequest getIdRequest = null;
    private GetIdResult getIdResult = null;

    private GetCredentialsForIdentityRequest getCredentialsForIdentityRequest = null;
    private GetCredentialsForIdentityResult getCredentialsForIdentity = null;

    private String identityId = null;

    @Value("${cognito.identity.pool.id}")
    private String COGNITO_IDENTITY_POOL_ID;

    @Value("${aws.account.id}")
    private String AWS_ACCOUNT_ID;

    Map providerTokens = new HashMap();

    final AWSCredentialsAdapter awsCredentialsAdapter;

    @Autowired
    public AWSCognitoAdapter(final AWSCredentialsAdapter awsCredentialsAdapter) {
        this.awsCredentialsAdapter = awsCredentialsAdapter;
    }

    public String getCOGNITO_IDENTITY_POOL_ID() {
        return COGNITO_IDENTITY_POOL_ID;
    }

    public void setCOGNITO_IDENTITY_POOL_ID(String COGNITO_IDENTITY_POOL_ID) {
        this.COGNITO_IDENTITY_POOL_ID = COGNITO_IDENTITY_POOL_ID;
    }

    public String getAWS_ACCOUNT_ID() {
        return AWS_ACCOUNT_ID;
    }

    public void setAWS_ACCOUNT_ID(String AWS_ACCOUNT_ID) {
        this.AWS_ACCOUNT_ID = AWS_ACCOUNT_ID;
    }

    /**
     * getS3Client
     *
     * @return AmazonS3
     * @throws Exception
     */
    public AmazonCognitoIdentity getAmazonCognito() throws Exception {
        if (this.amazonCognito == null) {
            try {
                this.amazonCognito = AmazonCognitoIdentityClientBuilder.standard()
                        .withCredentials(this.awsCredentialsAdapter.getAwsCredentials())
                        .withRegion(this.awsCredentialsAdapter.getAwsRegion())
                        .build();

                this.getIdRequest = new GetIdRequest();
                this.getIdRequest.setAccountId(this.AWS_ACCOUNT_ID);
                this.getIdRequest.setIdentityPoolId(this.COGNITO_IDENTITY_POOL_ID);

                this.getCredentialsForIdentity = this.amazonCognito.getCredentialsForIdentity(new GetCredentialsForIdentityRequest());

                this.getIdResult = this.amazonCognito.getId(this.getIdRequest);
                this.identityId = this.getIdResult.getIdentityId();

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
        return this.amazonCognito;
    }

    /**
     * @return
     */
    private String getOpenIdToken() {

        // Create the request object
        GetOpenIdTokenRequest tokenRequest = new GetOpenIdTokenRequest();
        tokenRequest.setIdentityId(this.identityId);

        // If you are authenticating your users through an identity provider
        // then you can set the Map of tokens in the request
        // Map providerTokens = new HashMap();
        // providerTokens.put("graph.facebook.com", "facebook session key");
        // tokenRequest.setLogins(providerTokens);

        GetOpenIdTokenResult tokenResp = this.amazonCognito.getOpenIdToken(tokenRequest);
        // get the OpenID token from the response
        String openIdToken = tokenResp.getToken();
        return openIdToken;
    }

    /*
    private void AssumeRoleWithWebIdentity() {
        // you can now create a set of temporary, limited-privilege credentials to access
// your AWS resources through the Security Token Service utilizing the OpenID
// token returned by the previous API call. The IAM Role ARN passed to this call
// will be applied to the temporary credentials returned
        AWSSecurityTokenService stsClient = new AWSSecurityTokenServiceClient(new AnonymousAWSCredentials());
        AssumeRoleWithWebIdentityRequest stsReq = new AssumeRoleWithWebIdentityRequest();
        stsReq.setRoleArn("arn:aws:iam::6157xxxxxxxx:role/a_valid_aws_role_arn");
        stsReq.setWebIdentityToken(awsAccessToken);
        stsReq.setRoleSessionName("AppTestSession");

        AssumeRoleWithWebIdentityResult stsResp = stsClient.assumeRoleWithWebIdentity(stsReq);
        Credentials stsCredentials = stsResp.getCredentials();

// Create the session credentials object
        AWSSessionCredentials sessionCredentials = new BasicSessionCredentials(
                stsCredentials.getAccessKeyId(),
                stsCredentials.getSecretAccessKey(),
                stsCredentials.getSessionToken()
        );
// save the timeout for these credentials
        Date sessionCredentialsExpiration = stsCredentials.getExpiration();

// these credentials can then be used to initialize other AWS clients,
// for example the Amazon Cognito Sync client
        AmazonCognitoSync syncClient = new AmazonCognitoSyncClient(sessionCredentials);
        ListDatasetsRequest syncRequest = new ListDatasetsRequest();
        syncRequest.setIdentityId(idResp.getIdentityId());
        syncRequest.setIdentityPoolId("YOUR_COGNITO_IDENTITY_POOL_ID");
        ListDatasetsResult syncResp = syncClient.listDatasets(syncRequest);
    }
    */

    /**
     * reset : reset current connection must be used through a chain
     *
     * @return
     */
    public AWSCognitoAdapter reset() {
        this.amazonCognito = null;
        return this;
    }


}