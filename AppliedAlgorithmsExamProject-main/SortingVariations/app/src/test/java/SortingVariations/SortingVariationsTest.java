package SortingVariations;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class SortingVariationsTest {

    private Integer[] testingArray1;
    private String[] emptyArray;


    @Before
    public void setup(){
        testingArray1 = new Integer[]{76,3,16,3,1,94,1,6,34,4,4,4,4,4,5};
        emptyArray = new String[]{};
    }


    @Test
    public void mergeSortTest() {
        //Integer[] sortedArray = new Integer[]{1,1,3,3,4,4,4,4,4,4,5,6,16,76,94};

        int k = testingArray1.length;
        Integer[] sortedArray = new Integer[k];

        for (int i = 0; i < k; i++) {
            sortedArray[i] = testingArray1[i];
            
        }
        
        Arrays.sort(sortedArray);
        RecursiveMergeSort<Integer> sorter = new RecursiveMergeSort<>();

        System.out.println(sorter.sort(testingArray1));
        
        assertArrayEquals(sortedArray, testingArray1);
    }

    @Test
    public void secondaryTest(){
        assertTrue(true);
    }

    @Test
    public void testEmpty(){
        RecursiveMergeSort<String> sort = new RecursiveMergeSort<>();
        String[] testArray = new String[]{};
        sort.sort(emptyArray);
        assertEquals(testArray, emptyArray);
    }
}
