import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;
    private int n = 0;

    public RandomizedQueue() {
        q = (Item[]) new Object[1];
    }                // construct an empty randomized queue

    public boolean isEmpty() {
        return n == 0;
    }                // is the queue empty?

    public int size() {
        return n;
    }                       // return the number of items on the queue

    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException();
        if (n == q.length)
            resize(2* q.length);
        q[n++] = item;
    }          // add the item

    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();
        int index = StdRandom.uniform(n);
        Item tmp = q[index];
        q[index] = q[--n];
        q[n] = null;
        if (n > 0 && n == q.length/4) resize(q.length/2);
        return tmp;
    }                   // remove and return a random item

    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();
        return q[StdRandom.uniform(n)];
    }                     // return (but do not remove) a random item

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }        // return an independent iterator over items in random order

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        System.arraycopy(q, 0, copy, 0, n);
        q = copy;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int i = 0;
        private Item[] copy;

        private RandomizedQueueIterator() {
            copy = (Item[]) new Object[n];
            System.arraycopy(q, 0, copy, 0, n);
            StdRandom.shuffle(copy);
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return copy[i++];
        }

        public boolean hasNext() {
            return copy[i] != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}