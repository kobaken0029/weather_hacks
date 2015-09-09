package jp.co.aizu_student.weatherhacks.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import jp.co.aizu_student.weatherhacks.R;
import jp.co.aizu_student.weatherhacks.models.Location;
import jp.co.aizu_student.weatherhacks.views.adapters.LocationListAdapter;


public class LocationListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        initToolbar();
        initLocationListView();
    }

    /**
     * toolbarの初期化。
     */
    protected void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_menu);

        mToolbar.setTitle(R.string.app_name);
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(mToolbar);
    }

    private void initLocationListView() {
        final ListView locationListView = (ListView) findViewById(R.id.location_listview);
        LocationListAdapter adapter = new LocationListAdapter(this, 0, getLocations());
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

    /**
     * モックメソッド
     *
     * @return 地域群
     */
    private List<Location> getLocations() {
        Location hokkaido = new Location();
        hokkaido.setPrefecture("北海道");
        hokkaido.setCity("函館");
        hokkaido.setId("017010");
        Location aomori = new Location();
        aomori.setPrefecture("青森");
        aomori.setCity("青森");
        aomori.setId("020010");
        Location niigata = new Location();
        niigata.setPrefecture("新潟");
        niigata.setCity("長岡");
        niigata.setId("150020");
        Location tokyo = new Location();
        tokyo.setPrefecture("東京");
        tokyo.setCity("渋谷");
        tokyo.setId("130010");

        List<Location> locations = new ArrayList<>();
        locations.add(hokkaido);
        locations.add(hokkaido);
        locations.add(hokkaido);
        locations.add(hokkaido);
        locations.add(aomori);
        locations.add(aomori);
        locations.add(aomori);
        locations.add(aomori);
        locations.add(aomori);
        locations.add(niigata);
        locations.add(niigata);
        locations.add(niigata);
        locations.add(niigata);
        locations.add(niigata);
        locations.add(niigata);
        locations.add(niigata);
        locations.add(tokyo);
        locations.add(tokyo);
        locations.add(tokyo);
        locations.add(tokyo);
        locations.add(tokyo);
        locations.add(tokyo);
        locations.add(tokyo);
        locations.add(tokyo);
        locations.add(tokyo);
        return locations;
    }
}
