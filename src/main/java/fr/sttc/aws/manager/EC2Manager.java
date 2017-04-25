package fr.sttc.aws.manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeAvailabilityZonesResult;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;

import fr.sttc.aws.credential.DefaultCredentialProvider;

public class EC2Manager {

	private EC2Manager(){

	}

	/**
	 *  provide a client of EC2 to the default region 
	 * @return
	 */
	public static AmazonEC2 getDefaultClient(){

		AmazonEC2ClientBuilder builder = AmazonEC2Client.builder();
		builder.withCredentials(DefaultCredentialProvider.INSTANCE)
		.withRegion(DefaultCredentialProvider.INSTANCE.getRegion());

		return builder.build();
	}


	/**
	 * Provide the right AmazonEC2 instance on the provided Regions
	 * @param region the region required
	 * @return a AmazonEC2 instance to work on
	 */
	public static AmazonEC2 getEC2Client(Regions region){

		AmazonEC2ClientBuilder builder = AmazonEC2Client.builder();
		builder.withCredentials(DefaultCredentialProvider.INSTANCE)
		.withRegion(region);

		return builder.build();
	}

	/**
	 * Provide the right AmazonEC2 instance on the provided region name. 
	 * Returns null if the name in indecipherable
	 * @param regionName the region name
	 * @return a AmazonEC2 instance to work on
	 */
	public static AmazonEC2 getEC2Client(String regionName){

		Regions region = RegionsManager.getRegion(regionName);
		if (region == null){
			return null;
		}

		return getEC2Client(region);
	}

	/**
	 * Garner the region list of EC2 with instance on
	 * @return the list of Regions. Empty if nothing found or in case of connection error
	 */
	public static List<Regions> getRegionOfEC2ActiveInstances(){
		List<Regions> result = new ArrayList<>();


		for (Regions r : Regions.values()){
			AmazonEC2 ec2 = getEC2Client(r);
			if (ec2 != null){

				DescribeAvailabilityZonesResult availabilityZonesResult = ec2.describeAvailabilityZones();

				DescribeInstancesResult describeInstancesRequest = ec2.describeInstances();
				List<Reservation> reservations = describeInstancesRequest.getReservations();
				Set<Instance> instances = new HashSet<Instance>();

				for (Reservation reservation : reservations) {
					instances.addAll(reservation.getInstances());
				}
			}
		}


		return result;
	}

}
