package ar.droid.admin.reader;

import java.util.List;

import ar.droid.model.IReader;

public abstract class ReaderNews{
	private String parameter;

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	
	public abstract List<Message> getMessages();
	public abstract String getName();
	
	public abstract void reader(IReader reader);	

}
