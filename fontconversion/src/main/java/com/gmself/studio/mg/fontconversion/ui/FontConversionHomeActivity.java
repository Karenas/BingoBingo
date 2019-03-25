package com.gmself.studio.mg.fontconversion.ui;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gmself.studio.mg.basemodule.base.ui.activity.BaseActivity;
import com.gmself.studio.mg.fontconversion.R;

/**
 * Created by guomeng on 2019/3/25.
 */
@Route(path = "/fontConversion/HomeActivity")
public class FontConversionHomeActivity extends BaseActivity{

    private TextView display_tv;
    private TextView button_tv;
    private EditText input_et;

    @Override
    protected int setLayoutID() {
        return R.layout.activity_font_conversion_home;
    }

    @Override
    public void initView() {
        display_tv = findViewById(R.id.fontConversion_home_tv);

//        AssetManager mgr= getAssets();
//        //根据路径得到Typeface
//        Typeface tf=Typeface.createFromAsset(mgr, "4.ttf");
//        //设置字体
//        display_tv.setTypeface(tf);
//
//        display_tv.setText("ᠮᠤᠩᠭᠤᠯ ᠬᠡᠯᠡ");

        input_et = findViewById(R.id.fontConversion_home_et);
        button_tv = findViewById(R.id.fontConversion_home_btn_tv);
    }

    @Override
    public void setListener() {
        button_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display_tv.setText(input_et.getText());
            }
        });
    }

    @Override
    public void setFunction() {

    }
}
