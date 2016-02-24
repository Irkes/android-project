package lisk.calc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import lisk.calc.bd_lab.ListActivity;


public class ServiceActivity extends Activity implements DataSetChanged{

    TextView tv;
    Handler mHandler = new Handler(Looper.myLooper());
    private DataSetChanged listener;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.service_activity);

        tv = (TextView) findViewById(R.id.tvCorse);
        MyService.setListener(this);

        Button btnGoToList = (Button) findViewById(R.id.goToList);
        btnGoToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), ListActivity.class);
                startActivity(intent);
            }
        });

        Button btnStop = (Button) findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyService.setListener(null);
                stopService(new Intent(getApplication(), MyService.class));
            }
        });

        listener = this;
        Button btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(getApplication(), MyService.class));
                MyService.setListener(listener);
            }
        });
    }

    private Runnable run = new Runnable() {
        @Override
        public void run() {
            if (tv != null && !MyService.isStop()) {
                tv.setText("Dollar " + dollar);
            }

        }
    };

    int dollar = 0;

    @Override
    public void onDataChange(int dollar) {
        this.dollar = dollar;
        mHandler.post(run);
    }
}
