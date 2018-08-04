package spa.lyh.cn.statusbarlightmode;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.util.Log;


/**
 * Created by liyuhao on 2017/4/26.
 */

public class ImmersionConfiguration {
    private static String TAG = "ImmersionConfiguration";

    final public static int ENABLE = 100;
    final public static int DISABLE = 101;
    Context context;
    int enable;
    int defaultColor;
    int navigationBarColor;

    final private static String blackColor = "#000000";

    private ImmersionConfiguration(ImmersionConfiguration.Builder builder){
        this.context = builder.context;
        this.enable = builder.enable;
        this.defaultColor = builder.defaultColor;
        this.navigationBarColor = builder.navigationBarColor;
    }


    public static class Builder{
        Context context;
        int enable;
        int defaultColor;
        int navigationBarColor;
        int statusBarViewId;
        public Builder(Context context){
            this.context = context;
        }

        public ImmersionConfiguration.Builder enableImmersionMode(int enable){
            this.enable = enable;
            return this;
        }

        /*public ImmersionConfiguration.Builder setStatusBarView(@IdRes int resID){
            this.statusBarViewId = resID;
            return this;
        }*/

        public ImmersionConfiguration.Builder setColor(String color){
            try{
                this.defaultColor = Color.parseColor(color);
            }catch (Exception e){
                Log.e(TAG,"Unknown defaultColor");
                this.defaultColor = Color.parseColor(blackColor);
            }
            return this;
        }
        public ImmersionConfiguration.Builder setColor(@ColorRes int resID){
            this.defaultColor = context.getResources().getColor(resID);
            return this;
        }
        public ImmersionConfiguration.Builder setIntColor(int color){
            this.defaultColor = color;
            return this;
        }
        public ImmersionConfiguration.Builder setNavigationBarColor(String color){
            try{
                this.navigationBarColor = Color.parseColor(color);
            }catch (Exception e){
                Log.e(TAG,"Unknown defaultColor");
                this.navigationBarColor = Color.parseColor(blackColor);
            }
            return this;
        }
        public ImmersionConfiguration.Builder setNavigationBarColor(@ColorRes int resID){
            this.navigationBarColor = context.getResources().getColor(resID);
            return this;
        }
        public ImmersionConfiguration.Builder setNavigationBarIntColor(int color){
            this.navigationBarColor = color;
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
                this.defaultColor = Color.parseColor(blackColor);
            }
            if (this.navigationBarColor == 0){
                this.navigationBarColor = Color.parseColor(blackColor);
            }
        }
    }
}
