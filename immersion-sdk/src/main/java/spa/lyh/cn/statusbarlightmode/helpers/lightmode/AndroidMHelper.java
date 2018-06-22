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
            View view = activity.getWindow().getDecorView();
            int oldVis = view.getSystemUiVisibility();
            int newVis = oldVis;
            if (isLightMode){
                newVis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }else {
                newVis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            if (newVis != oldVis) {
                view.setSystemUiVisibility(newVis);
            }

            return true;
        }
        Log.w("LightModeException","Failed to match Android 6.0");
        return false;
    }
}
