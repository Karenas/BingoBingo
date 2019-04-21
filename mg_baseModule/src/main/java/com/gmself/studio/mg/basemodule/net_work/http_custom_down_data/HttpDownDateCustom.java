package com.gmself.studio.mg.basemodule.net_work.http_custom_down_data;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by guomeng on 4/20.
 *
 * 下行数据公共部分定义
 *
 * 自定义格式
 */

public class HttpDownDateCustom {

    private HttpCustomServiceStatus status;

    private JSONObject result;

    public HttpCustomServiceStatus getStatus() {
        return status;
    }

    public void setStatus(HttpCustomServiceStatus status) {
        this.status = status;
    }

    public JSONObject getResult() {
        return result;
    }

    public void setResult(JSONObject result) {
        this.result = result;
    }
}
