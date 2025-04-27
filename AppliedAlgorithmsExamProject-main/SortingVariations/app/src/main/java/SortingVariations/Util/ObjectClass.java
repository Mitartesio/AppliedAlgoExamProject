package SortingVariations.Util;

//Class for testing in experiment 2
public class ObjectClass implements Comparable<ObjectClass> {
    private String comp;
    private boolean bool;
    private int integer;
    private int integer2;
    private int integer3;
    private String comp2;


    public ObjectClass(String comp){
        this.comp = comp;
        this.bool = comp.length() % 2 == 0;
        this.integer = comp.length();
        this.integer2 = comp.length()*2;
        this.integer3 = comp.length()*3;
        this.comp2 = "I Love Algorithms";
    }

    @Override
    public int compareTo(ObjectClass other) {
        return this.comp.compareTo(other.comp);
    }

}
