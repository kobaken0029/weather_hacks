package jp.co.aizu_student.weatherhacks.utils;

import android.databinding.BindingMethod;
import android.widget.ListView;

@BindingMethod(type = ListView.class, attribute = "android:onItemClick", method = "setOnItemClickListener")
public final class BindingUtils {
}
