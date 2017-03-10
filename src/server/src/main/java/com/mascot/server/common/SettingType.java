package com.mascot.server.common;

/**
 * Created by Nikolay on 10.03.2017.
 */
public enum SettingType {

    SITE_URL("http://blablabla.ru"),
    SITE_TOKEN("1"),
    SITE_SECRET("2");

    private final boolean visible;
    private final String name;
    private final Object defaultValue;
    private final Class valueClass;
    private boolean mandatory;

    private SettingType(Object defaultValue) {
        this(defaultValue.getClass(), defaultValue, true, true);
    }

    private SettingType(Object defaultValue, boolean visible) {
        this(defaultValue.getClass(), defaultValue, visible, true);
    }

    private SettingType(Object defaultValue, boolean visible, boolean mandatory) {
        this(defaultValue.getClass(), defaultValue, visible, mandatory);
    }

    private SettingType(Class valueClass, Object defaultValue, boolean visible, boolean mandatory) {
        this.mandatory = mandatory;
        this.name = name();
        this.valueClass = valueClass;
        this.defaultValue = defaultValue;
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public String getName() {
        return name;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public Class getValueClass() {
        return valueClass;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    }
