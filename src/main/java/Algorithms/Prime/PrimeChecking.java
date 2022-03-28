package Algorithms.Prime;

import Algorithms.Hashmap.OpenAddressing.RobinHood;

import java.awt.desktop.SystemEventListener;
import java.util.ArrayList;

public class PrimeChecking {

    static boolean isPrime(int a) {
        for (int i=2;i*i<=a;i++) {
            if (a%i==0) return false;
        }
        return true;
    }

    static int upperPrime(int a) {
        while(a!=Integer.MAX_VALUE && !isPrime(++a));
        return a;
    }

    static ArrayList<Integer> RobinHoodPrimes() {

        System.out.print("{");

        var res = new ArrayList<Integer>();
        for (int i=0;i<=30;i++) {
            res.add(upperPrime(1<<i));
            System.out.print((1<<i)+", ");
        }

        int num = 1<<30;
        for (int i=29;i>=5;i--) {
            num+=1<<i;
            res.add(upperPrime(num));
            System.out.print((num)+",");
        }

        System.out.println("}");
        return res;
    }

    public static void main(String[] args) {
        var mas = RobinHoodPrimes();
        System.out.print("{");
        for (int i=0;i<mas.size();i++) {
            System.out.print(mas.get(i)+",");
        }
        System.out.println("}");
    }

}
