package spa.lyh.cn.statusbarlightmode.barutils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by liyuhao on 2017/4/2.
 */

public class barUtils {
    /**
     * 判断是不是浅色
     * @return 是否为浅色
     */
    public static boolean isLightRGB(int[] colors){
        int grayLevel = (int) (colors[0] * 0.299 + colors[1] * 0.587 + colors[2] * 0.114);
        if(grayLevel>=192){
            return true;
        }
        return false;
    }

    /**
     * 用于获取状态栏的高度。 使用Resource对象获取
     *
     * @return 返回状态栏高度的像素值。
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 修改状态栏为全透明沉浸式
     *
     * @param activity
     */
    @TargetApi(19)
    public static void transparencyBar(Activity activity) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ViewGroup vg = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        vg.setFitsSystemWindows(true);
        vg.setClipToPadding(false);
    }
}
