package SortingVariations;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import SortingVariations.Util.StableTestClass;

public class StableTestClassTest {
    
    private final int[] cutoffValues = { 1, 2, 4, 8, 16 };
    private Integer[] testingArray1;
    private String[] emptyArray;
    private String[] unevenNumber;
    private StableTestClass[] stableTest1;
    private StableTestClass[] stableTest2;
    private Integer[] arr10000;
    private Integer[] arr100000;
    private Integer[] arr1000000;


    @Before
    public void setup() {
        testingArray1 = new Integer[] { 76, 3, 16, 3, 1, 94, 1, 6, 34, 4, 4, 4, 4, 4, 5 };
        emptyArray = new String[] {};
        unevenNumber = new String[] { "Hello", "Heyo", "And", "Anders", "Polution", "And", "Pull" };
        stableTest1 = new StableTestClass[10];
        for (int i = 0; i < 10; i++) {
            StableTestClass s = new StableTestClass(1, i);
            stableTest1[i] = s;
        }
        stableTest2 = new StableTestClass[15];
        for (int i = 0; i < stableTest2.length; i++) {
            if (i % 5 == 0) {
                StableTestClass s = new StableTestClass(1, i / 5);
                stableTest2[i] = s;
            } else {
                StableTestClass s = new StableTestClass(9, i / 5);
                stableTest2[i] = s;
            }
        }
    }

    // -2147483648 to 2147483647
    @Before
    public void setupStressTest() {
        Random random = new Random();
        this.arr10000 = new Integer[10000];
        for (int i = 0; i < 10000; i++) {
            arr10000[i] = random.nextInt(-2147483647, 2147483646);
        }

        this.arr100000 = new Integer[100000];
        for (int i = 0; i < 100000; i++) {
            arr100000[i] = random.nextInt(-2147483647, 2147483646);
        }

        this.arr1000000 = new Integer[1000000];
        for (int i = 0; i < 1000000; i++) {
            arr1000000[i] = random.nextInt(-2147483647, 2147483646);
        }
    }


    //helper method for dynamic test setup
    private <T extends Comparable<T>> void testAlgorithm(String algorithm, int cutoff, boolean isAdaptive, 
                                                        boolean useParallel, int threads, T[] array) {

        Sorter<T> sorter = SorterFactory.getSorter(algorithm, cutoff, isAdaptive, useParallel, threads);

        // Create a copy of the input array for comparison
        T[] expected = Arrays.copyOf(array, array.length);
        Arrays.sort(expected);

        // Perform the sort
        sorter.sort(array);

        // Validate the result
        assertArrayEquals(expected, array);
    }

    //dynamic test with adaptive and no parallel
    @Test
    public void testDynamicSorting() {
        // Get available algorithms dynamically from SorterFactory
        String[] algorithms = SorterFactory.getAvailableAlgorithms();

        // Configurations for the test
        boolean isAdaptive = true;
        boolean useParallel = false;
        int threads = 0;

        // Loop through all available algorithms and cutoff values
        for (String algorithm : algorithms) {
            for (int cutoff : cutoffValues) {
                // Test with Integer array
                testAlgorithm(algorithm, cutoff, isAdaptive, useParallel, threads, testingArray1);

                // Test with StableTestClass object array
                testAlgorithm(algorithm, cutoff, isAdaptive, useParallel, threads, stableTest1);
                testAlgorithm(algorithm, cutoff, isAdaptive, useParallel, threads, stableTest2);

                // Test with String arrays. Odd more if we need
                testAlgorithm(algorithm, cutoff, isAdaptive, useParallel, threads, emptyArray);
                testAlgorithm(algorithm, cutoff, isAdaptive, useParallel, threads, unevenNumber);

                // Stress testing with larger arrays
                testAlgorithm(algorithm, cutoff, isAdaptive, useParallel, threads, Arrays.copyOf(arr10000, arr10000.length));
                testAlgorithm(algorithm, cutoff, isAdaptive, useParallel, threads, Arrays.copyOf(arr100000, arr100000.length));
                // testAlgorithm(algorithm, cutoff, isAdaptive, useParallel, threads, Arrays.copyOf(arr1000000, arr1000000.length));
            }
        }
    }

    // dynamic test with non-adaptive and parallel
    @Test
    public void testDynamicSorting2() {
        // Get available algorithms dynamically from SorterFactory
        String[] algorithms = SorterFactory.getAvailableAlgorithms();

        // Configurations for the test
        boolean isAdaptive = false;
        boolean useParallel = true;
        int threads = 0;

        // Loop through all available algorithms and cutoff values
        for (String algorithm : algorithms) {
            for (int cutoff : cutoffValues) {
                // Test with Integer array
                testAlgorithm(algorithm, cutoff, isAdaptive, useParallel, threads, testingArray1);

                // Test with StableTestClass object array
                testAlgorithm(algorithm, cutoff, isAdaptive, useParallel, threads, stableTest1);
                testAlgorithm(algorithm, cutoff, isAdaptive, useParallel, threads, stableTest2);

                // Test with String arrays. Odd more if we need
                testAlgorithm(algorithm, cutoff, isAdaptive, useParallel, threads, emptyArray);
                testAlgorithm(algorithm, cutoff, isAdaptive, useParallel, threads, unevenNumber);

                // Stress testing with larger arrays
                testAlgorithm(algorithm, cutoff, isAdaptive, useParallel, threads,
                        Arrays.copyOf(arr10000, arr10000.length));
                testAlgorithm(algorithm, cutoff, isAdaptive, useParallel, threads,
                        Arrays.copyOf(arr100000, arr100000.length));
                // testAlgorithm(algorithm, cutoff, isAdaptive, useParallel, threads,
                //         Arrays.copyOf(arr1000000, arr1000000.length));
            }
        }
    }
}
