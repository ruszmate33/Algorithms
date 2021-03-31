/* *****************************************************************************
 *  Name:              Mate Rusz
 *  Coursera User ID:  123456
 *  Last modified:     28/3/2021
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int gridSize;
    private int[][] grid;
    private WeightedQuickUnionUF wquf;
    private int numOpen = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("grid must be at least 1 cells");
        }
        gridSize = n;
        grid = new int[n + 1][n + 1];
        wquf = new WeightedQuickUnionUF(n * n + 2);

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                grid[i][j] = 0;
                if (i == 1) {
                    wquf.union(getIDfromGrid(i, j), 0); // virtualTop: 0
                }
                if (i == n) {
                    wquf.union(getIDfromGrid(i, j), n * n + 1); // virtButton
                }
            }
        }
    }

    public int getIDfromGrid(int row, int col) {
        return (row - 1) * gridSize + col;
    }

    public int getSize() {
        return gridSize;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            grid[row][col] = 1;
            numOpen++;

            // if open connect them to newly opened site
            int newlyOpenID = getIDfromGrid(row, col);
            connectIfValid(newlyOpenID, row - 1, col);
            connectIfValid(newlyOpenID, row + 1, col);
            connectIfValid(newlyOpenID, row, col - 1);
            connectIfValid(newlyOpenID, row, col + 1);
        }
    }

    private void connectIfValid(int newlyOpenID, int row, int col) {
        if (isValidGrid(row, col) && isOpen(row, col)) {
            int adjacentID = getIDfromGrid(row, col);
            wquf.union(newlyOpenID, adjacentID);
        }
    }

    private boolean isValidGrid(int row, int col) {
        return (row > 0 && row <= gridSize) && (col > 0 && col <= gridSize);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return (grid[row][col] == 1);
        // return (grid[row][col] == 1 && !isConnected(row, col));
    }

    private boolean isConnected(int row, int col) {
        int idx = getIDfromGrid(row, col);
        return wquf.find(0) == wquf.find(idx);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return (grid[row][col] == 1 && !isConnected(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return (wquf.find(0) == wquf.find(gridSize * gridSize + 1));
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        Percolation percolation = new Percolation(n);
        while (!percolation.percolates()) {
            int randomRow = StdRandom.uniform(1, n + 1);
            int randomCol = StdRandom.uniform(1, n + 1);
            percolation.open(randomRow, randomCol);
        }
    }
}
