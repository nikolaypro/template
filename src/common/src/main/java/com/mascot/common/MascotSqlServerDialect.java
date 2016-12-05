package com.mascot.common;

import java.sql.Types;

/**
 * Created by Nikolay on 05.12.2016.
 */
public class MascotSqlServerDialect extends org.hibernate.dialect.SQLServer2008Dialect {
    public MascotSqlServerDialect() {
        registerHibernateType(Types.NVARCHAR, 4000, "string");
        registerHibernateType(Types.NCHAR, 4000, "string");
    }
}
