package SortingVariations;

public interface Sorter<T extends Comparable<T>> {

    Integer sort(T[] a);
    
}
