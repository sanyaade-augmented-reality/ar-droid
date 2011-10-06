package ar.droid.activity;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import ar.droid.R;
import ar.droid.admin.reader.view.EventAdapter;
import ar.droid.model.Entity;
import ar.droid.model.Event;
import ar.droid.model.Resource;
import ar.droid.sound.SoundManager;

public class ListEventsActivity extends ListActivity implements OnItemClickListener  {
	
	private Entity entity;
	private List<Event> events;
	private ArrayAdapter<Event> adapter;

	private static int RESULT_EVENTS=5; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_events);
		
		 //recupero la entidad
        entity = Resource.getInstance().getEntity(getIntent().getExtras().getLong("idEntity"));
        
        //se recupera eventos de la entidad
        events = Resource.getInstance().getEvents(entity);
		
        //se crea el modelo para la actidad listactivity
	    adapter= new EventAdapter(getApplicationContext(),R.layout.list_item_events,events);
	        
	    //se setea el adaptador para la acitvidad tipo list
	    setListAdapter(adapter);
	    
	    getListView().setOnItemClickListener(this);
	}


	@Override
	public void onItemClick(AdapterView<?> adapter, View v, int posistion, long id) {
		// TODO Auto-generated method stub
		Log.i("list", posistion + " " + id);	
		Event event = events.get(posistion);
		Bundle bundle = new Bundle();
		bundle.putLong("idEvent", event.getId());
		bundle.putLong("idEntity", entity.getId());
		Intent i = new Intent(getApplicationContext(), EventTabWidget.class);
        i.putExtras(bundle);
	    startActivity(i);
	    //finishFromChild(this);	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_entity, menu);
		return true;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		SoundManager.playSound(R.raw.action);
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		SoundManager.playSound(R.raw.button);
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.menu_goto_entity:
				Bundle bundle = new Bundle();
				bundle.putLong("idEntity", entity.getId());
				Intent mIntent = new Intent();
	            mIntent.putExtras(bundle);
	            getParent().setResult(RESULT_OK,mIntent);
				finishFromChild(this);
				return true;
			case R.id.menu_close:
	            getParent().setResult(RESULT_CANCELED);
				this.finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
}
