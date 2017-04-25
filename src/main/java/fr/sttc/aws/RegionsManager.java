package fr.sttc.aws;

import com.amazonaws.regions.Regions;

public class RegionsManager {

	private RegionsManager(){
		
	}
	
	/**
	 *  get the region from the provided name
	 *  returns null if nothing is found
	 * @param regionName
	 * @return
	 */
	public static Regions getRegion(String regionName) {
		
		if (regionName == null){
			return null;
		}
		for (Regions r : Regions.values()){
			if (regionName.toUpperCase().equals(r.getName().toUpperCase())){
				return r;
			}
		}
		return null;
	}
	
}
