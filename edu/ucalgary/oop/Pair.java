package edu.ucalgary.oop;

/**
 * Represents a pair of objects. This class mimics the basic properties of the
 * std:::pair in C++. Members are public for ease of access as it is supposed to
 * be a C++ struct.
 */
public class Pair<A, B> {
    public final A first;
    public final B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }
}
