package ar.droid.sound;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundManager {
	private static Context context;
	
	public static void createInstance(Context cxt){
		context = cxt;
	}
	
	public static void playSound(int resource){
		MediaPlayer mediaPlayer = MediaPlayer.create(context, resource);
		mediaPlayer.start();
	}
}
