package spa.lyh.cn.lightimmersionmode;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

import spa.lyh.cn.lightimmersionmode.base.BaseActivity;
import spa.lyh.cn.lightimmersionmode.fragment.FirstFagment;
import spa.lyh.cn.lightimmersionmode.fragment.SecondFragment;
import spa.lyh.cn.lightimmersionmode.fragment.ThirdFragment;

/**
 * Created by liyuhao on 2017/4/26.
 */

public class FragmentActivity extends BaseActivity implements View.OnClickListener{
    private FirstFagment fFragment;
    private SecondFragment sFragment;
    private ThirdFragment tFragment;

    private RelativeLayout first,second,third;

    private FragmentManager fm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        first = (RelativeLayout) findViewById(R.id.first_layout_view);
        second = (RelativeLayout) findViewById(R.id.second_layout_view);
        third = (RelativeLayout) findViewById(R.id.third_layout_view);
        first.setOnClickListener(this);
        second.setOnClickListener(this);
        third.setOnClickListener(this);

        fFragment = new FirstFagment();
        fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content_layout,fFragment);
        ft.commit();
    }
    private void hideFragment(Fragment fragment, FragmentTransaction ft) {
        if (fragment != null) {
            ft.hide(fragment);
        }

    }

    @Override
    public void onClick(View view){
        FragmentTransaction ft = fm.beginTransaction();
        switch (view.getId()){
            case R.id.first_layout_view:
                hideFragment(sFragment,ft);
                hideFragment(tFragment,ft);
                if (fFragment == null){
                    fFragment = new FirstFagment();
                    ft.add(R.id.content_layout,fFragment);
                }else {
                    ft.show(fFragment);
                }
                changeStatusBarColor(R.color.bar_color);
                break;
            case R.id.second_layout_view:
                hideFragment(fFragment,ft);
                hideFragment(tFragment,ft);
                if (sFragment == null){
                    sFragment = new SecondFragment();
                    ft.add(R.id.content_layout,sFragment);
                }else {
                    ft.show(sFragment);
                }
                changeStatusBarColor(R.color.colorPrimaryDark);
                break;
            case R.id.third_layout_view:
                hideFragment(fFragment,ft);
                hideFragment(sFragment,ft);
                if (tFragment == null){
                    tFragment = new ThirdFragment();
                    ft.add(R.id.content_layout,tFragment);
                }else {
                    ft.show(tFragment);
                }
                changeStatusBarColor("#83AF9B");
                break;
            default:
                break;
        }
        ft.commit();
    }
}
