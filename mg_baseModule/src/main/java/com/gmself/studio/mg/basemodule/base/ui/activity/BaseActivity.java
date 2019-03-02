package com.gmself.studio.mg.basemodule.base.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.WindowManager;



/**
 * 功能： Activity基类
 * 创建日期： 2017/5/2
 */

public abstract class BaseActivity extends MPermissionsActivity implements ActivityUIListener {

    /**
     * 初始化
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ClientApplication.activities.add(this);

        //#####################去除ActionBar#####################
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //#####################去除ActionBar  完结###############


        onCreateCustom();//初始化
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 初始化
     */
    protected void onCreateCustom() {
        setContentView(setLayoutID());

        initView();//初始化控件
        setListener();//设置监听
        setFunction();//设置功能
    }

    /**
     * 跳转界面
     * @param clazz
     * @return
     */
    protected final <T extends Activity>Intent activity(Class<T> clazz){
        return new Intent(this,clazz);
    }

    @LayoutRes  //通过注解注入布局文件
    protected abstract int setLayoutID();
    @Override
    public abstract void initView();

    //设置监听的方法
    @Override
    public abstract void setListener();

    //设置功能的方法
    @Override
    public abstract void setFunction();

}
