package ar.droid.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;
import ar.droid.R;
import ar.droid.admin.reader.ReaderNewsFacebook;
import ar.droid.admin.reader.ReaderNewsRSS;
import ar.droid.admin.reader.ReaderNewsTwitter;
import ar.droid.model.Entity;
import ar.droid.model.Resource;

public class EntityTabWidget extends TabActivity {
	
	private static String TAB_INFO ="INFO_TAB";
	private static String TAB_NEWS ="NEW_TAB";
	private static String TAB_EVENTS ="EVENT_TAB";
	
	private static Map<String, Integer> IC_TAB_NEWS = new HashMap<String, Integer>();
	static{
		IC_TAB_NEWS.put(new ReaderNewsRSS().getName(), R.drawable.ic_tab_entity_news);
		IC_TAB_NEWS.put(new ReaderNewsTwitter().getName(), R.drawable.ic_tab_entity_news_twitter);
		IC_TAB_NEWS.put(new ReaderNewsFacebook().getName(), R.drawable.ic_tab_entity_news_twitter);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// pantalla completa
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		//se setea el layout para tabs
		setContentView(R.layout.tab_host);
		
		//TabHost del la actividad
		TabHost tabHost = getTabHost();
		
		Entity entity = Resource.getInstance().getEntity(getIntent().getExtras().getLong("idEntity"));
		
		//se crea inten para lanzar la actividad correspondiente al tab main
		Intent intent =  new Intent().setClass(this, EntityActivity.class);
		intent.putExtra("idEntity",getIntent().getExtras().getLong("idEntity"));    
		
	    //se crea e incializa el TabSpec y se in se incorpora al tabhost 
		TabHost.TabSpec spec = tabHost.newTabSpec(TAB_INFO).setIndicator(getResources().getString(R.string.ic_tab_entity_main), getResources().getDrawable(R.drawable.ic_tab_entity_main)).setContent(intent);
		tabHost.addTab(spec);
		
		//se crean de la misma manera los demas tabs
		
		intent =  new Intent().setClass(this, ListNewsActivity.class);
		intent.putExtra("idEntity",getIntent().getExtras().getLong("idEntity"));  
		//se crea e incializa el TabSpec y se in se incorpora al tabhost 
		spec = tabHost.newTabSpec(TAB_NEWS).setIndicator(getResources().getString(R.string.ic_tab_entity_news), getResources().getDrawable(getResourceTab(entity))).setContent(intent);
		tabHost.addTab(spec);
		
		intent =  new Intent().setClass(this, ListEventsActivity.class);
		intent.putExtra("idEntity",getIntent().getExtras().getLong("idEntity"));  
		//se crea e incializa el TabSpec y se in se incorpora al tabhost 
		spec = tabHost.newTabSpec(TAB_EVENTS).setIndicator(getResources().getString(R.string.ic_tab_entity_events), getResources().getDrawable(R.drawable.ic_tab_entity_events)).setContent(intent);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);
		
	}
	
	private Integer getResourceTab(Entity entity){
		if (IC_TAB_NEWS.get(entity.getReaderNews().getName())== null)
			return R.drawable.ic_tab_entity_news;
		else return IC_TAB_NEWS.get(entity.getReaderNews().getName());
		
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		setResult(RESULT_CANCELED);
	}
	
	/*public void finishFromChild(Activity child) {
		Intent intent = child.getIntent();
	    setResult(RESULT_OK,intent);
	    super.finishFromChild(child);
	}*/

}
