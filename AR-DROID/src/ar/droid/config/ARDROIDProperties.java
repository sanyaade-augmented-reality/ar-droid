package ar.droid.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

public class ARDROIDProperties {

	private static ARDROIDProperties arDROIDProperties =null;
	private Context context;
	
	private ARDROIDProperties(Context context) {
		this.context = context;
	}
	
	private static Properties properties = null;

	public  Properties getPreperties() {
		if(properties == null){
			Resources resources = context.getResources();
			AssetManager assetManager = resources.getAssets();
			try {
				
				InputStream inputStream = assetManager.open("ardroid.properties");
			
				properties = new Properties();
			    properties.load(inputStream);
			    System.out.println("properties: " + properties);
			} catch (IOException e) {
			    e.printStackTrace();
			}
		}
		return properties;
	}

	public String getProperty(String name){
		return getPreperties().getProperty(name);
	}
	
	public int getIntProperty(String name){
		return new Integer(getPreperties().getProperty(name)).intValue();
	}
	
	public boolean getBoolProperty(String name){
		return Boolean.getBoolean(getPreperties().getProperty(name));
	}
	
	public static ARDROIDProperties createProperties(Context context){
		if (arDROIDProperties == null){
			arDROIDProperties = new ARDROIDProperties(context);
		}
		return arDROIDProperties;
	}	

	public static  ARDROIDProperties getInstance(){
		return arDROIDProperties;
	}
	
}
