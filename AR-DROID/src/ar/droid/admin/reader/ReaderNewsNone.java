package ar.droid.admin.reader;

import java.util.ArrayList;
import java.util.List;

public class ReaderNewsNone extends ReaderNews {

	@Override
	public List<Message> getMessages() {
		return new ArrayList<Message>();
	}

	@Override
	public String getName() {
		return "Ninguno";
	}

}
