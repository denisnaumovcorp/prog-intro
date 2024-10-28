import java.util.Arrays;

public class IntList {
     int[] intList;
     int size;
     int memorySize;

    public IntList() {
        intList = new int[0];
        size = 0;
        memorySize = 0;
    }

    public IntList(int[] arr, int aSize) {
        intList = Arrays.copyOf(arr, aSize);
        size = aSize;
        memorySize = aSize;
    }

    public void add(int number) {
        if (memorySize == 0) {
            intList = Arrays.copyOf(new int[1], 1);
            memorySize = 1;
        } else if (memorySize == size) {
            memorySize *= 2;
            intList = Arrays.copyOf(intList, memorySize);
        }
        intList[size] = number;
        size++;
    }

    public int get(int index) throws ArrayIndexOutOfBoundsException {
        if (index < size && index >= 0) {
            return intList[index];
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public int getFirst() throws ArrayIndexOutOfBoundsException {
        return get(0);
    }

    public void set(int index, int value) throws ArrayIndexOutOfBoundsException {
        if (index < size && index >= 0) {
           intList[index] = value;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public int size() {
        return size;
    }
}