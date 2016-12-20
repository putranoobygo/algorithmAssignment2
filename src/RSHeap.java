import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.Buffer;
import java.util.Random;

public class RSHeap {

    //in this class we need to use the heap to sort the output runs
    final private int heapSize;
    final private MinHeap heap;
    private int[] values;

    public RSHeap(int[] values, int heapSize){
        this.values = values;
        this.heapSize = heapSize;
        heap = new MinHeap(heapSize);
        heap.process(values);
    }

}
