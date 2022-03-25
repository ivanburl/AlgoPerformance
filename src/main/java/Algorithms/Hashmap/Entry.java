package Algorithms.Hashmap;

import org.jetbrains.annotations.NotNull;
import java.util.Map;


public class Entry<K, V> implements Map.Entry<K,V> {
    private final K key;
    private V value;

    public Entry(@NotNull K key, V value) {
        this.key = key;
        this.value = value;
    }

    public Entry(@NotNull Entry<K, V> entry) {
        this(entry.getKey(), entry.getValue());
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V setValue(V value) {
        var res = this.value;
        this.value = value;
        return res;
    }

    @Override
    public String toString() {
        return "key: " + key.toString() + " value: " + value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entry<?, ?> entry = (Entry<?, ?>) o;

        if (key != null ? !key.equals(entry.key) : entry.key != null) return false;
        return value != null ? value.equals(entry.value) : entry.value == null;
    }

    @Override
    public int hashCode() {
        return key.hashCode()^value.hashCode();
    }
}
