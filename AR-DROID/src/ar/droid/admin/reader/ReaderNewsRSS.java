package ar.droid.admin.reader;

import java.util.List;

import ar.droid.model.IReaderListener;

public class ReaderNewsRSS extends ReaderNews {

	
	@Override
	public List<Message> getMessages() {
		return ResourceNewsFactory.createResourceNewsRSS().getMessages(getParameter());
	}

	@Override
	public String getName() {
		return "RSS";
	}

	@Override
	public void reader(IReaderListener reader) {
		reader.doReaderRSS();
	}

}
