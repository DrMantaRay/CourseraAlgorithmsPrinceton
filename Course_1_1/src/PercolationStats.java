import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] trialResults;
    private int trials;
    public PercolationStats(int n, int trials) {
        double nSquared = n*n;
        this.trials = trials;
        trialResults = new double[this.trials];
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials cannot be less than or equal to zero");
        } else {

            while (trials > 0) {
                Percolation perc = new Percolation(n);
                double numTrials = 0;
                while (!perc.percolates()) {
                    int temp = StdRandom.uniform(n*n);
                    if (!perc.isOpen(temp/n+1, temp % n+1)) {
                        perc.open(temp/n+1, temp % n+1);
                        numTrials++;
                    }
                }
                trials--;
                trialResults[trials] = numTrials/(nSquared);

            }
        }
    }
    public double mean() { return StdStats.mean(trialResults); }
    public double stddev() { return StdStats.stddev(trialResults); }
    public double confidenceLo() { return mean()-1.96*stddev()/Math.sqrt(this.trials); }
    public double confidenceHi() { return mean()+1.96*stddev()/Math.sqrt(this.trials); }
    public static void main(String[] args){
        PercolationStats percExperiment=new PercolationStats(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
        System.out.println(percExperiment.trials);
        System.out.println("mean = " +percExperiment.mean());
        System.out.println("stddev = "+percExperiment.stddev());
        System.out.println("95% confidence interval = ["+percExperiment.confidenceLo()+","+
        percExperiment.confidenceHi()+"]");
    }
}
