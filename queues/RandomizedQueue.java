/* *****************************************************************************
 *  Name: Mate Rusz
 *  Date: 04.04.2021
 *  Description: resizing array implementation is based on code presented
 * in Algorithms, Part I by Princeton University
 * https://algs4.cs.princeton.edu/13stacks/ResizingArrayStack.java.html
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
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
        if (isEmpty()) {
            items[size++] = item;
        }
        else {
            int random = StdRandom.uniform(size);
            Item randomItem = items[random];
            items[size++] = randomItem;
            items[random] = item;
        }
    }

    private void resize(int newLength) {
        StdRandom.shuffle(items, 0, size);
        Item[] copy = (Item[]) new Object[newLength];
        for (int i = 0; i < size(); i++) {
            copy[i] = items[i];
        }
        items = copy;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        Item randomItem = items[size - 1];
        items[size - 1] = null;
        size--;
        if (size > 0 && items.length / 4 > size) resize(items.length / 2);
        return randomItem;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        int random = StdRandom.uniform(size);
        Item randomItem = items[random];
        return randomItem;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }

    private class ReverseArrayIterator implements Iterator<Item> {
        int last;

        ReverseArrayIterator() {
            last = size - 1;
        }

        public boolean hasNext() {
            return last >= 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            return items[last--];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        int n = 5;
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < n; i++)
            queue.enqueue(i);
        for (int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }
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
