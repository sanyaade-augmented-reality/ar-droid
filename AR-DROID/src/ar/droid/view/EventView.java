package ar.droid.view;

import java.text.SimpleDateFormat;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;
import ar.droid.R;
import ar.droid.model.Entity;
import ar.droid.model.Event;
import ar.droid.model.Resource;
import ar.droid.resources.ImageHelperFactory;

public class EventView extends Activity {
	
		private Event event;
		private Entity entity;
		static SimpleDateFormat formateador = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy HH:mm 'hs.'");
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.view_event);
			
			 //recupero la entidad
			entity = Resource.getInstance().getEntity(getIntent().getExtras().getLong("idEntity"));
			event = entity.getEvent(getIntent().getExtras().getLong("idEvent"));
	        
	        TextView title = (TextView) this.findViewById(R.id.title);
	        title.setText(event.getTitle());
	        
	        TextView subtitle = (TextView) this.findViewById(R.id.sutbtitle);
	        subtitle.setText(event.getTypeEvent().getDescription());
	       
	        
	       //se recupera el icono a mostrar para el tipo de entidad
			Drawable imageEvent =ImageHelperFactory.createImageHelper().getImageEvent(event);
			imageEvent.setBounds(0, 0, imageEvent.getIntrinsicWidth(), imageEvent.getIntrinsicHeight());
			
	        ImageView image  = (ImageView)this.findViewById(R.id.imageEntity);
	        image.setImageDrawable(imageEvent);
	        
	        TextView url = (TextView) this.findViewById(R.id.site);
	        url.setText( Html.fromHtml("<a href=\'"+entity.getUrl()+"\'> "+entity.getName()+"</a>" ));
	        url.setMovementMethod(LinkMovementMethod.getInstance());
	        
	        TextView descr = (TextView) this.findViewById(R.id.description);
	        descr.setText(event.getDescription());
	        
	        TextView place = (TextView) this.findViewById(R.id.place);
	        place.setText("Lugar: ...........");
	        
	     
	        TextView initdate = (TextView) this.findViewById(R.id.Initdate);
	        initdate.setText("Inicio: " + formateador.format(event.getEventCalendar().getStartDate()));
	        

	        TextView enddate = (TextView) this.findViewById(R.id.enddate);
	        enddate.setText("Fin: "+formateador.format(event.getEventCalendar().getEndDate()));
	        
	       /* ImageButton button = (ImageButton) findViewById(R.id.btn_ir_a);
	        button.setOnClickListener(this);*/
		}

}
