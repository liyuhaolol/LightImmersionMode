package spa.lyh.cn.statusbarlightmode.helpers.immersionmode;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import spa.lyh.cn.statusbarlightmode.ImmersionMode;
import spa.lyh.cn.statusbarlightmode.helpers.lightmode.AndroidMHelper;
import spa.lyh.cn.statusbarlightmode.helpers.lightmode.ColorOSHelper;
import spa.lyh.cn.statusbarlightmode.helpers.lightmode.FlymeHelper;
import spa.lyh.cn.statusbarlightmode.helpers.lightmode.MIUIHelper;

import static spa.lyh.cn.statusbarlightmode.barutils.barUtils.getStatusBarHeight;
import static spa.lyh.cn.statusbarlightmode.barutils.barUtils.isLightRGB;
import static spa.lyh.cn.statusbarlightmode.barutils.barUtils.transparencyBarAPI19;

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

    /**
     * 没想好叫啥
     * @param activity
     * @param color
     * @return
     */
    private static View changeStatusBarColor(Activity activity,int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.setStatusBarColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //设置开启沉浸式，并设置对应的必要属性
            transparencyBarAPI19(activity);
            //得到布局最底层的decorView
            ViewGroup vg = (ViewGroup) activity.getWindow().getDecorView();
            //实例化一个状态栏的填充view
            View v = new View(activity);
            //设置状态栏高度
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getStatusBarHeight(activity));
            //设置背景颜色
            v.setBackgroundColor(color);
            //设置宽高属性
            v.setLayoutParams(params);
            //添加状态栏填充view到decorView
            vg.addView(v);
            return v;
        }
        return null;
    }

    /**
     * 设置状态栏深浅字颜色的方法
     * @param activity
     * @param isLightMode
     * @return
     */
    private static boolean setStatusBarMode(Activity activity,boolean isLightMode){
        boolean flag = false;
        /*if (new MIUIHelper().setLightMode(activity,isLightMode)){
            flag = true;
        }else if (new FlymeHelper().setLightMode(activity,isLightMode)){
            flag = true;
        }else if (new AndroidMHelper().setLightMode(activity,isLightMode)){
            flag = true;
        }*/
        //Log.e("liyuhao",ImmersionMode.getInstance().getPhoneType()+"");
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
                if (new ColorOSHelper().setLightMode(activity,isLightMode)){
                    ImmersionMode.getInstance().setPhoneType(4);
                    flag = true;
                }else {
                    ImmersionMode.getInstance().setPhoneType(0);
                    flag = false;
                }
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
                }else if (new ColorOSHelper().setLightMode(activity,isLightMode)){
                    ImmersionMode.getInstance().setPhoneType(4);
                    flag = true;
                }
                break;
        }
        return flag;
    }
}
