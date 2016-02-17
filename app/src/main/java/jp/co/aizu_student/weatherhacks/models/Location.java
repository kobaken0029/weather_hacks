package jp.co.aizu_student.weatherhacks.models;

/**
 * Created by koba on 2015/09/06.
 */
public class Location {
    /** フィールド名 */
    public static final String FIELD_NAME_ID = "id";
    public static final String FIELD_NAME_CITY = "city";

    private String id;
    private String city;
    private String area;
    private String prefecture;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPrefecture() {
        return prefecture;
    }

    public void setPrefecture(String prefecture) {
        this.prefecture = prefecture;
    }
}
