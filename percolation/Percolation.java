/* *****************************************************************************
 *  Name:              Mate Rusz
 *  Coursera User ID:  123456
 *  Last modified:     28/3/2021
 **************************************************************************** */

public class Percolation {
    private int gridSize;
    private int[][] grid;
    private int[] roots;
    private int[] size;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("grid must be at least 1 cells");
        }
        gridSize = n;
        grid = new int[n + 1][n + 1];
        roots = new int[n * n];
        size = new int[n * n];

        int id = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                grid[i][j] = 0;
                roots[id] = id;
                size[id] = 1;
                id++;
            }
        }

    }

    public int getIDfromGrid(int row, int col) {
        return (row - 1) * gridSize + (col - 1);
    }

    public int getRoot(int row, int col) {
        // System.out.println(" getRoot row: " + row + " col: " + col);
        int idx = getIDfromGrid(row, col);
        // System.out.println("getRoot idx: " + idx);
        while (idx != roots[idx]) {
            roots[idx] = roots[roots[idx]];
            idx = roots[idx];
        }
        return idx;
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
            for (int adjRow : adjacentRows) {
                // connection function
                union(adjRow, col, row, col);
            }
            for (int adjCol : adjacentCols) {
                union(row, adjCol, row, col);
            }
        }
    }

    public void union(int row, int col, int row2, int col2) {
        // union function
        if (isOpen(row, col)) {
            // make the connection
            int root1 = getRoot(row, col);
            int root2 = getRoot(row2, col2);
            if (root1 == root2) return;
            if (size[root1] < size[root2]) {
                roots[root1] = root2;
                size[root2] += size[root1];
            }
            else {
                roots[root2] = root1;
                size[root1] += size[root2];
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

    // are two cells connected
    private boolean find(int row1, int col1, int row2, int col2) {
        int rootID1 = getRoot(row1, col1);
        int rootID2 = getRoot(row2, col2);
        return (rootID1 == rootID2);
    }

    // does the system percolate?
    public boolean percolates() {
        // for cells in the lowest row
        for (int i = 1; i <= gridSize; i++) {
            // if cells if open
            if (isOpen(gridSize, i)) {
                for (int j = 1; j <= gridSize; j++) {
                    if (isOpen(1, j)) {
                        return find(gridSize, i, 1, j);
                    }
                }
            }
        }

        // else return false
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
