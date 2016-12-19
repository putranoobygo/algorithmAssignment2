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
    private PrintWriter writer = new PrintWriter(new File("output.txt"));

    public RSHeap(int[] values, int heapSize) throws FileNotFoundException {
        this.values = values;
        this.heapSize = heapSize;
        heap = new MinHeap(heapSize);
        sort(values);
    }

    public RSHeap(int heapSize) throws FileNotFoundException {
        this.heapSize = heapSize;
        heap = new MinHeap(heapSize);
    }

    public void sort(int[] values){

    }

}
