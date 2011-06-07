package ar.droid.driving;

public interface IDirectionsListener {
	
	public void directionAvailable(Route route);
	
	public void directionNotAvailable();
	
	
}
