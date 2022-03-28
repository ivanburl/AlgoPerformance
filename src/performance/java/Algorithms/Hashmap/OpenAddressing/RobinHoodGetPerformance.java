package Algorithms.Hashmap.OpenAddressing;

import Algorithms.Hashmap.Entry;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 15)
public class RobinHoodGetPerformance {
    @Param({"350", "500", "1000", "10000", "100000", "1000000","10000000"})
    public static int size;


    private HashMap<Integer,Integer> hashmap;
    private RobinHood<Integer,Integer> robinhood;

    private List<Integer> inserted, empty;

    @Setup
    public void setup() {
        hashmap = new HashMap<>(size);
        robinhood = new RobinHood<>(size);
        var rnd = new Random();


        inserted = new ArrayList<>();
        empty = new ArrayList<>();

        for (int i=1;i<=size;i++) {
            int key = rnd.nextInt();
            int val = rnd.nextInt();
            hashmap.put(key,val);
            robinhood.put(key,val);
            inserted.add(key);
        }

        for (int i=1;i<=size;i++) {
            int key = rnd.nextInt();
            while(!hashmap.containsKey(key)) key++;
            empty.add(key);
        }

    }

    @Benchmark
    public void BuiltInHashmapEmpty(Blackhole bh) {

        for (var i: empty) {
            bh.consume(hashmap.get(i));
        }
    }

    @Benchmark
    public void RobinHoodHashmapEmpty(Blackhole bh) {

        for (var i: empty) {
            bh.consume(robinhood.get(i));
        }
    }

    @Benchmark
    public void RobinHoodHashmapInserted(Blackhole bh) {
        for (var i: inserted) {
            bh.consume(robinhood.get(i));
        }
    }

    @Benchmark
    public void BuiltInHashmapInserted(Blackhole bh) {
        for (var i: inserted) {
            bh.consume(robinhood.get(i));
        }
    }

    @Benchmark
    public void RobinHoodHashmapCustom(Blackhole bh) {
        for (int i=0;i<=2e7;i++) {
            bh.consume(robinhood.get(i));
        }
    }

    @Benchmark
    public void BuiltInHashmapCustom(Blackhole bh) {
        for (int i=0;i<=2e7;i++) {
            bh.consume(robinhood.get(i));
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(RobinHoodGetPerformance.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}
