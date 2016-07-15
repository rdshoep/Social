package com.rdshoep.android.social.demos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.rdshoep.android.social.login.base.AuthResult;
import com.rdshoep.android.social.login.base.LoginListener;
import com.rdshoep.android.social.login.base.LoginManager;
import com.rdshoep.android.social.login.base.LoginType;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LoginListener {

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.wechat).setOnClickListener(this);
        findViewById(R.id.qq).setOnClickListener(this);
        findViewById(R.id.sina).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wechat:
                LoginManager.login(this, LoginType.WeChat, this);
                break;
            case R.id.qq:
                LoginManager.login(this, LoginType.QQ, this);
                break;
            case R.id.sina:
                LoginManager.login(this, LoginType.SinaWeibo, this);
                break;
        }
    }

    @Override
    public void onComplete(LoginType type, AuthResult data) {
        toast("onComplete:" + type);
    }

    @Override
    public void onCancel() {
        toast("onCancel");
    }

    @Override
    public void onError(String err) {
        toast("onError:" + err);
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
