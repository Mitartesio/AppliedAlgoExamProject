package SortingVariations;

public class SorterFactory {
    public static <T extends Comparable<T>> Sorter<T> getSorter(String type, int cutoff, boolean isAdaptive, boolean useParallelMergesort,int numberOfThreads) {
        // We could pass a cutoff or something to this, method
        switch (type) {
            case "recursiveMergeSort":
                return new RecursiveMergeSort<>();
            // Lav flere cases nednefor, skal bare returne den classe vi vil nbruge

            case "insertionMergeSort":
                if (cutoff <= 0) {
                    throw new IllegalArgumentException("Cutoff value required for insertion sort.");
                }
                return new InsertionMergeSort<>(cutoff);

            case "iterativeMergeSort":
                if (cutoff <= 0) {
                    throw new IllegalArgumentException("Cutoff value required for insertion sort.");
                }
                return new IterativeMergeSortIndex<>(cutoff);

            case "binomialSort":
                if (cutoff <= 0) {
                    throw new IllegalArgumentException("Cutoff value required for insertion sort.");
                }
                return new BinomialSortIndex<>(cutoff, isAdaptive);

            case "levelSort":
                if (cutoff <= 0) {
                    throw new IllegalArgumentException("Cutoff value required for insertion sort.");
                }
                return new LevelSortIndex<>(cutoff, isAdaptive);

            case "parallelRecursiveMergeSort":
                if (cutoff <= 0) {
                    throw new IllegalArgumentException("Cutoff value required for parallelRecursiveMergeSort.");
                }
                return new ParallelRecursiveMergeSort<>(cutoff,useParallelMergesort,numberOfThreads);
                
            // case "IterativeMergeSort": 
            // if (cutoff < 0) {
            //     throw new IllegalArgumentException("Cutoff value required for insertion sort.");
            // }
            // return new IterativeMergeSortIndex<>(cutoff);
            //for eksempel:
            // case "iterative":
            //     return new IterativeMergeSort<>();
            // case "hybrid":
            //     return new HybridMergeSort<>(cutoff);

            default:
                throw new IllegalArgumentException("Unknown sorting type: " + type);
        }
    }

    public static String[] getAvailableAlgorithms() {
    return new String[] {
        "recursiveMergeSort",
        "insertionMergeSort",
        "iterativeMergeSort",
        "binomialSort",
        "levelSort",
        "parallelRecursiveMergeSort",
    };
}
}
