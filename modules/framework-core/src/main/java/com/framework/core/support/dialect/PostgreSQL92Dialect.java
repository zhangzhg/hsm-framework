package com.framework.core.support.dialect;

import org.hibernate.dialect.PostgreSQL9Dialect;

import java.sql.Types;

/**
 * hibernate postgresql9.2方言
 * @author linliangkun
 * @date 15/7/29
 */
public class PostgreSQL92Dialect extends PostgreSQL9Dialect {

    /**
     * Constructs a PostgreSQL92Dialect
     */
    public PostgreSQL92Dialect() {
        super();
        this.registerColumnType( Types.JAVA_OBJECT, "json" );
    }
}
