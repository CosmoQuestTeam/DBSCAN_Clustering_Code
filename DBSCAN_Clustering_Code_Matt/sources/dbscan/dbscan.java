package dbscan;

/**************************/
/* Built-in java packages */
/**************************/
import java.util.*;

/*************************/
/* User-defined packages */
/*************************/
import Rstar.TreeCreation;

public class dbscan
{
    /*************************************************/
    /* Declaration/Initialization of class variables */
    /*************************************************/
    public static int minpt = Gui.minpoints; // Value set in main function of Gui class
    public Vector<List> resultList = new Vector<List>();
    public Vector<Point> pointList = new Vector<Point>();
    public Vector<Point> Neighbours;

    /********************************************/
    /* Function that executes the database scan */
    /********************************************/
    public Vector<List> applyDbscan(HashMap<Integer, Vector<Point>> m, TreeCreation tc, Vector<Point> list, Float radius)
    {
	/****************************************************/
	/* Declaration/Initialization of function variables */
	/****************************************************/
	int i_cratersForOutput_counter;
	int i_cratersForOutput_interval;
	int index2 = 0;
	long l_timeForOutput_counter;
	long l_timeForOutput_interval;
	long l_timeForOutput_start;

	/*****************/
        /* Reset vectors */
	/*****************/
        resultList.clear();
        pointList.clear();
        Utility.VisitList.clear();          //initialize and declare (it's set up in Utility.java) the list of points that have been already looked at
	
	/*********************************/
	/* Retrieve data from input list */
	/*********************************/
        pointList = Utility.getList(list);
        
	/*********************************************************************/
	/* Record starting time of function (Following code added by Stuart) */
	/*********************************************************************/
        l_timeForOutput_start = System.currentTimeMillis();

	/****************************************************************************/
	/* Tracking/reporting progress of function (Following code added by Stuart) */
        /****************************************************************************/
	// l_timeForOutput_counter = 0;
        // l_timeForOutput_interval = 1;
        // i_cratersForOutput_counter = 0;
        // i_cratersForOutput_interval = (int)Math.round(pointList.size()/1000.);
        
        /*****************************************************************************************/
	/* Iterate through list of craters until clusters have been determined using entire list */
	/*****************************************************************************************/
	while (index2 < pointList.size())
        {
	    /**************************/
	    /* Select a point in list */
	    /**************************/
            Point p = pointList.get(index2);
            
	    /***********************************************************************************/
            /* Determine if point has been evaluated. If it has, move on to next point in list */
	    /***********************************************************************************/
	    if(!Utility.isVisited(p))
            {
		/*********************************************/
		/* Include point in list of evaluated points */
		/*********************************************/
                Utility.Visited(p);
                
		/**********************************************/
		/* Determines which points in list are within */
		/* some user-defined radius of the current    */
		/* point and includes those points as part of */
		/* a group.                                   */
		/**********************************************/
		Neighbours = Utility.getNeighbours(p, m, tc, radius);

                /****************************************/
		/* Check cluster size against threshold */
		/****************************************/
		if (Neighbours.size() >= minpt)
                {
                    int ind = 0;
                    
                    //... go through every point and check for other points that are neighbors (this is the "expandCluster" in Wikipedia's pseudocode for DBSCAN).
                    while(Neighbours.size() > ind)
                    {
                        Point r = Neighbours.get(ind);
                        
                        //Check to see whether this point has been visited before (getNeighbors does not
                        //  set this to true), and if it has not, then repeat what we did above:
                        //  1. Go through the getNeighbors() and find all the other points within epsilon of r.
                        //  2. If we're above the threshold for a point to not be considered noise, then merge
                        //      this cluster with the main neighbors cluster for point p.
                        //This uses a Neighbors2 to work from rather than Neighbors 
                        if(!Utility.isVisited(r))
                        {
                            Utility.Visited(r);
                            Vector<Point> Neighbours2 = Utility.getNeighbours(r, m, tc, radius);
                            if (Neighbours2.size() >= minpt)
                            {
                                Neighbours = Utility.Merge(Neighbours, Neighbours2);
                            }
                        } ind++;
                    }

                    //At this stage, we have a final cluster of neighbors of neighbors, ready to be output to the user.
                    resultList.add(Neighbours);

                    //Output the time, maybe.  Time will be output if:
                    //  1. The point we just visited is greater than the next crater at which an output
                    //      interval will be made.  As in, if you only want to output a minimum of every
                    //      1000 craters (i_cratersForOutput_interval), then check to see if index2 is
                    //      at that value.
                    //  2. The time elapsed since the last output is >= the time threshold that was set.
                    // if( (index2 >= i_cratersForOutput_counter) && (((System.currentTimeMillis()-l_timeForOutput_start)/1000) >= l_timeForOutput_counter) )
                    // {
                    //     //All the crazy math in here is to fix the output % to ##.##% decimal points.
                    //     System.out.println("Progress: " + (double)((int)Math.round((double)index2/(double)pointList.size()*10000.))/100. + "% done after " + (System.currentTimeMillis()-l_timeForOutput_start)/1000 + " seconds.");
                        
                    //     //Set the thresholds for the next output.
                    //     i_cratersForOutput_counter += i_cratersForOutput_interval;
                    //     l_timeForOutput_counter += l_timeForOutput_interval; //save how long the code's been running
                    // }
                }
            }
            index2++;
        }
        System.out.println("Total time to run the clustering:  " + ((System.currentTimeMillis()-l_timeForOutput_start)/1000.));
        
        //This returns the final list of clusters so they can be output.
        return resultList;	
    }
}
