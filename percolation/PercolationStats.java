/* *****************************************************************************
 *  Name:              Mate Rusz
 *  Coursera User ID:  123456
 *  Last modified:     28/03/221
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int gridSize;
    private int numTrials;
    private double[] openFracture;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0) {
            throw new IllegalArgumentException("grid must be at least 1 cells");
        }
        if (trials <= 0) {
            throw new IllegalArgumentException("you need more than 0 trials");
        }
        gridSize = n;
        numTrials = trials;
        openFracture = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int randomRow = StdRandom.uniform(1, n + 1);
                int randomCol = StdRandom.uniform(1, n + 1);
                percolation.open(randomRow, randomCol);
            }
            int opens = percolation.numberOfOpenSites();
            openFracture[i] = (double) opens / (gridSize * gridSize);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(openFracture);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(openFracture);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (mean() - 1.96 * stddev() / Math.sqrt(numTrials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean() + 1.96 * stddev() / Math.sqrt(numTrials));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, trials);
        System.out.println("mean                    = " + ps.mean());
        System.out.println("stdev                   = " + ps.stddev());
        System.out.println(
                "95% confidence interval = [ " + ps.confidenceLo() + ", " + ps.confidenceHi()
                        + "]");
    }
}
