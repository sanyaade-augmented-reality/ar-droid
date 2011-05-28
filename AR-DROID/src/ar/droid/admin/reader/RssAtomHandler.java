package ar.droid.admin.reader;



public class RssAtomHandler extends RssHandler {

	 static final String PUB_DATE = "updated";
	 static final  String DESCRIPTION = "summary";
	 static final  String LINK = "link";
	 static final  String TITLE = "title";
	 static final  String ITEM = "entry";
	 
	 
	 protected String getTagItem(){
	   	return ITEM;
	  }
	   
	  protected String getTagTitle(){
	   	return TITLE;
	  }
	   
	   protected String getTagLink(){
	   	return LINK;
	   }
	    
	   protected String getTagDescription(){
	   	return DESCRIPTION;
	   }
	    
	   protected String getTagPubDate(){
	   	return PUB_DATE;
	   }
	 
	  
	   
}
