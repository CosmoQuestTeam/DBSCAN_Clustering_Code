/*******************************************************************************
Purpose: A math omnibus file that calculates the standard deviations and
    weighted values of X, Y, D, and C (C is used for weights, where these are
    defined in Point.java).
*******************************************************************************/

package dbscan;
import java.util.*;


public class MathUtility {
	
	public static Float weightedMean_x;
	public static Float weightedMean_y;
	public static Float weightedMean_d;
    
    public static float StandardDeviationX (List<Point> cluster) 
    {
        // sd is sqrt of sum of (values-mean) squared divided by n - 1
        // Calculate the mean
        float mean = 0;
        final int n = cluster.size();
        if ( n < 2 )
        {
            return mean;
        }
        
        Iterator<Point> j = cluster.iterator();
        
        while (j.hasNext()) {
            Point w = j.next();
            mean += w.getX();
        }
        
        mean /= n;
        // calculate the sum of squares
        float sum = 0;
        
        Iterator<Point> k = cluster.iterator();
        while(k.hasNext())
        {
            Point w1 = k.next();
            final float v = w1.getX() - mean;
            sum += v * v;
        }
        
        // Change to ( n - 1 ) to n if you have complete data instead of a sample.
        return (float) Math.sqrt( sum / ( n ) );
    }
    
    
    public static float StandardDeviationY (List<Point> cluster) 
    {
        // sd is sqrt of sum of (values-mean) squared divided by n - 1
        // Calculate the mean
        float mean = 0;
        final int n = cluster.size();
        if ( n < 2 )
        {
            return mean;
        }
        
        Iterator<Point> j = cluster.iterator();
        
        while (j.hasNext()) {
            Point w = j.next();
            mean += w.getY();
        }
        
        mean /= n;
        // calculate the sum of squares
        float sum = 0;
        
        Iterator<Point> k = cluster.iterator();
        while(k.hasNext())
        {
            Point w1 = k.next();
            final float v = w1.getY() - mean;
            sum += v * v;
        }
        // Change to ( n - 1 ) to n if you have complete data instead of a sample.
        return (float) Math.sqrt( sum / ( n ) );
    }
    
    
    public static float StandardDeviationD (List<Point> cluster) 
    {
        // sd is sqrt of sum of (values-mean) squared divided by n - 1
        // Calculate the mean
        float mean = 0;
        final int n = cluster.size();
        if ( n < 2 )
        {
            return mean;
        }
        
        Iterator<Point> j = cluster.iterator();
        
        while (j.hasNext()) {
            Point w = j.next();
            mean += w.getD();
        }
        
        mean /= n;
        // calculate the sum of squares
        float sum = 0;
        
        Iterator<Point> k = cluster.iterator();
        while(k.hasNext())
        {
            Point w1 = k.next();
            final float v = w1.getD() - mean;
            sum += v * v;
        }
        // Change to ( n - 1 ) to n if you have complete data instead of a sample.
        return (float) Math.sqrt( sum / ( n ) );
    }
    
    
    public static float weightedMean_C (List<Point> cluster) 
    {
        // Calculate the mean
        float weightedMean = 0;
        float totalWeight = 0;
        final int n = cluster.size();
        if ( n < 2 )
        {
            return Float.NaN;
        }
        
        Iterator<Point> j = cluster.iterator();
        
        while (j.hasNext()) {
            Point w = j.next();
            weightedMean = weightedMean+ (w.getC()*w.getC());
            totalWeight += w.getC();
        }
        
        weightedMean /= totalWeight;
        
        return (float) weightedMean ;
    }
    
    
    public static float WeightedStandardDeviationX (List<Point> cluster) 
    {
        // sd is sqrt of sum of (values-mean) squared divided by n - 1
        // Calculate the mean
        float weightedMean = 0;
        float totalWeight = 0;
        final int n = cluster.size();
        if ( n < 2 )
        {
            return Float.NaN;
        }
        
        Iterator<Point> j = cluster.iterator();
        
        while (j.hasNext()) {
            Point w = j.next();
            weightedMean = weightedMean+ (w.getX()*w.getC());
            totalWeight += w.getC();
        }
        
        weightedMean /= totalWeight;
        
        weightedMean_x = weightedMean;
        // calculate the sum of squares
        float sum = 0;
        
        Iterator<Point> k = cluster.iterator();
        while(k.hasNext())
        {
            Point w1 = k.next();
            final float v = w1.getX() - weightedMean;
            sum = sum+ w1.getC() *v * v;
        }
        // Change to ( n - 1 ) to n if you have complete data instead of a sample.
        return (float) Math.sqrt( sum /  (totalWeight)     ) ;
    }
    
    
    public static float WeightedStandardDeviationY (List<Point> cluster) 
    {
        // sd is sqrt of sum of (values-mean) squared divided by n - 1
        // Calculate the mean
        float weightedMean = 0;
        float totalWeight = 0;
        final int n = cluster.size();
        if ( n < 2 )
        {
            return Float.NaN;
        }
        
        Iterator<Point> j = cluster.iterator();
        
        while (j.hasNext()) {
            Point w = j.next();
            weightedMean += (w.getY()*w.getC());
            totalWeight += w.getC();
        }
        
        weightedMean /= totalWeight;
        weightedMean_y = weightedMean;
        // calculate the sum of squares
        float sum = 0;
        
        Iterator<Point> k = cluster.iterator();
        while(k.hasNext())
        {
            Point w1 = k.next();
            final float v = w1.getY() - weightedMean;
            sum = sum+ w1.getC() *v * v;
        }
        // Change to ( n - 1 ) to n if you have complete data instead of a sample.
        return (float) Math.sqrt( sum / ( totalWeight) );
    }
    
    
    public static float WeightedStandardDeviationD (List<Point> cluster) 
    {
        // sd is sqrt of sum of (values-mean) squared divided by n - 1
        // Calculate the mean
        float weightedMean = 0;
        float totalWeight = 0;
        final int n = cluster.size();
        if ( n < 2 )
        {
            return Float.NaN;
        }
        
        Iterator<Point> j = cluster.iterator();
        
        while (j.hasNext()) {
            Point w = j.next();
            weightedMean += (w.getD()*w.getC());
            totalWeight += w.getC();
        }
        
        weightedMean /= totalWeight;
        weightedMean_d = weightedMean;
        // calculate the sum of squares
        float sum = 0;
        
        Iterator<Point> k = cluster.iterator();
        while(k.hasNext())
        {
            Point w1 = k.next();
            final float v = w1.getD() - weightedMean;
            sum = sum+ w1.getC() *v * v;
        }
        // Change to ( n - 1 ) to n if you have complete data instead of a sample.
        return (float) Math.sqrt( sum / ( totalWeight) );
    }
    
}