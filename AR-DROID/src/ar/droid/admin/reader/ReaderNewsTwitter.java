package ar.droid.admin.reader;

import java.util.List;

import ar.droid.model.IReader;

public class ReaderNewsTwitter extends ReaderNews {

	@Override
	public List<Message> getMessages() {
		return ResourceNewsFactory.createResourceNewsTwitter().getMessages(getParameter());
	}

	@Override
	public String getName() {
		return "Twitter";
	}

	@Override
	public void reader(IReader reader) {
		reader.readerTweeter();
		
	}


}
