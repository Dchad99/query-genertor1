package com.ukraine.dc;

import com.ukraine.dc.api.QueryGenerator;
import com.ukraine.dc.api.QueryTemplates;

import java.util.stream.Collectors;

import static com.ukraine.dc.api.EntityParser.*;
import static java.lang.String.join;

/**
 * The type SqlQueryGenerator.
 */
public final class SqlQueryGenerator implements QueryGenerator {

    @Override
    public String findAll(Class<?> clazz) {
        return String.format(QueryTemplates.SELECT_ALL.getQuery(),
                join(", ", getAllColumns(clazz, null).keySet()),
                getTableName(clazz));
    }

    @Override
    public String findById(Object idValue, Class<?> clazz) {
        return String.format(QueryTemplates.SELECT_BY_ID.getQuery(),
                getTableName(clazz), getIdName(clazz), idValue);
    }

    @Override
    public String insert(Object object) {
        return String.format(QueryTemplates.INSERT.getQuery(),
                getTableName(object.getClass()),
                join(", ", getAllColumns(object.getClass(), object).keySet()),
                join(", ", getAllColumns(object.getClass(), object).values()));
    }

    @Override
    public String remove(Object condition, Class<?> object) {
        return String.format(QueryTemplates.DELETE_BY_ID.getQuery(),
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
        return String.format(QueryTemplates.UPDATE_BY_ID.getQuery(), tableName,
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
