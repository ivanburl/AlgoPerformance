package Algorithms.Test;

import Algorithms.Hashmap.OpenAddressing.RobinHood;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RobinHoodTest {

    void add(int size) {
        var rand = new Random();
        var hashmap = new HashMap<Integer,Integer>();
        var test = new RobinHood<Integer,Integer>();

        for (int i=1;i<=size;i++) {
            int key = rand.nextInt();
            int value = rand.nextInt();
            test.put(key,value);
            hashmap.put(key,value);
        }

        var exp = new ArrayList<Integer>();
        var act = new ArrayList<Integer>();

        for (Integer i: hashmap.keySet()) {
            act.add(test.get(i));
            exp.add(hashmap.get(i));
        }

        assertEquals(exp,act);
    }

    @ParameterizedTest
    @ValueSource(ints = { 100, 500, 1000, 2000, 10000, 1000000, 1000000 })
    void addAllSizes(int size) {
        add(size);
    }

    @RepeatedTest(1000)
    void addRandomCheck() {
        add(100);
        add(10000);
    }

    void remove(int size) {
        var rnd = new Random();
        var test = new RobinHood<Integer,Integer>();
        var hashmap = new HashMap<Integer,Integer>();

        var list = new ArrayList<Integer>();

        for (int i= 1;i <= size; i++) {
            int key = rnd.nextInt();
            int val = rnd.nextInt();
            test.put(key,val);
            list.add(key); hashmap.put(key,val);
        }

        for (var i: list) {
            assertEquals(test.remove(i), hashmap.remove(i));
            assertEquals(test.size(), hashmap.size());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = { 100, 500, 1000, 2000, 10000, 1000000, 1000000 })
    void RemoveBigSize(int size) {
        remove(size);
    }

    @RepeatedTest(1000)
    void removeAllRandom() {
        remove(100);
        remove(10000);
    }

}