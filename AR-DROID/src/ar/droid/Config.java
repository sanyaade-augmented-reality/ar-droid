package ar.droid;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class Config extends PreferenceActivity {
	static String TAG = Config.class.getName();

	@Override
	public void onCreate(Bundle savedInstanceState) {

		// pantalla completa
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		addPreferencesFromResource(R.layout.preferences);

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		
		Toast.makeText(getBaseContext(),
				"Preferencias guardadas." + "\n" + 
						"Reinicie la aplicación para aplicar los cambios.", Toast.LENGTH_LONG)
				.show();
	}
}