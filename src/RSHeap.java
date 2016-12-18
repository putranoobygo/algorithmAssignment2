import java.nio.Buffer;
import java.util.Random;

public class RSHeap {
    //in this class we need to use the heap to sort the output runs
    private int heapSize;
    private MinHeap heap;
    private int[] values;

    public RSHeap(int[] values, int heapSize) {
        this.values = values;
        this.heapSize = heapSize;

        heap = new MinHeap(heapSize);
    }

    public void sort() {

    }
}
