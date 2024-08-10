import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class DoubleLinkedList<R> {

    private static final String INVALID_INDEX_ERR_MSG = "The LinkedList is empty!";

    class Node <T> {

        private T value;

        private Node<T> previous;

        private Node<T> next;

        public Node(T value, Node<T> previous, Node<T> next) {
            this.value = value;
            this.previous = previous;
            this.next = next;
        }
    }

    private Node<R> head;

    private Node<R> tail;

    private int size;

    public int getSize() {
        return size;
    }

    public DoubleLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void addFirst(R element) {
        if (this.size == 0) {
            Node<R> newNode = new Node<R>(element, null, null);
            this.head = newNode;
            this.tail = newNode;

        } else {
            this.head = new Node<R>(element, null, this.head);
        }

        this.size++;
    }

    public void addLast(R element) {
        if (this.size == 0) {
            Node<R> newNode = new Node<R>(element, null, null);
            this.tail = newNode;
            this.head = newNode;

        } else {
            Node<R> newNode = new Node<R>(element, this.tail, null);
            this.tail.next = newNode;
            this.tail = newNode;
        }

        size++;
    }

    public R removeFirst() {
        if (this.size == 0) {
            throw new NoSuchElementException(INVALID_INDEX_ERR_MSG);

        } else {
            R element = this.head.value;

            if (this.head.next != null) {
                this.head = this.head.next;

            } else {
                this.head = null;
            }

            this.size--;
            return element;
        }
    }

    public R removeLast() {
        if (this.size == 0) {
            throw new NoSuchElementException(INVALID_INDEX_ERR_MSG);

        } else {
            R element = this.tail.value;

            if (this.tail.previous != null) {
                this.tail = this.tail.previous;
                this.tail.next = null;

            } else {
                this.tail = null;
            }

            this.size--;
            return element;
        }
    }

    public R[] toArray() {
        if (this.size == 0) {
            throw new NoSuchElementException(INVALID_INDEX_ERR_MSG);
        }

        List<R> elements = new ArrayList<>();
        Node<R> currentNode = this.head;

        while (true) {
            elements.add(currentNode.value);

            if (currentNode.next == null) {
                break;
            }

            currentNode = currentNode.next;
        }

        return (R[]) elements.toArray();
    }

    public R get(int index) {
        if (index < 0 || index >= this.size) {
         throw new NoSuchElementException(INVALID_INDEX_ERR_MSG);
        }

        return this.toArray()[index];
    }

    public void forEach(Consumer<R> consumer) {
        for (R r : this.toArray()) {
            consumer.accept(r);
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(this.toArray());
    }
}
