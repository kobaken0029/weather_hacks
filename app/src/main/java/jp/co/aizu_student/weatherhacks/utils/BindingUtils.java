package jp.co.aizu_student.weatherhacks.utils;

import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.widget.ListView;

@BindingMethods({
        @BindingMethod(type = ListView.class, attribute = "android:onItemClick", method = "setOnItemClickListener"),
})
public final class BindingUtils {
}
