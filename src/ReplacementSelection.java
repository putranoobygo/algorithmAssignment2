import java.nio.Buffer;

/**
 * Created by Alvin on 12/2/2016.
 */
public class ReplacementSelection {

    public static void main(String args[]){

    }

    public int[] ReplacementSelection(int[] input, int heapsize){
        int[] heap = new int[heapsize]; //create heap
        int[] list = new int[heapsize]; // create list array
        int[] output = new int[input.length]; //create output array
        int listposition = 0; // list position
        boolean listfull = false; //create boolean to determine if list is full
        int runcount =0; // count runs
        int inputPosition =0; // position in input array
        for(int i=0; i<heapsize; i++){
            heap[i]= input[inputPosition]; //fill heap for first run
            inputPosition++;
        }
        insertionSort(heap); //sort heap
        int k = 0;
        output[k] = heap[0];
        while(listfull = false){
            for(k=0; k<output.length; k++){

                if(output[k]<=input[inputPosition]){
                    heap[0]=input[inputPosition];
                    insertionSort(heap);
                    output[k]=heap[0];
                }
                if(output[k]>input[inputPosition]){
                    list[listposition] = input[inputPosition];
                    listposition++;
                }
                inputPosition++;
                if(listposition==list.length){
                    listfull = true;
                }
            }
        }




    }

    void insertionSort(int[] ar)
    {
        for (int i=1; i < ar.length; i++)
        {
            int index = ar[i]; int j = i;
            while (j > 0 && ar[j-1] > index)
            {
                ar[j] = ar[j-1];
                j--;
            }
            ar[j] = index;
        } }
}
