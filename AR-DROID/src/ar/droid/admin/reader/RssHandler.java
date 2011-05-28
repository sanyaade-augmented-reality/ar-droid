package ar.droid.admin.reader;


import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RssHandler extends DefaultHandler {

	

	static final String PUB_DATE = "pubDate";
	 static final  String DESCRIPTION = "description";
	 static final  String LINK = "link";
	 static final  String TITLE = "title";
	 static final  String ITEM = "item";
	 
	private List<Message> messages;
    private Message currentMessage ;
    private StringBuilder builder;
    
    public List<Message> getMessages(){
        return this.messages;
    }
    @Override
    public void characters(char[] ch, int start, int length)  throws SAXException {
        super.characters(ch, start, length);
        if (builder!= null)
        	builder.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String name)throws SAXException {
        super.endElement(uri, localName, name);
        if (this.currentMessage != null){
            if (localName.equalsIgnoreCase(getTagTitle())){
                currentMessage.setTitle(builder.toString());
            } else if (localName.equalsIgnoreCase(getTagLink())){
                currentMessage.setLink(builder.toString());
            } else if (localName.equalsIgnoreCase(getTagDescription())){
                currentMessage.setDescription(builder.toString());
            } else if (localName.equalsIgnoreCase(getTagPubDate())){
                currentMessage.setDate(builder.toString());
            } else if (localName.equalsIgnoreCase(getTagItem())){
                messages.add(currentMessage);
            }
            builder.setLength(0);    
        }
    }

    @Override
    public void startDocument() throws SAXException {
    	super.startDocument();
        messages = new ArrayList<Message>();
       
    }
    
    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, name, attributes);
        if (localName.equalsIgnoreCase(getTagItem())){
            this.currentMessage = new Message();
            builder = new StringBuilder();
        }
    }


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
    protected Message getCurrentMessage() {
		return currentMessage;
	}
	protected void setCurrentMessage(Message currentMessage) {
		this.currentMessage = currentMessage;
	}
	protected StringBuilder getBuilder() {
		return builder;
	}
	protected void setBuilder(StringBuilder builder) {
		this.builder = builder;
	}
}
