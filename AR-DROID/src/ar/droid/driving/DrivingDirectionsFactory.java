package ar.droid.driving;

public class DrivingDirectionsFactory {

	public static DrivingDirections createDrivingDirections (){
		return new DrivingDirectionsGoogleJSON();
	}

}	
