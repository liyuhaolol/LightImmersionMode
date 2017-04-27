package spa.lyh.cn.statusbarlightmode;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

/**
 * Created by liyuhao on 2017/4/26.
 */

public class TemporaryConfig {
    private static String TAG = "TemporaryConfig";

    Context context;
    int enable;
    int temporaryColor;

    public TemporaryConfig(Context context){
        this.context = context;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public void setTemporaryResIdColor(int temporaryResIdColor) {
        try {
            this.temporaryColor = context.getResources().getColor(temporaryResIdColor);
        }catch (Exception e){
            Log.e(TAG,"Unknown temporaryColor");
            this.temporaryColor = Color.parseColor("#D0D0D0");
        }
    }

    public void setTemporaryStringColor(String temporaryStringColor) {
        try{
            this.temporaryColor = Color.parseColor(temporaryStringColor);
        }catch (Exception e){
            Log.e(TAG,"Unknown defaultColor");
            this.temporaryColor = Color.parseColor("#D0D0D0");
        }
    }

    public int getTemporaryColor() {
        return temporaryColor;
    }
}
