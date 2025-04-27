package SortingVariations.Util;

public class StableTestClass implements Comparable<StableTestClass> {
    private int comparable;
    private int tester;

    public StableTestClass(int comparable, int tester){
        this.comparable = comparable;
        this.tester = tester;
    }

    public int getComparable() {
        return comparable;
    }

    public int getTester() {
        return tester;
    }

    @Override
    public int compareTo(StableTestClass s){
        return s.getComparable() - this.comparable;
    }

}
