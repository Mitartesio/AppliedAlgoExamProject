package SortingVariations;

public class InsertionSort<T extends Comparable<T>> {


    public Integer sort(T[] a) {
        int comparisonCounter = 0;
        int n = a.length;
        for (int i = 1; i < n; i++) {
            for (int j = i; j > 0 && (a[j].compareTo(a[j-1])<0); j--) {
                comparisonCounter++;
                exch(a, j, j-1);
            }
            assert isSorted(a, 0, i);
        }
        /*assert isSorted(a);*/

        return comparisonCounter;
    }
    

    // exchange a[i] and a[j]
    private void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    private boolean isSorted(T[] a, int lo, int hi) {
        for (int i = lo + 1; i < hi; i++)
            if (a[i].compareTo(a[i-1])<0) return false;
        return true;
    }

    private boolean isSorted(T[] a) {
        return isSorted(a, 0, a.length);
    }
}
