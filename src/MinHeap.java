import java.util.ArrayList;
import java.util.Arrays;
import java.util.IllegalFormatCodePointException;

public class MinHeap {

    private int[] heap;
    private int lastPos;
    private int maxSize;
    private int deadSpace;
    private int runLength;

    private ArrayList<Integer> runLengths;

    private static final int FRONT = 1;

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
        heap[0] = -999;
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

        System.out.print("\t\t" + Arrays.toString(heap));
        System.out.print("\t" + lastPos + " Last Pos");
        System.out.print(", " + deadSpace + " Dead space");
        if (!spaceAvailable()) {
            System.out.println(", 0 Empty");
        } else
            System.out.println(", " + (maxSize - (lastPos + deadSpace)) + " Empty from [" + (lastPos + 1) + "]");
    }

    public void printTrees() {
        for (int i = 1; i <= lastPos / 2; i++) {
            int parent = heap[i];
            int left = heap[2 * i];
            int right = heap[2 * i + 1];
            System.out.print(" PARENT : " + parent + " LEFT CHILD : " + left
                    + " RIGHT CHILD :" + right);
            System.out.println();
        }
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
            try {
                if (heap[pos] > heap[leftChild(pos)] || heap[pos] > heap[rightChild(pos)]) {
                    if (heap[leftChild(pos)] < heap[rightChild(pos)]) {
                        swap(pos, leftChild(pos));
                        percolateUp(leftChild(pos));
                    } else {
                        swap(pos, rightChild(pos));
                        percolateUp(rightChild(pos));
                    }
                }
            } catch (ArrayIndexOutOfBoundsException ioe) {
                System.out.println("FUCK THIS SHIT");
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
            System.out.println("\tinsert " + element);
        } else {
            System.out.println("- Error inserting element " + element + ", heap full");
        }
        int current = lastPos;
        while (heap[current] < heap[parent(current)]) {
            swap(current, parent(current));
            current = parent(current);
        }
        percolateUp();
    }

    public void insertDead(int element) {
        if (spaceAvailable()) {
            heap[maxSize - deadSpace] = element;
            deadSpace++;
            System.out.println("\tdead insert " + element);
        } else {
            System.out.println("- Error inserting dead element " + element + ", heap full");
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
            System.out.println("\t**end of run");
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
        if (lastPos > 1)
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

        testAndFixDeadSpace();

        int popped = heap[FRONT];

        if ((lastPos - 1) >= 0) {
            heap[FRONT] = heap[lastPos--];
            percolateUp(FRONT);
            runLength++;
            System.out.println("\tpop " + popped);
        } else {
            System.out.println("- Error using pop, no available elements in heap");
        }

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
        return deadSpace == maxSize;
    }

    private void forceResetDead() {
        if (lastPos == 0)
            if (deadSpace > 0) {
                System.out.println("Force reset successful");
                for (int i = 0; i <= deadSpace; i++) {
                    heap[i] = heap[maxSize - (deadSpace + i)];
                }
            }
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

    public void process(int[] input) {

        System.out.println(Arrays.toString(input) + " input");

        int inputPos = 0;
        int outputPos = 0;

        int lastOutput = 0;

        int[] output = new int[input.length];

        for (int i = 0; i < input.length; i++) {
            System.out.println("i = " + i);
            print();
            if (spaceAvailable() && i < input.length) {
                if (input[i] >= lastOutput) {
                    insert(input[i]);
                } else {
                    if (!deadSpaceFull()) {
                        insertDead(input[i]);
                    }
                }
            } else {
                i--;
                lastOutput = getTop();
                output[outputPos++] = pop();
                System.out.println(Arrays.toString(output) + " output");
            }
            if (i == input.length-1){
                forceResetDead();
                i -= lastPos;
//                print();
//                System.out.println("pop remainder");
//                for (int i = 0; i < lastPos; ) {
//                    output[outputPos++] = pop();
//                    System.out.println(Arrays.toString(output) + " output");
//                    print();
//                    System.out.println(i + "," + lastPos);
//                }
            }
        }

    }
}
