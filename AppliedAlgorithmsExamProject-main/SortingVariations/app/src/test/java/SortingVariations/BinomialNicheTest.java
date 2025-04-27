package SortingVariations;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class BinomialNicheTest {
    private Integer[] arr2;
    private Integer[] arr3;

    @Before
    public void setup(){
       this.arr2 = new Integer[]{1,2,3,4,5,6,5,4,3,2,1};
       this.arr3 = new Integer[]{1,2,1,2,3,9,5,4,3,9};
    }

    //This test seeks to prove that the adaptive and nonadaptive will give you different results on the same input
    @Test
    public void firstTest(){
        BinomialSortIndex<Integer> bioAdaptive = new BinomialSortIndex<>(2,true);
        Integer result = bioAdaptive.sort(arr2);
        assertEquals((Integer)20, result);

        setup();

        BinomialSortIndex<Integer> bioNonAdaptive = new BinomialSortIndex<>(2,false);
        Integer result2 = bioNonAdaptive.sort(arr2);
        assertEquals((Integer)21, result2);
            }

        //Here we seek to prove that the algorithms correctly differ on different cutoff values
    @Test
    public void secondTest(){
        BinomialSortIndex<Integer> bioAdaptive = new BinomialSortIndex<>(2,true);
        Integer result = bioAdaptive.sort(arr3);
        assertEquals((Integer)24, result);

        setup();

        BinomialSortIndex<Integer> bioAdaptive2 = new BinomialSortIndex<>(3,true);
        Integer result2 = bioAdaptive2.sort(arr3);
        assertEquals((Integer)26, result2);

        setup();

        BinomialSortIndex<Integer> bioNonAdaptive = new BinomialSortIndex<>(2,false);
        Integer result3 = bioNonAdaptive.sort(arr3);
        assertEquals((Integer)24, result3);

        setup();

        BinomialSortIndex<Integer> bioNonAdaptive2 = new BinomialSortIndex<>(3,false);
        Integer result4 = bioNonAdaptive2.sort(arr3);
        assertEquals((Integer)23, result4);
    }
    
}
