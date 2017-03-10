package com.mascot.server.beans.setting;

import com.mascot.server.common.SettingType;
import com.mascot.server.common.Settings;

/**
 * Created by Nikolay on 10.03.2017.
 */
public interface SettingService {
    String NAME = "SettingProvider";

    <A> A getValue(SettingType settingType);

    <A extends Settings> A get(Class<A> aClass);
}
