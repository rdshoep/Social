package com.rdshoep.android.social.login.sina;
/*
 * @description
 *   Please write the SinaWeibo module's description
 * @author Zhang (rdshoep@126.com)
 *   http://www.rdshoep.com/
 * @version 
 *   1.0.0(6/22/2016)
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.rdshoep.android.social.login.base.AuthResult;
import com.rdshoep.android.social.login.base.Login;
import com.rdshoep.android.social.login.base.LoginListener;
import com.rdshoep.android.social.login.base.LoginType;
import com.rdshoep.android.social.login.utils.AppHelper;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

public class SinaWeiboLoginWrapper extends Login implements WeiboAuthListener {
    private static final String META_NODE_APP_ID = "SINA_WEIBO_APP_ID";
    private static final String META_NODE_CALL_BACK = "SINA_WEIBO_CALLBACK";
    private static final String SCOPE_DEFAULT = "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog,"
            + "invitation_write";
    private static String WEIBO_APP_ID = null;
    private static String WEIBO_CALLBACK = null;

    SsoHandler mSsoHandler;
    LoginListener listener;

    public SinaWeiboLoginWrapper(Context context) {
        super(context, LoginType.SinaWeibo);

        initAppId(context);
    }

    public void initAppId(Context context) {
        if (WEIBO_APP_ID == null) {
            String appId = AppHelper.getApplicationMetaData(context, META_NODE_APP_ID);
            if (appId != null) {
                WEIBO_APP_ID = appId;
            }

            if (appId == null) {
                throw new RuntimeException(String
                        .format("Can't find meta node(%s), please check!", META_NODE_APP_ID));
            }

            String callback = AppHelper.getApplicationMetaData(context, META_NODE_CALL_BACK);
            if (callback != null) {
                WEIBO_CALLBACK = callback;
            }

            if (callback == null) {
                throw new RuntimeException(String
                        .format("Can't find meta node(%s), please check!", META_NODE_CALL_BACK));
            }
        }
    }

    @Override
    public void login(Activity activity, LoginListener listener) {
        this.listener = listener;

        AuthInfo weiboAuth = new AuthInfo(activity, WEIBO_APP_ID, WEIBO_CALLBACK, SCOPE_DEFAULT);
        mSsoHandler = new SsoHandler(activity, weiboAuth);
        mSsoHandler.authorize(this);
    }

    @Override
    public void login(Fragment fragment, LoginListener listener) {
        login(fragment.getActivity(), listener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    @Override
    public void onComplete(Bundle bundle) {
        Oauth2AccessToken mAccessToken = Oauth2AccessToken.parseAccessToken(bundle);
        if (mAccessToken.isSessionValid()) {
            AuthResult result = new AuthResult();
            result.setExpiresIn(mAccessToken.getRefreshToken())
                    .setOpenId(mAccessToken.getUid())
                    .setAccessToken(mAccessToken.getToken())
                    .setRefreshToken(mAccessToken.getRefreshToken());
            listener.onComplete(getType(), result);
        } else {
            String code = bundle.getString("code", "");
            listener.onError(String.format("Sina Weibo auth login failed: return code %s", code));
        }
    }

    @Override
    public void onWeiboException(WeiboException e) {
        listener.onError(e.getMessage());
    }

    @Override
    public void onCancel() {
        listener.onCancel();
    }
}
