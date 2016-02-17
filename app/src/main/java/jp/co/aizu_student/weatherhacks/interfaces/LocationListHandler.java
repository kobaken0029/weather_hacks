package jp.co.aizu_student.weatherhacks.interfaces;

import java.util.List;

import jp.co.aizu_student.weatherhacks.models.Location;

public interface LocationListHandler {
    void initLocationListView(List<Location> locations);
}
