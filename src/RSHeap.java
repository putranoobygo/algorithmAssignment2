import java.util.ArrayList;
import java.util.Arrays;

public class RSHeap {

    private int[] heap;
    private int maxSize;
    private int heapSize;
    private int deadSpace;
    private int runLength;
    private int outputPos;
    private int[] output;

    private ArrayList<Integer> runLengths;

    private static final int FRONT = 1;

    /**
     * Constructor initializes heap with maxSize, sets heapSize to
     *
     * @param maxSize maximum maxSize of the heap
     */
    public RSHeap(int maxSize) {
        this.maxSize = maxSize;
        heapSize = 0;
        deadSpace = 0;
        runLength = 0;
        outputPos = 0;
        runLengths = new ArrayList<>();
        heap = new int[this.maxSize + 1];
        heap[0] = -999;
    }

    /**
     * @param pos
     * @return
     */
    private int parent(int pos) {
        return pos / 2;
    }

    /**
     * Prints the parents and children
     */
    private void print() {
        //heap, heapSize, deadspace, empty [from], outputPos
//        System.out.println("\t\t"+Arrays.toString(Arrays.copyOfRange(heap,1,maxSize+1)));
        System.out.println("\t\t" + toString(Arrays.copyOfRange(heap, 1, maxSize + 1)));
        System.out.println(">>\t" + Arrays.toString(Arrays.copyOfRange(output, 0, outputPos)));
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
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
     * Inserts an element at the last position of the heap (!then applies percolate up to move the node to the correct position)
     *
     * @param element the element to be inserted
     */
    private void insert(int element) {
        if (intSpaceAvailable() > 0) {
            heap[++heapSize] = element;
            System.out.println("\t\t> " + element);
        } else {
            System.out.println("- Error inserting element " + element + ", heap full");
        }
        int current = heapSize;
        while (heap[current] < heap[parent(current)]) {
            swap(current, parent(current));
            current = parent(current);
        }
        bubbleUp();
    }

    /**
     * @param element
     */
    private void insertDead(int element) {
        if (intSpaceAvailable() > 0) {
            heap[maxSize - deadSpace] = element;
            ++deadSpace;
            System.out.println("\t\t> (" + element + ")");
        } else {
            System.out.println("- Error inserting dead element " + element + ", heap full");
        }
    }

    /**
     *
     */
    private void bubbleUp() {
        int pos = heapSize - 1;
        while (pos > 0 && heap[pos / 2] > heap[pos]) {
            int y = heap[pos];
            heap[pos] = heap[pos / 2];
            heap[pos / 2] = y;
            pos = pos / 2;
        }
    }

    /**
     * Removes the top element and re-heaps the tree
     *
     * @return top element
     */
    private int pop() {

        int popped = heap[FRONT];

        heap[FRONT] = heap[heapSize--];
        sinkDown(FRONT);
        System.out.println("\tpop " + popped);

        return popped;
    }

    /**
     * The specified element is compared with its children and swapped if necessary; any elements swapped also have the operation performed on them.
     *
     * @param k the element at which the sink down operation should be started from
     */
    private void sinkDown(int k) {
        int smallest = k;
        if (2 * k <= heapSize && heap[smallest] >= heap[2 * k]) {
            smallest = 2 * k;
        }
        if (2 * k + 1 <= heapSize && heap[smallest] >= heap[2 * k + 1]) {
            smallest = 2 * k + 1;
        }
        if (smallest != k) {
            swap(k, smallest);
            sinkDown(smallest);
        }
    }

    /**
     * Gets the top element of the heap
     *
     * @return top element
     */
    private int getPop() {
        return heap[FRONT];
    }

    /**
     * @return the number of free spaces in the data structure
     */
    private int intSpaceAvailable() {
        return maxSize - deadSpace - heapSize;
    }

    /**
     * @param input the input array to be processed
     * @return
     */
    public int[] processArray(int[] input) {

        System.out.println("processing array; N = " + input.length + ", H = " + maxSize);

        if (maxSize == 0 || maxSize == 1 || input.length == 0) {
            return input;
        }

        boolean endOfheap = false;

        // reset run lengths
        runLengths.clear();
        runLength = 0;

        // store position of input array cursor
        int inputPos = 0;

        // store the value of last output
        int lastOutput = Integer.MIN_VALUE;

        // create output array
        output = new int[input.length];

        // while output array not full
        while (outputPos < input.length && !endOfheap) {

            // check if the
            endOfheap = (heapSize < 1 && deadSpace < 1 && outputPos > 1);

            // space available to place elements in heap
            while (intSpaceAvailable() > 0) {

                System.out.println(intSpaceAvailable() + " free spaces");

                // elements left in the input array
                if (inputPos < input.length) {

                    System.out.println(Arrays.toString(Arrays.copyOfRange(input, inputPos, input.length)) + "  <<");

                    // insert element into heap or dead space
                    if (input[inputPos] >= lastOutput) {
                        insert(input[inputPos]);
                    } else {
                        insertDead(input[inputPos]);
                    }

                    // increment
                    inputPos++;

                    print();
                } else break;
            }

            // start outputting

            // if there are elements in heap
            if (heapSize > 0) {
                lastOutput = getPop();

                // put top element from heap into output
                output[outputPos] = pop();

                // increment
                outputPos++;
                runLength++;

                System.out.println("output " + outputPos + "/" + output.length);

                print();
            } else if (deadSpace > 0) {

                System.out.println("converting dead space to heap");

                // convert dead space into heap space
                int dead[] = Arrays.copyOfRange(heap, heap.length - deadSpace, heap.length);
                heapSize = 0;
                deadSpace = 0;
                for (int i = 0; i < dead.length; i++) {
                    insert(dead[i]);
                }
                print();

                // mark the end of the run
                runLengths.add(runLength);
                runLength = 0;
            }
        }
        if (runLength > 0) {
            runLengths.add(runLength);
        }
        System.out.println("runlengths " + runLengths);
        return output;
    }

    /**
     * @param array
     * @return
     */
    public String toString(int[] array) {
        if (array == null) {
            return "null";
        } else {
            int var1 = array.length - 1;
            if (var1 == -1) {
                return "{}";
            } else {
                StringBuilder output = new StringBuilder();
                output.append('{');
                int i = 0;

                while (true) {
                    //here is where the numbers should be formatted
                    if (i >= heapSize) {
                        if (i < array.length - deadSpace) {
                            output.append('-');
                        } else {
                            output.append('(');
                            output.append(array[i]);
                            output.append(')');
                        }
                    } else {
                        output.append(array[i]);
                    }
                    //
                    if (i == var1) {
                        return output.append('}').toString();
                    }

                    output.append(", ");
                    ++i;
                }
            }
        }
    }
}
