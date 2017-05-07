package spa.lyh.cn.lightimmersionmode;

import android.os.Bundle;
import android.support.annotation.Nullable;

import spa.lyh.cn.lightimmersionmode.base.BaseActivity;

/**
 * Created by liyuhao on 2017/4/26.
 */

public class ChangeActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        changeStatusBarColor(R.color.colorAccent);
    }
}
