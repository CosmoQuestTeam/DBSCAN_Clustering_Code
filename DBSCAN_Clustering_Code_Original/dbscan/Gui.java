package dbscan;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.io.*;
import java.util.Timer;

import Rstar.PPoint;
import Rstar.SortedLinList;
import Rstar.TreeCreation;
import Rstar.Data;


public class Gui
{
	public static int minpoints;
	public static int tdistance;
	public static Boolean a;
	public static Float x1;
	public static Float y1;
	public static Float d1;
	public static Float d2;
	public static Float x2;
	public static Float y2;
	public static Float c1;
	public static Float c2;
    
	static ArrayList<Point> temp = new ArrayList<Point>();
	private final static String newline = "\n";
	static Boolean Y = false;
	
	
    public static TreeCreation tc;
    public static HashMap<Integer, Vector<Point>> m;
    public static HashMap<Integer, Vector<Point>> m_part1;
    public static HashMap<Integer, Vector<Point>> m_part2;
    public static HashMap<Integer, Vector<Point>> m_part3;
    public static HashMap<Integer, Vector<Point>> m_part4;
    public static HashMap<Integer, Vector<Point>> m_part5;
    
    public static Point[] part1;
	public static Point[] part2;
	public static Point[] part3;
	public static Point[] part4;
	public static Point[] part5;
	public static Vector<Point> hset = new Vector<Point>();
	public static Vector<List> trl = new Vector<List>();
    
	
	public static Vector<Point> hset_1 = new Vector<Point>();
	public static Vector<Point> hset_2 = new Vector<Point>();
	public static Vector<Point> hset_3 = new Vector<Point>();
	public static Vector<Point> hset_4 = new Vector<Point>();
	public static Vector<Point> hset_5 = new Vector<Point>();
	
	
	public static void main(String args[])
    {
        
        //This program allows you to input TWO arguments or NO arguments.  If
        //  you do 3, the order is INT STRING (number of craters, input file).
        //  We're going to initialize them here, now, and then reset them iF
        //  you supplied arguments at the command line.
        minpoints = 3;    //the maximum number of craters that will be read in by any file.
        String s_filename_input = new String("input.txt");
        
        if(args.length > 0)
        {
            try
            {
                minpoints = Integer.parseInt(args[0]);
            }
            catch (NumberFormatException e) {
                System.err.println("First argument must be an integer, the number of points needed for a group to be considered a cluster.");
                System.exit(1);
            }
            try
            {
                s_filename_input = args[1];
            }
            catch (Exception e) {
                System.err.println("Second argument must be a string, the file name that contains {x,y,d,c} data.");
                System.exit(1);
            }
        }
        
        
        Float max_x = 0.0f;
        Float max_y = 0.0f;
		
        //Make a timer variable.
        long l_time_start = System.currentTimeMillis();//used to output how long the code's been running
        long l_time_intermediate1;
        long l_time_intermediate2;
        
        
        //Read in the data file.
        System.out.println("Reading the input file ...");
        try
        {
            // Open the file that is the first command line parameter.
            FileInputStream fstream = new FileInputStream(s_filename_input);
            
            // Get the object of DataInputStream.
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            int num_line = 0;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null)
            {
                String[] temp;                  //temporary string to hold the line we just read
                String delimiter = "\\s+";      //what the data within the line is separated by
                temp = strLine.split(delimiter);//separate the line that was read by the delimiter
                
                //Save the data from this line.
                if(num_line != 0)
                {
                    //Save the actual points to float variables.
                    Float m_x = new Float(temp[0]);
                    Float m_y = new Float(temp[1]);
                    Float m_d = new Float(temp[2]);
                    Float m_c = new Float(temp[3]);
                    
                    //Figure out the maximum extent in x and y.
                    if(max_x <= m_x)
                        max_x = m_x;
                    if(max_y <= m_y)
                        max_y = m_y;
                    
                    //Create a structure Point from this data.
                    Point np = new Point(m_x, m_y, m_d, m_c);
                    
                    //Add this point to the hash set.
                    hset.add(np);
                }
                num_line++;
            }
            
            //Close the input stream
            in.close();
        }
        catch (Exception e) //Catch exception if any
        {
            System.err.println("Error: " + e.getMessage());
        }
        l_time_intermediate1 = System.currentTimeMillis();
        System.out.println("                       ... took " + (l_time_intermediate1-l_time_start)/1000. + " seconds.");
        
        
        //For the creation of the tree, we need to select the maximum dimension and add some more.
        //  I'm not actually sure why Di added 500, perhaps it's the maximum crater/feature diameter?
        Float image_size = 0.0f; //this is the max size of the data coodinate
        if(max_x > max_y)
            image_size = max_x+500;
        else
            image_size = max_y+500;
        
        //Create the tree.
        System.out.println("Creating tree ...");
        tc = new TreeCreation(2, image_size);
	l_time_intermediate2 = System.currentTimeMillis();
        System.out.println("              ... took " + (l_time_intermediate2-l_time_intermediate1)/1000. + " seconds.");
        l_time_intermediate1 = l_time_intermediate2;

        //Create the hash map to search.
        System.out.println("Creating hash map ...");
        m = HashmapSearch.getHashMap(hset, tc);
        l_time_intermediate2 = System.currentTimeMillis();
        System.out.println("                  ... took " + (l_time_intermediate2-l_time_intermediate1)/1000. + " seconds.");
        l_time_intermediate1 = l_time_intermediate2;
        
        //Actually run the DBScan clustering code.
        System.out.println("Running DBScan.");
        trl.clear();                                //[dunno]
        dbscan test = new dbscan();                 //create a new instance of the dbscan
        trl.addAll(test.applyDbscan(m, tc, hset));  //actually runs the dbscan
        PrintOutput.printListLong(trl, 1);          //output the verbose version of the clustered features (lists each cluster and its member) -- the second variable is appended to the file name
        PrintOutput.printListShort(trl, 1);         //output the short version of the clustered features -- the second variable is appended to the file name
        
        
        //Output the final time.
        l_time_intermediate2 = System.currentTimeMillis();
        System.out.println("Total time to run the code took " + (l_time_intermediate2-l_time_start)/1000. + " seconds.");
    }
}
