package ar.droid.resources;


public class ImageHelperFactory {
	
	public static ImageHelper createImageHelper (){
		return new ImageHelperHTTP();
	}

}
