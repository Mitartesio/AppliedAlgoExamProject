package SortingVariations.Util;

public class TwoSequenceSelect {

    public static <T extends Comparable<T>> int[] twoSequenceSelect(T[] a, T[] b, int k) {
        int[] aiBi = new int[2];
        int low = Math.max(0, k - b.length);

        int high = Math.min(k, a.length);

        while (low < high) {
            int ja = (low + high) / 2;

            int jb = k - ja;

            T leftA = (ja > 0) ? a[ja - 1] : null;
            T rightA = (ja < a.length) ? a[ja] : null;
            T leftB = (jb > 0) ? b[jb - 1] : null;

            T rightB = (jb < b.length) ? b[jb] : null;

                if ((leftA == null || rightB == null || leftA.compareTo(rightB) <= 0) &&
                        (leftB == null || rightA == null || leftB.compareTo(rightA) < 0)) {
                    aiBi[0] = ja;
                    aiBi[1] = jb;
                    return aiBi;
                }

            if (leftA != null && rightB != null && leftA.compareTo(rightB) > 0) {
                high = ja;

            } else {
                low = ja + 1;
            }
        }

        aiBi[0] = low;
        aiBi[1] = k - low;

        return aiBi;

    }



}
