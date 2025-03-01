// Online Java Compiler
// Use this editor to write, compile and run your Java code online

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.lang.IllegalArgumentException;
class Solution {
    // Time O(N), space O(N)
    public static List<Integer> mergedSortedArray(List<Integer> arr1, List<Integer> arr2) {
        List<Integer> list = new ArrayList<>();
        int idx1 = 0, idx2 = 0;
        while(idx1 != arr1.size() && idx2 != arr2.size()) {
            if(arr1.get(idx1) < arr2.get(idx2)) {
                list.add(arr1.get(idx1));
                idx1++;
            } else {
                list.add(arr2.get(idx2));
                idx2++;
            }
        }
        
        while(idx1 != arr1.size()) {
            list.add(arr1.get(idx1));
            idx1++;
        }
        
        while(idx2 != arr2.size()) {
            list.add(arr2.get(idx2));
            idx2++;
        }
        
        return list;
    }
    
    public static Map<Integer, Long> cachedValue = new ConcurrentHashMap<>();
    public static Map<Integer, Object> keys = new ConcurrentHashMap<>();
    private static synchronized Object getKey(int n) {
        keys.putIfAbsent(n, new Object());
        return keys.get(n);
    }
    
    public static long fib(int n) throws Exception {
        if(n < 0) throw new IllegalArgumentException("Invalid Input");
        if (n < 2) return n;
        if(cachedValue.containsKey(n)) { 
            return cachedValue.get(n);
        }
    
        synchronized (getKey(n)) {
            if(cachedValue.containsKey(n)) {
                return cachedValue.get(n);
            }
            
            long value = fib(n - 1) + fib(n - 2);
            cachedValue.put(n, value);
            return value;
        }
    }

    public static long[] fibArr(int n) throws Exception {
        if(n < 0) throw new IllegalArgumentException("Invalid Input");
        if(n == 0) return new long[]{-1, 0};
        if(n == 1) return new long[]{0, 1};
        // Slot in limited memory caching strat, more efficient use and simpler synchronzied when calculating anchor values pair, since the memory limit for caching is not clearly stated, assum that we have all memory we need so the fib function should be prioritized
        long[] fibPrev = fibArr(n - 1);
        long value = fibPrev[0] + fibPrev[1];
        fibPrev[0] = fibPrev[1];
        fibPrev[1] = value;
        
        return fibPrev; // one object for one calculation, reuse to reduce the GC
    }

    public static void main(String[] args) {
        // List<Integer> arr1 = Arrays.asList(1, 3, 5), arr2 = Arrays.asList(2, 4, 5);
        // System.out.println(mergedSortedArray(arr1, arr2));
        try {
            for(int i = 10; i >= 0; i --) {
                System.out.println(fibArr(i)[1]); // release the memory after use if in C/C++
                System.out.println(fib(i));
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
       
        
    }
}