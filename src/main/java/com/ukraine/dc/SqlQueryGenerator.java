package com.ukraine.dc;

import com.ukraine.dc.api.QueryGenerator;

import java.util.stream.Collectors;

import static com.ukraine.dc.api.EntityParser.*;
import static java.lang.String.join;

/**
 * The type SqlQueryGenerator.
 */
public final class SqlQueryGenerator implements QueryGenerator {
    private static final String INSERT = "INSERT INTO %s (%s) VALUES (%s);";
    private static final String SELECT_ALL = "SELECT %s FROM %s;";
    private static final String SELECT_BY_ID = "SELECT * FROM %s WHERE %s=%s;";
    private static final String DELETE_BY_ID = "DELETE FROM %s WHERE %s=%s;";
    private static final String UPDATE_BY_ID = "UPDATE %s SET %s WHERE %s=%s;";

    @Override
    public String findAll(Class<?> clazz) {
        return String.format(SELECT_ALL,
                join(", ", getAllColumns(clazz, null).keySet()),
                getTableName(clazz));
    }

    @Override
    public String findById(Object idValue, Class<?> clazz) {
        return String.format(SELECT_BY_ID, getTableName(clazz),
                getIdName(clazz), idValue);
    }

    @Override
    public String insert(Object object) {
        return String.format(INSERT,
                getTableName(object.getClass()),
                join(", ", getAllColumns(object.getClass(), object).keySet()),
                join(", ", getAllColumns(object.getClass(), object).values()));
    }

    @Override
    public String remove(Object condition, Class<?> object) {
        return String.format(DELETE_BY_ID,
                getTableName(object),
                getIdName(object),
                condition);
    }

    @Override
    public String update(Object object) {
        String tableName = getTableName(object.getClass());
        String idColumnName = getIdName(object.getClass());
        var queryParameters = getConditionForUpdate(object, object.getClass(), idColumnName);

        String conditionForUpdate = getIdName(object.getClass());
        return String.format(UPDATE_BY_ID, tableName,
                queryParameters, conditionForUpdate, getIdValue(object, idColumnName));
    }

    private String getConditionForUpdate(Object object, Class<?> clazz, String idColumn) {
        var map = getAllColumns(clazz, object);
        map.remove(idColumn);
        return map.keySet().stream().
                map(k -> k + "=" + map.get(k)).
                collect(Collectors.joining(", ", "", ""));
    }
}
