package ar.droid.admin.reader;

import java.util.ArrayList;
import java.util.List;

import ar.droid.model.IReaderListener;

public class ReaderNewsWeb extends ReaderNews {

	@Override
	public List<Message> getMessages() {
		return new ArrayList<Message>();
	}

	@Override
	public String getName() {
		return "Web";
	}

	@Override
	public void reader(IReaderListener reader) {
		reader.doReaderWeb();
	}

}
