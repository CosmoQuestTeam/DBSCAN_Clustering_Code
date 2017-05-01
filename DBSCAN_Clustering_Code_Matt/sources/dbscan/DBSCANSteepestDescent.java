package dbscan;

/**************************/
/* Built-in java packages */
/**************************/

import Rstar.TreeCreation;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/*************************/
/* User-defined packages */
/*************************/

@SuppressWarnings("unchecked")
public class DBSCANSteepestDescent {
    /*************************************************/
    /* Declaration/Initialization of class variables */
    /*************************************************/
    //public static dbscan test;
    public static float image_size; // Size of largest dimension (Unit: variable)
    public static float MapGridSpacing;
    public static float MaxD; // Diameter of largest feature (Unit: variable)  
    public static float MaxX; // Largest value in x dimension (Unit: variable)
    public static float MaxY; // Largest value in y dimension (Unit: variable)
    public static float SearchRadius;
    public static HashMap<Integer, Vector<Point>> Map;
    public static int MinCratersperCluster;
    public static int naxes; // Number of physical axes for map grid
    public static int NClusters;
    public static String CraterListFilename;
    public static TreeCreation RTree;
    public static float p[] = new float[2];
    public static float row[] = new float[4];
    public static Vector<List> trl = new Vector<List>();
    public static Vector<Point> hset = new Vector<Point>();
    public static Vector<float[]> Params;

    /************************************/
    /* Calculation of simple derivative */

    /************************************/
    public static float SimpleDerivative(float F2, float F1, float h) {
        /****************************************************/
    /* Declaration/Initialization of function variables */
        /****************************************************/
        float derivative;

        /**************/
	/* Derivative */
        /**************/
        derivative = (F2 - F1) / h;

        return derivative;
    }

    /**************************************/
    /* Determine number of clusters found */

    /**************************************/
    public static void ClustersFound() {
        /***********************************/
	/* Record number of clusters found */
        /***********************************/
        NClusters = trl.size();
    }

    /*********************************/
    /* Create the hash map to search */

    /*********************************/
    public static void CreateHashMap() {
        Map = HashmapSearch.getHashMap(hset, RTree);
    }

    /********************/
    /* Creation of tree */

    /********************/
    public static void CreateRTree() {
        RTree = new TreeCreation(naxes, image_size, MapGridSpacing);
    }

    /*********************************************************/
    /* Main function: Reads in list composed of the          */
    /* (x, y) positions, diameters, and confidence           */
    /* (unused data) of craters and       */
    /*  */

    /*********************************************************/
    public static void main(String args[]) {
        /****************************************************/
	/* Declaration/Initialization of function variables */
        /****************************************************/
        float a = 0.1f;
        float del = 1.0E-6f;

        /***********************/
	/* Retrieve parameters */
        /***********************/
        if (args.length != 4) {
            System.err.println("Usage: java -cp /path/to/DBSCAN/classes/directory/ dbscan/DBSCANSteepestDescent [Input Filename] [Min number of craters per cluster] [Map grid spacing] [Map search radius]");
            System.exit(1);
        }
        CraterListFilename = args[0];
        MinCratersperCluster = Integer.parseInt(args[1]);
        MapGridSpacing = Float.parseFloat(args[2]);
        SearchRadius = Float.parseFloat(args[3]);

        /****************************/
	/* Read-in crater list file */
        /****************************/
        ReadCraterList();

        /**********************/
	/* Set map parameters */
        /**********************/
        MaxD = 500.0f; // Value used in original code
        naxes = 2;
        image_size = (MaxX > MaxY) ? MaxX + MaxD : MaxY + MaxD;

        /***********************/
	/* Store Initial Guess */
        /***********************/
        p[0] = MapGridSpacing;
        p[1] = SearchRadius;

        /**************************************************/
	/* Apply steepest decent algorithm to determine   */
	/* best (MapGridSpacing, SearchRadius) parameters */
        /**************************************************/
        SteepestDescent(p, a, del);

        /****************************************/
	/* Return parameter that yields minimum */
        /****************************************/
    }

