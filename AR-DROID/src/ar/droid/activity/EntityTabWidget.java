package ar.droid.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;
import ar.droid.R;
import ar.droid.model.Entity;
import ar.droid.model.IReader;
import ar.droid.model.Resource;

public class EntityTabWidget extends TabActivity implements IReader {
	
	private static String TAB_INFO ="INFO_TAB";
	private static String TAB_NEWS ="NEW_TAB";
	private static String TAB_EVENTS ="EVENT_TAB";
	

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
		
		//crear tab-noticias	
		entity.getReaderNews().reader(this);
		
			
		intent =  new Intent().setClass(this, ListEventsActivity.class);
		intent.putExtra("idEntity",getIntent().getExtras().getLong("idEntity"));  
		//se crea e incializa el TabSpec y se in se incorpora al tabhost 
		spec = tabHost.newTabSpec(TAB_EVENTS).setIndicator(getResources().getString(R.string.ic_tab_entity_events), getResources().getDrawable(R.drawable.ic_tab_entity_events)).setContent(intent);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);
		
	}
	
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		setResult(RESULT_CANCELED);
	}

	@Override
	public void readerNone() {
		//no hace nada
	}

	@Override
	public void readerWeb() {
		//se crean de la misma manera los demas tabs
		Intent intent =  new Intent().setClass(this, WebNewsActivity.class);
		intent.putExtra("idEntity",getIntent().getExtras().getLong("idEntity"));  
		//se crea e incializa el TabSpec y se in se incorpora al tabhost 
		TabHost.TabSpec  spec = getTabHost().newTabSpec(TAB_NEWS).setIndicator(getResources().getString(R.string.ic_tab_entity_news), getResources().getDrawable(R.drawable.news_web)).setContent(intent);
		getTabHost().addTab(spec);
	}

	@Override
	public void readerRSS() {
		tabReaderNews(R.drawable.ic_tab_entity_news);	
		
	}

	@Override
	public void readerTweeter() {
		tabReaderNews(R.drawable.ic_tab_entity_news_twitter);	
		
	}
	
	
	@Override
	public void readerFacebook() {
		tabReaderNews(R.drawable.ic_tab_entity_news_facebook);		
	}
	
	private void tabReaderNews(int ic_tab_selector){
		//se crean de la misma manera los demas tabs
		Intent intent =  new Intent().setClass(this, ListNewsActivity.class);
		intent.putExtra("idEntity",getIntent().getExtras().getLong("idEntity"));  
		//se crea e incializa el TabSpec y se in se incorpora al tabhost 
		TabHost.TabSpec  spec = getTabHost().newTabSpec(TAB_NEWS).setIndicator(getResources().getString(R.string.ic_tab_entity_news), getResources().getDrawable(ic_tab_selector)).setContent(intent);
		getTabHost().addTab(spec);
	}
	
}
