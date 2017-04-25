package fr.sttc.aws.manager;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;

import fr.sttc.aws.credential.DefaultCredentialProvider;

public class S3Manager {

	private S3Manager(){
		
	}

	/**
	 *  Create a default instance based on the DefaultCredentialProvider
	 *  
	 * @return a AmazonS3 instance to work on
	 */
	public static AmazonS3 getDefaultS3instance(){

		AmazonS3ClientBuilder builder = AmazonS3Client.builder();
		builder.withCredentials(DefaultCredentialProvider.INSTANCE)
		.withRegion(DefaultCredentialProvider.INSTANCE.getRegion());

		return builder.build();
	}
	
	/**
	 * Provide the right AmazonS3 instance on the provided Regions
	 * @param region the region required
	 * @return a AmazonS3 instance to work on
	 */
	public static AmazonS3 getS3instance(Regions region){

		AmazonS3ClientBuilder builder = AmazonS3Client.builder();
		builder.withCredentials(DefaultCredentialProvider.INSTANCE)
		.withRegion(region);

		return builder.build();
	}
	
	/**
	 * Provide the right AmazonS3 instance on the provided region name. 
	 * Returns null if the name in indecipherable
	 * @param regionName the region name
	 * @return a AmazonS3 instance to work on
	 */
	public static AmazonS3 getS3instance(String regionName){
		
		Regions region = RegionsManager.getRegion(regionName);
		if (region == null){
			return null;
		}
		
		return getS3instance(region);
	}
	
	/**
	 * Garner the region list of S3 with data on
	 * @return the list of Regions. Empty if nothing found or in case of connection error
	 */
	public static List<Regions> getRegionOfS3ActiveInstances(){
		List<Regions> result = new ArrayList<>();
		
		AmazonS3 s3 = getDefaultS3instance();
		if (s3 == null){
			//TODO Error management
			return result;
		}
		for (Bucket bucket : s3.listBuckets()) {
			result.add(RegionsManager.getRegion(s3.getBucketLocation(bucket.getName())));
		}
		
		return result;
	}



}
