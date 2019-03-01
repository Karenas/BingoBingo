package com.gmself.studio.mg.bingobingo.overall.ui.activity;

import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gmself.studio.mg.basemodule.base.ui.activity.BaseActivity;
import com.gmself.studio.mg.bingobingo.overall.R;
import com.gmself.studio.mg.bingobingo.overall.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guomeng on 2019/3/1.
 *
 * Bingo的home页Activity
 */
@Route(path = "/overall/BingoHomeActivity")
public class BingoHomeActivity extends BaseActivity{

    @BindView(R2.id.overall_home_rv)
    private RecyclerView mRecyclerView;


    @Override
    protected int setLayoutID() {
        return R.layout.activity_bingo_home;
    }

    @Override
    public void initView() {
//        ARouter.getInstance().inject(this);

    }

    @Override
    public void setListener() {

    }

    @Override
    public void setFunction() {

    }
}
