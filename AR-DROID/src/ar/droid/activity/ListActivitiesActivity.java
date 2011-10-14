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
import ar.droid.admin.reader.view.ActivityAdapter;
import ar.droid.admin.reader.view.EventAdapter;
import ar.droid.model.Activity;
import ar.droid.model.Entity;
import ar.droid.model.Event;
import ar.droid.model.Resource;
import ar.droid.sound.SoundManager;

public class ListActivitiesActivity extends ListActivity implements
		OnItemClickListener {
	private Long idEvent;
	private Long idEntity;
	
	private List<Activity> activities;
	private ArrayAdapter<Activity> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_events);

		// recupero la entidad
		idEvent = getIntent().getExtras().getLong("idEvent");
		idEntity = getIntent().getExtras().getLong("idEntity");

		// se recupera eventos de la entidad
		activities = Resource.getInstance().getAtivities(idEvent);

		// se crea el modelo para la actidad listactivity
		if(activities.size() == 0){
			Activity act = new Activity();
			act.setName("Sin actividades");
			activities.add(act);
			
		}
		adapter = new ActivityAdapter(getApplicationContext(),
				R.layout.list_item_activities, activities);

		// se setea el adaptador para la acitvidad tipo list
		setListAdapter(adapter);
		getListView().setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View v, int posistion,
			long id) {
		Activity activity = activities.get(posistion);
		if(activity.getId() == null)
			return;
		
		// TODO Mostrar la descripción en un Dialog
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
		switch (item.getItemId()) {
		case R.id.menu_goto_entity:
			Bundle bundle = new Bundle();
			bundle.putLong("idEvent", idEvent);
			bundle.putLong("idEntity", idEntity);
			Intent mIntent = new Intent();
            mIntent.putExtras(bundle);
            getParent().setResult(RESULT_OK,mIntent);
			finishFromChild(this);
		case R.id.menu_close:
			getParent().setResult(RESULT_CANCELED);
			this.finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
