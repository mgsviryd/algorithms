import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double FACTOR_CONFIDENCE = 1.96;
    private double[] fractions;
    private int trials;

    public PercolationStats(final int n, final int t) {
        if (n <= 0 || t <= 0) {
            throw new IllegalArgumentException(
                    "Parameters of constructor are less or equal 0.");
        }
        trials = t;
        fractions = new double[trials];
        for (int iTrial = 0; iTrial < trials; iTrial++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int i = StdRandom.uniform(1, n + 1);
                int j = StdRandom.uniform(1, n + 1);
                percolation.open(i, j);
            }
            int nOpenSites = percolation.numberOfOpenSites();
            int nSites = n * n;
            double fraction = (double) nOpenSites / nSites;
            fractions[iTrial] = fraction;
        }
    }

    public double mean() {
        return StdStats.mean(fractions);
    }

    public double stddev() {
        return StdStats.stddev(fractions);
    }

    public double confidenceLo() {
        return mean() - ((FACTOR_CONFIDENCE * stddev()) / Math.sqrt(trials));
    }

    public double confidenceHi() {
        return mean() + ((FACTOR_CONFIDENCE * stddev()) / Math.sqrt(trials));
    }

    public static void main(final String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, trials);
        String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = [" + confidence + "]");
    }
}