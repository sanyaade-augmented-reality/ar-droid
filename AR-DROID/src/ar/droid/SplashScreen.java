package ar.droid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import ar.droid.config.ARDROIDProperties;
import ar.droid.model.Resource;

public class SplashScreen extends Activity {
	static String TAG = SplashScreen.class.getName();

	protected boolean _waitLoaded = true;
	protected int _splashTime = 3000;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// pantalla completa
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.splash);

		// crear hilo para mostrar el splash screen
		Thread splashTread = new Thread() {
			@Override
			public void run() {
				try {
					int waited = 0;
					while ((waited < _splashTime) || _waitLoaded) {
						sleep(100);
						waited += 100;
					}
				} catch (InterruptedException e) {
					// no hacer nada
				} finally {
					Intent myIntent = new Intent(SplashScreen.this, MainMap.class);
					SplashScreen.this.startActivity(myIntent);
					finish();
					stop();
				}
			}
		};
		splashTread.start();
		try {
			ARDROIDProperties.createProperties(this.getApplicationContext());
			Resource.getInstance().getEntities();
			_waitLoaded = false;
		} catch (Exception e) {
			Log.e(TAG, "ERROR", e);
			showError();
		}
	}

	private void showError() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("No se puede conectar con el servidor!").setCancelable(false)
				.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						int pid = android.os.Process.myPid();
						android.os.Process.killProcess(pid);
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

}