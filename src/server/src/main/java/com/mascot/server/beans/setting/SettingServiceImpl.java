package com.mascot.server.beans.setting;

import com.mascot.common.MascotUtils;
import com.mascot.server.beans.AbstractMascotService;
import com.mascot.server.common.SettingType;
import com.mascot.server.common.Settings;
import com.mascot.server.model.SettingEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Nikolay on 10.03.2017.
 */
@Service(SettingService.NAME)
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class SettingServiceImpl extends AbstractMascotService implements SettingServiceWriter {

    @Override
    public <A> A getValue(SettingType settingType) {
        final List<String> list = em.createQuery("select s.value from SettingEntity s where s.name = :name")
                .setParameter("name", settingType.getName())
                .getResultList();
        if (list.isEmpty()) {
            return (A) settingType.getDefaultValue();
        }
        if (list.size() > 1) {
            throw new IllegalStateException("Founded more than one setting for name '" + settingType.getName() + "'");
        }
        final String value = list.get(0);
        final SettingsLoader<Settings> settingsLoader = new SettingsLoader<Settings>(Settings.class);
        final Object result = settingsLoader.parse(value, settingType);
        return (A) result;
    }

    @Override
    public <A extends Settings> A get(Class<A> aClass) {
        final List<SettingEntity> resultList = em.createQuery("select s from SettingEntity s").getResultList();
        final SettingsLoader<A> settingsLoader = new SettingsLoader<A>(aClass);
        final A setting = settingsLoader.getDefaultSettings();
        for (SettingEntity entity : resultList) {
            settingsLoader.setValue(setting, entity.getName(), entity.getValue());
        }
        return setting;
    }

    public Settings getSettings() {
        final List<SettingEntity> resultList = em.createQuery("select s from SettingEntity s").getResultList();
        final SettingsLoader<Settings> settingsLoader = new SettingsLoader<Settings>(Settings.class);
        final Settings setting = settingsLoader.getDefaultSettings();
        for (SettingEntity entity : resultList) {
            settingsLoader.setValue(setting, entity.getName(), entity.getValue());
        }
        return setting;
    }

    @Override
    public void save(Map<SettingType, Object> settings) {
        for (Map.Entry<SettingType, Object> entry : settings.entrySet()) {
            final SettingType settingType = entry.getKey();
            final Object value = entry.getValue();
            final Object oldValue = getValue(settingType);
            if (!MascotUtils.equalsOrBothNull(value, oldValue)) {
                saveValue(settingType, value);
            }
        }
    }

    @Override
    public void saveValue(SettingType settingType, Object value) {
        final String formattedValue = SettingsLoader.format(value, settingType.getValueClass());
        final int updatedCount = em.createQuery("update SettingEntity set value = :value where name = :name")
                .setParameter("name", settingType.getName())
                .setParameter("value", formattedValue)
                .executeUpdate();
        if (updatedCount == 0) {
            final SettingEntity settingEntity = new SettingEntity();
            settingEntity.setName(settingType.getName());
            settingEntity.setValue(formattedValue);
            em.persist(settingEntity);
            logger.info(String.format("Setting %s was inserted with value %s", settingType, value));
        } else {
            logger.info(String.format("Setting %s was updated to value %s", settingType, value));
        }
    }

}
