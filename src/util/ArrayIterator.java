package util;

import java.util.Iterator;

/**
 * Iterates efficiently through an array.
 */

public class ArrayIterator<T> implements Iterator<T> {

    T[] values;//Generic Types.
    int counter;

    public ArrayIterator(T[] values) {
        this.values = values;
        counter = 0;
    }

    @Override
    public boolean hasNext() {
        return counter < values.length;
    }

    @Override
    public T next() {
        return values[counter++];
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
