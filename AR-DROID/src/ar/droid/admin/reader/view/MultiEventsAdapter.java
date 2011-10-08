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
import ar.droid.view.EventOverlayItem;
import ar.droid.view.SpotBalloon;

import com.google.android.maps.OverlayItem;

public class MultiEventsAdapter extends ArrayAdapter<OverlayItem> {

	private List<OverlayItem> eventItems; 
	
	public MultiEventsAdapter(Context context,List<OverlayItem> events) {
		super(context,R.layout.row_list_type_events,events);
		this.eventItems = events;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.row_list_type_events, null);
        }
        //se recupera el mensaje para la posicion de la lista
        EventOverlayItem te = (EventOverlayItem) eventItems.get(position);
        if (te != null) {
        		//se setan datos a mostrar
             	TextView tt = (TextView) v.findViewById(R.id.name_item_list_dialog);
             	ImageView dd = (ImageView)v.findViewById(R.id.name_image_list_dialog);
        		Drawable drawable = new SpotBalloon(Color.parseColor("#"+te.getEvent().getTypeEvent().getColor()), false);
        		dd.setImageDrawable(drawable);
                if (tt != null) {
                	tt.setText(te.getTitle());
                	tt.setTextColor(Color.BLACK);
               }            
        }
        return v;
}

}
