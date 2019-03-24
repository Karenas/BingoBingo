package com.gmself.stidio.gm.bingobingo.ui.activity;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.gmself.stidio.gm.bingobingo.Port_test;
import com.gmself.stidio.gm.bingobingo.R;
import com.gmself.studio.mg.basemodule.arouter.ENUM_RouterE;
import com.gmself.studio.mg.basemodule.arouter.Manager_RouterM;
import com.gmself.studio.mg.basemodule.net_work.exception.BingoNetWorkException;
import com.gmself.studio.mg.basemodule.net_work.http_core.listener.OkHttpListener;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Example of a call to a native method
//        TextView tv = (TextView) findViewById(R.id.sample_text);
//        tv.setText(stringFromJNI());

        SystemClock.sleep(1000);

        Port_test portTest = new Port_test();
        portTest.doPort(this, "11122233", new OkHttpListener() {
            @Override
            public void onSuccess(String jsonStr) {

            }

            @Override
            public void onError(BingoNetWorkException resultCode) {

            }

            @Override
            public void onFinally() {

            }
        });

//        Manager_RouterM.getInstance().router_goto(ENUM_RouterE.ACTIVITY_OVERALL_HOME);



    }

}
