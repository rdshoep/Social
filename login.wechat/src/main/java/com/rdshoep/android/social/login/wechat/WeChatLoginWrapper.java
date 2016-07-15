package com.rdshoep.android.social.login.wechat;
/*
 * @description
 *   Please write the WeChatLoginWrapper module's description
 * @author Zhang (rdshoep@126.com)
 *   http://www.rdshoep.com/
 * @version 
 *   1.0.0(6/22/2016)
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.rdshoep.android.social.login.base.Login;
import com.rdshoep.android.social.login.base.LoginListener;
import com.rdshoep.android.social.login.base.LoginType;
import com.rdshoep.android.social.login.utils.AppHelper;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WeChatLoginWrapper extends Login {
    public static final String TRANSACTION_PREFIX = "Login_";

    private static final String META_NODE_WECHAT_ID = "WECHAT_APP_ID";
    private static final String SCOPE_DEFAULT = "snsapi_userinfo";
    private static String WECHAT_APP_ID = null;

    private LoginListener listener;
    private IWXAPI wxApi;

    public WeChatLoginWrapper(Context context) {
        super(context, LoginType.WeChat);

        initAppId(context);

        wxApi = WXAPIFactory.createWXAPI(context, WECHAT_APP_ID, true);
        wxApi.registerApp(WECHAT_APP_ID);
    }

    public void initAppId(Context context) {
        if (WECHAT_APP_ID == null) {
            String appId = AppHelper.getApplicationMetaData(context, META_NODE_WECHAT_ID);
            if (appId != null) {
                WECHAT_APP_ID = appId;
            }

            if (appId == null) {
                throw new RuntimeException(String
                        .format("Can't find meta node(%s), please check!", META_NODE_WECHAT_ID));
            }
        }
    }

    protected IWXAPI getWxApi() {
        return wxApi;
    }

    protected LoginListener getListener() {
        return listener;
    }

    @Override
    public void login(Activity activity, LoginListener listener) {
        this.listener = listener;

        if (!wxApi.isWXAppInstalled()) {
            listener.onError("WeChat App is not installed!");
        }

        SendAuth.Req req = new SendAuth.Req();
        req.scope = SCOPE_DEFAULT;
        req.transaction = TRANSACTION_PREFIX + System.currentTimeMillis();
//        req.state = TRANSACTION_PREFIX + System.currentTimeMillis();
        wxApi.sendReq(req);
    }

    @Override
    public void login(Fragment fragment, LoginListener listener) {
        login(fragment.getActivity(), listener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
