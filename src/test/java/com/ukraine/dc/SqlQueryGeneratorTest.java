package com.ukraine.dc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SqlQueryGeneratorTest {
    private final SqlQueryGenerator generator = new SqlQueryGenerator();

    @Test
    void findAll() {
        String expected = "SELECT person_name, id, salary FROM persons;";
        String actual = generator.findAll(Person.class);
        assertEquals(expected, actual);
    }

    @Test
    void findById() {
        Person person = new Person(1, "David", 12000);
        String expected = "SELECT * FROM persons WHERE id=1;";
        String actual = generator.findById(person.getId(), Person.class);

        assertEquals(expected, actual);
    }

    @Test
    void insert() {
        String expected = "INSERT INTO persons (person_name, id, salary) VALUES ('David', 1, 120000.0);";

        Person person = new Person(1, "David", 120000);
        String actual = generator.insert(person);

        assertEquals(expected, actual);
    }

    @Test
    void remove() {
        String expected = "DELETE FROM persons WHERE id=1;";

        Person person = new Person(1, "David", 12000);
        String actual = generator.remove(person.getId(), Person.class);

        assertEquals(expected, actual);
    }

    @Test
    void update() {
        String expected = "UPDATE persons SET person_name='David', salary=12.0 WHERE id=1;";

        Person updated = new Person(1, "Roman", 21);
        updated.setName("David");
        updated.setSalary(12);
        String actual = generator.update(updated);
        assertEquals(expected, actual);
    }
}