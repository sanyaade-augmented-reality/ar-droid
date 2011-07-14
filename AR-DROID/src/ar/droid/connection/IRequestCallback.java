package ar.droid.connection;

import java.io.InputStream;

public interface IRequestCallback {
	
	public void onError(Throwable exception);
    public void onResponseReceived(InputStream response);
}
