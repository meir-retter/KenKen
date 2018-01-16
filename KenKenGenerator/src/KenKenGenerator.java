import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Meir on 9/6/2015.
 */
public class KenKenGenerator {

    public static int[][] regionGridGenerator(int size) {
        double P = 1.9;
        int counter = 1;
        int[][] ret = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0 && j == 0) {
                    ret[i][j] = counter;
                    counter++;
                } else if (i == 0 && j != 0) {
                    int left = ret[i][j - 1];
                    int leftCount = GL.count2D(ret, left);
                    double leftCutOff = 1.0 / Math.pow(P, leftCount);
                    Random random = new Random();
                    float r = random.nextFloat();
                    if (r <= leftCutOff) {
                        ret[i][j] = left;
                    } else if (r > leftCutOff) {
                        ret[i][j] = counter;
                        counter++;
                    }
                } else if (i != 0 && j == 0) {
                    int above = ret[i-1][j];
                    int aboveCount = GL.count2D(ret, above);
                    double aboveCutOff = 1.5 / Math.pow(P, aboveCount);
                    Random random = new Random();
                    float r = random.nextFloat();
                    if (r <= aboveCutOff) {
                        ret[i][j] = above;
                    } else if (r > aboveCutOff) {
                        ret[i][j] = counter;
                        counter++;
                    }
                } else if (i != 0 && j != 0) {
                    int left = ret[i][j-1];
                    int above = ret[i-1][j];
                    int leftCount = GL.count2D(ret, left);
                    int aboveCount = GL.count2D(ret, above);
                    double leftCutOff = 1.0/Math.pow(P, leftCount);
                    double aboveCutOff = leftCutOff + 1.5/Math.pow(P, aboveCount);
                    Random random = new Random();
                    float r = random.nextFloat();
                    if (r <= leftCutOff) {
                        ret[i][j] = left;
                    } else if (r > leftCutOff && r <= aboveCutOff) {
                        ret[i][j] = above;
                    } else if (r > aboveCutOff) {
                        ret[i][j] = counter;
                        counter++;
                    }
                }
            }
        }
        return arr2DMinus1(ret);
    }

    public static int[][] rowsSwapped(int[][] grid, int row1, int row2) {
        int size = grid.length;
        int[][] ret = new int[size][size];
        for (int i = 0; i < size; i++) {
            if (i == row1) {
                ret[i] = grid[row2].clone();
            } else if (i == row2) {
                ret[i] = grid[row1].clone();
            } else {
                ret[i] = grid[i].clone();
            }
        }
        return ret;
    }

    public static int[][] columnsSwapped(int[][] grid, int col1, int col2) {
        int size = grid.length;
        int[][] ret = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (j == col1) {
                    ret[i][j] = grid[i][col2];
                } else if (j == col2) {
                    ret[i][j] = grid[i][col1];
                } else {
                    ret[i][j] = grid[i][j];
                }
            }
        }
        return ret;
    }


    public static int[][] gridGenerator(int size) {
        // generates a solution grid, making sure there are no row/col clashes
        // first creates the trivial solution (all diagonals), then swaps columns and rows
        int[][] ret = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                ret[i][j] = ((j + i) % size) + 1;
            }
        }
        int iterations = 100;
        for (int iteration = 0; iteration < iterations; iteration++) {
            Random random = new Random();
            int row1 = random.nextInt(size);
            int row2 = random.nextInt(size);
            int col1 = random.nextInt(size);
            int col2 = random.nextInt(size);
            ret = rowsSwapped(ret, row1, row2);
            ret = columnsSwapped(ret, col1, col2);
        }
        return ret;
    }

    public static int[][] arr2DMinus1(int[][] arr) {
        // returns the same int[][], but with each int decremented by 1
        // this way we can design regions starting at 1, with the "empty" value being zero
        int[][] ret = new int[arr.length][arr.length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                ret[i][j] = arr[i][j] - 1;
            }
        }
        return ret;
    }

    public static int regionNumberOf(int[] square, int[][] regionGrid) {
        int row = square[0];
        int col = square[1];
        return regionGrid[row][col];
    }

    public static int[] numbersInRegionOf(int[] square, int[][] regionGrid, int[][] grid) {
        int size = grid.length;
        int row = square[0];
        int col = square[1];
        int regionNumber = regionNumberOf(square, regionGrid);
        ArrayList<Integer> ret = new ArrayList<Integer>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (regionGrid[i][j] == regionNumber) {
                    ret.add(grid[i][j]);
                }
            }
        }
        return GL.convertToIntArray(ret);
    }

    public static int numberOfRegions(int[][] regionGrid) {
        int size = regionGrid.length;
        int[] regionGrid1D = GL.arr2Dto1D(regionGrid);
        
        return GL.max(regionGrid1D) + 1;
    }

    public static int[] regionSizes(int[][] regionGrid) {
        int numRegions = numberOfRegions(regionGrid);
        int[] ret = new int[numRegions];
        for (int i = 0; i < numRegions; i++) {
            ret[i] = GL.count2D(regionGrid,i);
        }
        return ret;
    }

    public static int[] numbersInRegion(int regionNumber, int[][] regionGrid, int[][] grid) {
        int size = grid.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (regionGrid[i][j] == regionNumber) {
                    int[] square = new int[]{i,j};
                    return numbersInRegionOf(square, regionGrid, grid);
                }
            }
        }
        return null;
    }

    public static String[] goalOperatorListGenerator(int[][] regionGrid, int[][] grid) {
        int[] regionSizeList = regionSizes(regionGrid);
        int numRegions = regionSizeList.length;
        // for a 2-cage, make it likely to choose - or /
        String[] operators = new String[]{"+", "-", "*", "/", "/", "/"};
        String[] opsWithoutDiv = new String[]{"+", "-","-","-","*"};
        String[] addAndMult = new String[]{"+", "*"};
        String[] ret = new String[numRegions];
        for (int i = 0; i < numRegions; i++) {
            if (regionSizeList[i] == 1) {
                ret[i] = "+";
            } else if (regionSizeList[i] == 2) {
                int first = numbersInRegion(i, regionGrid, grid)[0];
                int second = numbersInRegion(i, regionGrid, grid)[1];
                if (Integer.max(first, second) / (double)Integer.min(first, second) % 1 == 0) {
                    ret[i] = GL.choice(operators);
                } else {
                    ret[i] = GL.choice(opsWithoutDiv);
                }
            } else {
                ret[i] = GL.choice(addAndMult);
            }
        }
        return ret;
    }

    public static long[] goalNumberListGenerator(int[][] regionGrid,
                                                int[][] grid,
                                                String[] goalOperators) {
        int size = grid.length;
        int numRegions = numberOfRegions(regionGrid);
        long[] ret = new long[numRegions];
        for (int regionNum = 0; regionNum < numRegions; regionNum++) {
            int[] numsInRegion = numbersInRegion(regionNum, regionGrid, grid);
            String goalOperator = goalOperators[regionNum];
            if (goalOperator.equals("+")) {
                ret[regionNum] = GL.arraySum(numsInRegion);
            } else if (goalOperator.equals("*")) {
                ret[regionNum] = GL.arrayProduct(numsInRegion);
            } else if (goalOperator.equals("-")) {
                ret[regionNum] = Math.abs(numsInRegion[0]-numsInRegion[1]);
            } else if (goalOperator.equals("/")) {
                int first = numsInRegion[0];
                int second = numsInRegion[1];
                ret[regionNum] = Integer.max(first, second) / Integer.min(first, second);
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        int size;
        if (args.length == 0) {
            size = 6;//GL.intInput("Enter a size: ");
        } else {
            size = Integer.parseInt(args[0]);
        }
        int[][] regionGrid = regionGridGenerator(size);
        int numRegions = numberOfRegions(regionGrid);
        int[][] grid = gridGenerator(size);
        String[] goalOperators = goalOperatorListGenerator(regionGrid, grid);
        long[] goalNumbers = goalNumberListGenerator(regionGrid, grid, goalOperators);
        System.out.println(size);
        System.out.println(numRegions);
        GL.printIntArray2D(regionGrid);
        for (int i = 0; i < numRegions; i++) {
            System.out.print(i + " ");
            System.out.print(goalOperators[i] + " ");
            System.out.println(goalNumbers[i]);
        }
//        // if you want to print out the solution as well
//        System.out.println("---SOLUTION (There may be more than one---");
//        GL.printIntArray2D(grid);



    }

}
