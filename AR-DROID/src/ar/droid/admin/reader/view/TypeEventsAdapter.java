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
import ar.droid.model.TypeEvent;
import ar.droid.view.SpotBalloon;

public class TypeEventsAdapter extends ArrayAdapter<TypeEvent> {

	private List<TypeEvent> typeEvents; 
	
	public TypeEventsAdapter(Context context,List<TypeEvent> typeEvents) {
		super(context,R.layout.row_list_type_events,typeEvents);
		this.typeEvents = typeEvents;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //se recupera la vista para cada elemento de la lista
            v = vi.inflate(R.layout.row_list_type_events, null);
        }
        //se recupera el mensaje para la posicion de la lista
        TypeEvent te = typeEvents.get(position);
        if (te != null) {
        		//se setan datos a mostrar
             	TextView tt = (TextView) v.findViewById(R.id.name_item_list_dialog);
             	ImageView dd = (ImageView)v.findViewById(R.id.name_image_list_dialog);
        		Drawable drawable = new SpotBalloon(Color.parseColor("#"+te.getColor()));
        		dd.setImageDrawable(drawable);
                if (tt != null) {
                	tt.setText(te.getDescription());
                	tt.setTextColor(Color.BLACK);
               }            
        }
        return v;
}

}
