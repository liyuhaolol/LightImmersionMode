package spa.lyh.cn.statusbarlightmode.barutils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by liyuhao on 2017/4/2.
 */

public class barUtils {
    /**
     * * 判断是不是浅色
     * @param colors 颜色数组
     * @return 是否为浅色
     */
    public static boolean isLightRGB(int[] colors){
        int grayLevel = (int) (colors[0] * 0.299 + colors[1] * 0.587 + colors[2] * 0.114);
        if(grayLevel>=192){
            return true;
        }
        return false;
    }

    /**
     * * 用于获取状态栏的高度。 使用Resource对象获取
     *
     * @param context 上下文
     * @return 返回状态栏高度的像素值。
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 修改状态栏为全透明沉浸式
     *
     * @param activity activity对象
     */
    @TargetApi(19)
    public static void transparencyBarAPI19(Activity activity) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ViewGroup vg = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        vg.setFitsSystemWindows(true);
        vg.setClipToPadding(false);
    }

    /**
     * 修改状态栏为全透明沉浸式
     *
     * @param activity activity对象
     */
    @TargetApi(21)
    public static void transparencyBarAPI21(Activity activity) {
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    public static Bitmap getBitmapFromView(View v)
    {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.RGB_565);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        // Draw background
        Drawable bgDrawable = v.getBackground();
        if (bgDrawable != null)
        {
            bgDrawable.draw(c);
        }
        else
        {
            c.drawColor(Color.WHITE);
        }
        // Draw view to canvas
        v.draw(c);
        if (null != b) {
            int pixel = b.getPixel(200, 5);
            //获取颜色
            int redValue = Color.red(pixel);
            int greenValue = Color.green(pixel);
            int blueValue = Color.blue(pixel);
            Log.e("CCCCCC", "【颜色值】 #" + Integer.toHexString(pixel).toUpperCase());
            //b.recycle();
        }
        return b;
    }
}
