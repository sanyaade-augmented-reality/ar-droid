package ar.droid.admin.reader;

import java.io.InputStream;
import java.util.List;

import android.util.Xml;
import ar.droid.connection.RESTClient;

public class SAXParserRSS extends ResourceNews {


	 
	 //enclosure,category luego tengo dos RSS y feed (tiene otros tags)
	 
	//implementado con 
	public List<Message> getMessages(String url) {
			InputStream instream = RESTClient.doGet(url);
			
		    try {
		    	//instream.mark(128);
	        	RssHandler handler = new RssHandler();
	            Xml.parse(instream, Xml.Encoding.UTF_8, handler);
	            if (handler.getMessages().isEmpty()){
	            	instream = RESTClient.doGet(url);
	            	//instream.reset();
	            	RssAtomHandler handlerAtom = new RssAtomHandler();	    
		        	Xml.parse(instream, Xml.Encoding.UTF_8, handlerAtom);
		            return handlerAtom.getMessages();
	            }
	            return handler.getMessages();
	        } catch (Exception e) {
	        	throw new RuntimeException(e);	            
	        }
	}
}
