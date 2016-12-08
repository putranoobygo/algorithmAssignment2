import java.nio.Buffer;
import java.util.Random;

/**
 * Created by Alvin on 12/2/2016.
 */
public class RSHeap {


    private int[] Heap;
    private int size;
    private int maxsize;

    private static final int FRONT = 1;

    public static void main(String args[]) {

        System.out.println("The Min Heap is ");
        RSHeap minHeap = new RSHeap(15);
        minHeap.insert(9);
        minHeap.insert(5);
        minHeap.insert(3);
        minHeap.insert(7);
        minHeap.insert(6);
        minHeap.insert(2);
        minHeap.insert(4);
        minHeap.insert(8);
        minHeap.insert(1);
        minHeap.minHeap();

        minHeap.print();
        System.out.println("The Min val is " + minHeap.remove());

    }

    public RSHeap(int maxsize)
    {
        this.maxsize = maxsize;
        this.size = 0;
        Heap = new int[this.maxsize + 1];
        Heap[0] = Integer.MIN_VALUE;
    }
    private int parent(int pos)
    {
        return pos / 2;
    }

    private int leftChild(int pos)
    {
        return (2 * pos);
    }

    private int rightChild(int pos)
    {
        return (2 * pos) + 1;
    }

    public void print()
    {
        for (int i = 1; i <= size / 2; i++ )
        {
            System.out.print(" PARENT : " + Heap[i] + " LEFT CHILD : " + Heap[2*i]
                    + " RIGHT CHILD :" + Heap[2 * i  + 1]);
            System.out.println();
        }
    }

    private boolean isLeaf(int pos)
    {
        if (pos >=  (size / 2)  &&  pos <= size)
        {
            return true;
        }
        return false;
    }

    private void swap(int fpos, int spos)
    {
        int tmp;
        tmp = Heap[fpos];
        Heap[fpos] = Heap[spos];
        Heap[spos] = tmp;
    }

    private void minHeapify(int pos)
    {
        if (!isLeaf(pos))
        {
            if ( Heap[pos] > Heap[leftChild(pos)]  || Heap[pos] > Heap[rightChild(pos)])
            {
                if (Heap[leftChild(pos)] < Heap[rightChild(pos)])
                {
                    swap(pos, leftChild(pos));
                    minHeapify(leftChild(pos));
                }else
                {
                    swap(pos, rightChild(pos));
                    minHeapify(rightChild(pos));
                }
            }
        }
    }

    public void insert(int element)
    {
        Heap[++size] = element;
        int current = size;

        while (Heap[current] < Heap[parent(current)])
        {
            swap(current,parent(current));
            current = parent(current);
        }
    }

    void insertionSort(int[] ar) {
        for (int i = 1; i < ar.length; i++) {
            int index = ar[i];
            int j = i;
            while (j > 0 && ar[j - 1] > index) {
                ar[j] = ar[j - 1];
                j--;
            }
            ar[j] = index;
        }
    }

    public void minHeap()
    {
        for (int pos = (size / 2); pos >= 1 ; pos--)
        {
            minHeapify(pos);
        }
    }

    public int remove()
    {
        int popped = Heap[FRONT];
        Heap[FRONT] = Heap[size--];
        minHeapify(FRONT);
        return popped;
    }

    int getRunCount(int[] list) {
        // assuming runs go from right to left
        int runCount = 0; // keep track of number of runs
        for (int i = (list.length - 1); i > 1; i--) {
            if (list[i] > list[i - 1]) { // if we reach the end of a run
                runCount++; // increase amount of runs
            }
        }
        return runCount;
    }

    int getAverageRunLength(int[] list, int runCount) {
        return list.length / runCount; // providing list.length and runLengths are > 0
    }

}
