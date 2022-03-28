package Algorithms.Hashmap;

import org.jetbrains.annotations.NotNull;


public class Entry<K, V> {
    private final K key;
    private V value;

    public Entry(@NotNull K key, V value) {
        this.key = key;
        this.value = value;
    }

    public V getValue() {
        return value;
    }


    public K getKey() {
        return key;
    }


    public V setValue(V value) {
        var res = this.value;
        this.value = value;
        return res;
    }

    public String toString() {
        return "key: " + key.toString() + " value: " + value.toString();
    }

}
