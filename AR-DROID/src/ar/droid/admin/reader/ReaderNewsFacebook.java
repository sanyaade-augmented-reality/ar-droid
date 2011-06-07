package ar.droid.admin.reader;

import java.util.List;

public class ReaderNewsFacebook extends ReaderNews {

	@Override
	public List<Message> getMessages() {
		return ResourceNewsFactory.createResourceNewsFacebook().getMessages(getParameter());
	}



}
