package com.mascot.server.common;

/**
 * Created by Nikolay on 10.03.2017.
 */
public class Settings {

    private String siteUrl;
    private String siteToken;
    private String siteSecret;

    @SettingDescriptor(SettingType.SITE_URL)
    public String getSiteUrl() {
        return siteUrl;
    }

    @SettingDescriptor(SettingType.SITE_URL)
    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    @SettingDescriptor(SettingType.SITE_TOKEN)
    public String getSiteToken() {
        return siteToken;
    }

    @SettingDescriptor(SettingType.SITE_TOKEN)
    public void setSiteToken(String siteToken) {
        this.siteToken = siteToken;
    }

    @SettingDescriptor(SettingType.SITE_SECRET)
    public String getSiteSecret() {
        return siteSecret;
    }

    @SettingDescriptor(SettingType.SITE_SECRET)
    public void setSiteSecret(String siteSecret) {
        this.siteSecret = siteSecret;
    }

}

