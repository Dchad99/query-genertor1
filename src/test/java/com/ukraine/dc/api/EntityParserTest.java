package com.ukraine.dc.api;

import com.ukraine.dc.EntityWithoutId;
import com.ukraine.dc.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.ukraine.dc.api.EntityParser.getIdName;
import static com.ukraine.dc.api.EntityParser.getTableName;
import static org.junit.jupiter.api.Assertions.*;

class EntityParserTest {

    @Test
    void givenEntityWithExistingIdAnnotation() {
        assertEquals("id", getIdName(Person.class));
    }

    @Test
    void givenEntityWithoutIdColumn_thenThrowIllegalArgumentException() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> getIdName(EntityWithoutId.class));
        assertEquals("Annotation @Id should be present", exception.getMessage());
    }

    @Test
    void givenEntityWithoutEntityAnnotation_thenShouldThrowIllegalArgumentException() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> getTableName(EntityWithoutId.class));
        assertEquals("Annotation @Entity should be present", exception.getMessage());
    }

    @Test
    void givenEntity_thenShouldReturnTableName() {
        String tableName = getTableName(Person.class);
        assertEquals("persons", tableName);
    }

}