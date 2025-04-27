package SortingVariations;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class IterativeMergeSortNicheTest {

private Integer[] arr1;
private Integer[] arr2;

@Before
    public void setup(){
        this.arr1 = new Integer[]{1,2,3,4,5,4,3,2,1};
        this.arr2 = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        }

        @Test
        public void test1(){
        IterativeMergeSortIndex<Integer> sorter = new IterativeMergeSortIndex<>(2);
        Integer result = sorter.sort(arr1);

        assertEquals((Integer)16, result);

        setup();

        IterativeMergeSortIndex<Integer> sorter2 = new IterativeMergeSortIndex<>(5);
        Integer result2 = sorter2.sort(arr1);

        assertEquals((Integer)18, result2);

        setup();

        IterativeMergeSortIndex<Integer> sorter3 = new IterativeMergeSortIndex<>(10);
        Integer result3 = sorter3.sort(arr1);

        assertEquals((Integer)24, result3);
        }

        //This test seeks to show that the insertion sort is correctly used with different cutoff values
        @Test
        public void test2(){
        IterativeMergeSortIndex<Integer> sorter = new IterativeMergeSortIndex<>(2);
        Integer result = sorter.sort(arr2);

        assertEquals((Integer)21, result);

        setup();

        IterativeMergeSortIndex<Integer> sorter2 = new IterativeMergeSortIndex<>(4);
        Integer result2 = sorter2.sort(arr2);

        assertEquals((Integer)19, result2);

        setup();

        IterativeMergeSortIndex<Integer> sorter3 = new IterativeMergeSortIndex<>(10);
        Integer result3 = sorter3.sort(arr2);

        assertEquals((Integer)9, result3);
        }

}
