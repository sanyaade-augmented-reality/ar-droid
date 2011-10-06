package ar.droid.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import ar.droid.R;
import ar.droid.admin.reader.Message;
import ar.droid.admin.reader.view.MessageNewsAdapter;
import ar.droid.model.Entity;
import ar.droid.model.Resource;
import ar.droid.sound.SoundManager;

public class ListNewsActivity extends ListActivity implements OnItemClickListener {
	
	private ProgressDialog progressDialog = null; 
	private Entity entity;
	private List<Message> messages;
	private ArrayAdapter<Message> adapter;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//se sea la vista de la actividad
		setContentView(R.layout.view_news);

        //recupero la entidad
        entity = Resource.getInstance().getEntity(getIntent().getExtras().getLong("idEntity"));
        
        //List<Message> messages = entity.getReaderNews().getMessages();
        
        messages = new ArrayList<Message>();
        
        //se crea el modelo para la actividad list
        adapter= new MessageNewsAdapter(getApplicationContext(),R.layout.list_item_news,messages);
        
        
        
        //se setea el adaptador
        setListAdapter(adapter);
        
        //se cargan las noticias en un thread
        Runnable viewNews = new Runnable(){
            public void run() {
                getMessages();
            }
        };
        Thread thread =  new Thread(null, viewNews, "MagentoBackground");
        
        //se lanza el dialogo de espera hasta que se cargen las noticas
        progressDialog = ProgressDialog.show(this,"","Cargando Noticias....");
        runOnUiThread(thread);
        
        getListView().setOnItemClickListener(this);
  	}
	
	
	private void getMessages(){
		 //recupero las noticias a mostrar
         messages = entity.getReaderNews().getMessages();   
         //se actualiza el adaptador con las noticias recuperadas
         runOnUiThread(returnRes);  
	}
	
	private Runnable returnRes = new Runnable() {
        @Override
        public void run() {
          //Incorporo las noticas al adaptador
            Iterator<Message> itMessage = messages.iterator();
            
            adapter.notifyDataSetChanged();
            while (itMessage.hasNext()) {
            	Message message = itMessage.next();
            	adapter.add(message);
   		 	}
            //notifica los cambios para actualizar la vista
            adapter.notifyDataSetChanged();
            
            //cierra el dialogo de espera
            progressDialog.dismiss();
        }
    };

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Message message = messages.get(position);
		Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse(message.getLink())); 
		startActivity(i);
		
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
