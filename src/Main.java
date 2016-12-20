import java.util.Random;

public class Main {
    public static void main(String[] args) {

        final int amountOfValues = 15;
        final int heapSize = 5;
        RSHeap heap = new RSHeap(new Main().generateValues(amountOfValues), heapSize);
    }

    public Main() {

    }

    private void testHeap() {
        MinHeap minHeap = new MinHeap(15);

        minHeap.insert(0);
        minHeap.insert(1);
        minHeap.insert(2);
        minHeap.insert(3);
        minHeap.insert(4);
        minHeap.insert(5);
        minHeap.insert(6);
        minHeap.insert(7);
        minHeap.insertDead(10);
        minHeap.insertDead(11);
        minHeap.insert(8);
        minHeap.insertDead(12);
        minHeap.insertDead(13);
        minHeap.insertDead(14);
        minHeap.insertDead(15);
        minHeap.insertDead(16); // err
        minHeap.print(); // 9 last pos, 6 dead space, empty 0

        minHeap.pop();
        minHeap.print(); // 8 last pos, 6 dead space, empty 1 [9]
        minHeap.pop();
        minHeap.print(); // 7 last pos, 6 dead space, empty 2 [9]

        minHeap.insertDead(16);
        minHeap.insertDead(17);
        minHeap.print(); // 7 last pos, 8 dead space, empty 0

        minHeap.pop();
        minHeap.pop();
        minHeap.pop();
        minHeap.pop();
        minHeap.pop();
        minHeap.pop();
        minHeap.pop();
        minHeap.pop(); // err
        minHeap.print(); // 0 last pos, 8 dead space, empty 7 [1]

        minHeap.insertDead(18);
        minHeap.insertDead(19);
        minHeap.insertDead(20);
        minHeap.insertDead(21);
        minHeap.insertDead(22);
        minHeap.insertDead(23);
        minHeap.insertDead(25); // run finished, all dead space converted to usable space, heap still full
        minHeap.insertDead(24); // err
        minHeap.insert(2); // err
        minHeap.print(); // last pos 15, dead space 0, empty 0

        minHeap.printTrees();
    }

    public int[] generateValues(int n) {

        int[] permutations = new int[n];
        int swapPosition;
        long counter = 0;
        for (int i = 0; i < n; i++) {
            counter++;
            swapPosition = (int)(Math.random() * (i + 1)); //0-i
            permutations[i] = permutations[swapPosition]; //replace [i] with the temp value
            permutations[swapPosition] = i; //replace the [swapPosition] with i
        }

        return permutations;
    }
}
