package ar.droid.admin.reader;

import java.util.List;

public interface IReaderNews {
	
	public List<Message> getMessages(String url);
}
