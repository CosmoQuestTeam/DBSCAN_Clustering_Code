package dbscan;


import java.util.*;
import Rstar.TreeCreation;


public class dbscan
{
    //This variable is set in the Gui.java file and is the minimum number of
    //  points that are needed for a group to be considered a cluster to output.
    public static int minpt = Gui.minpoints;
    
    //This will hold the results of the algorithm that are output to the user.
	public Vector<List> resultList = new Vector<List>();

    //Holds the original data, read in to cluster.
	public Vector<Point> pointList = new Vector<Point>();

	//Temporary vector that will hold the neighbors to a point of interest.
	public Vector<Point> Neighbours;

	//Main code that does the DBSCAN.
	public Vector<List> applyDbscan(HashMap<Integer, Vector<Point>> m, TreeCreation tc, Vector<Point> list)
    {
        //Re-initialize everything.
        resultList.clear();                 //initialize the list of results
        Utility.VisitList.clear();          //initialize and declare (it's set up in Utility.java) the list of points that have been already looked at
        pointList.clear();                  //initialize the points list to null
        pointList = Utility.getList(list);  //fill the points list from the input file
        
        //This is the main counter that iterates through every crater that was read in, looking for close craters to cluster with.
        int index2 = 0;
        
        //Make a timer (code added by Stuart!).
        long l_timeForOutput_start = System.currentTimeMillis();//used to output how long the code's been running
        long l_timeForOutput_counter = 0;                       //used to make sure at least d_secondsgoneby_foroutput second(s) has/have gone by before outputting progress.
        long l_timeForOutput_interval = 1;                      //how many seconds need to go by before the next progress point is output (in case it would be output more quickly)
        int i_cratersForOutput_counter = 0;                     //this program can take awhile, so this is used to figure out when to output progress to the user
        int i_cratersForOutput_interval = (int)Math.round(pointList.size()/1000.);  //the interval in # crater clusters found at which point progress will be output to the user
        
        
//        System.out.print(pointList.size()); //make sure it read in the correct number of craters
        
        
        //This is the main loop within the main function that does everything.  The basic idea here is:
        //  1. Loop through every point.  For each point ...
        while (pointList.size() > index2)
        {
            //Extract the point of interest, saving it to the structure "Point" instance "p".
            Point p = pointList.get(index2);
            
            //Check to see if this particular point has already been looked at.  If NOT, then ...
            if(!Utility.isVisited(p))
            {
				//Add this point to the visited list.
                Utility.Visited(p);
                
                //Where the initial "magic" happens and every point is examined relative to this one
                //  to determine if it is within epsilon distance to include it within the cluster.
                Neighbours = Utility.getNeighbours(p, m, tc);

                //If we're over the threshold for a cluster (given the user input of minimum number of points), then ...
                if (Neighbours.size() >= minpt)//*** shouldn't this be "minpt-1" because point "p" is not included in this? also, shouldn't we check for neighbors' neighbors before a minimum threshold is checked?
                {
                    int ind=0; //dummy counter to iterate through all the neighbors and search for other matches
                    
                    //... go through every point and check for other points that are neighbors (this is the "expandCluster" in Wikipedia's pseudocode for DBSCAN).
                    while(Neighbours.size() > ind)
                    {
                        Point r = Neighbours.get(ind);  //save this point it to the structure "Point" instance "r"
                        
                        //Check to see whether this point has been visited before (getNeighbors does not
                        //  set this to true), and if it has not, then repeat what we did above:
                        //  1. Go through the getNeighbors() and find all the other points within epsilon of r.
                        //  2. If we're above the threshold for a point to not be considered noise, then merge
                        //      this cluster with the main neighbors cluster for point p.
                        //This uses a Neighbors2 to work from rather than Neighbors 
                        if(!Utility.isVisited(r))
                        {
                            Utility.Visited(r);
                            Vector<Point> Neighbours2 = Utility.getNeighbours(r, m, tc);
                            if (Neighbours2.size() >= minpt)
                            {
                                Neighbours = Utility.Merge(Neighbours, Neighbours2);
                            }
                        } ind++;
                    }
                    
//                   System.out.println("N"+Neighbours.size());
                    
                    //At this stage, we have a final cluster of neighbors of neighbors, ready to be output to the user.
                    resultList.add(Neighbours);
                    
                    
                    //Output the time, maybe.  Time will be output if:
                    //  1. The point we just visited is greater than the next crater at which an output
                    //      interval will be made.  As in, if you only want to output a minimum of every
                    //      1000 craters (i_cratersForOutput_interval), then check to see if index2 is
                    //      at that value.
                    //  2. The time elapsed since the last output is >= the time threshold that was set.
                    if( (index2 >= i_cratersForOutput_counter) && (((System.currentTimeMillis()-l_timeForOutput_start)/1000) >= l_timeForOutput_counter) )
                    {
                        //All the crazy math in here is to fix the output % to ##.##% decimal points.
                        System.out.println("Progress: " + (double)((int)Math.round((double)index2/(double)pointList.size()*10000.))/100. + "% done after " + (System.currentTimeMillis()-l_timeForOutput_start)/1000 + " seconds.");
                        
                        //Set the thresholds for the next output.
                        i_cratersForOutput_counter += i_cratersForOutput_interval;
                        l_timeForOutput_counter += l_timeForOutput_interval; //save how long the code's been running
                    }
                }
            }
            index2++; //increment the main point counter so that we do the next point next
        }
        
        //Output the time.
        System.out.println("Total time to run the clustering:  " + ((System.currentTimeMillis()-l_timeForOutput_start)/1000.));
        
        //This returns the final list of clusters so they can be output.
        return resultList;	
    }
}