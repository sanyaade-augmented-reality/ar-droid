package ar.droid.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import ar.droid.R;
import ar.droid.admin.reader.Message;
import ar.droid.model.Entity;
import ar.droid.model.Resource;

public class NewsView extends ListActivity {
	
	private ProgressDialog progressDialog = null; 
	private Entity entity;
	private List<Message> messages;
	private ArrayAdapter<Message> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_news);

      
        //recupero la entidad
        entity = new Entity();
        entity.setId(getIntent().getExtras().getLong("idEntity"));
        entity = Resource.getInstance().getEntity(entity);
        
        //List<Message> messages = entity.getReaderNews().getMessages();
        
        messages = new ArrayList<Message>();
        
        adapter= new ArrayAdapter<Message>(this,R.layout.list_item_news ,messages);
        
        setListAdapter(adapter);
        
        Runnable viewOrders = new Runnable(){
            public void run() {
                getMessages();
            }
        };
        Thread thread =  new Thread(null, viewOrders, "MagentoBackground");
        thread.start();
        
        progressDialog = ProgressDialog.show(this,"Por favor espere..","Cargando Noticias....");
  	}
	
	
	private void getMessages(){
		 //recupero las noticias a mostrar
         messages = entity.getReaderNews().getMessages();   
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
            
            //cierra el diaglo de espera
            progressDialog.dismiss();
        }
    };

}
