package com.rdshoep.android.social.login.base;
/*
 * @description
 *   Please write the LoginListener module's description
 * @author Zhang (rdshoep@126.com)
 *   http://www.rdshoep.com/
 * @version 
 *   1.0.0(6/22/2016)
 */

public interface LoginListener {

    void onComplete(LoginType type, AuthResult data);

    void onCancel();

    void onError(String err);
}
