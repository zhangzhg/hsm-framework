package com.framework.core.support.dialect;

import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;


public class PostgreSQL94Dialect extends PostgreSQL92Dialect {

    /**
     * Constructs a PostgreSQL94Dialect
     */
    public PostgreSQL94Dialect() {
        super();
        registerFunction( "make_interval", new StandardSQLFunction("make_interval", StandardBasicTypes.TIMESTAMP) );
        registerFunction( "make_timestamp", new StandardSQLFunction("make_timestamp", StandardBasicTypes.TIMESTAMP) );
        registerFunction( "make_timestamptz", new StandardSQLFunction("make_timestamptz", StandardBasicTypes.TIMESTAMP) );
        registerFunction( "make_date", new StandardSQLFunction("make_date", StandardBasicTypes.DATE) );
        registerFunction( "make_time", new StandardSQLFunction("make_time", StandardBasicTypes.TIME) );
    }
}
