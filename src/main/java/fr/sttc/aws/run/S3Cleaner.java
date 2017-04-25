package fr.sttc.aws.run;

import java.io.IOException;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;

import fr.sttc.aws.manager.S3Manager;

public class S3Cleaner {


	public static void main(String[] args) throws IOException {

		AmazonS3 s3 = S3Manager.getDefaultS3instance();
		System.out.println("Listing buckets");
		if (s3 == null){
			System.out.println("Nothing to see");
			return;
		}
		System.out.println("We're on " + s3.getRegionName());
		for (Bucket bucket : s3.listBuckets()) {
			String bucketName = bucket.getName();
			String location = s3.getBucketLocation(bucketName);
			System.out.println(" - " + bucketName + " on " + location);
			AmazonS3 remoteS3 = S3Manager.getS3instance(location);
			if (remoteS3 != null){
				System.out.println("Connected on " + remoteS3.getRegionName());
				if (remoteS3.doesBucketExist(bucketName)){
					System.out.println("Deleting bucket " + bucketName + "\n");
					//remoteS3.deleteBucket(bucketName);
				}
			} else {
				System.out.println("Can't connect to " + location);
			}

		}
		System.out.println();

	}

}
