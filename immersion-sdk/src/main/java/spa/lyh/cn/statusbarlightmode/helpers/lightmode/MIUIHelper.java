package spa.lyh.cn.statusbarlightmode.helpers.lightmode;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by liyuhao on 2017/3/17.
 */

public class MIUIHelper implements ILightModeHelper{
    @Override
    public boolean setLightMode(Activity activity, boolean isLightMode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            Window window = activity.getWindow();
            View view = window.getDecorView();
            if (window != null){
                Class clazz = window.getClass();
                try{
                    Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                    Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                    Method extraFlagField = clazz.getMethod("setExtraFlags",int.class,int.class);
                    //MIUI老版本的浅色模式支持MIUI Android4.4版本到MIUI 201(7.7.13)版
                    if (isLightMode){
                        //切换到浅色模式
                        //调用MIUI自己的方法，设置状态栏，只支持老版本MIUI，新版MIUI调用这句话会被自动忽略，不会造成稳定性影响
                        extraFlagField.invoke(window,field.getInt(layoutParams),field.getInt(layoutParams));
                    }else {
                        //还原为深色模式
                        //调用MIUI自己的方法，设置状态栏，只支持老版本MIUI，新版MIUI调用这句话会被自动忽略，不会造成稳定性影响
                        extraFlagField.invoke(window,0,field.getInt(layoutParams));
                    }
                    //MIUI新版本的浅色模式，代码与Google官方完全一致
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        //确保运行在Android6.0上，避免程序崩溃
                        //当miui的版本大于201(7.7.13)年发布以后所有最新的MIUI版本，稳定版版本号未知，但是发布日期需要晚于2017年7月13日
                        setDarkFontNew(view,isLightMode);
                    }
                    return true;
                }catch (Exception e){
                    Log.w("LightModeException","Failed to match MIUI");
                }
            }
        }
        return false;
    }

    @TargetApi(23)
    private void setDarkFontNew(View v, boolean isLightMode){
        int oldVis = v.getSystemUiVisibility();
        int newVis = oldVis;
        if (isLightMode){
            newVis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }else {
            newVis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
        if (newVis != oldVis) {
            v.setSystemUiVisibility(newVis);
        }
    }
}
