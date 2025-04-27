package SortingVariations;

import java.util.Stack;

public class IterativeMergeSortIndex<T extends Comparable<T>> implements Sorter<T>{
    private int cutoff; //Need to make this compatible with insertionSort, discuss with Frederik and Tobias
    private int counter;

    public IterativeMergeSortIndex(int cutoff){
        this.cutoff = cutoff;
        this.counter = 0;
    }


    @Override
    public Integer sort(T[] a){
        counter = 0;
        T[] aux = a.clone();
        Stack<Integer[]> stack = new Stack<>();
        int i = 0;
        //Check whether i + cutoff is larger than array size
        while(i<a.length){
            Integer[] arr = new Integer[2];

            if(i+cutoff<=a.length){
                arr[0] = i;
                i+=cutoff-1;
                arr[1] = i;
                i++;
            }
            else{
                arr[0] = i;
                i=a.length-1;
                arr[1] = i;
                i++;
            }

            //Use insertionsort
            insertionSort(a, arr[0], arr[1]);

            while(stack.size()> 0){
                Integer[] topArray = stack.peek();
                if(topArray[1] - topArray[0] == arr[1] - arr[0]){
                    stack.pop();
                    merge(a,aux,topArray[0], arr[0]-1, arr[1]);
                    arr[0] = topArray[0];
                }else{
                    break;
                }
            }
            stack.add(arr);

        }
        //While more than one run merge two runs on top of the stack
        while(stack.size()>1){
            Integer[] arr = stack.pop();
            merge(a,aux,stack.peek()[0], arr[0]-1, arr[1]);
            stack.peek()[1] = arr[1];
        }

        return counter;
    }

    private void merge(T[] a, T[] aux, int low, int mid, int high){

        for (int k = low; k<=high; k++) {
            aux[k] = a[k];
        }


        int i = low;
        int j = mid+1;

        for (int k = low; k <= high; k++) {
            if(i > mid){
            a[k] = aux[j++];}
            else if (j > high)
            { a[k] = aux[i++];}
            else if((aux[j].compareTo(aux[i]))<0) 
            {a[k] = aux[j++];
            counter++;}
            else 
            {a[k] = aux[i++];
            counter++;}
            
        }
    }

    private void insertionSort(T[] a, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            T key = a[i];
            int j = i - 1;

            while (j >= low && a[j].compareTo(key) > 0) {
                counter++; // Couning that its[j] > key
                a[j + 1] = a[j];
                j--;
            }
    
            // if while loop condition failscount comparison
            if (j >= low) {
                counter++; // Counting comparison when while loop eends
            }

            a[j + 1] = key;
        }
    }

}

