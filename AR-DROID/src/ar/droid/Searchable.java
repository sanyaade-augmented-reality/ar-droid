package ar.droid;

import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import ar.droid.activity.EventTabWidget;
import ar.droid.admin.reader.view.EventAdapter;
import ar.droid.model.Event;
import ar.droid.model.Resource;

public class Searchable extends ListActivity implements OnItemClickListener {
	static String TAG = Searchable.class.getName();
	List<Event> events;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// pantalla completa
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		Intent intent = getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			this.setTitle("Resultados de búsqueda de eventos para '" + query + "'");
			doSearch(query);
		}
		else
			Searchable.this.finish();
		
		getListView().setOnItemClickListener(this);
	}

	public void doSearch(String textSearch) {
		// Voy a buscar, muestro popUp y al seleccionar va a pantalla Entidad
		events = Resource.getInstance().searchEvents(textSearch);

		if (!events.isEmpty()) {
			this.setListAdapter(new EventAdapter(getApplicationContext(),
					R.layout.row_list_events, events));
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("No se encontraron eventos.")
					.setCancelable(false)
					.setPositiveButton("Cerrar",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									Searchable.this.finish();
								}
							});
			builder.create();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int posistion, long id) {
		// Abrir el evento
		Searchable.this.finish();
		Event event = events.get(posistion);
		Bundle bundle = new Bundle();
		bundle.putLong("idEvent", event.getId());
		bundle.putLong("idEntity", event.getEntity().getId());
		Intent i = new Intent(getApplicationContext(), EventTabWidget.class);
        i.putExtras(bundle);
	    startActivity(i);
	}
}