package Algorithms.Hashmap.OpenAddressing;


import Algorithms.BinarySearch.BinarySearch;
import Algorithms.Hashmap.Entry;
import Algorithms.Hashmap.MyHashMap;

import java.util.*;


public class RobinHood<K, V> implements MyHashMap<K,V> {

    static float DEFAULT_LOAD_FACTOR = 0.75f;
    static int DEFAULT_CAPACITY = 512;

    private final static int[] PRIMES = new int[] {2,3,5,11,17,37,67,131,257,521,1031,2053,4099,8209,16411,32771,65537,131101,262147,524309,1048583,2097169,4194319,8388617,16777259,33554467,67108879,134217757,268435459,536870923,1073741827,1610612741,1879048201,2013265921,2080374797,2113929217,2130706433,2139095053,2143289353,2145386521,2146435103,2146959361,2147221507,2147352577,2147418127,2147450923,2147467321,2147475481,2147479573,2147481629,2147482661,2147483137,2147483399,2147483543,2147483587,2147483629};

    private final float loadFactor;

    private int capacity, size = 0, maxDistance = 0 , maxProbeCount, capacityIndex, MOD;

    private Entry<K,V>[] array;
    private int[] dist;

    public RobinHood(int capacity, float loadFactor) { //TODO: find fastest algo for log2


        capacityIndex = BinarySearch.lowerBound(PRIMES,capacity);
        maxProbeCount = Math.min(capacityIndex, 30);

        MOD = PRIMES[capacityIndex];
        capacity = MOD+maxProbeCount+10;


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

    private int getHash(K key) {
        int hash = key.hashCode();
        if (hash < 0)
            return (hash+1) % MOD + MOD - 1;
        return hash % MOD;
    }

    private V put(Entry<K, V> element) {
        int d = 0;
        int i = getHash(element.getKey());

        if (size > capacity * loadFactor) return resize(element);

        while (true) {
            if (array[i] == null) {
                array[i] = element; dist[i] = d; size++;
                maxDistance = Math.max(maxDistance, dist[i]);
                return null;
            } else if (array[i].getKey().equals(element.getKey())) {
                var res = array[i].getValue();
                array[i].setValue(element.getValue());
                return res;
            } else if (dist[i] < d) {
                maxDistance = Math.max(maxDistance, d);
                var tmpE = array[i]; array[i] = element; element = tmpE;
                var tmp = dist[i]; dist[i] = d; d = tmp;
            }

            ++d; ++i;
            if (d > maxProbeCount) return resize(element);
        }
    }

    private int getIndex(K key) {
        int i = getHash(key);
        for (int d = 0; d <= maxDistance; d++, i++) {
            if (array[i] == null) return -1;
            if (array[i].getKey().equals(key)) return i;
        }
        return -1;
    }

    @Override
    public V put(K key, V value) {
        return put(new Entry<>(key,value));
    }

    public V get(K elementKey) {
        int i = getIndex(elementKey);
        if (i == -1) return null;
        else return array[i].getValue();
    }

    public V remove(K elementKey) {
        int i = getIndex(elementKey);
        if (i == -1) return null;

        for (int j = i+1; ; i = j, j++) {

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
        for (int i=1;i<= capacity; i++) {
            dist[i] = 0;
            array[i] = null;
        }
    }

    private V resize(Entry<K,V> element) {

        capacityIndex++;
        if (capacityIndex== PRIMES.length) throw new UnsupportedOperationException();

        maxProbeCount = Math.max(capacityIndex, 30);
        MOD = PRIMES[capacityIndex];
        capacity = MOD + maxProbeCount + 10;
        maxDistance = 0; size = 0;


        var oldData = this.array;

        array = new Entry[capacity];
        dist = new int[capacity];

        for (var i : oldData) {
            if (i != null) {
                put(i);
            }
        }

        return put(element);
    }


}
