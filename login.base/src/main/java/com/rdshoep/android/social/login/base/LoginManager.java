package com.rdshoep.android.social.login.base;
/*
 * @description
 *   Please write the LoginManager module's description
 * @author Zhang (rdshoep@126.com)
 *   http://www.rdshoep.com/
 * @version 
 *   1.0.0(6/22/2016)
 */

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class LoginManager {
    public static LoginWrapper loginWrapper = null;

    public static void login(Activity activity, LoginType type, LoginListener listener) {
        loginWrapper = new LoginWrapper(LoginType.getLoginWrapperInstance(type, activity));
        loginWrapper.login(activity, listener);
    }

    public static void login(Fragment fragment, LoginType type, LoginListener listener) {
        loginWrapper = new LoginWrapper(LoginType.getLoginWrapperInstance(type, fragment.getContext()));
        loginWrapper.login(fragment, listener);
    }

    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (loginWrapper != null) {
            loginWrapper.onActivityResult(requestCode, resultCode, data);
        }
        loginWrapper = null;
    }
}
