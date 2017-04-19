package fr.sttc.aws;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;

public class S3Manager {



	public static AmazonS3 getDefaultS3instance(){

		AmazonS3ClientBuilder builder = AmazonS3Client.builder();
		builder.withCredentials(DefaultCredentialProvider.INSTANCE)
		.withRegion(DefaultCredentialProvider.INSTANCE.getRegion());

		return builder.build();
	}

	public static List<AmazonS3> getActiveS3instance(){
		return Arrays.asList(Regions.values()).stream()
				.map(e -> getS3Instance(e))
				.filter(e -> e != null && hasBucket(e)) //Wrong way because check bucket is in global space
				.collect(Collectors.toList());
	}

	
	private static boolean hasBucket(AmazonS3 s3) {

		try{
			List<Bucket> buckets = s3.listBuckets();
			return buckets != null && buckets.size() > 0;
		} catch (AmazonS3Exception ase){
			//You can't connect ? you have nothing there
			return false;
		}
	}

	public static AmazonS3 getS3Instance(Regions r){

		AmazonS3ClientBuilder builder = AmazonS3Client.builder();
		builder.withCredentials(DefaultCredentialProvider.INSTANCE)
		.withRegion(r);

		return builder.build();
	}

}
