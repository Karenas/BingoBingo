package com.gmself.studio.mg.basemodule.glidepackaging;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by guomeng on 2018/7/29.
 *
 * 使用Glide 加载图片
 */

public class GlideHandler {


    /**裁剪成圆形加载*/
    public static void loadCropCircleImage(Context context, String loadImageUrl, ImageView view ){
        Glide.with(context).load(loadImageUrl).bitmapTransform(new CropCircleTransformation(context)).into(view);
    }

    public static void loadCropCircleImage(Context context, String loadImageUrl, ImageView view, int undefineImage){
        Glide.with(context)
                .load(loadImageUrl)
                .error(undefineImage)
//                .placeholder(undefineImage)//加载成功前显示的图片
//                .fallback(undefineImage)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(view);
    }

    /**加载缩略图片*/
    public static void loadSmallImage(Context context, String loadImageUrl, ImageView view ){
        Glide.with(context).load(loadImageUrl).thumbnail(0.5f).into(view);

    }

    /**加载一般图片*/
    public static void loadImage(Context context, String loadImageUrl, ImageView view ){
        Glide.with(context)
                .load(loadImageUrl)
                .centerCrop() //拉伸
                .into(view);

    }

    /**加载一般图片 且带有缺省图u*/
    public static void loadImage(Context context, String loadImageUrl, ImageView view, int undefineImage){
        Glide.with(context)
                .load(loadImageUrl)
                .centerCrop() //拉伸
//                .fitCenter()
                .error(undefineImage)
                .placeholder(undefineImage)//加载成功前显示的图片
                .fallback(undefineImage)
                .into(view);

    }

    public static void loadImage(Context context, int imageResID, ImageView view){
        Glide.with(context)
                .load(imageResID)
                .centerCrop() //拉伸
                .into(view);

    }

    private static String getUsedPath(String imageURL, String fullName){
        return imageURL;
//        if (TextUtils.isEmpty(fullName)){
//            return imageURL;
//        }
//
//        String path = DirsTools.GetCacheImage(fullName);
//         if (!File_tools.isEffective(path)){
//            OkHttpManger.getInstance().downloadFileAsyHttp(imageURL, new File(path), new OkHttpListener() {
//                @Override
//                public void onSuccess(String jsonStr) {
//                }
//
//                @Override
//                public void onError(int resultCode) {
//                }
//
//                @Override
//                public void onFinally() {
//                }
//            });
//            return imageURL;
//        }
//        return path;
    }

}
