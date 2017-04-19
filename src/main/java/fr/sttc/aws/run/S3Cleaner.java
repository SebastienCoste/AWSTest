package fr.sttc.aws.run;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;

import fr.sttc.aws.S3Manager;

public class S3Cleaner {


	public static void main(String[] args) throws IOException {

		//		List<AmazonS3> allS3 = S3Manager.getActiveS3instance();
		List<AmazonS3> allS3 = Arrays.asList(S3Manager.getDefaultS3instance());
		System.out.println("Listing buckets");
		if (allS3 == null || allS3.isEmpty()){
			System.out.println("Nothing to see");
			return;
		}
		for (AmazonS3 s3 : allS3){
			System.out.println(s3.getRegionName());
			for (Bucket bucket : s3.listBuckets()) {
				String bucketName = bucket.getName();
				String location = s3.getBucketLocation(bucketName);
				System.out.println(" - " + bucketName + " - " + location);
				//				if (s3.doesBucketExist(bucketName)){
				//					System.out.println("Deleting bucket " + bucketName + "\n");
				//					s3.deleteBucket(bucketName);
				//				}
			}
		}
		System.out.println();

	}

}
