package com.gmself.studio.mg.basemodule.utils.performanceUtil;

import android.os.Debug;
import android.text.TextUtils;

import com.gmself.studio.mg.basemodule.utils.dirUtil.DirsTools;

import java.io.File;

/**
 * Created by guomeng on 4/23.
 */

public class PerformanceTools {
    private static String filePath = null;

    private static PerformanceTools instance = new PerformanceTools();
    public static PerformanceTools getInstance(){
        if (TextUtils.isEmpty(filePath)){
            filePath = DirsTools.GetAnalysisFileCachePath();
            File f = new File(filePath);
            if (!f.exists()){
                f.mkdirs();
            }
        }
        return instance;
    }

    public void StartMethodTracing(String fileName){
        File file = new File(filePath+"/"+fileName);
        Debug.startMethodTracing(file.getAbsolutePath());
    }

    public void EndMethodTracing(){
        Debug.stopMethodTracing();
    }
}
