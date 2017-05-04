package spa.lyh.cn.lightimmersionmode;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.normalactivity:
                intent = new Intent(MainActivity.this,NormalActivity.class);
                startActivity(intent);
                break;
            case R.id.changeconfig:
                intent = new Intent(MainActivity.this,ChangeActivity.class);
                startActivity(intent);
                break;
            case R.id.fragment:
                intent = new Intent(MainActivity.this, FragmentActivity.class);
                startActivity(intent);
                break;
            case R.id.disable:
                intent = new Intent(MainActivity.this, DisableActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

    }

}
