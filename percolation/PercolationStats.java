/* *****************************************************************************
 *  Name:              Mate Rusz
 *  Coursera User ID:  123456
 *  Last modified:     28/03/221
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int gridSize;
    final int numTrials;
    final double[] openFraction;
    final double confConst;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("grid must be at least 1 cells");
        }
        gridSize = n;
        numTrials = trials;
        openFraction = new double[trials];
        confConst = 1.96;
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int randomRow = StdRandom.uniform(1, n + 1);
                int randomCol = StdRandom.uniform(1, n + 1);
                percolation.open(randomRow, randomCol);
            }
            int opens = percolation.numberOfOpenSites();
            openFraction[i] = (double) opens / (gridSize * gridSize);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(openFraction);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (openFraction.length == 1) return Double.NaN;
        return StdStats.stddev(openFraction);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (mean() - confConst * stddev() / Math.sqrt(numTrials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean() + confConst * stddev() / Math.sqrt(numTrials));
    }

    // test client (see below)
    public static void main(String[] args) {
        // Stopwatch stopwatch = new Stopwatch();
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, trials);
        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                   = " + ps.stddev());
        System.out.println(
                "95% confidence interval = [ " + ps.confidenceLo() + ", " + ps.confidenceHi()
                        + "]");
        // System.out.println(stopwatch.elapsedTime());
    }
}
