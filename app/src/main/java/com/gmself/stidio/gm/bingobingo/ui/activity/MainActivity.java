package com.gmself.stidio.gm.bingobingo.ui.activity;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.gmself.stidio.gm.bingobingo.R;
import com.gmself.studio.mg.basemodule.arouter.ENUM_RouterE;
import com.gmself.studio.mg.basemodule.arouter.Manager_RouterM;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Example of a call to a native method
//        TextView tv = (TextView) findViewById(R.id.sample_text);
//        tv.setText(stringFromJNI());

        SystemClock.sleep(1000);

        Manager_RouterM.getInstance().router_goto(ENUM_RouterE.ACTIVITY_OVERALL_HOME);

    }

}
