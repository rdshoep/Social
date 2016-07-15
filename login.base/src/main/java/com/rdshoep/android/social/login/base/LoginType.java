package com.rdshoep.android.social.login.base;
/*
 * @description
 *   Please write the LoginType module's description
 * @author Zhang (rdshoep@126.com)
 *   http://www.rdshoep.com/
 * @version 
 *   1.0.0(6/22/2016)
 */

import android.content.Context;
import android.support.annotation.NonNull;

import java.lang.reflect.Constructor;

public enum LoginType {
    QQ("qq.QQ"), //QQ
    WeChat("wechat.WeChat"), //微信
    SinaWeibo("sina.SinaWeibo"); //Sina微博

    private String classNamePrefix;

    LoginType(@NonNull String classNamePrefix) {
        this.classNamePrefix = classNamePrefix;
    }

    /**
     * 返回对应类的前缀
     * 这个是严格约束，{@link Login}的实现类必须是 classNamePrefix + "LoginWrapper" 的格式，否则无法找到相应的类
     *
     * @return 实现类名的前缀
     */
    @NonNull
    public String getClassNamePrefix() {
        return classNamePrefix;
    }

    /**
     * 根据类型实例化相应的类
     *
     * @param type    登陆类别
     * @param context 上下文
     */
    public static Login getLoginWrapperInstance(LoginType type, Context context) {
        String className = "com.rdshoep.android.social.login." + type.classNamePrefix + "LoginWrapper";
        Class clz = null;

        try {
            clz = Class.forName(className);
            Constructor<?>[] constructors = clz.getConstructors();

            return (Login) constructors[0].newInstance(context);
        } catch (Exception e) {
            e.printStackTrace();

            if (e instanceof ClassNotFoundException) {
                throw new RuntimeException(String.format("Can't find class(%s)", className));
            } else {
                throw new RuntimeException(String.format("Can't invoke newInstance for class(%s)", className));
            }
        }
    }
}
