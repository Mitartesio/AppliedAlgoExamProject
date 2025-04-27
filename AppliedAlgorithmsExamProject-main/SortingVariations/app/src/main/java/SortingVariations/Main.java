package SortingVariations;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import SortingVariations.Util.ObjectClass;
public class Main {
    
    public static void main(String[] args) throws IOException {

        String sortType = args[0];
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        //Horse race experiment
        if(args[1].equals("HorseRace")){
            //Arrays.sort
            if(args[3].equals("Arrays.sort")){
                String input;
                while((input = reader.readLine()) != null){
                    String[] stringArray = reader.readLine().split(" ");
                    Integer[] arr = Arrays.stream(stringArray)
                        .map(Integer::parseInt)
                        .toArray(Integer[]::new);

                        Long start = System.nanoTime();
                        Arrays.sort(arr);
                        Long end = System.nanoTime();
                        System.out.println(arr.length + " " + (end-start)/1_000_000_000.0);

                }
                //Non-adaptive algorithms
            }else if(args[3].equals("NonAdaptive")){
                int cutoff = Integer.parseInt(args[4]);
                Sorter<Integer> sorter = SorterFactory.getSorter(sortType,cutoff,false,false,0);
                sorterMethod(sorter, reader(args[2]));

                //Adaptive algorithms
            }else if (args[3].equals("Adaptive")) {
                int cutoff = Integer.parseInt(args[4]);
                Sorter<Integer> sorter = SorterFactory.getSorter(sortType, cutoff,true,false,0);
                sorterMethod(sorter, reader(args[2]));

            //Parallel implementation
            }else{
                if(args[3].equals("Parallel")){
                int cutoff = Integer.parseInt(args[4]);
                Sorter<Integer> sorter = SorterFactory.getSorter(sortType, cutoff,false,true,0);
                sorterMethod(sorter, reader(args[2]));
            }
        }
    }
        //Basecase for mergesort
        else if(args[1].equals("BaseCase")){
                Sorter<Integer> sorter = SorterFactory.getSorter(sortType,0,false,false,0);
                sorterMethod(sorter, reader(args[2]));
            }
            //Presorted test
        else if(args[1].equals("Presort")) {
            if (args[3].equals("Adaptive")){
                
                int cutoff = Integer.parseInt(args[4]);
                Sorter<Integer> sorter = SorterFactory.getSorter(sortType,cutoff,true,false,0);
                sorterMethod(sorter, reader(args[2]));
            }else{
                int cutoff = Integer.parseInt(args[4]);
                Sorter<Integer> sorter = SorterFactory.getSorter(sortType,cutoff,false,false,0);
                sorterMethod(sorter, reader(args[2]));
            }
                }
            //Test for cutoff values
        else if(args[1].equals("Cutoff")){
                int cutoff = Integer.parseInt(args[3]);
                Sorter<Integer> sorter = SorterFactory.getSorter(sortType,cutoff,false,false,0);
                sorterMethod(sorter, reader(args[2]));
            }
        else if(args[1].equals("parallelTesting")){
            ParallelRunner runner = new ParallelRunner();
            if(args[2].equals("ParallelCutoff100k")){
                runner.benchmarkParallelCutoffThresholdsSize100k();
            }
            if(args[2].equals("ParallelCutoff1M")){
                runner.benchmarkParallelCutoffThresholdsSize1M();
            }
            if(args[2].equals("ParallelCutoff10M")){
                runner.benchmarkParallelCutoffThresholdsSize10M();
            }

        }
        else if (args[1].equals("ThreadScaling")){
            if(args[2].equals("threadScaling100K")){
                ParallelRunner runner = new ParallelRunner();
                runner.testThreadScaling100K();
            } else if(args[2].equals("threadScaling1M")){
                ParallelRunner runner = new ParallelRunner();
                runner.testThreadScaling1M();
            } else if(args[2].equals("threadScaling10M")){
                ParallelRunner runner = new ParallelRunner();
                runner.testThreadScaling10M();
            }

        }

    }


    public static Object[] reader(String inputType) throws IOException {
        // Scanner scanner = new Scanner(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine());

        if(inputType.equals("INTEGERS")){
                String[] stringArray = reader.readLine().split(" ");
                return Arrays.stream(stringArray)
                        .map(Integer::parseInt)
                        .toArray(Integer[]::new);

        }else if(inputType.equals("OBJECT")){

                ObjectClass[] arr = new ObjectClass[n];
                String[] strings = reader.readLine().split(" ");
                for(int i = 0; i<n; i++){
                    arr[i] = new ObjectClass(strings[i]);
                }
            return arr;
        }else{

            return reader.readLine().split(" ");

        }
    }

    public static <T extends Comparable<T>> void sorterMethod(Sorter sorter, Object[] arr){
                Long start = System.nanoTime();
                int comp = sorter.sort((Comparable[]) arr);
                Long end = System.nanoTime();
                System.out.println(arr.length + " " + (end-start)/1_000_000_000.0 + " " + comp);
                // System.out.println("Array type: " + arr.getClass().getComponentType());
    }
}
