package ds;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CircularLinkedList<T> implements IndexableList<T> {

    private static class Node<T> {
        T data; Node<T> next;
        Node(T data) { this.data = data; }
    }

    private Node<T> head, tail;
    private int size = 0;

    @Override
    public void addLast(T value) {
        Node<T> n = new Node<>(value);
        if (head == null) {
            head = tail = n;
            tail.next = head;
        } else {
            tail.next = n;
            tail = n;
            tail.next = head;
        }
        size++;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node<T> cur = head;
        for (int i = 0; i < index; i++) cur = cur.next;
        return cur.data;
    }

    @Override
    public int size() { return size; }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            Node<T> cur = head;
            int seen = 0;

            @Override public boolean hasNext() { return seen < size; }

            @Override public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                T v = cur.data;
                cur = cur.next;
                seen++;
                return v;
            }
        };
    }
}
