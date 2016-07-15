package com.rdshoep.android.social.login.wechat;
/*
 * @description
 *   Please write the WeChatCallback module's description
 * @author Zhang (rdshoep@126.com)
 *   http://www.rdshoep.com/
 * @version 
 *   1.0.0(6/22/2016)
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.rdshoep.android.social.login.base.AuthResult;
import com.rdshoep.android.social.login.base.LoginListener;
import com.rdshoep.android.social.login.base.LoginManager;
import com.rdshoep.android.social.login.base.LoginType;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

public abstract class WeChatCallback extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "WeChatCallback";
    WeChatLoginWrapper loginWrapper;
    IWXAPI wxApi;
    LoginListener loginListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (LoginManager.loginWrapper != null
                && LoginManager.loginWrapper.getLoginWrapper() instanceof WeChatLoginWrapper) {
            loginWrapper = (WeChatLoginWrapper) LoginManager.loginWrapper.getLoginWrapper();
            wxApi = loginWrapper.getWxApi();
            loginListener = loginWrapper.getListener();
            wxApi.handleIntent(getIntent(), this);
        } else {
            finish();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        wxApi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.d(TAG, "onReq: ");
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onResp(BaseResp baseResp) {
        Log.d(TAG, "onResp: ");
        if (baseResp.transaction.startsWith(WeChatLoginWrapper.TRANSACTION_PREFIX)) {
            switch (baseResp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    SendAuth.Resp resp = (SendAuth.Resp) baseResp;
                    AuthResult authResult = new AuthResult();
                    authResult.setCode(resp.code);
                    authResult.setOpenId(resp.openId);
                    loginListener.onComplete(LoginType.WeChat, authResult);
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    loginListener.onCancel();
                    break;
                default:
                    loginListener.onError(String
                            .format("WeChat login failed(msg: %s), return code %d"
                                    , baseResp.errStr, baseResp.errCode));
                    break;
            }
            this.finish();
        }
    }
}
