package spa.lyh.cn.statusbarlightmode.helpers;

import android.app.Activity;
import android.os.Build;
import android.util.Log;
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
            if (window != null){
                Class clazz = window.getClass();
                try{
                    Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                    Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                    Method extraFlagField = clazz.getMethod("setExtraFlags",int.class,int.class);
                    if (isLightMode){
                        //切换到浅色模式
                        extraFlagField.invoke(window,field.getInt(layoutParams),field.getInt(layoutParams));
                    }else {
                        //还原为深色模式
                        extraFlagField.invoke(window,0,field.getInt(layoutParams));
                    }
                    return true;
                }catch (Exception e){
                    Log.e("LightModeException","Failed to match MIUI");
                }
            }
        }
        return false;
    }
}
