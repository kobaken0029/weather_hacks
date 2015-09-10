package jp.co.aizu_student.weatherhacks.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import jp.co.aizu_student.weatherhacks.R;
import jp.co.aizu_student.weatherhacks.models.Location;


/**
 * Created by koba on 2015/09/08.
 */
public class LocationListAdapter extends ArrayAdapter<Location> {
    private LayoutInflater mLayoutInflater;

    private class ViewHolder {
        TextView mLocationText;

        ViewHolder(View view) {
            mLocationText = (TextView) view.findViewById(R.id.location);
        }
    }

    public LocationListAdapter(Context context, int textViewResourceId, List<Location> list) {
        super(context, textViewResourceId, list);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Location location = getItem(position);

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.activity_location_list_row, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (location != null) {
            holder.mLocationText.setText(location.getPrefecture() + " " + location.getCity());
        }

        return convertView;
    }
}
