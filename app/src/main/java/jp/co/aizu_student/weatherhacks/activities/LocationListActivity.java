package jp.co.aizu_student.weatherhacks.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import java.util.List;

import javax.inject.Inject;

import jp.co.aizu_student.weatherhacks.R;
import jp.co.aizu_student.weatherhacks.databinding.ActivityLocationListBinding;
import jp.co.aizu_student.weatherhacks.helpers.WeatherHacksRssHelper;
import jp.co.aizu_student.weatherhacks.models.Location;
import jp.co.aizu_student.weatherhacks.views.adapters.LocationListAdapter;

public class LocationListActivity extends BaseActivity {

    @Inject
    WeatherHacksRssHelper rssHelper;

    private ActivityLocationListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_location_list);

        initToolbar(binding.toolbar, R.string.location_list, true, false, null);
        rssHelper.rssParse(this);
    }

    public void initLocationListView(List<Location> locations) {
        if (locations == null) {
            return;
        }

        LocationListAdapter locationListAdapter = new LocationListAdapter(this, 0, locations);
        binding.locationListview.setAdapter(locationListAdapter);
        binding.locationListview.setOnItemClickListener((parent, v, position, id) -> {
            Location location = (Location) parent.getItemAtPosition(position);

            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString(Location.FIELD_NAME_ID, location.getId());
            bundle.putString(Location.FIELD_NAME_CITY, location.getCity());
            intent.putExtras(bundle);

            setResult(RESULT_OK, intent);
            finish();
        });
    }

}
