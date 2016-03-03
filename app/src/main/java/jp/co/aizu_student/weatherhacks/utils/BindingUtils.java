package jp.co.aizu_student.weatherhacks.utils;

import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

@BindingMethods({
        @BindingMethod(type = ListView.class, attribute = "android:onItemClick", method = "setOnItemClickListener"),
        @BindingMethod(type = SwipeRefreshLayout.class, attribute = "android:onSwipeRefresh", method = "setOnRefreshListener")
})
public final class BindingUtils {
}
