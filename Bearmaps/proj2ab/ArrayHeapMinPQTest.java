package bearmaps;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {

    @Test
    public void baseCase() {
        ArrayHeapMinPQ<String> stringPQ = new ArrayHeapMinPQ<>();
        ArrayHeapMinPQ<Integer> integerPQ = new ArrayHeapMinPQ<>();
        ArrayHeapMinPQ<Double> doublePQ = new ArrayHeapMinPQ<>();
        assertEquals(0, stringPQ.size());
        assertEquals(0, integerPQ.size());
        assertEquals(0, doublePQ.size());
    }

    @Test
    public void basicAdd() {
        ArrayHeapMinPQ<Integer> integerPQ = new ArrayHeapMinPQ<>();
        integerPQ.add(123, 1.2);
        integerPQ.add(23, 2.3);
        integerPQ.add(2, 5.3);
        integerPQ.add(3, 3.3);
        assertEquals(integerPQ.size(), 4);
    }

    @Test
    public void testContain() {
        ArrayHeapMinPQ<String> arrayHeapMinPQ = new ArrayHeapMinPQ<>();
        arrayHeapMinPQ.add("ddd", 1.5);
        arrayHeapMinPQ.add("eee", 1.6);
        arrayHeapMinPQ.add("fff", 1.7);
        arrayHeapMinPQ.add("ggg", 1.8);
        assertTrue(arrayHeapMinPQ.contains("ddd"));
        arrayHeapMinPQ.removeSmallest();
        assertFalse(arrayHeapMinPQ.contains("ddd"));
    }


    @Test
    public void basicRemove() {
        ArrayHeapMinPQ<String> stringPQ = new ArrayHeapMinPQ<>();
        stringPQ.add("Hey", 1);
        stringPQ.add("now", 2);
        stringPQ.add("you're", 3);
        stringPQ.add("an", 4);
        stringPQ.add("allStar", 5);
        stringPQ.removeSmallest();
        assertEquals(stringPQ.size(), 4);
        assertEquals(stringPQ.getSmallest(), "now");
    }

    @Test
    public void removeAll() {
        ArrayHeapMinPQ<String> stringPQ = new ArrayHeapMinPQ<>();
        stringPQ.add("Hey", 1);
        stringPQ.add("now", 2);
        stringPQ.add("you're", 3);
        stringPQ.add("an", 4);
        stringPQ.add("allStar", 5);
        stringPQ.removeSmallest();
        stringPQ.removeSmallest();
        stringPQ.removeSmallest();
        stringPQ.removeSmallest();
        assertEquals(stringPQ.size(), 1);
    }


    @Test
    public void demoHeap() {
        ArrayHeapMinPQ<Character> minPQ = new ArrayHeapMinPQ<>();
        minPQ.add('s', 1.0);
        minPQ.add('h', 3.4);
        minPQ.add('r', 5.6);
        minPQ.add('e', 4.5);
        minPQ.add('c', 45);
        minPQ.add('k', 3.2);
        minPQ.contains('k');
        assertEquals(true, minPQ.contains('k'));
        assertEquals(false, minPQ.contains('l'));
        minPQ.changePriority('h', 2.0);
        minPQ.changePriority('r', 2.5);
        minPQ.changePriority('c', 5.5);
        minPQ.changePriority('k', 5.7);
        System.out.println(minPQ.getSmallest());
    }


    @Test
    public void testTime() {

        //MinHeapQP start
        long heapStart = System.currentTimeMillis();
        ArrayHeapMinPQ<Integer> minPQ = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 100000; i++) {
            minPQ.add(i, 100000 - i);
        }
        long heapEnd = System.currentTimeMillis();
        long resultHeap = heapEnd - heapStart;
        System.out.println("add in ms: " + resultHeap);

        // remove MinHeapPQ

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000 - 1; i++) {
            minPQ.removeSmallest();
        }
        long end = System.currentTimeMillis();
        long result = end - start;
        System.out.println("add in ms: " + result);

    }


}





