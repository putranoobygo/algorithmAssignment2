import java.nio.Buffer;

/**
 * Created by Alvin on 12/2/2016.
 */
public class RSHeap {

    public static void main(String args[]) {

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
