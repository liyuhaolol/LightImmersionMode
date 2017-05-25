package spa.lyh.cn.statusbarlightmode.helpers.lightmode;

import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.view.View;

/**
 * Created by liyuhao on 2017/3/17.
 */

public class AndroidMHelper implements ILightModeHelper{

    @Override
    public boolean setLightMode(Activity activity, boolean isLightMode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (isLightMode){
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }else {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
            return true;
        }
        Log.e("LightModeException","Failed to match Android 6.0");
        return false;
    }
}
