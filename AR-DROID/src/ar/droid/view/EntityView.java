package ar.droid.view;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;
import ar.droid.R;
import ar.droid.model.Entity;
import ar.droid.model.Resource;
import ar.droid.resources.ImageHelperFactory;

public class EntityView extends Activity {
	
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// setear layout
        setContentView(R.layout.view_entity);

        //recupero la entidad
        Entity entity = new Entity();
        entity.setId(getIntent().getExtras().getLong("idEntity"));
        entity = Resource.getInstance().getEntity(entity);
        
        TextView title = (TextView) this.findViewById(R.id.title);
        title.setText(entity.getName());
       
        
       //se recupera el icono a mostrar para el tipo de entidad
		Drawable photoEntity =ImageHelperFactory.createImageHelper().getImage(entity.getPhotoUrl());
		photoEntity.setBounds(0, 0, photoEntity.getIntrinsicWidth(), photoEntity.getIntrinsicHeight());
		
        ImageView image  = (ImageView)this.findViewById(R.id.imageEntity);
        image.setImageDrawable(photoEntity);
        
        TextView url = (TextView) this.findViewById(R.id.site);
        url.setText( Html.fromHtml("<a href=\'"+entity.getUrl()+"\'> Más Información</a>" ));
        url.setMovementMethod(LinkMovementMethod.getInstance());
        
        TextView descr = (TextView) this.findViewById(R.id.description);
        descr.setText(entity.getDescription());
	}
}
