package com.mascot.server.model;

import com.mascot.server.common.ServerUtils;

/**
 * Created by Nikolay on 20.03.2017.
 */
public enum EntityType {
    USER(User.class, true, false),
    PRODUCT(User.class, true, true),
    CLOTH(User.class, true, true);

    private EntityType(Class entityClass, boolean hasWeb, boolean selfRemove) {
        this.tableName = ServerUtils.getTableName(entityClass);
        this.entityName = entityClass.getSimpleName();
        this.hasWeb = hasWeb;
        this.selfRemove = selfRemove;
    }

//    public final Class entityClass;
    public final String tableName;
    public final String entityName;
    public final boolean hasWeb;
    public final boolean selfRemove;

}
