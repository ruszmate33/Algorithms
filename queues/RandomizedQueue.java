/* *****************************************************************************
 *  Name: Mate Rusz
 *  Date: 04.04.2021
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        // items = new Item[1];
        items = (Item[]) new Object[1];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return (size == 0);
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (items.length == size) resize(2 * items.length);
        items[size++] = item;
    }

    private void resize(int newLength) {
        // Item[] copy = new Item[newLength];
        Item[] copy = (Item[]) new Object[newLength];
        for (int i = 0; i < size(); i++) {
            copy[i] = items[i];
        }
        items = copy;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int random = StdRandom.uniform(items.length);
        Item randomItem = items[random];
        while (size > 0 && randomItem == null) {
            random = StdRandom.uniform(items.length);
            randomItem = items[random];
        }
        items[random] = null;
        size--;
        return randomItem;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int random = StdRandom.uniform(items.length);
        Item randomItem = items[random];
        while (size > 0 && randomItem == null) {
            random = StdRandom.uniform(items.length);
            randomItem = items[random];
        }
        return randomItem;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new UniformRandomArrayIterator();
    }

    private class UniformRandomArrayIterator implements Iterator<Item> {

        public boolean hasNext() {
            return !isEmpty();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            return dequeue();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        rq.enqueue("fucking");
        rq.enqueue("hell");
        rq.enqueue("omg");

        for (int i = 1; i <= 10; i++) {
            System.out.println("sample " + i + ": " + rq.sample());
        }
        System.out.println("end of iteration");
        System.out.println("size: " + rq.size());
        for (String item : rq) {
            System.out.println(item);
        }
        System.out.println("size: " + rq.size());
        System.out.println("empty: " + rq.isEmpty());
        System.out.println(rq.dequeue());
    }
}
