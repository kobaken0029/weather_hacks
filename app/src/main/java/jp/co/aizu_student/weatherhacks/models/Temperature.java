package jp.co.aizu_student.weatherhacks.models;

import java.util.HashMap;


/**
 * Created by koba on 2015/09/06.
 */
public class Temperature {
    private HashMap<String, String> max;
    private HashMap<String, String> min;

    public HashMap<String, String> getMax() {
        return max;
    }

    public void setMax(HashMap<String, String> max) {
        this.max = max;
    }

    public HashMap<String, String> getMin() {
        return min;
    }

    public void setMin(HashMap<String, String> min) {
        this.min = min;
    }
}