    public static void DBScanAlgo() {
        /****************/
	/* Create RTree */
        /****************/
        CreateRTree();

        /*******************/
	/* Create Hash Map */
        /*******************/
        CreateHashMap();

        /**************/
	/* Run DBScan */
        /**************/
        dbscan test = new dbscan();
        trl.addAll(test.applyDbscan(Map, RTree, hset, MinCratersperCluster, SearchRadius));

        /**************************************/
	/* Calculate number of clusters found */
        /**************************************/
        ClustersFound();

        /******************/
	/* Reset trl list */
        /******************/
        trl.clear();
    }

    public static void ReadCraterList() {
        /****************************************************/
	/* Declaration/Initialization of function variables */
        /****************************************************/
        File f;
        float max_d = 0.0f;
        float max_x = 0.0f;
        float max_y = 0.0f;

        /**************************/
	/* Check validity of file */
        /**************************/
        f = new File(CraterListFilename);
        if (!f.isFile()) {
            System.err.println("\nError in ReadCraterList: " + CraterListFilename + " does not exist.");
            System.exit(1);
        }

        /*************************/
        /* Read-in the data file */
        /*************************/
        try {
            /*************/
	    /* Open file */
            /*************/
            FileInputStream fstream = new FileInputStream(CraterListFilename);

            /*************************************/
	    /* Get the object of DataInputStream */
            /*************************************/
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;

            /**************************/
	    /* Read file line by line */
            /**************************/
            while ((strLine = br.readLine()) != null) {
                /*******************************************/
		/* Locate indicies of tabs in current line */
                /*******************************************/
                int p1 = strLine.indexOf("\t", 0);
                int p2 = strLine.indexOf("\t", p1 + 1);
                int p3 = strLine.indexOf("\t", p2 + 1);

                /***************/
		/* Record data */
                /***************/
                if ((p1 != -1) && !strLine.substring(0, p1).equals("X")) {
                    float m_x = new Float(strLine.substring(0, p1));
                    float m_y = new Float(strLine.substring(p1 + 1, p2));
                    float m_d = new Float(strLine.substring(p2 + 1, p3));
                    float m_c = new Float(strLine.substring(p3 + 1));

                    /**************************************************/
		    /* Determine maximum extent in x, y, and diameter */
                    /**************************************************/
                    max_d = (max_d <= m_d) ? m_d : max_d;
                    max_x = (max_x <= m_x) ? m_x : max_x;
                    max_y = (max_y <= m_y) ? m_y : max_y;

                    /******************************************************************************/
		    /* If needed, convert to local standard unit (Not implemented, maybe someday) */
                    /******************************************************************************/

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
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        /**********************************************************/
	/* Record maximum values for (x, y) position and diameter */
        /**********************************************************/
        MaxD = max_d;
        MaxX = max_x;
        MaxY = max_y;
    }

    public static void SteepestDescent(float p[], float a, float del) {
        /****************************************************/
	/* Declaration/Initialization of function variables */
        /****************************************************/
        int n = p.length;
        //float h = 1.0E-6f;
        float h = 10.0f;

        /****************************/
	/* Calculate starting point */
        /****************************/
        DBScanAlgo();
        float f1 = NClusters;
        System.out.println(MapGridSpacing + " " + SearchRadius + " " + f1);
        System.exit(0);

        /********************************************/
	/* Calculate derivative for first parameter */
        /********************************************/
        MapGridSpacing = p[0] + h;
        DBScanAlgo();
        float f2 = NClusters;
        System.out.println(MapGridSpacing + " " + SearchRadius + " " + f2);

        /*********************************************/
	/* Calculate derivative for second parameter */
        /*********************************************/
        MapGridSpacing = p[0];
        SearchRadius = p[1] + h;
        DBScanAlgo();
        float f3 = NClusters;
        System.out.println(MapGridSpacing + " " + SearchRadius + " " + f3);
        System.exit(0);

	/* Determine */
    }
}
