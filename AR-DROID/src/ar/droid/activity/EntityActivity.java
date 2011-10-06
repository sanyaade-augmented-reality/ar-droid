package ar.droid.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import ar.droid.R;
import ar.droid.model.Entity;
import ar.droid.model.Resource;
import ar.droid.resources.ImageHelperFactory;

public class EntityActivity extends Activity {
	
	private Entity entity;	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// setear layout
        setContentView(R.layout.view_entity);

        //recupero la entidad
        entity = Resource.getInstance().getEntity(getIntent().getExtras().getLong("idEntity"));
        
        TextView title = (TextView) this.findViewById(R.id.title);
        title.setText(entity.getName());
       
        
       //se recupera el icono a mostrar para el tipo de entidad
		Drawable photoEntity =ImageHelperFactory.createImageHelper().getImageEntity(entity);
		photoEntity.setBounds(0, 0, photoEntity.getIntrinsicWidth(), photoEntity.getIntrinsicHeight());
		
        ImageView image  = (ImageView)this.findViewById(R.id.imageEntity);
        image.setImageDrawable(photoEntity);
        
        TextView url = (TextView) this.findViewById(R.id.site);
        url.setText( Html.fromHtml("<a href=\'"+entity.getUrl()+"\'>"+entity.getUrl()+"</a>" ));
        url.setMovementMethod(LinkMovementMethod.getInstance());
        
        TextView descr = (TextView) this.findViewById(R.id.description);
        descr.setText(entity.getDescription());
        
       // ImageButton button = (ImageButton) findViewById(R.id.btn_ir_a);
       // button.setOnClickListener(this);
   }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_entity, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
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
				this.finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
