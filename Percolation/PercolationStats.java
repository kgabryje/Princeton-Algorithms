import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] results;

    public PercolationStats(int n, int trials) {
        Percolation p;
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        results = new double[trials];
        for (int i = 0; i < trials; i++) {
            p = new Percolation(n);
            while (!p.percolates()) {
                p.open(StdRandom.uniform(1, n+1), StdRandom.uniform(1, n+1));
            }
            results[i] = (double) p.numberOfOpenSites()/(double) (n*n);
        }
    }  // perform trials independent experiments on an n-by-n grid

    public double mean() {
        return StdStats.mean(results);
    }                         // sample mean of percolation threshold

    public double stddev() {
        return StdStats.stddev(results);
    }                       // sample standard deviation of percolation threshold

    public double confidenceLo() {
        return mean() - 1.96*stddev()/Math.sqrt(results.length);
    }                 // low  endpoint of 95% confidence interval

    public double confidenceHi() {
        return mean() + 1.96*stddev()/Math.sqrt(results.length);
    }                 // high endpoint of 95% confidence interval

    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean = " + ps.mean());
        System.out.println("stddev = " + ps.stddev());
        System.out.println("95% confidence interval = [" + ps.confidenceLo() +", " + ps.confidenceHi() + "]");
    }        // test client (described below)
}
