package bearmaps;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.HashMap;


public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private ArrayList<Node> heapPQ;
    private HashMap<T, Integer> hashMap;

    public ArrayHeapMinPQ() {
        heapPQ = new ArrayList<>();
        hashMap = new HashMap<>();
    }

    private class Node {
        private T item;
        private double priority;

        private Node(T i, double p) {
            item = i;
            priority = p;
        }

        public double getPriority() {
            return priority;
        }

        private void setPriority(Double p) {
            priority = p;
        }

        private T getItem() {
            return item;
        }

    }


    @Override
    public void add(T item, double priority) {
        if (hashMap.containsKey(item)) {
            throw new IllegalArgumentException(item + " is duplicate");
        }
        Node t = new Node(item, priority);
        heapPQ.add(t);
        hashMap.put(item, size() - 1);
        swimUp(size() - 1);
    }

    // recursive function that will compare
    public void swimUp(int child) {
        int parent = parent(child);
        if (heapPQ.get(child).getPriority() < heapPQ.get(parent).getPriority()) {
            swap(child, parent);
            swimUp(parent);
        }
        return;
    }


    public void swap(int item1, int item2) {
        Node temp = heapPQ.get(item1);
        heapPQ.set(item1, heapPQ.get(item2));
        heapPQ.set(item2, temp);
        hashMap.put(heapPQ.get(item1).getItem(), item1);
        hashMap.put(heapPQ.get(item2).getItem(), item2);
    }

    public void sink(int parent) {
        if (heapPQ.isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }

        int minChild = minChild(parent);
        if (heapPQ.get(parent).getPriority() > heapPQ.get(minChild).getPriority()) {
            swap(parent, minChild);
            sink(minChild);
        }
        return;
    }


    private int minChild(int parent) {
        int left = leftChild(parent);
        int right = rightChild(parent);

        //both idx are bigger than the arr
        if (left > size() - 1 && right > size() - 1) {
            return parent;
        }
        //one of the idx are bigger than the arr
        if (left <= heapPQ.size() - 1 && right > heapPQ.size() - 1) {
            return left;
        } else if ((right <= heapPQ.size() - 1 && left > heapPQ.size() - 1)) {
            return right;
        }
        //both are less than the arr
        if (heapPQ.get(left).getPriority() < heapPQ.get(right).getPriority()) {
            return left;
        }
        return right;
    }


    private static int parent(int i) {
        if (i == 0) {
            return 0;
        }
        return (i - 1) / 2;
    }

    private static int leftChild(int i) {
        return 2 * i + 1;
    }

    private static int rightChild(int i) {
        return 2 * i + 2;
    }


    @Override
    public boolean contains(T item) {
        return hashMap.containsKey(item);
    }

    @Override
    public T getSmallest() {
        if (heapPQ.isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        return (T) heapPQ.get(0).item;
    }

    @Override
    public T removeSmallest() {
        if (heapPQ.isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        //grab the smallest for return
        T smallest = getSmallest();
        //swap bottom with top
        swap(0, size() - 1);
        //remove the swapped
        heapPQ.remove(size() - 1);
        //alter the hash map
        hashMap.remove(smallest);
        //allow the new root to get comfy
        sink(0);
        return smallest;
    }

    @Override
    public int size() {
        return heapPQ.size();
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!hashMap.containsKey(item)) {
            throw new NoSuchElementException(item + " is not in here!");
        }
        //get old
        int idx = hashMap.get(item);
        double temp = heapPQ.get(idx).getPriority();
        //assign new priority
        heapPQ.get(idx).setPriority(priority);
        //allow for heap to fix itself
        if (temp > priority) {
            swimUp(idx);
        } else {
            sink(idx);
        }

    }

}
