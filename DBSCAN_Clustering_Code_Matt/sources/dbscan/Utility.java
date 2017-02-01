/*******************************************************************************
Purpose: This is really the main file that figures out if a feature should be
    clustered with another.
*******************************************************************************/
package dbscan;

/**************************/
/* Built-in java packages */
/**************************/
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/*************************/
/* User-defined packages */
/*************************/
import Rstar.PPoint;
import Rstar.SortedLinList;
import Rstar.TreeCreation;
import Rstar.Data;

public class Utility
{
    /*************************************************/
    /* Declaration/Initialization of class variables */
    /*************************************************/
    public static Vector<Point> VisitList = new Vector<Point>();
    
    /*************************************************************/
    /* Function calculates Euclidian distance between two points */
    /*************************************************************/
    public static double getDistance (Point p, Point q)
    {
	Float dx = p.getX()-q.getX();
	Float dy = p.getY()-q.getY();
	double distance = Math.sqrt (dx * dx + dy * dy);
       
	return distance;
    }
    
    //A short but key method that goes through and gets (a) the physical distance (in
    //  whatever units the data are in) between two points (your point of interest,
    //  "p", and the point to compare to, "q"), and (b) the relative diameter difference
    //  between the two points, and then if it is within the chosen threshold (your
    //  "epsilon" parameter from the very beginning), then it counts the crater as a
    //  member of the cluster and goes on.
    /*************************************************/
    /* Function returns vector containing all Points */
    /* that are within a user-defined radius of the  */
    /* current point being evaluated. Function works */
    /* independent of physical unit used.            */
    /*************************************************/
    public static Vector<Point> getNeighbours(Point p, HashMap<Integer, Vector<Point>> m, TreeCreation tc)
    {
	/****************************************************/
	/* Declaration/Initialization of function variables */
	/****************************************************/
	Vector<Point> neigh = new Vector<Point>();
        SortedLinList res;
        PPoint pp;
        pp = new PPoint(2);
        res = new SortedLinList();
        pp.data[0] = p.getX();
        pp.data[1] = p.getY();
        Float radius = 20.0f;
        tc.rt.rangeQuery(pp, radius, res);
        
		for( Object obj = res.get_first(); obj != null; obj = res.get_next())
        {
//		 if(res.get_first()!=null)
//		 {
            if(m.containsKey(((Data)obj).id))
            {
                Iterator<Point> it = m.get(((Data)obj).id).iterator();
                while(it.hasNext())
                {
                    Point q = it.next();
                    if( (getDistance(p, q) < (0.10*(p.getD()+q.getD())/2.)) &&  //if the absolute distance between the points is < X times the average of the diameters
                        (Math.abs(p.getD()-q.getD()) < (0.25*Math.min(p.getD(),q.getD()))) ) //if the difference between the diameters if < X times the minimum diameter
		            {
						neigh.add(q); //if Point q is within the epsilon threshold of Point p, then add it as a neighbor
		            }
                }
            }
//		 }
        }

		
        //Di commented out the following stuff in the summer after I made the alteration.  This was due to his change to
        //  a tree structure and multi-threading.  The key alteration I made is included in his modification, above.
        
        /*
		Iterator<Point> points = dbscan.pointList.iterator();
		while(points.hasNext())
        {
            Point q = points.next();
            
            //The following line was commented out from Di's original code.  It uses the classic
            //  2-D DBSCAN neighbor threshold "epsilon" to determine whether two points are close
            //  enough to be a cluster, but then (as far as I can tell) uses a non-robust "random"
            //  value of 40 pixels difference in diameter threshold for two craters to b
            //  considered part of a cluster or not.
//            if( (getDistance(p, q) <= dbscan.e) && (Math.abs(p.getD()-q.getD()) < 40) )
            
            //My alteration, below, was based on some experimentation.  Neither the distance nor
            //  diameter threshold are fixed, both scale with the point size.
            //  DISTANCE:  This compares the distance, in pixels (or whatever unit the data are in),
            //      between the two points to a user-input value (right now set to 0.5) times the
            //      sum of the radii of the two points (which also happens to be the average of
            //      the two diameters).  If the distance is less than this threshold times the
            //      sum of the diameters, then the point q is considered a neighbor of point p.
            //  SIZE:  This compares the absolute difference between the two points' diameters
            //      and scales it by a threshold (right now set to 1.0) times the MINIMUM of the
            //      two craters' diameters.  The minimum is chosen as opposed to the average or
            //      maximum because, if one of the craters is very large and the other is very
            //      small, then the absolute difference will be large but then this would be
            //      scaled to be very small -- we don't want that.
            //NOTE: My alteration makes the code take 25% LONGER to run ... but I can't figure
            //  out why it should be that much of a difference.  I got rid of the divisions
            //  (used to have the normalization factors divided into the distance / difference
            //  instead of multiplied on the other side of the inequality, and that did speed
            //  things up by 20%, but it's still slower and should somehow be optimized if possible.
            if( (getDistance(p, q) < (0.5*(0.5*(p.getD()+q.getD())))) && (Math.abs(p.getD()-q.getD()) < (0.5*Math.min(p.getD(),q.getD()))) )
            {
				neigh.add(q); //if Point q is within the epsilon threshold of Point p, then add it as a neighbor
            }
		}
		*/
        
		return neigh; //this returns the list of all craters that pass the if() statement above to be considered within the cluster
	}
    
    
    //Simple function to add a point to the list of visited points so it's not checked again.
	public static void Visited(Point d)
    {
        VisitList.add(d);
	}
    
    
    //Checks to see if the point has been visited or not, returning a boolean true/false.
	public static boolean isVisited(Point c)
	{
		if (VisitList.contains(c))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
    
    
    //This merges two groups of points into one.
	public static Vector<Point> Merge(Vector<Point> a, Vector<Point> b)
	{
        Iterator<Point> it5 = b.iterator();
        while(it5.hasNext())
        {
            Point t = it5.next();
            if( !a.contains(t) )  //make sure that we're not duplicating a point
            {
                a.add(t);
            }
        }
        return a;
	}
    
    
    //Returns PointsList to DBscan.java
	public static Vector<Point> getList(Vector<Point> list)
    {
        Vector<Point> newList =new Vector<Point>();
        newList.clear();
        newList.addAll(list);
        return newList;
	}		
    
}
