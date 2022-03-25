package Algorithms.Hashmap.OpenAddressing;


import Algorithms.Hashmap.Entry;
import Algorithms.Hashmap.MyHashMap;

import java.util.*;


public class RobinHood<K, V> implements MyHashMap<K,V> {

    static int DEFAULT_CAPACITY = 1 << 9;
    static float DEFAULT_LOAD_FACTOR = 0.6f;

    private final float loadFactor;
    private int capacity, size = 0, maxDistance = 0;
    private Entry<K,V>[] array;
    private int[] dist;

    public RobinHood(int capacity, float loadFactor) {
        array = new Entry[capacity];
        dist = new int[capacity];

        this.capacity = capacity;
        this.loadFactor = loadFactor;
    }

    public RobinHood() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public RobinHood(int capacity) {
        this(capacity , DEFAULT_LOAD_FACTOR);
    }

    private int getHash(Entry element) {
        int hash = element.getKey().hashCode();
        if (hash < 0)
            return (hash+1) % capacity + capacity - 1;
        return hash % capacity;
    }

    private V put(Entry<K, V> element) {
        int d = 0;
        int i = getHash(element);

        if (size > capacity*loadFactor) {
            resize();
        }

        while (true) {
            if (array[i] == null) {
                array[i] = new Entry<K, V>(element);// TODO: is there any sense of new Entry
                dist[i] = d;
                maxDistance = Math.max(maxDistance, d);
                size++;

                if (size > capacity * loadFactor) resize();
                return null;
            } else if (array[i].getKey().equals(element.getKey())) {
                var res = array[i].getValue();
                array[i].setValue(element.getValue());
                return res;
            } else if (dist[i] < d) {
                var tmpE = array[i]; array[i] = element; element = tmpE;
                int tmp = dist[i]; dist[i] = d; d = tmp; // TODO: Find the way to create normal swap
            }

            i =  (i+1) % capacity; d++;
        }
    }

    private int getIndex(K elementKey) {
        int i = getHash(new Entry<K, V>(elementKey, null));
        for (int d = 0; d <= maxDistance+10; d++, i=(i+1)%capacity) {
            if (array[i] == null) return -1;
            if (array[i].getKey().equals(elementKey)) return i;
        }
        return -1;
    }

    @Override
    public V put(K key, V value) {
        return put(new Entry<K,V>(key,value));
    }

    public V get(K elementKey) {
        int i = getIndex(elementKey);
        if (i == -1) return null;
        else return (V) array[i].getValue();
    }

    public V remove(K elementKey) {
        int i = getIndex(elementKey);
        if (i == -1) return null;

        for (int j = (i + 1) % capacity; ; i = j, j = (j + 1) % capacity) {
            if (array[j] == null || dist[j] == 0) {
                var res = array[i].getValue();
                array[i] = null;
                dist[i] = 0;
                size--;
                return res;
            }

            var tmp = array[i]; array[i] = array[j]; array[j] = tmp;
            dist[i] = dist[j] - 1;
        }
    }

    @Override
    public Set<K> setKeys() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> setEntries() {
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public void clear() {
        size = 0;
        for (int i=1;i<=capacity;i++) {
            array[i] = null;
            dist[i]=0;
        }
    }

    public void resize() {
        capacity += capacity;
        var oldData = this.array;

        array = new Entry[capacity];
        dist = new int[capacity];
        maxDistance = 0;
        size = 0;

        for (var i : oldData) {
            if (i != null) {
                put(i);
            }
        }
    }

}
