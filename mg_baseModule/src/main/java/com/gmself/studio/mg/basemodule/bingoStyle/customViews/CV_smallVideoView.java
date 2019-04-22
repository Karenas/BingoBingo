package com.gmself.studio.mg.basemodule.bingoStyle.customViews;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.gmself.stidio.gm.ijkplayer.media.IRenderView;
import com.gmself.stidio.gm.ijkplayer.media.IjkVideoView;

/**
 * Created by guomeng on 4/11.
 */

public class CV_smallVideoView extends IjkVideoView {
    public CV_smallVideoView(Context context) {
        super(context);
        init();
    }

    public CV_smallVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CV_smallVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CV_smallVideoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init(){
        super.setAspectRatio(IRenderView.AR_ASPECT_FIT_PARENT);
    }

    public void setVideoURI(String uri){
        if (TextUtils.isEmpty(uri)){
            return;
        }
        super.setVideoURI(Uri.parse(uri));
    }

    public void setVideoPath(String path){
        if (TextUtils.isEmpty(path)){
            return;
        }
        super.setVideoPath(path);
    }

    public void startVideo(){
        super.start();
    }

}
