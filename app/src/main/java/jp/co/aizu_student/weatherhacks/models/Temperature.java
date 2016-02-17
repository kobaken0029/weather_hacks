package jp.co.aizu_student.weatherhacks.models;

import java.util.HashMap;

public class Temperature {
    /** HASH値のkey */
    public static final String HASH_KEY_CELSIUS = "celsius";

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
