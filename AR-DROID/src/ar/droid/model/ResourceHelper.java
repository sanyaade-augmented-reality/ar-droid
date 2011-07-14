package ar.droid.model;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import ar.droid.connection.RESTClient;

public abstract class ResourceHelper  implements IResourceHelper{
	
	
	protected Reader loadManySerialized(String url){
	
		  InputStream instream = RESTClient.doGet(url);
		  if (instream!= null){
			  return new InputStreamReader(instream);
		  }
		  return null;
          
	}	
	
}
