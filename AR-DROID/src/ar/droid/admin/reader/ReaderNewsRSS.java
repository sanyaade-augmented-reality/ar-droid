package ar.droid.admin.reader;

import java.util.List;

public class ReaderNewsRSS extends ReaderNews {

	
	@Override
	public List<Message> getMessages() {
		return ResourceNewsFactory.createResourceNewsRSS().getMessages(getParameter());
	}

	@Override
	public String getName() {
		return "RSS";
	}

}
