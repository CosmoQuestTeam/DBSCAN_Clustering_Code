package dbscan;

/**************************/
/* Built-in java packages */
/**************************/

import Rstar.Data;
import Rstar.PPoint;
import Rstar.SortedLinList;
import Rstar.TreeCreation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Vector;

/*************************/
/* User-defined packages */
/*************************/

public class HashmapSearch {
    public static HashMap<Integer, Vector<Point>> getHashMap(Vector<Point> hset, TreeCreation tc) {
        /****************************************************/
    /* Declaration/Initialization of function variables */
        /****************************************************/
        HashMap<Integer, Vector<Point>> m = new HashMap<Integer, Vector<Point>>();
        int index_4 = 0;

        try {
            /*****************************/
	    /* Open log file to write to */
            /*****************************/
            FileWriter fstream = new FileWriter("log.txt");
            BufferedWriter out = new BufferedWriter(fstream);

            /******************************/
	    /* Iterate through all points */
            /******************************/
            while (hset.size() > (index_4)) {
                /*****************************************/
		/* Retrieve coordinates of current point */
		/* (i.e., grab (x, y, d, c) values of    */
		/* the current point)                    */
                /*****************************************/
                Point pp = hset.get(index_4);

                /*********************************************/
		/* Store (x, y) coordinates of current point */
                /*********************************************/
                PPoint p = new PPoint(2);
                p.data[0] = pp.getX();
                p.data[1] = pp.getY();

                SortedLinList res = new SortedLinList();
                tc.rt.point_query(p, res);

                if (res.get_first() != null) {
                    out.write(Float.toString(pp.getX()) + " " + Float.toString(pp.getY()) + " ");
                    out.write(Integer.toString(((Data) (res.get_first())).id) + res.get_first().toString());
                    out.newLine();

                    if (m.containsKey(((Data) (res.get_first())).id)) {
                        m.get(((Data) (res.get_first())).id).add(pp);
                    } else {
                        Vector<Point> vp;
                        vp = new Vector<Point>();
                        vp.add(pp);
                        m.put(((Data) (res.get_first())).id, vp);
                    }
                }
                index_4++;
            }
            out.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        return m;
    }
}
