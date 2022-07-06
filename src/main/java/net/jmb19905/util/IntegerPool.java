package net.jmb19905.util;

import java.util.ArrayList;
import java.util.List;

public class IntegerPool {

    private final List<Integer> pool;
    private final List<Integer> unused;

    private int largest;

    public IntegerPool(int initialSize) {
        this.pool = new ArrayList<>();
        this.unused = new ArrayList<>();
        for(int i=0;i<initialSize;i++) this.unused.add(i);
        this.largest = initialSize - 1;
    }

    public int takeInt() {
        int out;
        if(unused.isEmpty()) {
            out = largest + 1;
            pool.add(out);
            largest = out;
        }else {
            out = unused.get(0);
            unused.remove(0);
            pool.add(out);
            if(out > largest) largest = out;
        }
        return out;
    }

    public void returnInt(int i) {
        if(pool.contains(i)) {
            pool.remove(i);
            unused.add(i);
            if(i == largest) {
                int newLargest = 0;
                for(Integer in : pool) if(in > newLargest) newLargest = in;
                largest = newLargest;
            }
        }
    }

}
