package dbscan;

/**************************/
/* Built-in java packages */
/**************************/
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.io.*;
import java.util.Timer;

/*************************/
/* User-defined packages */
/*************************/
import Rstar.Data;
import Rstar.PPoint;
import Rstar.SortedLinList;
import Rstar.TreeCreation;

public class Gui
{
    /*************************************************/
    /* Declaration/Initialization of class variables */
    /*************************************************/
    public static Float interval; // Spacing between points on map grid
    public static Float radius; // Map search radius 
    public static HashMap<Integer, Vector<Point>> m;
    public static int minpoints; // Minimum number of points needed for group to be considered a cluster
    public static int naxes; // Number of physical axes for map grid
    public static String s_filename_input;
    public static TreeCreation tc;
    public static Vector<List> trl = new Vector<List>();
    public static Vector<Point> hset = new Vector<Point>();
    static ArrayList<Point> temp = new ArrayList<Point>();

    /***********************************************************/
    /* Main function: Accepts a maximum of three arguements.   */
    /* Reads in list composed of the (x, y) positions,         */
    /* diameters, and confidence (unused data) of craters and  */
    /* executes the dbscan algorithm to determine spatial      */
    /* clustering of craters.                                  */
    /***********************************************************/
    public static void main(String args[])
    {
	/****************************************************/
	/* Declaration/Initialization of function variables */
	/****************************************************/
        Float image_size = 0.0f; // Size of largest dimension (Unit: variable)
	Float max_d = 0.0f; // Diameter of largest feature (Unit: variable)  
	Float max_x = 0.0f; // Largest value in x dimension (Unit: variable)
        Float max_y = 0.0f; // Largest value in y dimension (Unit: variable)
	long l_time_intermediate1;
        long l_time_intermediate2;
	long l_time_start;

	/************************************/
	/* Record starting time of function */
	/************************************/
	l_time_start = System.currentTimeMillis();

	/******************************************/
	/* Retrieve arguements. If no arguements  */
	/* are given, default arguements are used */
	/******************************************/
        if (args.length == 1)
        {
	    s_filename_input = args[0];
	    minpoints = 3; // Why minimum of 3 craters per cluster?
	    interval = 30.0f; // Why interval of 30 units?
	    radius = 20.0f; // Why search radius of 20 units?
	}
	else if (args.length == 2)
	{
	    s_filename_input = args[0];
	    minpoints = Integer.parseInt(args[1]);
	    interval = 30.0f; // Why interval of 30 units?
	    radius = 20.0f; // Why search radius of 20 units?
        }
	else if (args.length == 3)
	{
	    s_filename_input = args[0];
	    minpoints = Integer.parseInt(args[1]);
	    interval = Float.parseFloat(args[2]);
	    radius = 20.0f; // Why search radius of 20 units?
        }
	else if (args.length == 4)
	{
	    s_filename_input = args[0];
	    minpoints = Integer.parseInt(args[1]);
	    interval = Float.parseFloat(args[2]);
	    radius = Float.parseFloat(args[3]);
        }
        else
	{
	    s_filename_input = new String("input.txt");
	    minpoints = 3; // Why minimum of 3 craters per cluster?
	    interval = 30.0f; // Why interval of 30 units?
	    radius = 20.0f; // Why search radius of 20 units?
	}        
        
	/*********************/
	/* Output parameters */
	/*********************/
	System.out.println("Input file: "+s_filename_input);
	System.out.println("Minimum craters per cluster: "+minpoints);
        System.out.println("Map grid spacing: "+interval);
	System.out.println("Map search radius: "+radius);
	
	/**************************/
	/* Check validity of file */
	/**************************/
	File f = new File(s_filename_input);
	if (!f.isFile())
	{
	    System.err.println("\nError: "+s_filename_input+" does not exist.");
	    System.err.println("Usage: java -cp /path/to/DBSCAN/classes/directory/ dbscan/Gui [Input Filename] [Min number of craters per cluster] [Map grid spacing] [Map search radius]");
	    System.exit(1);
	}
	
	/*************************/
        /* Read-in the data file */
	/*************************/
        System.out.println("\nReading the input file...");
	try
	{
	    /*************/
	    /* Open file */
	    /*************/
	    FileInputStream fstream = new FileInputStream(s_filename_input);
            
	    /*************************************/
	    /* Get the object of DataInputStream */
	    /*************************************/
	    DataInputStream in = new DataInputStream(fstream);
	    BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    String strLine;
	    
	    /**************************/
	    /* Read file line by line */
	    /**************************/
	    while ((strLine = br.readLine()) != null)
	    {
		/*******************************************/
		/* Locate indicies of tabs in current line */
		/*******************************************/
		int p1 = strLine.indexOf("\t", 0);
		int p2 = strLine.indexOf("\t", p1+1);
		int p3 = strLine.indexOf("\t", p2+1);
	    
		/***************/
		/* Record data */
		/***************/
		if ((p1 != -1) && !strLine.substring(0, p1).equals("X"))
		{
		    Float m_x = new Float(strLine.substring(0, p1));
		    Float m_y = new Float(strLine.substring(p1+1, p2));
		    Float m_d = new Float(strLine.substring(p2+1, p3));
		    Float m_c = new Float(strLine.substring(p3+1));

		    /**************************************************/
		    /* Determine maximum extent in x, y, and diameter */
		    /**************************************************/
		    max_d = (max_d <= m_d) ? m_d : max_d;
		    max_x = (max_x <= m_x) ? m_x : max_x;
		    max_y = (max_y <= m_y) ? m_y : max_y;
		    
		    /*****************************************************************************/
		    /* If needed, convert to local standard unit (Not implemented yet, but soon) */
		    /*****************************************************************************/
		    
		    /*******************************************/
		    /* Create a structure Point from this data */
		    /*******************************************/
		    Point np = new Point(m_x, m_y, m_d, m_c);
		    
		    /**********************************/
		    /* Add this point to the hash set */
		    /**********************************/
		    hset.add(np);
		}
	    }
	    in.close();
	}
	catch (Exception e)
	{
	    System.err.println("Error: " + e.getMessage());
	}
	max_d /= 2f; // Should only require to use the radius, not diameter

	/*****************************************/
	/* Record current time and print elapsed */
	/* time since previous checkpoint        */
	/*****************************************/
        l_time_intermediate1 = System.currentTimeMillis();
        System.out.println("                       ... took " + (l_time_intermediate1-l_time_start)/1000. + " seconds.");

	/***********************************************************/
	/* Check validity of minimum number of craters per cluster */
	/***********************************************************/
	if ((minpoints < 1) || (hset.size() < minpoints))
	{
	    System.err.println("Error: Invalid minimum number of craters per cluster. Acceptable range: 1 - "+hset.size()+". Quantity given: "+minpoints+".");
	    System.err.println("Usage: java -cp /path/to/DBSCAN/classes/directory/ dbscan/Gui [Input Filename] [Min number of craters per cluster] [Map grid spacing] [Map search radius]");
	    System.exit(1);
	}

	/******************/
	/* Set image size */
	/******************/
	max_d = 500f; // Value in original code
	image_size = (max_x > max_y) ? max_x+max_d : max_y+max_d;

	/**************************************/
	/* Check validity of map grid spacing */
	/**************************************/
	if ((interval < 1) || (image_size < interval))
	{
	    System.err.println("Error: Invalid grid spacing. Acceptable range: 1 - "+image_size+"Value given: "+interval+".");
	    System.err.println("Usage: java -cp /path/to/DBSCAN/classes/directory/ dbscan/Gui [Input Filename] [Min number of craters per cluster] [Map grid spacing] [Map search radius]");
	    System.exit(1);
	}
        
        /********************/
	/* Creation of tree */ 
	/********************/
        System.out.println("Creating tree ...");
	naxes = 2;
        tc = new TreeCreation(naxes, image_size, interval);

	/*****************************************/
	/* Record current time and print elapsed */
	/* time since previous checkpoint        */
	/*****************************************/
        l_time_intermediate2 = System.currentTimeMillis();
        System.out.println("              ... took " + (l_time_intermediate2-l_time_intermediate1)/1000. + " seconds.");
        l_time_intermediate1 = l_time_intermediate2;

	/*********************************/
        /* Create the hash map to search */
	/*********************************/
        System.out.println("Creating hash map ...");
        m = HashmapSearch.getHashMap(hset, tc);

	/*****************************************/
	/* Record current time and print elapsed */
	/* time since previous checkpoint        */
	/*****************************************/
        l_time_intermediate2 = System.currentTimeMillis();
        System.out.println("                  ... took " + (l_time_intermediate2-l_time_intermediate1)/1000. + " seconds.");
        l_time_intermediate1 = l_time_intermediate2;
        
	/***************************************/
	/* Check validity of map search radius */
	/***************************************/
	if ((radius < 1) || (image_size/2.0f < radius))
	{
	    System.err.println("Error: Invalid search radius. Acceptable range: 1 - "+image_size/2.0f+"Value given: "+radius+".");
	    System.err.println("Usage: java -cp /path/to/DBSCAN/classes/directory/ dbscan/Gui [Input Filename] [Min number of craters per cluster] [Map grid spacing] [Map search radius]");
	    System.exit(1);
	}

	/**********************************/
        /* Run the DBScan clustering code */
	/**********************************/
        System.out.println("Running DBScan.");

	/******************************************/
	/* Create an instance of the dbscan class */
	/******************************************/
        dbscan test = new dbscan();

	/*********************************/
	/* Execution of DBSCAN algorithm */
	/* Output stored in vector trl   */
	/*********************************/
        trl.addAll(test.applyDbscan(m, tc, hset, minpoints, radius));
	
	/**********************************************************/
	/* Output results of dbscan to file in long/short formats */
	/**********************************************************/
	PrintOutput.printListLong(trl, 1);
        PrintOutput.printListShort(trl, 1);
        
        /*****************************************/
	/* Record current time and print elapsed */
	/* time for entire program               */
	/*****************************************/
        l_time_intermediate2 = System.currentTimeMillis();
        System.out.println("Total time to run the code took " + (l_time_intermediate2-l_time_start)/1000. + " seconds.");
    }
}
