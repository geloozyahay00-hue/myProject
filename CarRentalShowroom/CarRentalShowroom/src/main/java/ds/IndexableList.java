package ds;

public interface IndexableList<T> extends Iterable<T> {
    void addLast(T value);
    T get(int index);
    int size();
}
