package Algorithms.Performance;

import Algorithms.Hashmap.Entry;
import Algorithms.Hashmap.OpenAddressing.RobinHood;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class HashmapPerformance {

    @State(Scope.Benchmark)
    public static class Data {

        @Param({"100","350","500","1000"})
        int size;
        Entry<Integer,Integer>[] list;


        @Setup
        public void setup() {
            var rnd = new Random();
            list = new Entry[size];
            for (int i=0;i<size;i++) list[i] = new Entry<Integer,Integer>(rnd.nextInt(),rnd.nextInt());
        }
    }

    @Benchmark
    public HashMap<Integer,Integer> BuiltInHashmap(Data d) {
        var rnd = new Random();
        var hashmap = new HashMap<Integer,Integer>();

        for (var i: d.list) {
            hashmap.put(i.getKey(),i.getValue());
        }
        return hashmap;
    }

    @Benchmark
    public RobinHood<Integer,Integer> RobinHoodHashmap(Data d) {
        var hashmap = new RobinHood<Integer,Integer>();

        for (var i:d.list) {
            hashmap.put(i.getKey(),i.getValue());
        }
        return hashmap;
    }


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(HashmapPerformance.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
