package ar.droid.admin.reader;

import java.util.List;

public class TwitterREST  extends ResourceNews {
	
	@Override
	public List<Message> getMessages(String uid) {
		//uid= usuario de twitter
		SAXParserRSS saxParserRSS = new SAXParserRSS();
		return saxParserRSS.getMessages("http://twitter.com/statuses/user_timeline.rss?id="+uid);
	}
}
