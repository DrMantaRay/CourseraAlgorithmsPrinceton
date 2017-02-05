import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by pchen on 2/4/2017.
 */

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int capacity;
    private int size;
    private int tail;
    private Item[] s;
    public RandomizedQueue() {
        capacity = 1;
        size = 0;
        tail = 0;
        s = (Item[]) new Object[capacity];
    }
    public boolean isEmpty()    {
        return size == 0;
    }
    public int size() {
        return size;
    }
    public void enqueue(Item item)  {
        if (item == null){
            throw new NullPointerException();
        }
        else if (size == capacity) {
            Item[] oldArray = s;
            capacity*=2;
            s = (Item[]) new Object[capacity];
            for (int i = 0; i < size; i++) {
                s[i]=oldArray[i];
            }
            if (isEmpty()) { s[tail] = item; }
            else {s[++tail] = item; }
            size++;
        }
        else {
            if (isEmpty()) { s[tail] = item; }
            else {s[++tail] = item; }
            size++;
        }
    }
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        else if (size < capacity/4) {
            int randVal = StdRandom.uniform(size);
            Item[] oldArray = s;
            capacity/=2;
            s = (Item[]) new Object[capacity];
            for (int i = 0; i<size; i++) {
                s[i]=oldArray[i];
            }
            size--;
            Item returnVal = s[randVal];
            if (isEmpty()) {s[tail] = null;}
            else {
                s[randVal] = s[tail];
                s[tail--] = null;
            }
            return returnVal;
        }
        else {
            int randVal = StdRandom.uniform(size);
            Item returnVal = s[randVal];
            size--;
            if (isEmpty()) {s[tail] = null;}
            else {
                s[randVal] = s[tail];
                s[tail--] = null;
            }
            return returnVal;
        }
    }
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        else {
            return(s[StdRandom.uniform(size)]);
        }
    }
    public Iterator<Item> iterator() {
        return new ListIterator();
    }
    private class ListIterator implements Iterator<Item> {
        Item[] b = (Item[]) new Object[size];
        int counter = 0;
        public ListIterator () {
            for (int i = 0; i < size; i++) {
                b[i] = s[i];
            }
            StdRandom.shuffle(b);
        }
        public boolean hasNext() {return counter != size;}
        public void remove() { throw new UnsupportedOperationException(); }
        public Item next() {
            if (hasNext()) {
                return b[counter++];
            }
            else {
                throw new NoSuchElementException();
            }
        }
    }
    public static void main(String[] args) {
        RandomizedQueue<Integer> rQ = new RandomizedQueue<>();
        for (int i = 0; i < 10; i++) {
            rQ.enqueue(i);
        }
        for (Integer i: rQ) {
            System.out.println(i);
        }
        System.out.println(rQ.sample());
        System.out.println(rQ.sample());
        System.out.println(rQ.sample());
        System.out.println(rQ.sample());
        System.out.println(rQ.sample());
        System.out.println(rQ.dequeue());
        System.out.println(rQ.size());
    }
}