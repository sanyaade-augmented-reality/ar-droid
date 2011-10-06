package ar.droid.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import ar.droid.R;
import ar.droid.model.Entity;
import ar.droid.model.Resource;
import ar.droid.sound.SoundManager;

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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_entity, menu);
		return true;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		SoundManager.playSound(R.raw.action);
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		SoundManager.playSound(R.raw.button);
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.menu_goto_entity:
				Bundle bundle = new Bundle();
				bundle.putLong("idEntity", entity.getId());
				Intent mIntent = new Intent();
	            mIntent.putExtras(bundle);
	            getParent().setResult(RESULT_OK,mIntent);
				finishFromChild(this);
				return true;
			case R.id.menu_close:
	            getParent().setResult(RESULT_CANCELED);
				this.finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
