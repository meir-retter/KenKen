/**
 * Created by Meir on 9/6/2015.
 */
import java.util.*;


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

    public static int count2D(int[][] arr, int i) {
        // counts the number of times an int appears in a 2D arr
        int ret = 0;
        for (int[] ar : arr) {
            for (int a : ar) {
                if (a == i) {
                    ret++;
                }
            }
        }
        return ret;
    }

    public static String input(String instructions) {
        System.out.print(instructions);
        Scanner scanner = new Scanner(System.in);
        String ret = scanner.nextLine();
        return ret;
    }

    public static int intInput(String instructions) {
        System.out.print(instructions);
        Scanner scanner = new Scanner(System.in);
        int ret = scanner.nextInt();
        return ret;
    }

    public static String letterAtIndex(String string, int index) {
        return string.substring(index, index+1);
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


    public static List<Integer> merge(List<Integer> array1, List<Integer> array2) {
        List<Integer> arr1 = new ArrayList<Integer>();
        List<Integer> arr2 = new ArrayList<Integer>();
        arr1.addAll(array1);
        arr2.addAll(array2);
        List<Integer> ret = new ArrayList<Integer>();
        while (arr1.size() > 0 && arr2.size() > 0) {
            if (arr1.get(0) <= arr2.get(0)) {
                ret.add(arr1.get(0));
                arr1 = arr1.subList(1,arr1.size());
            } else {
                ret.add(arr2.get(0));
                arr2 = arr2.subList(1,arr2.size());
            }
        }
        ret.addAll(arr1);
        ret.addAll(arr2);
        return ret;
    }

    public static List mergeSort(List<Integer> arr) {
        if (arr.size() == 1) {
            return arr;
        } else {
            List<Integer> firstHalf = arr.subList(0,arr.size() / 2);
            List<Integer> secondHalf = arr.subList(arr.size() / 2, arr.size());
            return merge(mergeSort(firstHalf), mergeSort(secondHalf));
        }
    }

    public static String choice(String[] strings) {
        Random random = new Random();
        return strings[random.nextInt(strings.length)];
    }

    public static int choice(int[] ar) {
        Random random = new Random();
        return ar[random.nextInt(ar.length)];
    }

    public static void print() {
        System.out.println();
    }

    public static int[] arr2Dto1D(int[][] arr) {
        // collapses a 2D array into a 1D array
        int size = arr.length;
        int[] ar = new int[size*size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                ar[i*size + j] = arr[i][j];
            }
        }
        return ar;
    }
}
