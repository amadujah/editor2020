package fr.istic.aco.editor.utils;

public class Pair<K, V> {
    private K first;       // first field of a Pair
    private V second;      // second field of a Pair

    // Constructs a new Pair with specified values
    private Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }

    public V getValue() {
        return second;
    }

    public void setValue(K key, V value) {
        first = key;
        second = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;

        // call equals() method of the underlying objects
        if (!first.equals(pair.first))
            return false;
        return second.equals(pair.second);
    }

    @Override
    public int hashCode() {
        // use hash codes of the underlying objects
        return 31 * first.hashCode() + second.hashCode();
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }

    public static <K, V> Pair<K, V> of(K key, V value) {
        // calls private constructor
        return new Pair<>(key, value);
    }
}

