package spa.lyh.cn.statusbarlightmode.helpers.immersionmode;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import spa.lyh.cn.statusbarlightmode.helpers.lightmode.AndroidMHelper;
import spa.lyh.cn.statusbarlightmode.helpers.lightmode.FlymeHelper;
import spa.lyh.cn.statusbarlightmode.helpers.lightmode.MIUIHelper;

import static spa.lyh.cn.statusbarlightmode.barutils.barUtils.getStatusBarHeight;
import static spa.lyh.cn.statusbarlightmode.barutils.barUtils.isLightRGB;
import static spa.lyh.cn.statusbarlightmode.barutils.barUtils.transparencyBar;

/**
 * Created by liyuhao on 2017/4/26.
 */

public class ImmersionHelper {

    public static void statusBarFitToAPP(Activity activity, int color){
        int redValue = Color.red(color);
        int greenValue = Color.green(color);
        int blueValue = Color.blue(color);
        int[] colorArry = new int[]{redValue,greenValue,blueValue};
        changeStatusBarColor(activity,color);
        setStatusBarMode(activity,isLightRGB(colorArry));
    }

    private static void changeStatusBarColor(Activity activity,int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.setStatusBarColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            transparencyBar(activity);
            ViewGroup vg = (ViewGroup) activity.getWindow().getDecorView();
            View v = new View(activity);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getStatusBarHeight(activity));
            v.setBackgroundColor(color);
            v.setLayoutParams(params);
            vg.addView(v);
        }

    }

    private static boolean setStatusBarMode(Activity activity,boolean isLightMode){
        boolean flag = false;
        if (new MIUIHelper().setLightMode(activity,isLightMode)){
            flag = true;
        }else if (new FlymeHelper().setLightMode(activity,isLightMode)){
            flag = true;
        }else if (new AndroidMHelper().setLightMode(activity,isLightMode)){
            flag = true;
        }
        return flag;
    }
}
