package ar.droid.activity;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import ar.droid.R;
import ar.droid.admin.reader.view.EventAdapter;
import ar.droid.model.Entity;
import ar.droid.model.Event;
import ar.droid.model.Resource;

public class ListEventsActivity extends ListActivity implements OnItemClickListener  {
	
	private Entity entity;
	private List<Event> events;
	private ArrayAdapter<Event> adapter;

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
}
