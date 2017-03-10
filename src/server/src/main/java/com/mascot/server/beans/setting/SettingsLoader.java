package com.mascot.server.beans.setting;

import com.mascot.server.common.SettingDescriptor;
import com.mascot.server.common.SettingType;
import com.mascot.server.common.Settings;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Nikolay on 10.03.2017.
 */
public class SettingsLoader <A extends Settings> {
    private final Map<SettingType, Method> setters;
    private final Map<SettingType, Method> getters;
    private final Class<A> aClass;
    protected final Logger logger = Logger.getLogger(getClass());

    public SettingsLoader(Class<A> aClass) {
        this.aClass = aClass;
        setters = new HashMap<SettingType, Method>();
        getters = new HashMap<SettingType, Method>();
        for (Method method : aClass.getMethods()) {
            final SettingType setting = getSetting(method);
            if (setting == null) {
                continue;
            }
            final Class<?>[] params = method.getParameterTypes();
            if (isVoidReturnType(method) && params.length == 1 && setting.getValueClass().equals(params[0])) {
                setters.put(setting, method);
            } else if (!isVoidReturnType(method) && params.length == 0 && setting.getValueClass().equals(method.getReturnType())) {
                getters.put(setting, method);
            } else {
                throw new IllegalStateException("Wrong annotation on method Setting: " + method.getName());
            }
        }
    }

    public Map<SettingType, Object> getSettingToValue(Settings settings) {
        final Map<SettingType, Object> result = new HashMap<SettingType, Object>(getters.size());
        if (!settings.getClass().equals(aClass)) {
            throw new IllegalArgumentException("Expected class '" + aClass.getName() + "' but was '" + settings.getClass().getName() + "'");
        }
        for (Map.Entry<SettingType, Method> entry : getters.entrySet()) {
            final SettingType setting = entry.getKey();
            final Method method = entry.getValue();
            try {
                final Object value = method.invoke(settings);
                result.put(setting, value);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            } catch (InvocationTargetException e) {
                throw new IllegalStateException(e);
            }
        }
        return result;
    }

    private boolean isVoidReturnType(Method m) {
        final Class<?> returnType = m.getReturnType();
        return Void.TYPE.equals(returnType) || returnType == null;
    }

    private SettingType getSetting(Method method) {
        final SettingDescriptor annotation = method.getAnnotation(SettingDescriptor.class);
        return annotation != null ? annotation.value() : null;
    }

    public A getDefaultSettings() {
        final A result;
        try {
            result = aClass.newInstance();
            for (Map.Entry<SettingType, Method> entry : setters.entrySet()) {
                entry.getValue().invoke(result, entry.getKey().getDefaultValue());
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return result;
    }

    public void setValue(A settings, String name, String value) {
        final SettingType setting;
        try {
            setting = SettingType.valueOf(name);
            final Method method = setters.get(setting);
            method.invoke(settings, parse(value, setting));
        } catch (IllegalArgumentException e) {
            logger.warn(String.format("Skip setting value for setting name '%s'. Error message: %s", name, e.getMessage()));
        } catch (InvocationTargetException e) {
            throw new IllegalStateException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    public Object parse(String stringValue, SettingType setting) {
        final Class valueClass = setting.getValueClass();
        if (String.class.equals(valueClass)) {
            return stringValue;
        }
        if (Date.class.equals(valueClass) || java.sql.Date.class.equals(valueClass)) {
            try {
                return getDateFormat().parse(stringValue);
            } catch (ParseException e) {
                return setting.getDefaultValue();
            }
        }
        if (Boolean.class.equals(valueClass)) {
            return Boolean.parseBoolean(stringValue);
        }
        if (Integer.class.equals(valueClass)) {
            try {
                return Integer.parseInt(stringValue);
            } catch (Exception e) {
                logger.warn(String.format("Can't parse '%s' as Integer value", stringValue));
                return setting.getDefaultValue();
            }
        }
        if (Long.class.equals(valueClass)) {
            try {
                return Long.parseLong(stringValue);
            } catch (Exception e) {
                logger.warn(String.format("Can't parse '%s' as Long value", stringValue));
                return setting.getDefaultValue();
            }
        }
        if (Double.class.equals(valueClass)) {
            try {
                return Double.parseDouble(stringValue);
            } catch (Exception e) {
                logger.warn(String.format("Can't parse '%s' as Double value", stringValue));
                return setting.getDefaultValue();
            }
        }
        if (valueClass.isEnum()) {
            try {
                return Enum.valueOf(valueClass, stringValue);
            } catch (Exception e) {
                logger.warn(String.format("Can't parse '%s' as %s value", stringValue, valueClass.getSimpleName()));
                return setting.getDefaultValue();
            }
        }
        if (valueClass.isArray()) {
            Class enumType = valueClass.getComponentType();
            if (enumType.isEnum()) {
                try {
                    final String[] values = stringValue != null ? stringValue.split(";") : new String[0];
                    final Object[] enumsArray = new Object[values.length];
                    for (int i = 0; i < values.length; i++) {
                        enumsArray[i] = Enum.valueOf(enumType, values[i]);
                    }
/*
                    Object enumsArray = Array.newInstance(enumType, values.length);

                    for (int i = 0; i < values.length; i++) {
                        Array.set(enumsArray, i, Enum.valueOf(enumType, values[i]));
                    }
*/
                    return enumsArray;
                } catch (Exception e) {
                    logger.warn(String.format("Can't parse '%s' as %s value", stringValue, valueClass.getSimpleName()));
                    return setting.getDefaultValue();
                }
            }
        }
        throw new UnsupportedOperationException(String.format("Setting type %s is not supported", valueClass.getName()));
    }

    public static String format(Object value, Class valueClass) {
        if (value == null) {
            return null;
        }
        if (Date.class.equals(valueClass) || java.sql.Date.class.equals(valueClass)) {
            getDateFormat().format(value);
        }
        if (valueClass.isArray() && valueClass.getComponentType().isEnum()) {
            StringBuilder values = new StringBuilder();
            for (Enum v : (Enum[]) value) {
                if (values.length() > 0) {
                    values.append(";");
                }
                values.append(String.valueOf(v));
            }
            return values.toString();
        }
        return String.valueOf(value);
    }


    private static SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm");
    }


    public List<SettingType> getSettings() {
        return new ArrayList<SettingType>(setters.keySet());
    }
}
