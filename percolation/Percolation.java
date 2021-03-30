/* *****************************************************************************
 *  Name:              Mate Rusz
 *  Coursera User ID:  123456
 *  Last modified:     28/3/2021
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int gridSize;
    private int[][] grid;
    private WeightedQuickUnionUF wquf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("grid must be at least 1 cells");
        }
        gridSize = n;
        grid = new int[n + 1][n + 1];
        wquf = new WeightedQuickUnionUF(n * n);

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                grid[i][j] = 0;
            }
        }
    }

    public int getIDfromGrid(int row, int col) {
        return (row - 1) * gridSize + (col - 1);
    }

    public int getSize() {
        return gridSize;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isFull(row, col)) {
            grid[row][col] = 1;

            // check all adjacent squares if they are open
            int[] adjacentRows = getAdjacents(row);
            int[] adjacentCols = getAdjacents(col);
            // if open connect them to newly opened site
            int newlyOpenID = getIDfromGrid(row, col);
            for (int adjRow : adjacentRows) {
                if (isOpen(adjRow, col)) {
                    int adjacentID = getIDfromGrid(adjRow, col);
                    // connection function
                    wquf.union(newlyOpenID, adjacentID);
                }
            }
            for (int adjCol : adjacentCols) {
                if (isOpen(row, adjCol)) {
                    int adjacentID = getIDfromGrid(row, adjCol);
                    wquf.union(newlyOpenID, adjacentID);
                }
            }
        }
    }

    public int[] getAdjacents(int current) {
        int[] adjacent;
        if (current > 1 && current < gridSize) {
            adjacent = new int[] { current - 1, current + 1 };
        }
        else if (current == 1) {
            adjacent = new int[] { current + 1 };
        }
        // current == gridSize
        else {
            adjacent = new int[] { current - 1 };
        }
        return adjacent;
    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return (grid[row][col] == 1);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return (!isOpen(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int numOpen = 0;
        for (int i = 0; i <= gridSize; i++) {
            for (int j = 0; j <= gridSize; j++) {
                numOpen += grid[i][j];
            }
        }
        return numOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        // for cells in the lowest row
        for (int i = 1; i <= gridSize; i++) {
            // if cells if open
            if (isOpen(gridSize, i)) {
                int lowerRowID = getIDfromGrid(gridSize, i);
                int rootLower = wquf.find(lowerRowID);
                for (int j = 1; j <= gridSize; j++) {
                    if (isOpen(1, j)) {
                        int upperRowID = getIDfromGrid(1, j);
                        return (wquf.find(upperRowID) == rootLower);
                    }
                }
            }
        }
        return false;
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        Percolation percolation = new Percolation(n);
        while (!percolation.percolates()) {
            int randomRow = percolation.getRandomNumber(1, n + 1);
            int randomCol = percolation.getRandomNumber(1, n + 1);
            if (percolation.isFull(randomRow, randomCol)) {
                percolation.open(randomRow, randomCol);
            }
        }
        // return percolation.numberOfOpenSites();
    }
}
