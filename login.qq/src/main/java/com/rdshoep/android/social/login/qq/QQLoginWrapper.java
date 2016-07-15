package com.rdshoep.android.social.login.qq;
/*
 * @description
 *   Please write the QQLoginWrapper module's description
 * @author Zhang (rdshoep@126.com)
 *   http://www.rdshoep.com/
 * @version 
 *   1.0.0(6/22/2016)
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.rdshoep.android.social.login.base.AuthResult;
import com.rdshoep.android.social.login.base.Login;
import com.rdshoep.android.social.login.base.LoginListener;
import com.rdshoep.android.social.login.base.LoginType;
import com.rdshoep.android.social.login.utils.AppHelper;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

public class QQLoginWrapper extends Login implements IUiListener {
    private static final String META_NODE_QQ_ID = "QQ_APP_ID";
    private static final String SCOPE_DEFAULT = "all";
    private static String QQ_APP_ID = null;

    private Tencent tencent;
    private LoginListener listener;

    public QQLoginWrapper(Context context) {
        super(context, LoginType.QQ);

        initAppId(context);

        tencent = Tencent.createInstance(QQ_APP_ID, context.getApplicationContext());
    }

    public void initAppId(Context context) {
        if (QQ_APP_ID == null) {
            String appId = AppHelper.getApplicationMetaData(context, META_NODE_QQ_ID);
            if (appId != null) {
                QQ_APP_ID = appId;
            }

            if (appId == null) {
                throw new RuntimeException(String
                        .format("Can't find meta node(%s), please check!", META_NODE_QQ_ID));
            }
        }
    }

    @Override
    public void login(Activity activity, LoginListener listener) {
        this.listener = listener;
        tencent.login(activity, SCOPE_DEFAULT, this);
    }

    @Override
    public void login(Fragment fragment, LoginListener listener) {
        this.listener = listener;
        tencent.login(fragment, SCOPE_DEFAULT, this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN) {
            Tencent.handleResultData(data, this);
        }
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onComplete(Object o) {
        JSONObject res = (JSONObject) o;

        int ret = res.optInt("ret", -1);
        if (ret == 0) {
            AuthResult authResult = new AuthResult();
            authResult.setOpenId(res.optString("openid"));
            authResult.setAccessToken(res.optString("access_token"));
            authResult.setExpiresIn(res.optString("expires_in"));
            listener.onComplete(getType(), authResult);
            return;
        }

        listener.onError(String.format("QQ login onComplete ret is %d , not 0!", ret));
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onError(UiError uiError) {
        listener.onError(String.format("QQ login error(%d): %s \n details: %s"
                , uiError.errorCode, uiError.errorMessage, uiError.errorDetail));
    }

    @Override
    public void onCancel() {
        listener.onCancel();
    }
}
