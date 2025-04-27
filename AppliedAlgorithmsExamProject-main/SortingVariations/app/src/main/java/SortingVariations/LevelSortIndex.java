package SortingVariations;

import java.util.Stack;

public class LevelSortIndex<T extends Comparable<T>> implements Sorter<T> {
    private int counter = 0;
    private int cutoff;
    private boolean adaptive;

    public LevelSortIndex(int cutoff, boolean adaptive) {
        this.cutoff = cutoff;
        this.adaptive = adaptive;
    }

    @Override
    public Integer sort(T[] a) {

        if(a.length == 0){
            return 0;
        }

        T[] aux = a.clone();
        counter = 0;

        Stack<Integer[]> stack = new Stack<>();
        Stack<Integer> stackLvl = new Stack<>(); 
        int i = 0;

        Integer[] run = new Integer[2];
        run[0] = 0;
        int y = validateSequence(run, i, cutoff, adaptive, a);
        i = y;
        run[1] = i-1; 
        while (i < a.length) {
            Integer[] nextRun = new Integer[2];
            nextRun[0] = i;

            int k = validateSequence(nextRun, i, cutoff, adaptive, a);
            i = k;
            nextRun[1]= i-1;

            int lvl = computeLevel(run[0], nextRun[0]-1, nextRun[1]+1);

            // Continue merging based on level size [run 1 on stack, run, nextRun]
            while (!stack.isEmpty()) {
                int topRunLvl = stackLvl.peek();
                assert topRunLvl != lvl;
                if (topRunLvl < lvl) { // i.e run1 (4) <= run(6)
                    Integer[] topRun = stack.pop(); //run 1
                    stackLvl.pop(); // 4
                    merge(a, aux, topRun[0], topRun[1], run[1]); //run 1 and run
                    run[0] = topRun[0]; // start of run = run1
                    
                } else {
                    break;
                }
            }
            
            stack.add(run);
            
            stackLvl.add(lvl);
            run = nextRun;
        }
        stack.add(run);//add last stack before merging last runs
        // Final merging phase
        while (stack.size() > 1) {
            Integer[] newRun = stack.pop();
            merge(a, aux, stack.peek()[0], newRun[0]-1, newRun[1]);
            stack.peek()[1] = newRun[1];
        }
        return counter;
    }

    private int findSequence(int i, T[] a) {
        int j = i;
        if (j == a.length - 1) {
            return 1;
        }
        if (a[j].compareTo(a[j + 1]) <= 0) { //ascending sequence
            j++;
            counter++;
            while (j < a.length - 1) {
                counter++;
                if (a[j].compareTo(a[j + 1]) <= 0) {
                    j++;
                } else {
                    j++;
                    break;
                }
            }
            if (j == a.length - 1 && a[j - 1].compareTo(a[j]) <= 0) {
                j++;
            }
        } else { //descending sequence
            j++;
            counter++;
            while (j < a.length - 1) {
                counter++;
                if (a[j].compareTo(a[j + 1]) > 0) {
                    j++;
                } else {
                    j++;
                    break;
                }
            }
            if (j == a.length - 1 && a[j - 1].compareTo(a[j]) > 0) {
                j++;
            }
            if (j >= cutoff) { //reverse descending sequence to ascending
                for (int k = 0; k < (j - i) / 2; k++) {
                    T smallElm = a[i + k];
                    a[i + k] = a[j - k - 1];
                    a[j - k - 1] = smallElm;
                }
            }
        }

        return j - i;

    }

    private int validateSequence(Integer[] run, int i, int cutoff, boolean adaptive, T[] a) {
        // Determine run length
            if (adaptive) {
                int sequence = findSequence(i, a);
                if (sequence >= cutoff) {
                    i += sequence - 1;
                } else {
                    if (i + cutoff >= a.length) {
                        insertionSort(a, i, a.length-1);
                        i = a.length-1;
                    } else {
                        insertionSort(a, i, i + cutoff - 1);
                        i += cutoff - 1;
                    }
                }
            } else {
                if (i + cutoff >= a.length) {
                    insertionSort(a, i, a.length-1);
                    i = a.length-1;
                } else {
                    insertionSort(a, i, i + cutoff - 1);
                    i += cutoff - 1;
                }
            }
            i++;
            return i;
    }

    private int computeLevel(int ia, int ib, int ic) {
        /* 
         * Takes 3 int arguments ia, ib and ic.
         * returns an int level
        */
        long ml = (ia + ib)/2; 
        long mr = (ib + ic)/2;

        long xor = ml ^ mr; // find the differing parts

        int level = 64 - Long.numberOfLeadingZeros(xor);

        return level;
    }

    private void merge(T[] a, T[] aux, int low, int mid, int high) {
        for (int k = low; k <= high; k++) {
            aux[k] = a[k];
        }

        int i = low;
        int j = mid + 1;

        for (int k = low; k <= high; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > high) {
                a[k] = aux[i++];
            } else if ((aux[j].compareTo(aux[i])) < 0) {
                a[k] = aux[j++];
                counter++;
            } else {
                a[k] = aux[i++];
                counter++;
            }
        }
    }

    private void insertionSort(T[] a, int ia, int ic) {
        for (int i = ia + 1; i <= ic; i++) {
            T key = a[i];
            int j = i - 1;

            while (j >= ia && a[j].compareTo(key) > 0) {
                counter++; // Count comparison
                a[j + 1] = a[j];
                j--;
            }

            if (j >= ia) {
                counter++; // Count final comparison
            }

            a[j + 1] = key;
        }
    }
}
