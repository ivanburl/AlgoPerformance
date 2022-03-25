package Algorithms.Hashmap;
import java.util.Set;

//TODO: add docs
public interface MyHashMap<K, V> {
    V put(K key, V value);
    V get(K element);
    V remove(K elementKey);


    Set<K> setKeys();
    Set<Entry<K,V>> setEntries();

    int size();
    boolean isEmpty();
    void clear();
}
