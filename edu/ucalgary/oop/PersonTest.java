package edu.ucalgary.oop;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PersonTest {

    @Test
    public void testGetFirstName() {
        Person person = new Person("John", "Doe");
        assertEquals("John", person.getFirstName());
    }

    @Test
    public void testGetLastName() {
        Person person = new Person("John", "Doe");
        assertEquals("Doe", person.getLastName());
    }

    @Test
    public void testSetFirstName() {
        Person person = new Person("John", "Doe");
        person.setFirstName("Jane");
        assertEquals("Jane", person.getFirstName());
    }

    @Test
    public void testSetLastName() {
        Person person = new Person("John", "Doe");
        person.setLastName("Smith");
        assertEquals("Smith", person.getLastName());
    }

    @Test
    public void testSetLastNameNull() {
        Person person = new Person("John", "Doe");
        person.setLastName(null);
        assertEquals("", person.getLastName());
    }

    @Test
    public void testConstructorWithFirstNameOnly() {
        Person person = new Person("John");
        assertEquals("John", person.getFirstName());
        assertEquals("", person.getLastName());
    }
}