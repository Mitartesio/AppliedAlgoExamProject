package SortingVariations;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import SortingVariations.Util.StableTestClass;


public class IterativeMergeSortIndexTest {
    private Integer[] testingArray1;
    private String[] emptyArray;
    private String[] unevenNumber;
    private StableTestClass[] stableTest1;
    private StableTestClass[] stableTest2;
    private Integer[] arr10000;
    private Integer[] arr100000;
    private Integer[] arr1000000;

    @Before
    public void setup(){
        testingArray1 = new Integer[]{76,3,16,3,1,94,1,6,34,4,4,4,4,4,5};
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

    //-2147483648 to 2147483647
    @Before
    public void setupStressTest(){
        Random random = new Random();
        this.arr10000 = new Integer[10000];
        for(int i = 0; i<10000; i++){
            arr10000[i] = random.nextInt(-2147483647, 2147483646);
        }

        this.arr100000 = new Integer[100000];
        for(int i = 0; i<100000; i++){
            arr100000[i] = random.nextInt(-2147483647, 2147483646);
        }

        this.arr1000000 = new Integer[1000000];
        for(int i = 0; i<1000000; i++){
            arr1000000[i] = random.nextInt(-2147483647, 2147483646);
        }
    }

        @Test
    public void IterativeMergeSortIndexTest() {

        int k = testingArray1.length;
        Integer[] sortedArray = new Integer[k];

        for (int i = 0; i < k; i++) {
            sortedArray[i] = testingArray1[i];
            
        }
        
        Arrays.sort(sortedArray);
        IterativeMergeSortIndex<Integer> bioAdaptive = new IterativeMergeSortIndex<>(2);

        bioAdaptive.sort(testingArray1);
        
        assertArrayEquals(sortedArray, testingArray1);
    }

    @Test
    public void emptyTest(){
        IterativeMergeSortIndex bioAdaptive = new IterativeMergeSortIndex<>(5);
        bioAdaptive.sort(emptyArray);
        String[] emptyTestArray = {};
        assertEquals(emptyTestArray, emptyArray);

        IterativeMergeSortIndex<String> bioAdaptiveNonAdaptive = new IterativeMergeSortIndex<>(5);
        bioAdaptiveNonAdaptive.sort(emptyArray);
        assertEquals(emptyTestArray, emptyArray);
    }

    @Test
    public void testUneven(){
        IterativeMergeSortIndex<String> bioAdaptive = new IterativeMergeSortIndex<>(3);
        String[] testArray = new String[unevenNumber.length];
        for(int i = 0; i<unevenNumber.length; i++){
            testArray[i] = unevenNumber[i];
        }
        Arrays.sort(testArray);
        bioAdaptive.sort(unevenNumber);

        assertEquals(testArray, unevenNumber);

        
    }

    @Test
    public void stableTest1(){
        IterativeMergeSortIndex<StableTestClass> bioAdaptive = new IterativeMergeSortIndex<>(3);
        
        bioAdaptive.sort(stableTest1);
        for(int i=0; i<stableTest1.length; i++){
            assertEquals(i, stableTest1[i].getTester());
        }

    }

    @Test
    public void stableTest2(){
        IterativeMergeSortIndex<StableTestClass> bioAdaptive = new IterativeMergeSortIndex<>(4);
        
        bioAdaptive.sort(stableTest1);
        assertEquals(1,stableTest1[0].getComparable());
        assertEquals(1,stableTest1[1].getComparable());
        assertEquals(1,stableTest1[2].getComparable());

        assertEquals(0,stableTest1[0].getTester());
        assertEquals(1,stableTest1[1].getTester());
        assertEquals(2,stableTest1[2].getTester());

    }

    @Test 
    public void stressTest(){
        IterativeMergeSortIndex<Integer> bioAdaptive = new IterativeMergeSortIndex<>(3);

        Integer[] stress1Test = new Integer[arr10000.length];
        for(int i = 0; i<arr10000.length; i++){
            stress1Test[i] = arr10000[i];
        }
        bioAdaptive.sort(arr10000);
        Arrays.sort(stress1Test);

        assertEquals(stress1Test, arr10000);

        
    }   

    @Test
    public void stressTest2(){
        IterativeMergeSortIndex<Integer> bioAdaptive = new IterativeMergeSortIndex<>(3);

        Integer[] stress1Test = new Integer[arr100000.length];
        for(int i = 0; i<arr100000.length; i++){
            stress1Test[i] = arr100000[i];
        }
        bioAdaptive.sort(arr100000);
        Arrays.sort(stress1Test);

        assertEquals(stress1Test, arr100000);

    }

    @Test
    public void stressTest3(){
        IterativeMergeSortIndex<Integer> bioAdaptive = new IterativeMergeSortIndex<>(3);

        Integer[] stress1Test = new Integer[arr1000000.length];
        for(int i = 0; i<arr1000000.length; i++){
            stress1Test[i] = arr1000000[i];
        }
        bioAdaptive.sort(arr1000000);
        Arrays.sort(stress1Test);

        assertEquals(stress1Test, arr1000000);

    }

}
