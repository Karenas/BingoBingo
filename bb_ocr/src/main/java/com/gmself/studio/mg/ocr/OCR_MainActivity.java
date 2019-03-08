package com.gmself.studio.mg.ocr;


import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gmself.studio.mg.basemodule.base.ui.activity.BaseActivity;

/**
 * Created by guomeng on 2019/3/4.
 */

@Route(path = "/ocr/ocr_MainActivity")
public class OCR_MainActivity extends BaseActivity {


    @Override
    protected int setLayoutID() {
        overridePendingTransition(R.anim.activity_bottom_in, R.anim.activity_out);
        return R.layout.activity_ocr_main;
    }

    @Override
    public void initView() {
//        Fragment
    }


    @Override
    public void setListener() {

    }

    @Override
    public void setFunction() {

    }


}
