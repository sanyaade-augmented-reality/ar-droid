package ar.droid.sound;

import android.content.Context;
import android.media.MediaPlayer;
import ar.droid.config.AppPreferences;

public class SoundManager {
	private static boolean soundEnable = AppPreferences.getBool(
			"soundEnable", false);
	private static Context context;

	public static void createInstance(Context cxt) {
		context = cxt;
	}

	public static void playSound(final int resource) {
		if (soundEnable)
			return;

		Thread runnable = new Thread() {
			@Override
			public void run() {
				MediaPlayer.create(context, resource).start();
			}
		};
		runnable.start();
	}
}
