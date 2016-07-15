package com.rdshoep.android.social.login.utils;
/*
 * @description
 *   Please write the AppHelper module's description
 * @author Zhang (rdshoep@126.com)
 *   http://www.rdshoep.com/
 * @version 
 *   1.0.0(6/22/2016)
 */

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

public class AppHelper {
    /**
     * 获取meta信息
     *
     * @param context app 上下文
     * @param name    meta节点名称
     * @return 节点对应的值
     */
    public static String getApplicationMetaData(Context context, String name) {
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo appInfo = pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo != null && appInfo.metaData != null) {
                Object value = appInfo.metaData.get(name);

                if (value != null) {
                    //纯数字会被异常解析，无法正常转换成原始值
                    if (value instanceof Number) {
                        throw new RuntimeException("Meta data do not support number, please see http://stackoverflow.com/questions/2154945/how-to-force-a-meta-data-value-to-type-string !");
                    } else {
                        return String.valueOf(value);
                    }
                }

                return null;
            }
        } catch (PackageManager.NameNotFoundException ignored) {
            ignored.printStackTrace();
        }
        return null;
    }
}
