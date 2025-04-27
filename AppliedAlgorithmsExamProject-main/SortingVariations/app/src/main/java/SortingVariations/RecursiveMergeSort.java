package SortingVariations;

public class RecursiveMergeSort<T extends Comparable<T>> implements Sorter<T>{
    private int counter;

    
    @Override
    public Integer sort(T[] a){
        this.counter = 0;
        T[] aux = a.clone();
        sort(a, aux, 0, a.length-1);
        //assert isSorted(a);
        return counter;
    }

    private void sort(T[] a, T[] aux, int low, int high){
        // Using Comparable we force the user to convert to complex types, should be fine
        // Basically just a checker whether it's not valid. Or actually this is our base case?
        if (high <= low) return;

        // find mid
        int mid = low + (high - low) / 2;

        //recursively call sort of the low to mid and mid to high
        sort(a, aux, low, mid);
        sort(a, aux, mid + 1, high);

        // final merge? or no, this is the merge for all the recursive calls when it becmes small enough
        merge(a, aux, low, mid, high);
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


}
