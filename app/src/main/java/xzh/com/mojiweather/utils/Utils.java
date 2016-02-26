package xzh.com.mojiweather.utils;

import android.content.Context;

/**
 * Created by xiangzhihong on 2016/2/26 on 10:15.
 */
public class Utils {

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
