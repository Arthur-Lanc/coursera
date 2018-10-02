import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int trials;
    private Percolation pcl;
    private double[] thresholdArr;
    private double meanValue;
    private double stdValue;
    
   public PercolationStats(int n, int trials) {
       if (n <= 0 || trials <= 0) {
           throw new IllegalArgumentException();
       }
       
       thresholdArr = new double[trials];
       this.trials = trials;
       
       for (int i=0; i<trials; i++) {
           pcl = new Percolation(n);
           while(true) {
               int row = StdRandom.uniform(1,n+1);
               int col = StdRandom.uniform(1,n+1);
               pcl.open(row, col);
               if (pcl.percolates()){
                   break;
               }
           }
           double x = ((double)pcl.numberOfOpenSites())/(n*n);
           thresholdArr[i] = x;
       }
   }
   
   public double mean() {
       meanValue = StdStats.mean(thresholdArr);
       return meanValue;
   }
   
   public double stddev() {
       stdValue = StdStats.stddev(thresholdArr);
       return stdValue;
   }
   
   public double confidenceLo() {
       return StdStats.mean(thresholdArr)-(1.96*StdStats.stddev(thresholdArr)/Math.sqrt(trials));
   }
   
   public double confidenceHi() {
       return StdStats.mean(thresholdArr)+(1.96*StdStats.stddev(thresholdArr)/Math.sqrt(trials));
   }

   public static void main(String[] args) {
       String arg1 = args[0];
       String arg2 = args[1];
       PercolationStats pcls = new PercolationStats(Integer.parseInt(arg1),Integer.parseInt(arg2));
       System.out.println("mean                    = "+pcls.mean());
       System.out.println("stddev                  = "+pcls.stddev());
       System.out.println("95% confidence interval = "+"["+pcls.confidenceLo()+", "+pcls.confidenceHi()+"]");
   }
}