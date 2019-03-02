package com.gmself.studio.mg.basemodule.arouter;

import android.nfc.FormatException;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.gmself.studio.mg.basemodule.log_tool.Logger;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Created by guomeng on 2019/3/1.
 */

public class Manager_RouterM {

    private static Manager_RouterM instance;

    public static Manager_RouterM getInstance(){
        instance = new Manager_RouterM();
        return instance;
    }

    private Postcard postcard;

    public void router_goto(ENUM_RouterE page){
        initPostcard(page);

        postcard.navigation();
    }

    public void router_goto(ENUM_RouterE page, Map<String, Object> values){
        initPostcard(page);
        for (String key : values.keySet()){
            withValue(key, values.get(key));
        }
        postcard.navigation();
    }

    public void router_goto(ENUM_RouterE page, String key, int value){
        initPostcard(page);
        withValue(key, value);
        postcard.navigation();
    }

    public void router_goto(ENUM_RouterE page, String key, String value){
        initPostcard(page);
        withValue(key, value);
        postcard.navigation();
    }

    public void router_goto(ENUM_RouterE page, String key, Serializable value){
        initPostcard(page);
        postcard.withSerializable(key, value);
        postcard.navigation();
    }

    private void initPostcard(ENUM_RouterE page){
        postcard = ARouter.getInstance().build(page.getPath());
    }

    private void withValue(String key, Object value){
        if (value instanceof Integer) {
            int i = ((Integer) value).intValue();
            postcard.withInt(key, i);
        } else if (value instanceof String) {
            String s = (String) value;
            postcard.withString(key, s);
        } else if (value instanceof Double) {
            double d = ((Double) value).doubleValue();
            postcard.withDouble(key, d);
        } else if (value instanceof Float) {
            float f = ((Float) value).floatValue();
            postcard.withFloat(key, f);
        } else if (value instanceof Long) {
            long l = ((Long) value).longValue();
            postcard.withLong(key, l);
        } else if (value instanceof Boolean) {
            boolean b = ((Boolean) value).booleanValue();
            postcard.withBoolean(key, b);
        } else {
            Logger.log(Logger.Type.WRONG, "ARouter -参数数据类型错误  value = "+value);
        }
    }


}
