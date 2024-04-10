package edu.ucalgary.oop;

/**
 * Represents a pair of objects. This class mimics the basic properties of the
 * std:::pair in C++. Members are private for no other reason than for the
 * assingment as it is supposed to
 * be a C++ struct.
 */
public class Pair<A, B> {
    private A first;
    private B second;

    /**
     * Constructs a new Pair object with the specified first and second objects.
     * 
     * @param first  an object of type A
     * @param second an object of type B
     */
    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Returns the first object of the pair.
     * 
     * @return the first object of the pair
     */
    public A first() {
        return first;
    }

    /**
     * Returns the second object of the pair.
     * 
     * @return the second object of the pair
     */
    public B second() {
        return second;
    }
}
