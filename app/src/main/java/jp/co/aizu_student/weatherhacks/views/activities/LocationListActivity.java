package jp.co.aizu_student.weatherhacks.views.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.LinkedHashSet;
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
    private List<Location> locations;
    private List<Location> allLocations;
    private ArrayAdapter<String> locationSpinnerArrayAdapter;

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

        findViewById(R.id.progress_bar).setVisibility(View.GONE);

        locationSpinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        locationSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.locationSpinner.setAdapter(locationSpinnerArrayAdapter);
        binding.locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = (Spinner) parent;
                String prefectureName = (String) spinner.getSelectedItem();

                if (allLocations == null) {
                    return;
                }

                // 選択された都道府県に応じてフィルタリング
                locations = Stream.of(allLocations)
                        .filter(l -> l.getPrefecture().equals(prefectureName) || prefectureName.equals("--------"))
                        .collect(Collectors.toList());
                ((ArrayAdapter<Location>) binding.locationListview.getAdapter()).clear();
                ((ArrayAdapter<Location>) binding.locationListview.getAdapter()).addAll(locations);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // nothing
            }
        });
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
        this.locations = locations;
        this.allLocations = new ArrayList<>(this.locations);

        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add("--------");
        Stream.of(allLocations).forEach(l -> {
            if (!linkedHashSet.contains(l.getPrefecture())) {
                linkedHashSet.add(l.getPrefecture());
            }
        });
        locationSpinnerArrayAdapter.addAll(linkedHashSet);

        LocationListAdapter locationListAdapter = new LocationListAdapter(this, 0, this.locations);
        binding.locationListview.setAdapter(locationListAdapter);
        binding.setItemClickListener(this);

        findViewById(R.id.progress_bar).setVisibility(View.GONE);
    }

    @Override
    public void showErrorMessage() {
        binding.emptyText.setVisibility(View.VISIBLE);
        binding.setEmptyMessageClickListener(this);
        findViewById(R.id.progress_bar).setVisibility(View.GONE);
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
