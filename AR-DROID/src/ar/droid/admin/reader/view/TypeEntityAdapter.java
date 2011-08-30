package ar.droid.admin.reader.view;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ar.droid.R;
import ar.droid.model.TypeEntity;
import ar.droid.resources.ImageHelperFactory;

public class TypeEntityAdapter extends ArrayAdapter<TypeEntity> {

	private List<TypeEntity> typeEntity; 
	
	public TypeEntityAdapter(Context context,List<TypeEntity> typeEntity) {
		super(context,R.layout.row_list_type_events,typeEntity);
		this.typeEntity = typeEntity;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //se recupera la vista para cada elemento de la lista
            v = vi.inflate(R.layout.row_list_type_events, null);
        }
        //se recupera el mensaje para la posicion de la lista
        TypeEntity te = typeEntity.get(position);
        if (te != null) {
        		//se setan datos a mostrar
             	TextView tt = (TextView) v.findViewById(R.id.name_item_list_dialog);
             	ImageView dd = (ImageView)v.findViewById(R.id.name_image_list_dialog);
            	Drawable iconTypeEntity = ImageHelperFactory.createImageHelper().getIconTypeEntity(te);
        		dd.setImageDrawable(iconTypeEntity);
                if (tt != null) {
                	tt.setText(te.getDescription());
                	tt.setTextColor(Color.BLACK);
               }            
        }
        return v;
}

}
