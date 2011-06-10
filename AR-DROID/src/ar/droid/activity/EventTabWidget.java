package ar.droid.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;
import ar.droid.R;

public class EventTabWidget extends TabActivity {
	
	private static String TAB_INFO ="INFO_TAB";
	private static String TAB_ACTIVITY ="ACT_TAB";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// pantalla completa
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
		//se setea el layout para tabs
		setContentView(R.layout.tab_host);
		
		//TabHost del la actividad
		TabHost tabHost = getTabHost();
		
		//se crea inten para lanzar la actividad correspondiente al tab main
		
		Intent intent =  new Intent().setClass(this, EventActivity.class);
		intent.putExtra("idEvent",getIntent().getExtras().getLong("idEvent"));    
		intent.putExtra("idEntity",getIntent().getExtras().getLong("idEntity"));
		
	    //se crea e incializa el TabSpec y se in se incorpora al tabhost 
		TabHost.TabSpec spec = tabHost.newTabSpec(TAB_INFO).setIndicator(getResources().getString(R.string.ic_tab_events_main), getResources().getDrawable(R.drawable.ic_tab_event_main)).setContent(intent);
		tabHost.addTab(spec);
		
		//se crean de la misma manera los demas tabs
		intent =  new Intent().setClass(this, ActivityView.class);
		intent.putExtra("idEvent",getIntent().getExtras().getLong("idEvent"));  
		intent.putExtra("idEntity",getIntent().getExtras().getLong("idEntity"));
		//se crea e incializa el TabSpec y se in se incorpora al tabhost 
		spec = tabHost.newTabSpec(TAB_ACTIVITY).setIndicator(getResources().getString(R.string.ic_tab_events_act), getResources().getDrawable(R.drawable.ic_tab_event_activity)).setContent(intent);
		tabHost.addTab(spec);
		
		tabHost.setCurrentTab(0);
		
	}

}
