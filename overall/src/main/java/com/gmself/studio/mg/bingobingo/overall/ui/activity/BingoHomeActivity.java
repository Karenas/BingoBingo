package com.gmself.studio.mg.bingobingo.overall.ui.activity;

import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gmself.studio.mg.basemodule.base.ui.activity.BaseActivity;
import com.gmself.studio.mg.basemodule.base.ui.recyclerview.DividerGridItemDecoration;
import com.gmself.studio.mg.bingobingo.overall.R;
import com.gmself.studio.mg.bingobingo.overall.entity.EntityHomeRvItem;
import com.gmself.studio.mg.bingobingo.overall.ui.adapter.AdapterOverall_Home_rv;

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

    Random random = new Random();


    private RecyclerView mRecyclerView;

    private AdapterOverall_Home_rv adapter;

    private List<EntityHomeRvItem> mailList;

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

    }

    @Override
    public void setFunction() {

    }

    private void initMainList(){
        mailList = new ArrayList<>();
        EntityHomeRvItem rvItem;
        for (int i = 0; i < 10; i++){
            rvItem = new EntityHomeRvItem();
            rvItem.setName("名称"+i);
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
