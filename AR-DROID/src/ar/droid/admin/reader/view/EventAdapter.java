package ar.droid.admin.reader.view;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ar.droid.R;
import ar.droid.model.Event;

public class EventAdapter extends ArrayAdapter<Event> {
	
	private List<Event> events; 
	public EventAdapter(Context context,int textViewResourceId,List<Event> events) {
		super(context,textViewResourceId, events);
		this.events = events;
		
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.row_list_events, null);
        }
        Event e = events.get(position);
        if (e != null) {
             	TextView tt = (TextView) v.findViewById(R.id.toptext);
                TextView bt = (TextView) v.findViewById(R.id.bottomtext);
                if (tt != null) {
                    tt.setText(e.getTitle());
               }
                if(bt != null){
                      bt.setText(e.getTypeEvent().getDescription());
                }
        }
        return v;
	}
}
