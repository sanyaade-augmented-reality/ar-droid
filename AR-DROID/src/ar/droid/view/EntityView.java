package ar.droid.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import ar.droid.R;

public class EntityView extends Activity {
	
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setear layout
        setContentView(R.layout.actentdemo);
       
        TextView title = (TextView) this.findViewById(R.id.title);
        title.setText("Entidad");
        
        TextView dtitle = (TextView) this.findViewById(R.id.destitle);
        dtitle.setText(getIntent().getExtras().getString("name"));
        
        
        TextView url = (TextView) this.findViewById(R.id.url);
        url.setText(getIntent().getExtras().getString("url"));
        
        TextView descr = (TextView) this.findViewById(R.id.descript);
        descr.setText(getIntent().getExtras().getString("name")  + " >>>> descripcion entidad " +getIntent().getExtras().getString("desc"));
	}
}
