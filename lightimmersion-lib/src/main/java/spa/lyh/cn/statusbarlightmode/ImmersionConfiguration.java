package spa.lyh.cn.statusbarlightmode;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

/**
 * Created by liyuhao on 2017/4/26.
 */

public class ImmersionConfiguration {
    private static String TAG = "ImmersionConfiguration";

    public static int ENABLE = 100;
    public static int DISABLE = 101;
    Context context;
    int enable;
    int defaultColor;

    private ImmersionConfiguration(ImmersionConfiguration.Builder builder){
        this.context = builder.context;
        this.enable = builder.enable;
        this.defaultColor = builder.defaultColor;
    }


    public static class Builder{
        Context context;
        int enable;
        int defaultColor;
        public Builder(Context context){
            this.context = context;
        }

        public ImmersionConfiguration.Builder enableImmersionMode(int enable){
            this.enable = enable;
            return this;
        }

        public ImmersionConfiguration.Builder defaultColor(String color){
            try{
                this.defaultColor = Color.parseColor(color);
            }catch (Exception e){
                Log.e(TAG,"Unknown defaultColor");
                this.defaultColor = Color.parseColor("#D0D0D0");
            }
            return this;
        }
        public ImmersionConfiguration.Builder defaultColor(int resID){
            try {
                this.defaultColor = context.getResources().getColor(resID);
            }catch (Exception e){
                Log.e(TAG,"Unknown defaultColor");
                this.defaultColor = Color.parseColor("#D0D0D0");
            }
            return this;
        }
        public ImmersionConfiguration.Builder setIntColor(int color){
            this.defaultColor = color;
            return this;
        }
        public ImmersionConfiguration build(){
            this.initEmptyFieldsWithDefaultValues();
            return new ImmersionConfiguration(this);
        }
        private void initEmptyFieldsWithDefaultValues(){
            if (this.enable == 0){
                this.enable = ENABLE;
            }
            if (this.defaultColor == 0){
                this.defaultColor = Color.parseColor("#D0D0D0");
            }
        }
    }
}
