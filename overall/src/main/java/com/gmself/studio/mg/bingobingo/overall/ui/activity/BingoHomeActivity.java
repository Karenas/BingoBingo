package com.gmself.studio.mg.bingobingo.overall.ui.activity;

import android.graphics.Color;
import android.os.SystemClock;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MotionEvent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gmself.studio.mg.basemodule.arouter.ENUM_RouterE;
import com.gmself.studio.mg.basemodule.arouter.Manager_RouterM;
import com.gmself.studio.mg.basemodule.base.ui.activity.BaseActivity;
import com.gmself.studio.mg.basemodule.base.ui.recyclerview.DividerGridItemDecoration;
import com.gmself.studio.mg.basemodule.mg_dataProcess.MGThreadTool;
import com.gmself.studio.mg.basemodule.mg_dataProcess.MGThreadTool_processing;
import com.gmself.studio.mg.bingobingo.overall.R;
import com.gmself.studio.mg.bingobingo.overall.entity.EntityHomeRvItem;
import com.gmself.studio.mg.bingobingo.overall.ui.adapter.AdapterOverall_Home_rv;
import com.gmself.studio.mg.bingobingo.overall.ui.customView.RecyclerView_home;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by guomeng on 2019/3/1.
 *
 * Bingo的home页Activity
 */
@Route(path = "/overall/BingoHomeActivity")
public class BingoHomeActivity extends BaseActivity{

    private Random random = new Random();


    private RecyclerView_home mRecyclerView;

    private AdapterOverall_Home_rv adapter;

    private List<EntityHomeRvItem> mailList;

    private String[] functionList = {"图片文字识别", "待开放", "待开放", "待开放", "待开放", "待开放", "待开放", "待开放"};

    @Override
    protected int setLayoutID() {
        return R.layout.activity_overall_bingo_home;
    }

    @Override
    public void initView() {

        mRecyclerView = findViewById(R.id.overall_home_rv);


        initMainList();
        adapter = new AdapterOverall_Home_rv(this, mailList);

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(BingoHomeActivity.this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setListener() {
        adapter.setOnItemClickListener(new AdapterOverall_Home_rv.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                jumpPage(view, position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                jumpPage(view, position);
            }
        });
    }

    @Override
    public void setFunction() {

    }

    private void jumpPage(View view, final int position){
        MGThreadTool.getInstance().doProcess(new MGThreadTool_processing() {
            @Override
            public Object doInBackground() {
                SystemClock.sleep(500);
                return true;
            }

            @Override
            public void onSuccess(Object r) {

                switch (position){
                    case 0:
                        Manager_RouterM.getInstance().router_goto(ENUM_RouterE.ACTIVITY_OCR_MAIN);
                        break;
                }

            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void initMainList(){
        mailList = new ArrayList<>();
        EntityHomeRvItem rvItem;
        for (int i = 0; i < functionList.length; i++){
            rvItem = new EntityHomeRvItem();
            rvItem.setName(functionList[i]);
            rvItem.setBackGroundColor(Color.argb(randomColor(), randomColor(), randomColor(), randomColor()));
            rvItem.setHeight(randomHeight());
            mailList.add(rvItem);
        }
    }


    private int randomColor(){
        return  random.nextInt(155)+50;
    }

    private int randomHeight(){
        return  random.nextInt(200)+250;
    }
}
