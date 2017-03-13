package com.mascot.server.common.site;

import com.mascot.server.common.Settings;

/**
 * Created by Nikolay on 10.03.2017.
 */
public class SiteSettings {
    private String url;
    private String token;
    private String secret;

    public String getUrl() {
        return url;
    }

    public String getToken() {
        return token;
    }

    public String getSecret() {
        return secret;
    }

    public static SiteSettings build(Settings settings) {
        SiteSettings result = new SiteSettings();
        result.url = settings.getSiteUrl();
        result.token = settings.getSiteToken();
        result.secret = settings.getSiteSecret();
        return result;
    }
}
