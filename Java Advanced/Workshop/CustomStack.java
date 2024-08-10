import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class CustomStack <T> {

    private static final String EMPTY_STACK_ERR_MSG = "The Stack is empty!";

    private static final int INITIAL_CAPACITY = 4;

    private T[] items;

    private int currentIndex;

    private int customStackCapacity;

    public CustomStack() {
        this.items = (T[]) new Object[INITIAL_CAPACITY];
        this.customStackCapacity = INITIAL_CAPACITY;
    }

    public void push(T element) {
        this.items[this.currentIndex] = element;
        this.currentIndex++;

        if (this.currentIndex == this.customStackCapacity) {
            resize();
        }
    }

    public T peek() {
        if (Arrays.stream(this.items).filter(x -> x != null).count() == 0) {
            throw new NoSuchElementException(EMPTY_STACK_ERR_MSG);
        }

        return this.items[this.currentIndex - 1];
    }

    public T pop() {
        if (Arrays.stream(this.items).filter(x -> x != null).count() == 0) {
            throw new NoSuchElementException(EMPTY_STACK_ERR_MSG);
        }

        this.currentIndex--;
        T removedItem = this.items[this.currentIndex];
        this.items[this.currentIndex] = null;

        if (this.currentIndex * 2 < this.customStackCapacity) {
            shrink();
        }

        return removedItem;
    }

    private void resize() {
        this.customStackCapacity *= 2;
        T[] tempArray = (T[]) new Object[this.customStackCapacity];

        for (int i = 0; i < this.items.length; i++) {
            tempArray[i] = this.items[i];
        }

        this.items = tempArray;
    }

    private void shrink() {
     this.customStackCapacity /= 2;
        T[] tempArray = (T[]) new Object[this.customStackCapacity];

        for (int i = 0; i < this.customStackCapacity; i++) {
            tempArray[i] = this.items[i];
        }
        this.items = tempArray;
    }

    public void forEach(Consumer<T> consumer) {
        for (T t : items) {
            if (t == null) break;
            consumer.accept(t);
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(
            Arrays.stream(this.items)
                .filter(x -> x != null)
                .toArray()
        );
    }
}
