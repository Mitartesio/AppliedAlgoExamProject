package SortingVariations;

import SortingVariations.Util.StableTestClass;
import SortingVariations.Util.TwoSequenceSelect;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ParallelRecursiveMergeSortTest {

    private Integer[] testingArray1;
    private Integer[] testingArray2;
    private Integer[] testingArray3;
    private String[] emptyArray;
    private String[] unevenNumber;
    private StableTestClass[] stableTest1;
    private StableTestClass[] stableTest2;

    @Before
    public void setup(){
        testingArray1 = new Integer[]{76,3,16,3,1,94,1,6,34,4,4,4,4,4,5};
        testingArray2 = new Integer[]{10, 2, 2, 9, 1, 15, 15, 3, 8, 8, 8, 22, 0, -1, 100};
        testingArray3 = new Integer[]{100, 95, 90, 85, 80, 75, 70, 65, 60, 55, 50, 45, 40, 35, 30,100, 95, 90, 85, 80, 75, 70, 65, 60, 55, 50, 45, 40, 35, 30,100, 95, 90, 85, 80, 75, 70, 65, 60, 55, 50, 45, 40, 35, 30,100, 95, 90, 85, 80, 75, 70, 65, 60, 55, 50, 45, 40, 35, 30};
        emptyArray = new String[]{};
        unevenNumber = new String[] {"Hello", "Heyo", "And", "Anders", "Polution", "And", "Pull"};
        stableTest1 = new StableTestClass[10];
        for(int i = 0; i<10; i++){
            StableTestClass s = new StableTestClass(1, i);
            stableTest1[i] = s;
        }
        stableTest2 = new StableTestClass[15];
        for(int i = 0; i<stableTest2.length; i++){
            if(i%5 == 0){
                StableTestClass s = new StableTestClass(1, i/5);
                stableTest2[i] = s;
            }else{
                StableTestClass s = new StableTestClass(9, i/5);
                stableTest2[i] = s;
            }
        }
    }

    @Test
    public void parallelTestIntegerArray1() {

        int k = testingArray1.length;
        Integer[] sortedArray = new Integer[k];

        System.arraycopy(testingArray1, 0, sortedArray, 0, k);

        Arrays.sort(sortedArray);
        ParallelRecursiveMergeSort<Integer> parallelSort = new ParallelRecursiveMergeSort<>(10,true,0);


        parallelSort.sort(testingArray1);

        assertArrayEquals(sortedArray, testingArray1);

    }

    @Test
    public void parallelTestIntegerArray2() {

        int k = testingArray2.length;
        Integer[] sortedArray = new Integer[k];

        System.arraycopy(testingArray2, 0, sortedArray, 0, k);

        Arrays.sort(sortedArray);
        ParallelRecursiveMergeSort<Integer> parallelSort = new ParallelRecursiveMergeSort<>(10,true,0);


        parallelSort.sort(testingArray2);

        assertArrayEquals(sortedArray, testingArray2);

    }

    @Test
    public void parallelTestIntegerArray3() {

        int k = testingArray3.length;
        Integer[] sortedArray = new Integer[k];

        System.arraycopy(testingArray3, 0, sortedArray, 0, k);

        Arrays.sort(sortedArray);
        ParallelRecursiveMergeSort<Integer> parallelSort = new ParallelRecursiveMergeSort<>(10,true,0);


        parallelSort.sort(testingArray3);

        assertArrayEquals(sortedArray, testingArray3);

    }

    @Test
    public void parallelTestStringArray() {

        int k = unevenNumber.length;
        String[] sortedArray = new String[k];

        System.arraycopy(unevenNumber, 0, sortedArray, 0, k);

        Arrays.sort(sortedArray);
        ParallelRecursiveMergeSort<String> parallelSort = new ParallelRecursiveMergeSort<>(10,true,0);


        parallelSort.sort(unevenNumber);

        assertArrayEquals(sortedArray, unevenNumber);

    }

    @Test
    public void parallelTestComplexTypeArray() {

        int k = stableTest1.length;
        StableTestClass[] sortedArray = new StableTestClass[k];

        System.arraycopy(stableTest1, 0, sortedArray, 0, k);

        Arrays.sort(sortedArray);
        ParallelRecursiveMergeSort<StableTestClass> parallelSort = new ParallelRecursiveMergeSort<>(10,true,0);


        parallelSort.sort(stableTest1);

        assertArrayEquals(sortedArray, stableTest1);

    }

    @Test
    public void parallelTestComparisonAmounts() {

        int k = testingArray1.length;
        Integer[] sortedArray = new Integer[k];

        System.arraycopy(testingArray1, 0, sortedArray, 0, k);

        RecursiveMergeSort<Integer> recursiveSorting = new RecursiveMergeSort<>();
        int recursiveCounter = recursiveSorting.sort(sortedArray);

        ParallelRecursiveMergeSort<Integer> parallelSort = new ParallelRecursiveMergeSort<>(10,false,0);


        int parallelRecursiveComparisons = parallelSort.sort(testingArray1);

        assertEquals(recursiveCounter, parallelRecursiveComparisons);

    }






    @Test
    public void twoSequenceSelectSmallArrayTest(){
        int[] aibi = TwoSequenceSelect.twoSequenceSelect(new Integer[]{1, 3, 5}, new Integer[]{2, 4, 6}, 4);

        // Extract ja and jb from the result
        int ja = aibi[0];
        int jb = aibi[1];

        // Arrays a and b
        Integer[] a = new Integer[]{1, 3, 5};
        Integer[] b = new Integer[]{2, 4, 6};

        // Find the k-th element
        Integer kthElement;
        if (ja > 0 && (jb == 0 || a[ja - 1] <= b[jb - 1])) {
            kthElement = b[jb - 1]; // Take from b
        } else {
            kthElement = a[ja - 1]; // Take from a
        }

        Assert.assertEquals((Integer)4,kthElement);

    }

    @Test
    public void twoSequenceSelectLargerArrayTest(){

        int[] aibi = TwoSequenceSelect.twoSequenceSelect(new Integer[]{1, 2,3,4, 5,6,7,8,9,10,11,12,13,14,19}, new Integer[]{2, 4, 6,13}, 16);

        // Extract ja and jb from the result
        int ja = aibi[0];
        int jb = aibi[1];

        // Arrays a and b
        Integer[] a = new Integer[]{1, 2,3,4, 5,6,7,8,9,10,11,12,13,14,19};
        Integer[] b = new Integer[]{2, 4, 6,13};

        // Find the k-th element
        Integer kthElement;
        if (ja > 0 && (jb == 0 || a[ja - 1] <= b[jb - 1])) {
            kthElement = b[jb - 1]; // Take from b
        } else {
            kthElement = a[ja - 1]; // Take from a
        }

        Assert.assertEquals((Integer)13,kthElement);

    }

    @Test
    public void twoSequenceSelectEmptyArrayTest() {
        int k = 3; // Convert 1-indexed to 0-indexed
        int[] aibi = TwoSequenceSelect.twoSequenceSelect(new Integer[]{}, new Integer[]{1, 2, 3}, k);

        int ja = aibi[0];
        int jb = aibi[1];

        Integer[] a = new Integer[]{};
        Integer[] b = new Integer[]{1, 2, 3};

        // Safely determine the k-th element
        Integer kthElement;
        if (ja == 0) { // If a is empty or all elements from a are used
            kthElement = b[jb - 1];
        } else { // This branch won't be reached, but keeping it for symmetry
            kthElement = a[ja - 1];
        }

        Assert.assertEquals((Integer)3, kthElement);
    }

    @Test
    public void twoSequenceSelectAllInFirstArrayTest() {
        int k = 3; // Convert 1-indexed to 0-indexed
        int[] aibi = TwoSequenceSelect.twoSequenceSelect(new Integer[]{1, 2, 3, 4, 5}, new Integer[]{}, k);

        int ja = aibi[0];
        int jb = aibi[1];

        Integer[] a = new Integer[]{1, 2, 3, 4, 5};
        Integer[] b = new Integer[]{};

        // Safely determine the k-th element
        Integer kthElement;
        if (jb == 0) { // If b is empty or all elements from b are used
            kthElement = a[ja - 1];
        } else { // This branch won't be reached, but keeping it for symmetry
            kthElement = b[jb - 1];
        }

        Assert.assertEquals((Integer)3, kthElement);
    }

    @Test
    public void twoSequenceSelectAllInSecondArrayTest() {
        int k = 4; // Convert 1-indexed to 0-indexed
        int[] aibi = TwoSequenceSelect.twoSequenceSelect(new Integer[]{}, new Integer[]{1, 2, 3, 4, 5}, k);

        int ja = aibi[0];
        int jb = aibi[1];

        Integer[] a = new Integer[]{};
        Integer[] b = new Integer[]{1, 2, 3, 4, 5};

        // Safely determine the k-th element
        Integer kthElement;
        if (ja == 0) { // If a is empty or all elements from a are used
            kthElement = b[jb - 1];
        } else if (jb == 0) { // If b is empty or all elements from b are used
            kthElement = a[ja - 1];
        } else if (a[ja - 1] <= b[jb - 1]) { // Compare valid elements
            kthElement = a[ja - 1];
        } else {
            kthElement = b[jb - 1];
        }

        Assert.assertEquals((Integer)4, kthElement);
    }


    @Test
    public void twoSequenceSelectRepeatedElementsTest() {
        int[] aibi = TwoSequenceSelect.twoSequenceSelect(new Integer[]{1, 1, 1, 1}, new Integer[]{1, 1, 1, 1}, 6);

        int ja = aibi[0];
        int jb = aibi[1];

        Integer[] a = new Integer[]{1, 1, 1, 1};
        Integer[] b = new Integer[]{1, 1, 1, 1};

        Integer kthElement;
        if (ja > 0 && (jb == 0 || a[ja - 1] <= b[jb - 1])) {
            kthElement = b[jb - 1];
        } else {
            kthElement = a[ja - 1];
        }

        Assert.assertEquals((Integer)1, kthElement);
    }

    @Test
    public void twoSequenceSelectEndOfMergeTest() {
        int[] aibi = TwoSequenceSelect.twoSequenceSelect(new Integer[]{1, 3, 5}, new Integer[]{2, 4, 6}, 5);

        int ja = aibi[0];
        int jb = aibi[1];

        Integer[] a = new Integer[]{1, 3, 5};
        Integer[] b = new Integer[]{2, 4, 6};

        // Safely determine the k-th element
        Integer kthElement;
        if (ja == 0) {
            kthElement = b[jb - 1]; // Take from b
        } else if (jb == 0) {
            kthElement = a[ja - 1]; // Take from a
        } else if (a[ja - 1] <= b[jb - 1]) {
            kthElement = b[jb - 1];
        } else {
            kthElement = a[ja - 1];
        }

        Assert.assertEquals((Integer)5, kthElement);
    }


    @Test
    public void compareComparisonsWithParallelMerge() {
        // Create a testing array
        Integer[] testingArray = new Integer[]{10, 8, 6, 4, 2, 1, 3, 5, 7, 9}; // Example unsorted array
        int arraySize = testingArray.length;

        // Sequential merge sort for comparison
        Integer[] sequentialSortedArray = new Integer[arraySize];
        System.arraycopy(testingArray, 0, sequentialSortedArray, 0, arraySize);
        RecursiveMergeSort<Integer> sequentialSort = new RecursiveMergeSort<>();
        int sequentialComparisons = sequentialSort.sort(sequentialSortedArray);

        // Parallel merge sort with parallel merging enabled
        Integer[] parallelSortedArray = new Integer[arraySize];
        System.arraycopy(testingArray, 0, parallelSortedArray, 0, arraySize);
        ParallelRecursiveMergeSort<Integer> parallelSort = new ParallelRecursiveMergeSort<>(10, true, 0);
        int parallelComparisons = parallelSort.sort(parallelSortedArray);

        // Ensure the arrays are sorted correctly
        assertArrayEquals(sequentialSortedArray, parallelSortedArray);

        // Compare the number of comparisons
        assertEquals(sequentialComparisons, parallelComparisons);
    }


    @Test
    public void parallelTestWithParallelMergeSort() {

        int k = testingArray3.length;
        Integer[] sortedArray = new Integer[k];

        System.arraycopy(testingArray3, 0, sortedArray, 0, k);

        Arrays.sort(sortedArray);
        ParallelRecursiveMergeSort<Integer> parallelSort = new ParallelRecursiveMergeSort<>(1,true,0);


        System.out.println(parallelSort.sort(testingArray3));

        assertArrayEquals(sortedArray, testingArray3);

    }


}
