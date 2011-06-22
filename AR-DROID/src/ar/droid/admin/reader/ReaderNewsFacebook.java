package ar.droid.admin.reader;

import java.util.List;

import ar.droid.model.IReaderListener;

public class ReaderNewsFacebook extends ReaderNews {

	@Override
	public List<Message> getMessages() {
		return ResourceNewsFactory.createResourceNewsFacebook().getMessages(getParameter());
	}

	@Override
	public String getName() {
		return "Facebook";
	}

	@Override
	public void reader(IReaderListener reader) {
		reader.doReaderFacebook();		
	}



}
