import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.locks.StampedLock;

/**
 * Created by Meir on 8/31/2015.
 */
public class KenKenSolver {

    public static int size;

    // the data structure which will store the actual KenKen puzzle
    public static int[][] grid;

    public static int numberOfRegions;

    // holds information telling which region each square belongs to
    public static int[][] regionGrid;

    // tells which number each region has to sum/multiply/etc. to
    public static int[] goalNumberList;

    // tells which operation each region must use to reach its goal number
    public static String[] goalOperationList;

    public static int[] firstEmptySquare() {
        int size = grid.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == 0) {
                    int[] ret = {i, j};
                    return ret;
                }
            }
        }
        return null;
    }

    public static boolean solveKenKen() {
        // recursively solveKenKens the puzzle using backtracking
        int[] square = firstEmptySquare();
        if (square != null) {
            for (int i = 1; i <= size; i++) {
                if (safeToPlace(i, square)) {
                    grid[square[0]][square[1]] = i;
                    if (solveKenKen()) {
                        return true;
                    }
                    grid[square[0]][square[1]] = 0;
                }
            }
        } else {
            return true;
        }
        return false;
    }

    public static int regionNumberOf(int[] square) {
        int row = square[0];
        int col = square[1];
        return regionGrid[row][col];
    }
    public static ArrayList<Integer> numbersInRegionOf(int[] square) {
        int row = square[0];
        int col = square[1];
        int regionNumber = regionNumberOf(square);
        ArrayList<Integer> ret = new ArrayList<Integer>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (regionGrid[i][j] == regionNumber) {
                    ret.add(grid[i][j]);
                }
            }
        }
        return ret;
    }

    public static int[] rowAt(int y) {
        return grid[y].clone();
    }

    public static int[] columnAt(int x) {
        int[] column = new int[size];
        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            column[rowIndex] = grid[rowIndex][x];
        }
        return column;
    }

    public static boolean meetsGoal(int[] numbersInRegion, int goalNumber, String goalString) {
        // goalString is the operation (+, -, *, or /)
        if (GL.contains(numbersInRegion, 0)) {
            return false; // region is not full
        }
        if (goalString.equals("+")) {
            return (GL.arraySum(numbersInRegion) == goalNumber);
        }
        if (goalString.equals("*")) {
            return (GL.arrayProduct(numbersInRegion) == goalNumber);
        }
        if (goalString.equals("-")) {
            assert numbersInRegion.length == 2;
            return (Math.max(numbersInRegion[0], numbersInRegion[1]) - Math.min(numbersInRegion[0],numbersInRegion[1]) == goalNumber);
        }
        if (goalString.equals("/")) {
            assert numbersInRegion.length == 2;
            return ((double)Math.max(numbersInRegion[0], numbersInRegion[1]) /
                    (double)Math.min(numbersInRegion[0],numbersInRegion[1])
                    == goalNumber);
        }
        assert false; // the function should have returned already; throw an error
        return false;
    }

    public static boolean safeToPlace(int n, int[] square) {
        // returns whether it's safe to place 'n' in a given square
        int row = square[0];
        int column = square[1];
        if ((GL.contains(rowAt(row), n) || GL.contains(columnAt(column), n))) {
            return false;
        }
        int regionNumber = regionNumberOf(square);
        ArrayList<Integer> numbersInRegion = numbersInRegionOf(square);
        if (GL.count(numbersInRegion, 0) == 1) {
            numbersInRegion.remove((Integer)0);
            // the (Integer) is necessary so that it makes 0 an object instead of a primitive
            // this causes 'remove' to take out the 0, rather than the int at the first index
            numbersInRegion.add(n);
            return meetsGoal(GL.convertToIntArray(numbersInRegion),
                    goalNumberList[regionNumber],
                    goalOperationList[regionNumber]);
        }
        return true;
    }

    public static void main(String[] args) {
        // first read the file
        Scanner sc = null;
        String fileName = "";
        if (args.length > 0) {
            fileName = args[0];
        } else {
            fileName = "myTestFile1.txt";
        }
        try {
            FileReader fr = new FileReader(fileName);
            sc = new Scanner(fr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        size = Integer.parseInt(sc.nextLine());
        grid = new int[size][size];
        numberOfRegions = Integer.parseInt(sc.nextLine());
        regionGrid = new int[size][size];
        for (int i = 0; i < size; i++) {
            regionGrid[i] = GL.intStringToIntArray(sc.nextLine());
        }
        goalNumberList = new int[numberOfRegions];
        goalOperationList = new String[numberOfRegions];
        for (int i = 0; i < numberOfRegions; i++) {
            String[] arr = GL.stringToStringArray(sc.nextLine());
            goalOperationList[i] = arr[1];
            goalNumberList[i] = Integer.parseInt(arr[2]);
        }
        sc.close();
        // now solve the puzzle!
        long startTime = System.nanoTime();
        solveKenKen();
        long endTime = System.nanoTime();
        double duration = (endTime-startTime)/1000000000.0;
        System.out.println("In " + duration + " seconds:");
        System.out.println();
        GL.printIntArray2D(grid);
    }
}