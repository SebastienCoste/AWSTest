package fr.sttc.aws.run;

import java.io.IOException;
import java.util.List;

import com.amazonaws.regions.Regions;

import fr.sttc.aws.manager.EC2Manager;

public class EC2tester {


	public static void main(String[] args) throws IOException {

		List<Regions> regs = EC2Manager.getRegionOfEC2ActiveInstances();
		
		System.out.println(regs);

	}

}
