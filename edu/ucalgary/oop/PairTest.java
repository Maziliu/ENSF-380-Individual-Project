package edu.ucalgary.oop;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PairTest {
    @Test
    public void testFirst() {
        Integer first = 10;
        String second = "test";
        Pair<Integer, String> pair = new Pair<>(first, second);
        assertEquals(first, pair.first());
    }

    @Test
    public void testSecond() {
        Integer first = 10;
        String second = "test";
        Pair<Integer, String> pair = new Pair<>(first, second);
        assertEquals(second, pair.second());
    }
}