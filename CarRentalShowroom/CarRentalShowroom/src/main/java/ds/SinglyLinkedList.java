package ds;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SinglyLinkedList<T> implements IndexableList<T> {

    private static class Node<T> {
        T data; Node<T> next;
        Node(T data) { this.data = data; }
    }

    private Node<T> head, tail;
    private int size = 0;

    @Override
    public void addLast(T value) {
        Node<T> n = new Node<>(value);
        if (head == null) head = tail = n;
        else { tail.next = n; tail = n; }
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

            @Override
            public boolean hasNext() { return cur != null; }

            @Override
            public T next() {
                if (cur == null) throw new NoSuchElementException();
                T v = cur.data;
                cur = cur.next;
                return v;
            }
        };
    }
}
