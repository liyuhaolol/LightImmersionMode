package spa.lyh.cn.statusbarlightmode.helpers.lightmode;

import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

public class ColorOSHelper implements ILightModeHelper{

    private final int SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT = 0x00000010;

    @Override
    public boolean setLightMode(Activity activity, boolean isLightMode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window  = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            int vis = window.getDecorView().getSystemUiVisibility();
            if (isLightMode) {
                vis |= SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT;
            } else {
                vis &= ~SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT;
            }
            window.getDecorView().setSystemUiVisibility(vis);
            return true;
        }
        return false;

    }
}
