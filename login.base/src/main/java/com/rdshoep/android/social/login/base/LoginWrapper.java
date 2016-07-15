package com.rdshoep.android.social.login.base;
/*
 * @description
 *   Please write the LoginWrapper module's description
 * @author Zhang (rdshoep@126.com)
 *   http://www.rdshoep.com/
 * @version 
 *   1.0.0(6/22/2016)
 */

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class LoginWrapper extends Login {

    private Login loginWrapper;

    public LoginWrapper(Login wrapper) {
        super(wrapper.getContext(), wrapper.getType());

        this.loginWrapper = wrapper;
    }

    public Login getLoginWrapper() {
        return loginWrapper;
    }

    @Override
    public void login(Activity activity, LoginListener listener) {
        this.loginWrapper.login(activity, listener);
    }

    @Override
    public void login(Fragment fragment, LoginListener listener) {
        this.loginWrapper.login(fragment, listener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.loginWrapper.onActivityResult(requestCode, resultCode, data);
    }
}
