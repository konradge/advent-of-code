package Utils.IO;

public class ArrayUtils {
    public static int[] concat(int[] arr1, int[] arr2) {
        int[] res = new int[arr1.length + arr2.length];

        for (int i = 0; i < arr1.length; i++) {
            res[i] = arr1[i];
        }

        for (int i = 0; i < arr2.length; i++) {
            res[i + arr1.length] = arr2[i];
        }

        return res;
    }
}
