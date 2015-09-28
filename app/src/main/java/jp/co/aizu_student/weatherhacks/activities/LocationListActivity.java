package jp.co.aizu_student.weatherhacks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.co.aizu_student.weatherhacks.R;
import jp.co.aizu_student.weatherhacks.helpers.WeatherHacksRssHelper;
import jp.co.aizu_student.weatherhacks.models.Location;
import jp.co.aizu_student.weatherhacks.views.adapters.LocationListAdapter;


public class LocationListActivity extends BaseActivity {
    @Bind(R.id.location_listview)
    ListView mLocationListView;

    @Inject
    WeatherHacksRssHelper rssHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);
        ButterKnife.bind(this);

        initToolbar(R.string.location_list, true, false, null);
        rssHelper.rssParse(this);
    }

    public void initLocationListView(List<Location> locations) {
        if (locations == null) return;

        LocationListAdapter adapter = new LocationListAdapter(this, 0, locations);
        mLocationListView.setAdapter(adapter);
        mLocationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView l = (ListView) parent;
                Location location = (Location) l.getItemAtPosition(position);

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(Location.FIELD_NAME_ID, location.getId());
                bundle.putString(Location.FIELD_NAME_CITY, location.getCity());
                intent.putExtras(bundle);

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

}
