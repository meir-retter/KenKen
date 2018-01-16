import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meir on 8/31/2015.
 */
public class GL {

    // general library of functions

    public static int arraySum(int[] ar) {
        // returns the sum of an array
        int ret = 0; // additive identity
        for (int a : ar) {
            ret += a;
        }
        return ret;
    }
    public static int arrayProduct(int[] ar) {
        // returns the cumulative product of an array
        int ret = 1; // multiplicative identity
        for (int a : ar) {
            ret *= a;
        }
        return ret;
    }
    public static boolean contains(int[] arr, int n) {
        // returns whether the array 'arr' contains the int 'n'
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == n) {
                return true;
            }
        }
        return false;
    }

    public static int[] intStringToIntArray(String s) {
        // takes a string such as "4 2 10 4 8"
        // converts it to {4, 2, 10, 4, 8}
        ArrayList<Integer> ret0 = new ArrayList<Integer>();
        int pointer = 0;
        String intString = "";
        String ds;
        while (pointer < s.length()) {
            ds = s.substring(pointer, pointer+1);
            if ("0123456789".contains(ds)) {
                intString += ds;
            } else {
                ret0.add(Integer.parseInt(intString));
                intString = "";
            }
            pointer++;
        }
        if (intString != "") {
            ret0.add(Integer.parseInt(intString));
        }
        return convertToIntArray(ret0);
    }

    public static String[] stringToStringArray(String s) {
        // takes a string such as "4 + 10"
        // converts it to {"4", "+", "10"}
        // input string should contain only numeral and operation characters
        ArrayList<String> ret0 = new ArrayList<String>();
        int pointer = 0;
        String stringOrOperation = "";
        String ds;
        while (pointer < s.length()) {
            ds = s.substring(pointer, pointer+1);
            if ("0123456789+-*/".contains(ds)) {
                stringOrOperation += ds;
            } else {
                ret0.add(stringOrOperation);
                stringOrOperation = "";
            }
            pointer++;
        }
        if (stringOrOperation != "") {
            ret0.add(stringOrOperation);
        }
        return convertToStringArray(ret0);
    }


    public static int[] convertToIntArray(ArrayList<Integer> ar) {
        // converts an ArrayList<Integer> into an int[]
        int[] ret = new int[ar.size()];
        for (int i = 0; i < ar.size(); i++) {
            ret[i] = ar.get(i);
        }
        return ret;
    }

    public static String[] convertToStringArray(ArrayList<String> ar) {
        // converts an ArrayList<String> into a String[]
        String[] ret = new String[ar.size()];
        for (int i = 0; i < ar.size(); i++) {
            ret[i] = ar.get(i);
        }
        return ret;
    }

    public static int count(ArrayList<Integer> arr, int i) {
        // returns the number of times the int 'i' appears in the ArrayList<Integer> 'arr'
        int ret = 0;
        for (int n : arr) {
            if (n == i) {
                ret++;
            }
        }
        return ret;
    }


    public static int max(int[] arr) {
        // returns the maximum value in an array
        int maxSoFar = arr[0];
        for (int n : arr) {
            if (n > maxSoFar) {
                maxSoFar = n;
            }
        }
        return maxSoFar;
    }

    public static boolean areAllUnique(ArrayList<Integer> arr) {
        // returns whether 'arr' contains only distinct elements
        ArrayList<Integer> newArr = new ArrayList<Integer>();
        for (int i = 0; i < arr.size(); i++) {
            int n = arr.get(i);
            if (!newArr.contains(n)) {
                newArr.add(n);
            } else {
                return false;
            }
        }
        return true;
    }

    public static void printIntArray(int[] ar) {
        // prints out an int array
        for (int i = 0; i < ar.length-1; i++) {
            System.out.print(ar[i]+" ");
        }
        System.out.println(ar[ar.length-1]);
    }

    public static void printStringArray(String[] ar) {
        // prints out a String array
        for (int i = 0; i < ar.length-1; i++) {
            System.out.print(ar[i]+" ");
        }
        System.out.println(ar[ar.length-1]);
    }

    public static void printIntArray2D(int[][] arr) {
        // prints out a 2D array in grid format
        for (int[] ar : arr) {
            printIntArray(ar);
        }
    }

}

