package com.rdshoep.android.social.login.base;
/*
 * @description
 *   Please write the Login module's description
 * @author Zhang (rdshoep@126.com)
 *   http://www.rdshoep.com/
 * @version 
 *   1.0.0(6/22/2016)
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public abstract class Login {

    /**
     * 登陆的类别
     */
    private LoginType type;
    /**
     * APP上下文环境
     */
    protected Context context;

    public Login(Context context, LoginType type) {
        this.context = context;
        this.type = type;
    }

    /**
     * 具体实现登陆的类别
     */
    public LoginType getType() {
        return type;
    }

    public Context getContext() {
        return context;
    }

    /**
     * 执行auth登陆认证
     *
     * @param activity 调用login的Activity容器
     * @param listener 认证回调
     */
    public abstract void login(Activity activity, LoginListener listener);


    /**
     * 执行auth登陆认证
     *
     * @param fragment 调用login的Fragment容器
     * @param listener 认证回调
     */
    public abstract void login(Fragment fragment, LoginListener listener);

    /**
     * 容器login登陆后的系统回调方法，确保某些低内存情况下能够正常获取返回值
     *
     * @param requestCode requestCode
     * @param resultCode  requestCode
     * @param data        data
     */
    public abstract void onActivityResult(int requestCode, int resultCode, Intent data);
}
