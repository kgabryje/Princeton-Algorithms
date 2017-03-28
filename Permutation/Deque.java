import java.util.NoSuchElementException;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private int size = 0;

    public Deque() { }                          // construct an empty deque

    public boolean isEmpty() {
        return first == null || last == null;
    }                // is the deque empty?

    public int size() {
        return size;
    }                       // return the number of items on the deque

    public void addFirst(Item item) {
        if (item == null)
            throw new NullPointerException();
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.previous = null;
        if (isEmpty())
            last = first;
        else {
            oldfirst.previous = first;
            first.next = oldfirst;
        }
        size++;
    }         // add the item to the front

    public void addLast(Item item) {
        if (item == null)
            throw new NullPointerException();
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty())
            first = last;
        else {
            oldlast.next = last;
            last.previous = oldlast;
        }
        size++;
    }          // add the item to the end

    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();
        Item item = first.item;
        first = first.next;
        if (isEmpty())
            last = null;
        size--;
        return item;
    }               // remove and return the item from the front

    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();
        Item item = last.item;
        last = last.previous;
        if (isEmpty())
            first = null;
        size--;
        return item;
    }                // remove and return the item from the end

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }        // return an iterator over items in order from front to end


    private class Node {
        private Item item;
        private Node next;
        private Node previous;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}