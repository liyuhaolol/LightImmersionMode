package spa.lyh.cn.lightimmersionmode;

import android.os.Bundle;
import androidx.annotation.Nullable;

import spa.lyh.cn.lightimmersionmode.base.BaseActivity;

/**
 * Created by liyuhao on 2017/5/4.
 */

public class DisableActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisableImmersionMode();
        setContentView(R.layout.activity_disablemode);
    }
}
