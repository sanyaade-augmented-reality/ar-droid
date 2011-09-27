package ar.droid.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ARDroidPreferences {

	private static SharedPreferences preferences = null;

	public static SharedPreferences createPreferences(Context context) {
		if (preferences == null) {
			preferences = PreferenceManager
					.getDefaultSharedPreferences(context);
		}
		return preferences;
	}

	private static SharedPreferences getPreferences() {
		return preferences;
	}

	public static String getString(String key, String defValue) {
		if(ARDroidPreferences.getPreferences() == null)
			return defValue;
		try {
			return ARDroidPreferences.getPreferences().getString(key, defValue);
		} catch (Exception e) {
			return defValue;
		}
	}

	public static int getInt(String key, int defValue) {
		if(ARDroidPreferences.getPreferences() == null)
			return defValue;
		try {
			return new Integer(ARDroidPreferences.getPreferences().getString(
					key, new Integer(defValue).toString())).intValue();
		} catch (Exception e) {
			return ARDroidPreferences.getPreferences().getInt(key, defValue);
		}
	}

	public static float getFloat(String key, float defValue) {
		if(ARDroidPreferences.getPreferences() == null)
			return defValue;
		try {
			return new Float(ARDroidPreferences.getPreferences().getString(key,
					new Float(defValue).toString())).floatValue();
		} catch (Exception e) {
			return ARDroidPreferences.getPreferences().getFloat(key, defValue);
		}
	}

	public static boolean getBool(String key, boolean defValue) {
		if(ARDroidPreferences.getPreferences() == null)
			return defValue;
		try {
			return new Boolean(ARDroidPreferences.getPreferences().getString(
					key, new Boolean(defValue).toString())).booleanValue();
		} catch (Exception e) {
			return ARDroidPreferences.getPreferences()
					.getBoolean(key, defValue);
		}
	}
}
