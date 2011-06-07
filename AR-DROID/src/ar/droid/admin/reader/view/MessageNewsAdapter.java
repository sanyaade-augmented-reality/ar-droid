package ar.droid.admin.reader.view;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ar.droid.R;
import ar.droid.admin.reader.Message;

public class MessageNewsAdapter extends ArrayAdapter<Message> {

	private List<Message> messages; 
	public MessageNewsAdapter(Context context,int textViewResourceId,List<Message> messages) {
		super(context,textViewResourceId, messages);
		this.messages = messages;
		
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //se recupera la vista para cada elemento de la lista
            v = vi.inflate(R.layout.row_list_news, null);
        }
        //se recupera el mensaje para la posicion de la lista
        Message m = messages.get(position);
        if (m != null) {
        	 
        		//se setan datos a mostrar
             	TextView tt = (TextView) v.findViewById(R.id.toptext);
                TextView bt = (TextView) v.findViewById(R.id.bottomtext);
                
                if (tt != null) {
                	//tt.setText( Html.fromHtml("<a href=\'"+m.getLink()+"\'> "+m.getTitle()+"</a>" ));
                	//tt.setMovementMethod(LinkMovementMethod.getInstance());
                	//Log.i("link rss ",""+m.getLink());
                    tt.setText(m.getTitle());
               }
                if(bt != null){
                      bt.setText(m.getDate());
                }
        }
        return v;
}

}
