package ar.droid.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppPreferences {

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
		if(AppPreferences.getPreferences() == null)
			return defValue;
		try {
			return AppPreferences.getPreferences().getString(key, defValue);
		} catch (Exception e) {
			return defValue;
		}
	}

	public static int getInt(String key, int defValue) {
		if(AppPreferences.getPreferences() == null)
			return defValue;
		try {
			return new Integer(AppPreferences.getPreferences().getString(
					key, new Integer(defValue).toString())).intValue();
		} catch (Exception e) {
			return AppPreferences.getPreferences().getInt(key, defValue);
		}
	}

	public static float getFloat(String key, float defValue) {
		if(AppPreferences.getPreferences() == null)
			return defValue;
		try {
			return new Float(AppPreferences.getPreferences().getString(key,
					new Float(defValue).toString())).floatValue();
		} catch (Exception e) {
			return AppPreferences.getPreferences().getFloat(key, defValue);
		}
	}

	public static boolean getBool(String key, boolean defValue) {
		if(AppPreferences.getPreferences() == null)
			return defValue;
		try {
			return new Boolean(AppPreferences.getPreferences().getString(
					key, new Boolean(defValue).toString())).booleanValue();
		} catch (Exception e) {
			return AppPreferences.getPreferences()
					.getBoolean(key, defValue);
		}
	}
}
