import java.util.Random;

public class Main {
    public static void main(String[] args) {
        new Main();
    }

    public Main() {

        final int[] n = {10, 10, 5, 20, 0, 20, 1, 4, 16, 100};
        final int[] h = {10, 5, 10, 0, 20, 1, 20, 16, 4, 3};

        for (int i = 0; i < n.length; i++) {
            int[] inputFile = generateRandomList(n[i]);
            RSHeap minHeap = new RSHeap(h[i]);
            minHeap.processArray(inputFile);
        }
    }

    /**
     * Generates a random list of numbers between zero and and a specified number
     *
     * @param n the maximum value range in the list
     * @return a random list of integers
     */
    private int[] generateRandomList(int n) {
        Random r = new Random();
        int[] list = new int[n];
        for (int i = 0; i < n; i++) {
            list[i] = r.nextInt(n);
        }
        return list;
    }
}
