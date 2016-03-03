package jp.co.aizu_student.weatherhacks.views.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import java.util.List;

import javax.inject.Inject;

import jp.co.aizu_student.weatherhacks.R;
import jp.co.aizu_student.weatherhacks.databinding.ActivityLocationListBinding;
import jp.co.aizu_student.weatherhacks.helpers.WeatherHacksRssHelper;
import jp.co.aizu_student.weatherhacks.interfaces.LocationListHandler;
import jp.co.aizu_student.weatherhacks.interfaces.OnEmptyMessageClickListener;
import jp.co.aizu_student.weatherhacks.models.Location;
import jp.co.aizu_student.weatherhacks.adapter.LocationListAdapter;

public class LocationListActivity extends BaseActivity
        implements LocationListHandler, AdapterView.OnItemClickListener, OnEmptyMessageClickListener {

    @Inject
    WeatherHacksRssHelper weatherHacksRssHelper;

    private ActivityLocationListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_location_list);

        initToolbar(binding.toolbar, R.string.location_list, true, false, null);
        weatherHacksRssHelper.rssParse(this);
    }

    @Override
    protected void onDestroy() {
        weatherHacksRssHelper.onDestroy();
        weatherHacksRssHelper = null;
        super.onDestroy();
    }

    @Override
    public void setUpLocationListView(List<Location> locations) {
        if (locations == null) {
            return;
        }

        LocationListAdapter locationListAdapter = new LocationListAdapter(this, 0, locations);
        binding.locationListview.setAdapter(locationListAdapter);
        binding.setItemClickListener(this);
    }

    @Override
    public void showErrorMessage() {
        binding.emptyText.setVisibility(View.VISIBLE);
        binding.setEmptyMessageClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Location location = (Location) parent.getItemAtPosition(position);

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(Location.FIELD_NAME_ID, location.getId());
        bundle.putString(Location.FIELD_NAME_CITY, location.getCity());
        intent.putExtras(bundle);

        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onEmptyMessageClick(View v) {
        binding.emptyText.setVisibility(View.GONE);
        weatherHacksRssHelper.rssParse(this);
    }
}
