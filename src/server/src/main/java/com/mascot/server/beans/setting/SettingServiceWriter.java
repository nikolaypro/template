package com.mascot.server.beans.setting;

import com.mascot.server.common.SettingType;

import java.util.Map;

/**
 * Created by Nikolay on 10.03.2017.
 */
public interface SettingServiceWriter extends SettingService {
    void save(Map<SettingType, Object> settings);

    void saveValue(SettingType settingType, Object value);
}
