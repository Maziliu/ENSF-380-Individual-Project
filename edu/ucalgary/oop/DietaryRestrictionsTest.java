package edu.ucalgary.oop;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class DietaryRestrictionsTest {

    @Test
    public void testToStringAVML() {
        assertEquals("AVML", DietaryRestrictions.AVML.toString());
    }

    @Test
    public void testToStringDBML() {
        assertEquals("DBML", DietaryRestrictions.DBML.toString());
    }

    @Test
    public void testToStringGFML() {
        assertEquals("GFML", DietaryRestrictions.GFML.toString());
    }

    @Test
    public void testToStringKSML() {
        assertEquals("KSML", DietaryRestrictions.KSML.toString());
    }

    @Test
    public void testToStringLCML() {
        assertEquals("LCML", DietaryRestrictions.LCML.toString());
    }

    @Test
    public void testToStringLSML() {
        assertEquals("LSML", DietaryRestrictions.LSML.toString());
    }

    @Test
    public void testToStringMOML() {
        assertEquals("MOML", DietaryRestrictions.MOML.toString());
    }

    @Test
    public void testToStringPFML() {
        assertEquals("PFML", DietaryRestrictions.PFML.toString());
    }

    @Test
    public void testToStringVGML() {
        assertEquals("VGML", DietaryRestrictions.VGML.toString());
    }

    @Test
    public void testToStringVJML() {
        assertEquals("VJML", DietaryRestrictions.VJML.toString());
    }
}