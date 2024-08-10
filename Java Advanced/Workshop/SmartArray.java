import java.util.Arrays;
import java.util.function.Consumer;

public class SmartArray <T> {

    private static final String INVALID_INDEX_ERR_MSG = "Invalid given index!";

    private static final int INITIAL_CAPACITY = 4;

    private T[] smartArray;

    private int currentIndex;

    private int smartArrayCapacity;

    public SmartArray() {
        this.smartArray = (T[]) new Object[INITIAL_CAPACITY];
        this.smartArrayCapacity = INITIAL_CAPACITY;
    }

    public void add(T element) {
        this.smartArray[this.currentIndex] = element;
        this.currentIndex++;

        if (this.currentIndex == this.smartArrayCapacity) {
            resize();
        }
    }

    public T get(int index) {
        if (index < 0 || index >= this.currentIndex) {
            throw new IndexOutOfBoundsException(INVALID_INDEX_ERR_MSG);
        }

        return this.smartArray[index];
    }

    public T remove(int index) {
        if (index < 0 || index > this.currentIndex) {
            throw new IndexOutOfBoundsException(INVALID_INDEX_ERR_MSG);
        }
        T removedItem = this.smartArray[index];

        for (int i = index + 1; i < this.currentIndex; i++) {
            this.smartArray[i - 1] = this.smartArray[i];
        }

        this.currentIndex--;
        this.smartArray[this.currentIndex] = null;
        if (this.currentIndex * 2 < this.smartArrayCapacity) {
            shrink();
        }

        return removedItem;
    }

    public boolean contains(T element) {
        for (T t : smartArray) {
            if (t != null) {
                if (t.equals(element)) {
                    return true;
                }
            } else {
                return false;
            }
        }
      
        return false;
    }

    public void add(int index, T element) {
        if (index == this.currentIndex) {
            add(element);
            return;
        } else if (index < 0 || index > this.currentIndex) {
            throw new IndexOutOfBoundsException(INVALID_INDEX_ERR_MSG);
        }

        T keeper = this.smartArray[index];
        this.smartArray[index] = element; // setting the new element at its new position

        this.currentIndex++;
        if (this.currentIndex == this.smartArrayCapacity) {
            resize();
        }

        for (int i = this.currentIndex; i > index; i--) { // pushing every element to the right
            if (i == index + 1) {
                this.smartArray[i] = keeper;
            } else {
                this.smartArray[i] = this.smartArray[i - 1];
            }
        }
    }

    private void resize() {
        this.smartArrayCapacity *= 2;
        T[] tempArray = (T[]) new Object[this.smartArrayCapacity];

        for (int i = 0; i < this.smartArray.length; i++) {
            tempArray[i] = this.smartArray[i];
        }

        this.smartArray = tempArray;
    }

    private void shrink() {
     this.smartArrayCapacity /= 2;
        T[] tempArray = (T[]) new Object[this.smartArrayCapacity];

        for (int i = 0; i < this.smartArrayCapacity; i++) {
            tempArray[i] = this.smartArray[i];
        }
        this.smartArray = tempArray;
    }

    public void forEach(Consumer<T> consumer) {
        for (T t : smartArray) {
            if (t == null) break;
            consumer.accept(t);
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(
            Arrays.stream(this.smartArray)
                .filter(x -> x != null)
                .toArray()
        );
    }
}
