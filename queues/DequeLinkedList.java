/* *****************************************************************************
 *  Name: Mate Rusz
 *  Date: 04.04.2021
 *  Description:
 **************************************************************************** */

import java.util.Iterator;

public class DequeLinkedList<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    private class Node {
        Item item;
        Node next;
    }

    // construct an empty deque
    public DequeLinkedList() {
        /*first = null;
        last = null;
        size = 0;*/
    }

    // is the deque empty?
    public boolean isEmpty() {
        //return (first == null);
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        if (isEmpty()) {
            last = first;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            first = last;
        }
        else {
            oldlast.next = last;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) throw new java.util.NoSuchElementException();
        Item item = first.item;
        first = first.next;
        if (isEmpty()) last = null;
        size--;
        return item;

    }

    // remove and return the item from the back
    public Item removeLast() {
        if (last == null) throw new java.util.NoSuchElementException();
        Item item = last.item;
        Node newLast = first;
        for (int i = 0; i < size - 2; i++) {
            newLast = newLast.next;
        }
        last = newLast;
        size--;
        if (isEmpty()) first = null;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }


    private class ListIterator implements Iterator<Item> {
        Node current = first;

        public boolean hasNext() {
            return (current != null);
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        DequeLinkedList<String> deque = new DequeLinkedList<String>();
        assert deque.isEmpty() : "deque not empty upon creation";
        deque.addFirst("first to first");
        assert !deque.isEmpty() : "deque still empty";
        deque.addLast("second added as last");
        deque.addFirst("third added as first");
        for (String item : deque) {
            System.out.println(item);
        }
        assert deque.first.item.equals(deque.removeFirst()) : "removeFirst error";
        assert deque.last.item.equals(deque.removeLast()) : "removeLast error";
        /*System.out.println(deque.removeFirst());
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeFirst());*/
        System.out.println("size " + deque.size());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println("after removing, last is ");
        System.out.println("size " + deque.size());
        for (String item : deque) {
            System.out.println(item);
        }
        assert !deque.first.item.equals(deque.last.item) : "removeFirst or removeLast error";
        // deque.removeLast();
        //deque.removeFirst();
    }
}
