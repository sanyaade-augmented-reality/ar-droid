package ar.droid.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebView;
import ar.droid.R;
import ar.droid.model.Entity;
import ar.droid.model.Resource;

public class WebNewsActivity extends Activity {
	private ProgressDialog progressDialog;
	private WebView webView;
	Entity entity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_news_web);

		webView = (WebView) this.findViewById(R.id.webview);

		// recupero la entidad
		entity = Resource.getInstance().getEntity(getIntent().getExtras().getLong("idEntity"));

		// crear mensaje de espera
		progressDialog = ProgressDialog.show(this, "Por favor espere..", "Cargando Noticias....");

		Thread thread = new Thread() {
			@Override
			public void run() {
				// cargar url
				webView.loadUrl(entity.getReaderNews().getParameter());

				try {
					while (webView.getProgress() < 100)
						sleep(100);
					progressDialog.dismiss();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		thread.start();
	}
}
