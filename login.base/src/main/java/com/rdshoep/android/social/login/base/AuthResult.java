package com.rdshoep.android.social.login.base;
/*
 * @description
 *   Please write the AuthInfo module's description
 * @author Zhang (rdshoep@126.com)
 *   http://www.rdshoep.com/
 * @version 
 *   1.0.0(6/22/2016)
 */

public class AuthResult {
    private String accessToken;
    private String refreshToken;
    private String expiresIn;
    private String openId;
    private String code;

    public String getAccessToken() {
        return accessToken;
    }

    public AuthResult setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public AuthResult setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public AuthResult setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    public String getOpenId() {
        return openId;
    }

    public AuthResult setOpenId(String openId) {
        this.openId = openId;
        return this;
    }

    public String getCode() {
        return code;
    }

    public AuthResult setCode(String code) {
        this.code = code;
        return this;
    }
}
