package SortingVariations;

import java.util.Stack;

public class BinomialSortIndex<T extends Comparable<T>> implements Sorter<T> {
    private int counter;
    private int cutoff;
    private boolean adaptive;

    public BinomialSortIndex(int cutoff, boolean adaptive){
        this.cutoff = cutoff;
        this.adaptive = adaptive;
    }

    @Override
    public Integer sort(T[] a) {
        T[] aux = a.clone();
        counter = 0;

        Stack<Integer[]> stack = new Stack<>();
        int i = 0;
        while(i<a.length){
            Integer[] arr = new Integer[2];
            arr[0] = i; 

            //Check whether adaptive
            if(adaptive){
                int sequence = findSequence(i, a);
                //Check if sequence is greater than or equal to cutoff
                if(sequence >= cutoff){
                arr[1] = i + sequence-1;
                i += sequence;
                }else{
                    //Check if i + cutoff is greater than length
                    if(i + cutoff >= a.length){
                        arr[1] = a.length-1;
                        insertionSort(a, i, arr[1]);
                        i = a.length;
                    }else{
                    arr[1] = i + cutoff-1;
                    insertionSort(a, i, i+cutoff-1);
                    i+=cutoff;
                }
                }
            }else{
                if(i + cutoff >= a.length){
                        arr[1] = a.length-1;
                        insertionSort(a, i, arr[1]);
                        i = a.length;
                    }else{
                    arr[1] = i + cutoff-1;
                    insertionSort(a, i, i+cutoff-1);
                    i+=cutoff;
                }
                }
            
            //Checking whether new input is at least the size of the run on top of the stack
            while(!stack.isEmpty()){
                Integer[] topStack = stack.peek();
                if(topStack[1]-topStack[0] + 1 < arr[1] - arr[0] + 1){
                    merge(a, aux, topStack[0], arr[0]-1, arr[1]);
                    stack.pop();
                    arr[0] = topStack[0]; 
                }else{
                    break;
                }
            }

            //Checking whether the new run has half the length of the run on top of the stack
            while(!stack.isEmpty()){
                Integer[] topStack = stack.peek();
                if(topStack[1]-topStack[0] +1 < (arr[1] - arr[0] + 1) * 2){
                    merge(a, aux, topStack[0], arr[0]-1, arr[1]);
                    arr[0] = topStack[0];
                    stack.pop();
            }else{
                break;
            }
        }

        //Assert statement for testing
        assert stack.isEmpty() ||stack.peek()[1]-stack.peek()[0]+1 >= (arr[1]-arr[0]+1)*2;
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

    private int findSequence(int i, T[]a){
        int j = i;
        if(j==a.length-1){
            return 1;
        }
        if(a[j].compareTo(a[j+1])<=0){
            j++;
            counter++;
            while(j<a.length-1){
                counter++;
                if(a[j].compareTo(a[j+1])<=0){
                    j++;
                }else{
                    j++;
                    break;
                }
            }
            if(j == a.length-1 && a[j-1].compareTo(a[j])<=0){
                j++;
            }
        }else{
            j++;
            counter++;
            while(j<a.length-1){
                counter++;
                if(a[j].compareTo(a[j+1])> 0){
                    j++;
                }else{
                    j++;
                    break;
                }
            }
            if(j == a.length-1 && a[j-1].compareTo(a[j])> 0){
                    j++;
                }
            if(j>= cutoff){
            for(int k= 0; k<(j-i)/2; k++){
                T smallElm = a[i+k];
                a[i+k] = a[j-k-1];
                a[j-k-1] = smallElm; 
            }
        }
        }
        
        return j-i;
        
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

