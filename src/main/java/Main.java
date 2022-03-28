import Algorithms.BinarySearch.BinarySearch;
import Algorithms.Hashmap.Entry;
import Algorithms.Hashmap.OpenAddressing.RobinHood;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        int[] a = new int[] {1,2,3,3,4,5,5,6,7,8,9,10,20,30,30,40,50};
        Arrays.sort(a);

        int elem = 60;
        System.out.println(a[BinarySearch.upperBound(a,elem)]+" "+BinarySearch.upperBound(a,elem));
        System.out.println(a[BinarySearch.lowerBound(a,elem)]+" "+BinarySearch.lowerBound(a,elem));
    }
}
