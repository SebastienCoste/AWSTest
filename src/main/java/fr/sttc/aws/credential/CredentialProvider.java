package fr.sttc.aws.credential;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;

public enum CredentialProvider implements AWSCredentialsProvider{

	DEFAULT,
	EC2;
	
	private AWSCredentials credentials = null;
	

	public AWSCredentials getCredentials() {
		if (credentials == null){
			refresh();
		}
		return credentials;
	}

	public void refresh() {
	        try {
	            credentials = new ProfileCredentialsProvider(getCredentialName()).getCredentials();
	        } catch (Exception e) {
	            throw new AmazonClientException(
	                    "Cannot load the credentials from the credential profiles file. " +
	                    "Please make sure that your credentials file is at the correct " +
	                    "location (/home/static/.aws/credentials), and is in valid format.",
	                    e);
	        }
	}
	
	private String getCredentialName(){
		
		switch(this){
		case DEFAULT: return "default";
		case EC2: return "ec2profile";
		}
		
		return "";
	}
	
	public Regions getRegion(){
		
		switch(this){
		case DEFAULT: return Regions.US_WEST_2;
		case EC2: return Regions.CA_CENTRAL_1;
		}
		
		return Regions.US_WEST_2;
	}
}
