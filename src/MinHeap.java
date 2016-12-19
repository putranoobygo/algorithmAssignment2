import java.util.ArrayList;
import java.util.Arrays;

public class MinHeap {

    private int[] heap;
    private int lastPos;
    private int maxSize;
    private int deadSpace;
    private ArrayList<Integer> runLengths;

    private static final int FRONT = 1;
    private int runLength;

    /**
     * Constructor initializes heap with maxSize, sets lastPos to
     *
     * @param maxSize maximum size of the heap
     */
    public MinHeap(int maxSize) {
        this.maxSize = maxSize;
        lastPos = 0;
        deadSpace = 0;
        runLength = 0;
        runLengths = new ArrayList<>();
        heap = new int[this.maxSize + 1];
        heap[0] = Integer.MIN_VALUE;
    }

    private int parent(int pos) {
        return pos / 2;
    }

    private int leftChild(int pos) {
        return (2 * pos);
    }

    private int rightChild(int pos) {
        return (2 * pos) + 1;
    }

    /**
     * Prints the parents and children
     */
    public void print() {
//        for (int i = 1; i <= lastPos / 2; i++) {
//
//            int parent = heap[i];
//            int left = heap[2 * i];
//            int right = heap[2 * i + 1];
//
//            System.out.print(" PARENT : " + parent + " LEFT CHILD : " + left
//                    + " RIGHT CHILD :" + right);
//            System.out.println();
//        }
        System.out.println(Arrays.toString(heap));
        System.out.println("Dead space: " + deadSpace);
        System.out.println("Last position: " + lastPos);
        switch (maxSize - (lastPos + deadSpace)){
            case 0:
                System.out.println("Empty: 0");
                break;
            default:
                System.out.println("Empty: " + (maxSize - (lastPos + deadSpace)) + " from [" + (lastPos + 1) + "]");
                break;
        }
        if (!spaceAvailable()) {
            System.out.println("heap is full");
        }
        System.out.println();
    }

    /**
     * Checks the heap at a specific position and determines whether it is a leaf or not
     *
     * @param pos the position in the heap which should be checked
     * @return boolean pertaining to whether it is a leaf
     */
    private boolean isLeaf(int pos) {
        if (pos >= (lastPos / 2) && pos <= lastPos) {
            return true;
        }
        return false;
    }

    /**
     * Swaps two nodes
     *
     * @param fPos the first position
     * @param sPos the second position
     */
    private void swap(int fPos, int sPos) {
        int tmp;
        tmp = heap[fPos];
        heap[fPos] = heap[sPos];
        heap[sPos] = tmp;
    }

    /**
     * Sorts the heap from a position
     *
     * @param pos the position to apply the percolation to
     */
    private void percolateUp(int pos) {
        if (!isLeaf(pos)) {
            if (heap[pos] > heap[leftChild(pos)] || heap[pos] > heap[rightChild(pos)]) {
                if (heap[leftChild(pos)] < heap[rightChild(pos)]) {
                    swap(pos, leftChild(pos));
                    percolateUp(leftChild(pos));
                } else {
                    swap(pos, rightChild(pos));
                    percolateUp(rightChild(pos));
                }
            }
        }
    }

    /**
     * Inserts an element at the last position of the heap (!then applies percolate up to move the node to the correct position)
     *
     * @param element the element to be inserted
     */
    public void insert(int element) {
        if (spaceAvailable()) {
            heap[++lastPos] = element;
            System.out.println("insert = " + element);
        } else {
            System.out.println("- error inserting element " + element + ", heap full");
        }
        int current = lastPos;
        while (heap[current] < heap[parent(current)]) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    public void insertDead(int element) {
        if (spaceAvailable()) {
            heap[maxSize - deadSpace] = element;
            deadSpace++;
            System.out.println("dead insert = " + element);
        } else {
            System.out.println("- error inserting dead element " + element + ", heap full");
        }
        testAndFixDeadSpace();
    }

    private void testAndFixDeadSpace() {
        if (deadSpaceFull()) {
            runLengths.add(runLength + 1);
            runLength = 0;
            deadSpace = 0;
            lastPos = maxSize;
            percolateUp();
            System.out.println("**end of run");
        }
    }

//    /**
//     *
//     * @param ar
//     */
//    void insertionSort(int[] ar) {
//        for (int i = 1; i < ar.length; i++) {
//            int index = ar[i];
//            int j = i;
//            while (j > 0 && ar[j - 1] > index) {
//                ar[j] = ar[j - 1];
//                j--;
//            }
//            ar[j] = index;
//        }
//    }

    /**
     * Sorts the whole heap
     */
    public void percolateUp() {
        for (int pos = (lastPos / 2); pos >= 1; pos--) {
            percolateUp(pos);
        }
    }

    /**
     * Pops the top element and sorts the tree
     *
     * @return top element
     */
    public int pop() {

        testAndFixDeadSpace ();

        int popped = heap[FRONT];

        if ((lastPos -1) >= 0) {
            heap[FRONT] = heap[lastPos--];
            percolateUp(FRONT);
            runLength++;
            System.out.println("pop = " + popped);
        } else System.out.println("*pop " + popped);

        return popped;
    }

    /**
     * Gets the top element of the heap
     *
     * @return top element
     */
    public int getTop() {
        return heap[FRONT];
    }

    /**
     * Checks if space is available in the array to put elements in
     *
     * @return boolean whether space is available
     */
    private boolean spaceAvailable() {
        return lastPos + deadSpace < maxSize;
    }

    private boolean deadSpaceFull() {
        return deadSpace >= maxSize;
    }

    /**
     * @param list
     * @return
     */
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

    /**
     * @param list
     * @param runCount
     * @return
     */
    int getAverageRunLength(int[] list, int runCount) {
        return list.length / runCount; // providing list.length and runLengths are > 0
    }

}
