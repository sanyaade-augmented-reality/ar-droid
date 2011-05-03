package ar.droid.model;


public abstract class ResourceHelperFactory {
	
	public static ResourceHelper createResourceHelper (){
		return new ResourceHelperJSON();
	}

}
