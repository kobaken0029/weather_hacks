package jp.co.aizu_student.weatherhacks.views.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import jp.co.aizu_student.weatherhacks.R;
import jp.co.aizu_student.weatherhacks.databinding.ActivityLocationListRowBinding;
import jp.co.aizu_student.weatherhacks.models.Location;

public class LocationListAdapter extends ArrayAdapter<Location> {
    private LayoutInflater mLayoutInflater;

    public LocationListAdapter(Context context, int textViewResourceId, List<Location> list) {
        super(context, textViewResourceId, list);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ActivityLocationListRowBinding binding;

        if (convertView == null) {
            binding = DataBindingUtil.inflate(mLayoutInflater, R.layout.activity_location_list_row, parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (ActivityLocationListRowBinding) convertView.getTag();
        }

        binding.setLocation(getItem(position));

        return convertView;
    }
}
