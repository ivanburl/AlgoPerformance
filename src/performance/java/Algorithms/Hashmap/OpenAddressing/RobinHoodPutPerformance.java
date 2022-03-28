package Algorithms.Hashmap.OpenAddressing;

import Algorithms.Hashmap.Entry;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.*;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 10)
public class RobinHoodPutPerformance {


    @Param({"350", "500", "1000", "10000", "100000", "1000000","10000000"})
    public static int size;

    @Param({"true", "false"})
    public static boolean capacityEnabled;

    private List<Entry<Integer,Integer>> list;

    @Setup
    public void setup() {
        list = createData();
    }

    @Benchmark
    public void BuiltInHashmap(Blackhole bh) {
        HashMap<Integer,Integer> hashmap;
        if (capacityEnabled)  hashmap = new HashMap<>(size); else
            hashmap = new HashMap<>();

        for (var i: list) {
            bh.consume(hashmap.put(i.getKey(),i.getValue()));
        }
    }

    @Benchmark
    public void RobinHoodHashmap(Blackhole bh) {
        RobinHood<Integer,Integer> robinhood;

        if (capacityEnabled) robinhood = new RobinHood<>(size); else
            robinhood = new RobinHood<>();

        for (var i: list) {
            bh.consume(robinhood.put(i.getKey(), i.getValue()));
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(RobinHoodPutPerformance.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    public List<Entry<Integer,Integer>> createData() {
        var list = new ArrayList<Entry<Integer,Integer>>();
        var rnd = new Random();
        for (int i=1;i<=size;i++) {
            list.add(new Entry<>(rnd.nextInt(),rnd.nextInt()));
        }
        return list;
    }
}

/*
Algorithms.Performance.HashmapPerformance.BuiltInHashmap                     350  avgt   10     0.011 ±    0.001  ms/op
Algorithms.Performance.HashmapPerformance.BuiltInHashmap                     500  avgt   10     0.017 ±    0.001  ms/op
Algorithms.Performance.HashmapPerformance.BuiltInHashmap                    1000  avgt   10     0.044 ±    0.007  ms/op
Algorithms.Performance.HashmapPerformance.BuiltInHashmap                   10000  avgt   10     0.682 ±    0.084  ms/op
Algorithms.Performance.HashmapPerformance.BuiltInHashmap                  100000  avgt   10    29.061 ±    0.520  ms/op
Algorithms.Performance.HashmapPerformance.BuiltInHashmap                 1000000  avgt   10   332.960 ±   14.815  ms/op
Algorithms.Performance.HashmapPerformance.BuiltInHashmap                10000000  avgt   10  5506.824 ± 1530.312  ms/op
Algorithms.Performance.HashmapPerformance.BuiltInHashmapWithCapacity         350  avgt   10     0.011 ±    0.002  ms/op
Algorithms.Performance.HashmapPerformance.BuiltInHashmapWithCapacity         500  avgt   10     0.019 ±    0.004  ms/op
Algorithms.Performance.HashmapPerformance.BuiltInHashmapWithCapacity        1000  avgt   10     0.034 ±    0.003  ms/op
Algorithms.Performance.HashmapPerformance.BuiltInHashmapWithCapacity       10000  avgt   10     0.334 ±    0.028  ms/op
Algorithms.Performance.HashmapPerformance.BuiltInHashmapWithCapacity      100000  avgt   10    25.628 ±    3.419  ms/op
Algorithms.Performance.HashmapPerformance.BuiltInHashmapWithCapacity     1000000  avgt   10   208.180 ±   13.741  ms/op
Algorithms.Performance.HashmapPerformance.BuiltInHashmapWithCapacity    10000000  avgt   10  2826.527 ±  271.828  ms/op
Algorithms.Performance.HashmapPerformance.RobinHoodHashmap                   350  avgt   10     0.011 ±    0.001  ms/op
Algorithms.Performance.HashmapPerformance.RobinHoodHashmap                   500  avgt   10     0.022 ±    0.001  ms/op
Algorithms.Performance.HashmapPerformance.RobinHoodHashmap                  1000  avgt   10     0.084 ±    0.002  ms/op
Algorithms.Performance.HashmapPerformance.RobinHoodHashmap                 10000  avgt   10     1.245 ±    0.140  ms/op
Algorithms.Performance.HashmapPerformance.RobinHoodHashmap                100000  avgt   10   118.604 ±   53.032  ms/op
Algorithms.Performance.HashmapPerformance.RobinHoodHashmap               1000000  avgt   10  1215.634 ±  836.065  ms/op
Algorithms.Performance.HashmapPerformance.RobinHoodHashmap              10000000  avgt   10  9141.876 ±  715.627  ms/op
Algorithms.Performance.HashmapPerformance.RobinHoodHashmapWithCapacity       350  avgt   10     0.011 ±    0.001  ms/op
Algorithms.Performance.HashmapPerformance.RobinHoodHashmapWithCapacity       500  avgt   10     0.022 ±    0.001  ms/op
Algorithms.Performance.HashmapPerformance.RobinHoodHashmapWithCapacity      1000  avgt   10     0.060 ±    0.008  ms/op
Algorithms.Performance.HashmapPerformance.RobinHoodHashmapWithCapacity     10000  avgt   10     0.528 ±    0.054  ms/op
Algorithms.Performance.HashmapPerformance.RobinHoodHashmapWithCapacity    100000  avgt   10    35.081 ±    1.106  ms/op
Algorithms.Performance.HashmapPerformance.RobinHoodHashmapWithCapacity   1000000  avgt   10   470.585 ±    9.760  ms/op
Algorithms.Performance.HashmapPerformance.RobinHoodHashmapWithCapacity  10000000  avgt   10  3439.937 ±   86.532  ms/op

Process finished with exit code 0
 */
