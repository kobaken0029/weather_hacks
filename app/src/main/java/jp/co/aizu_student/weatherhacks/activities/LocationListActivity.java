package jp.co.aizu_student.weatherhacks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import javax.inject.Inject;

import jp.co.aizu_student.weatherhacks.R;
import jp.co.aizu_student.weatherhacks.helpers.WeatherHacksRssHelper;
import jp.co.aizu_student.weatherhacks.models.Location;
import jp.co.aizu_student.weatherhacks.views.adapters.LocationListAdapter;


public class LocationListActivity extends BaseActivity {

    @Inject
    WeatherHacksRssHelper rssHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        initToolbar();
        initLocationListView();
    }

    private void initLocationListView() {
        final ListView locationListView = (ListView) findViewById(R.id.location_listview);
        LocationListAdapter adapter = new LocationListAdapter(this, 0, rssHelper.getLocations());
        locationListView.setAdapter(adapter);
        locationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView l = (ListView) parent;
                Location location = (Location) l.getItemAtPosition(position);

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("id", location.getId());
                bundle.putString("city", location.getCity());
                intent.putExtras(bundle);

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

}
