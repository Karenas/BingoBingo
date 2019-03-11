package com.gmself.stidio.gm.superflyerpage.ui;

import android.graphics.Color;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gmself.stidio.gm.superflyerpage.R;
import com.gmself.stidio.gm.superflyerpage.entity.Flyer;
import com.gmself.stidio.gm.superflyerpage.ui.customView.FreeItemView;
import com.gmself.stidio.gm.superflyerpage.ui.customView.FreeRunningQueue;
import com.gmself.studio.mg.basemodule.base.ui.activity.BaseActivity;
import com.gmself.studio.mg.basemodule.log_tool.Logger;

/**
 * Created by guomeng on 3/7.
 */
@Route(path = "/freeFlyer/FreeFlyerActivity")
public class FreeFlyerActivity extends BaseActivity{

    private Flyer flyer;

    @Override
    protected int setLayoutID() {
        return R.layout.activity_freeflyer_home;
    }

    @Override
    public void initView() {
        FrameLayout layout = findViewById(R.id.freeflyer_home_content_fl);

        FreeRunningQueue queue = new FreeRunningQueue(this, 5, layout);
        flyer = queue.getFlyer();

        queue.run(400);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void setFunction() {
//        Flyer flyer = this.flyer;
//        for (int i = 0 ; i <20 ; i++){
//            Logger.log(Logger.Type.DEBUG, "------ "+flyer.getInfo()+" ------");
//            flyer = flyer.getNextFlyer();
//        }
    }


}
