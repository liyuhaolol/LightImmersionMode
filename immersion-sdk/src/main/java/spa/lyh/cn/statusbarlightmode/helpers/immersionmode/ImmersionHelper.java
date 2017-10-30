package spa.lyh.cn.statusbarlightmode.helpers.immersionmode;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import spa.lyh.cn.statusbarlightmode.ImmersionMode;
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

    public static View statusBarFitToAPP(Activity activity, int color){
        int redValue = Color.red(color);
        int greenValue = Color.green(color);
        int blueValue = Color.blue(color);
        int[] colorArry = new int[]{redValue,greenValue,blueValue};
        View v = changeStatusBarColor(activity,color);
        if (!setStatusBarMode(activity,isLightRGB(colorArry))){//retry once,incase failed
            setStatusBarMode(activity,isLightRGB(colorArry));
        }
        return v;
    }

    private static View changeStatusBarColor(Activity activity,int color){
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
            return v;
        }
        return null;
    }

    private static boolean setStatusBarMode(Activity activity,boolean isLightMode){
        boolean flag = false;
        /*if (new MIUIHelper().setLightMode(activity,isLightMode)){
            flag = true;
        }else if (new FlymeHelper().setLightMode(activity,isLightMode)){
            flag = true;
        }else if (new AndroidMHelper().setLightMode(activity,isLightMode)){
            flag = true;
        }*/

        switch (ImmersionMode.getInstance().getPhoneType()){
            case 1:
                if (new MIUIHelper().setLightMode(activity,isLightMode)){
                    ImmersionMode.getInstance().setPhoneType(1);
                    flag = true;
                }else {
                    ImmersionMode.getInstance().setPhoneType(0);
                    flag = false;
                }
                break;
            case 2:
                if (new FlymeHelper().setLightMode(activity,isLightMode)){
                    ImmersionMode.getInstance().setPhoneType(2);
                    flag = true;
                }else {
                    ImmersionMode.getInstance().setPhoneType(0);
                    flag = false;
                }
                break;
            case 3:
                if (new AndroidMHelper().setLightMode(activity,isLightMode)){
                    ImmersionMode.getInstance().setPhoneType(3);
                    flag = true;
                }else {
                    ImmersionMode.getInstance().setPhoneType(0);
                    flag = false;
                }
                break;
            case 4:
                break;
            case 0:
            default:
                if (new MIUIHelper().setLightMode(activity,isLightMode)){
                    ImmersionMode.getInstance().setPhoneType(1);
                    flag = true;
                }else if (new FlymeHelper().setLightMode(activity,isLightMode)){
                    ImmersionMode.getInstance().setPhoneType(2);
                    flag = true;
                }else if (new AndroidMHelper().setLightMode(activity,isLightMode)){
                    ImmersionMode.getInstance().setPhoneType(3);
                    flag = true;
                }else {
                    ImmersionMode.getInstance().setPhoneType(4);
                    flag = true;
                }
                break;
        }
        return flag;
    }
}
