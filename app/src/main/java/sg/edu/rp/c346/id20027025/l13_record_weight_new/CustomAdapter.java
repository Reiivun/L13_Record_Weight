package sg.edu.rp.c346.id20027025.l13_record_weight_new;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;
    ArrayList<Weight> songList;

    public CustomAdapter(Context context, int resource, ArrayList<Weight> objects) {
        super(context, resource, objects);

        parent_context = context;
        layout_id = resource;
        songList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(layout_id, parent, false);

        TextView tvWeight = rowView.findViewById(R.id.tvWeight);
        TextView tvDescription = rowView.findViewById(R.id.tvDescription);
        TextView tvDay = rowView.findViewById(R.id.tvDay);

        Weight currentVersion = songList.get(position);

        tvWeight.setText("" + currentVersion.getWeight());
        tvDescription.setText(currentVersion.getDescription());
        tvDay.setText("" + currentVersion.getId());

        return rowView;
    }
}
