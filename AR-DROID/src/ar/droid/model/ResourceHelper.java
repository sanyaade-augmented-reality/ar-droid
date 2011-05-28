package ar.droid.model;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import ar.droid.connection.ConnectionUtil;

public abstract class ResourceHelper  implements IResourceHelper {
	
	
	protected Reader loadManySerialized(String url){
	
		  InputStream instream = ConnectionUtil.getInputStream(url);
		  if (instream!= null){
			  return new InputStreamReader(instream);
		  }
		  
		  return null;
          
	}
	

}
