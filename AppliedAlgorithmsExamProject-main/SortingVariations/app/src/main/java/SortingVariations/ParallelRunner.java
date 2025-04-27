package SortingVariations;

import java.util.concurrent.ForkJoinPool;

public class ParallelRunner {

    public ParallelRunner(){

    }

    /* ************************ BENCHMARKING BELOW *********************** */



    public void benchmarkParallelCutoffThresholdsSize100k() {
        int pSize = 100000;

        ForkJoinPool pool = new ForkJoinPool();

        int[] thresholds = {5000,10000,50000,70000,100000,500000}; // Threshold values to test



        int n = 50;
        // System.out.println("testing with" + n + " number of repetitions & no parallel merging 100000");
        ParallelRecursiveMergeSort.runCutoffBenchmark(pool, pSize, thresholds, n,false,0);

        pool.shutdown();


    }

    public void benchmarkParallelCutoffThresholdsSize1M() {
        int pSize = 1000000;

        ForkJoinPool pool = new ForkJoinPool();

        int[] thresholds = {5000,10000,50000,70000,100000,500000}; // Threshold values to test

        int n = 50;

        // System.out.println("testing with" + n + " number of repetitions & no parallel merging 1.000.000");
        ParallelRecursiveMergeSort.runCutoffBenchmark(pool, pSize, thresholds, n,false,0);

        pool.shutdown();


    }

    public void benchmarkParallelCutoffThresholdsSize10M() {
        int pSize = 10000000;

        ForkJoinPool pool = new ForkJoinPool();

        int[] thresholds = {5000,10000,50000,70000,100000,500000}; // Threshold values to test

        int n = 50;
        // System.out.println("testing with" + n + " number of repetitions & no parallel merging 10.000.000");
        ParallelRecursiveMergeSort.runCutoffBenchmark(pool, pSize, thresholds, n,false,0);

        pool.shutdown();


    }


    public void testThreadScaling100K() {
        int[] threadCounts = {1, 2, 4, 6, 8, 16};
        int arraySize = 1000000;
        int threshold = 50000;


        Integer[] inputArray = ParallelRecursiveMergeSort.generateRandomArray(arraySize, 1000000);
        ParallelRecursiveMergeSort.benchmarkSortingWithThreads(inputArray, threshold, threadCounts);

    }

    public void testThreadScaling1M() {
        int[] threadCounts = {1, 2, 4, 6, 8, 16};
        int arraySize = 1000000;
        int threshold = 50000;


        Integer[] inputArray = ParallelRecursiveMergeSort.generateRandomArray(arraySize, 1000000);
        ParallelRecursiveMergeSort.benchmarkSortingWithThreads(inputArray, threshold, threadCounts);

    }

    public void testThreadScaling10M() {
        int[] threadCounts = {1, 2, 4, 6, 8, 16};
        int arraySize = 10000000;
        int threshold = 50000;


        Integer[] inputArray = ParallelRecursiveMergeSort.generateRandomArray(arraySize, 1000000);
        ParallelRecursiveMergeSort.benchmarkSortingWithThreads(inputArray, threshold, threadCounts);

    }


}
