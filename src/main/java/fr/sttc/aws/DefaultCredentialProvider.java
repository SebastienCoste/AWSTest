package fr.sttc.aws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;

public enum DefaultCredentialProvider implements AWSCredentialsProvider{

	INSTANCE;
	
	private AWSCredentials credentials = null;
	

	public AWSCredentials getCredentials() {
		if (credentials == null){
			refresh();
		}
		return credentials;
	}

	public void refresh() {
	        try {
	            credentials = new ProfileCredentialsProvider("default").getCredentials();
	        } catch (Exception e) {
	            throw new AmazonClientException(
	                    "Cannot load the credentials from the credential profiles file. " +
	                    "Please make sure that your credentials file is at the correct " +
	                    "location (/home/static/.aws/credentials), and is in valid format.",
	                    e);
	        }
	}
	
	public Regions getRegion(){
		
		return Regions.US_WEST_2;
	}
}
