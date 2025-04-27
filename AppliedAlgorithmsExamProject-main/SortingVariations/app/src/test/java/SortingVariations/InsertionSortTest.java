package SortingVariations;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Before;
import org.junit.Test;

public class InsertionSortTest {
    

    private Integer[] testingArray1;


    @Before
    public void setup(){
        testingArray1 = new Integer[]{76,3,16,3,1,94,1,6,34,4,4,4,4,4,5};

    }


    @Test
    public void insertionSortTest() {

        int k = testingArray1.length;
        Integer[] sortedArray = new Integer[k];

        for (int i = 0; i < k; i++) {
            sortedArray[i] = testingArray1[i];
            
        }
        
        Arrays.sort(sortedArray);
        InsertionSort<Integer> sorter = new InsertionSort<>();

        sorter.sort(testingArray1);
        
        assertArrayEquals(sortedArray, testingArray1);
    }

}
