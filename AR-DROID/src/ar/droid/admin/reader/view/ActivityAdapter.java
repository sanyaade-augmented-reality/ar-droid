package ar.droid.admin.reader.view;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ar.droid.R;
import ar.droid.model.Activity;

public class ActivityAdapter extends ArrayAdapter<Activity> {

	private List<Activity> activities;

	public ActivityAdapter(Context context, int textViewResourceId,
			List<Activity> activities) {
		super(context, textViewResourceId, activities);
		this.activities = activities;

	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.row_list_activities, null);
		}
		Activity activity = activities.get(position);
		if (activity != null) {
			TextView tt = (TextView) v.findViewById(R.id.toptext);
			TextView bt = (TextView) v.findViewById(R.id.bottomtext);
			if (tt != null) {
				tt.setText(activity.getName());
			}
			if (bt != null) {
				if(activity.getTypeActivity() == null)
					bt.setText("El evento no define actividades");
				else
					bt.setText(activity.getTypeActivity().getDescription());
			}
		}
		return v;
	}
}
