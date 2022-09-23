package com.ukraine.dc.api;

import com.ukraine.dc.api.domain.Column;
import com.ukraine.dc.api.domain.Entity;
import com.ukraine.dc.api.domain.Id;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

/**
 * The type EntityParser.
 */
public final class EntityParser {

    private EntityParser() {
    }

    public static String getIdValue(Object object, String columnName) {
        var data = getAllColumns(object.getClass(), object);
        return data.get(columnName);
    }

    public static Map<String, String> getAllColumns(Class<?> clazz, Object object) {
        Map<String, String> data = new HashMap<>();
        for (Field declaredField : clazz.getDeclaredFields()) {
            Column columnAnnotation = declaredField.getAnnotation(Column.class);
            if (columnAnnotation != null) {
                String columnNameFromAnnotation = columnAnnotation.name();
                String columnName = columnNameFromAnnotation.isEmpty() ? declaredField.getName() : columnNameFromAnnotation;
                declaredField.setAccessible(true);
                try {
                    var value = object != null ? declaredField.get(object).toString() : null;
                    if (declaredField.getType().isAssignableFrom(String.class)) {
                        value = format("'%s'", value);
                    }
                    data.put(columnName, value);
                } catch (IllegalAccessException e) {
                    throw new IllegalArgumentException("Can't get field: " + columnName);
                }
            }
        }
        return data;
    }

    public static String getTableName(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Annotation @Entity should be present");
        }
        Entity clazzAnnotation = clazz.getAnnotation(Entity.class);
        return clazzAnnotation.table().isEmpty() ? clazz.getSimpleName() : clazzAnnotation.table();
    }

    public static String getIdName(Class<?> clazz) {
        for (Field declaredField : clazz.getDeclaredFields()) {
            Id idAnnotation = declaredField.getAnnotation(Id.class);
            if (idAnnotation != null) {
                Column columnAnnotation = declaredField.getAnnotation(Column.class);
                String columnNameFromAnnotation = columnAnnotation != null ? columnAnnotation.name() : null;
                return columnNameFromAnnotation != null && columnNameFromAnnotation.isEmpty() ? declaredField.getName()
                        : columnNameFromAnnotation;
            }
        }
        throw new IllegalArgumentException("Annotation @Id should be present");
    }

}
