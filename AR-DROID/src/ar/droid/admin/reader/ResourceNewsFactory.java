package ar.droid.admin.reader;


public class ResourceNewsFactory {
	
	public static ResourceNews createResourceNewsRSS (){
		return new SAXParserRSS();
	}
	
	public static ResourceNews createResourceNewsFacebook (){
		return new FacebookREST();
	}
	
	public static ResourceNews createResourceNewsTwitter (){
		return new TwitterREST();
	}
}
