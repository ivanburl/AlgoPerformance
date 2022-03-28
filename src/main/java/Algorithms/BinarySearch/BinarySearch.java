package Algorithms.BinarySearch;

public class BinarySearch {

    public static int binarySearch(int[] mas,int element) {
        int l = 0, r = mas.length;
        while(l<r) {
            int mid = l+((r-l)>>1);
            if (mas[mid] == element) return mid;
            if (mas[mid] > element) r = mid; else l = mid+1;
        }

        return l;
    }

    public static int lowerBound(int[] mas,int element) {
        int l = 0, r = mas.length;
        while(l<r) {
            int mid = l+((r-l)>>1);
            if (mas[mid] >= element) r = mid; else l = mid + 1;
        }

        if (l<mas.length && mas[l] < element) l++;

        return l;
    }

    public static int upperBound(int[] mas,int element) {
        int l = 0, r = mas.length;

        while(l<r) {
            int mid = l+((r-l)>>1);
            if (mas[mid] > element) r = mid; else l = mid + 1;
        }

        return l;
    }

}
