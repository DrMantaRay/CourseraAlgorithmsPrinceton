

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by pchen on 2/4/2017.
 */
public class Deque<Item> implements Iterable <Item> {
    private int size;
    private class Node {
        private Node next = null;
        private Node previous = null;
        private Item value = null;
    }
    private Node first;
    private Node last;
    public Deque() {
        size=0;
        first = new Node();
        last = first;
    }

    public boolean isEmpty() {
        return size==0;
    }
    public int size() {
        return size;
    }
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (size==0) {
            first.value=item;
            last=first;
        }
        else {
            Node newfirst=new Node();
            newfirst.previous=first;
            newfirst.value=item;
            first.next=newfirst;
            first=newfirst;

        }
        size++;
    };
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (size==0) {
            last.value=item;
            first=last;
        }
        else {
            Node newlast=new Node();
            newlast.next=last;
            newlast.value=item;
            last.previous=newlast;
            last=newlast;
        }
        size++;
    };
    public Item removeFirst() {
        if (size>1) {
            Node oldfirst = first;
            first.previous.next = null;
            first = oldfirst.previous;
            oldfirst.previous = null;
            size--;
            return oldfirst.value;
        }
        else if (size==1) {
            Item printval=first.value;
            first.next=null;
            first.previous=null;
            first.value=null;
            last=first;
            size--;
            return printval;
        }
        else {
            throw new NoSuchElementException();
        }
    }
    public Item removeLast() {
        if (size>1) {
            Node oldlast = last;
            last.next.previous = null;
            last = oldlast.next;
            oldlast.next = null;
            size--;
            return oldlast.value;
        }
        else if (size==1) {
            Item printval=last.value;
            last.next=null;
            last.previous=null;
            last.value=null;
            first=last;
            size--;
            return printval;
        }
        else {
            throw new NoSuchElementException();
        }
    };
    public Iterator<Item> iterator() {
        return new ListIterator();
    }
    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        private int localSize=size;
        public boolean hasNext() {return localSize!=0;}
        public void remove() { throw new UnsupportedOperationException(); }
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            else if (localSize>1){
                localSize--;
                Item value = current.value;
                current = current.previous;
                return value;
            }
            else {
                localSize--;
                Item returnvalue=current.value;
                return returnvalue;
            }
        }
    }
    public static void main(String[] args) {
        Deque<Integer> deque=new Deque<> ();
        deque.addFirst(2);
        deque.addLast(3);
        deque.addLast(4);
        deque.addFirst(1);
        deque.addFirst(0);
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        deque.addFirst(2);
        deque.addLast(3);
        deque.addLast(4);
        deque.addFirst(1);
        deque.addFirst(0);
        for (int i : deque) {
            System.out.println(i);
        }
        System.out.println(deque.removeFirst());
        System.out.println(deque.size());
        System.out.println(deque.removeLast());
    }
}
