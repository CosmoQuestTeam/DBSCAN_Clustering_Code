package dbscan;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Vector;

import Rstar.Data;
import Rstar.PPoint;
import Rstar.SortedLinList;
import Rstar.TreeCreation;


public class HashmapSearch
{
	public static HashMap<Integer,Vector<Point>> getHashMap(Vector<Point> hset,TreeCreation tc)
	{
		HashMap<Integer, Vector<Point>> m;
        m = new HashMap<Integer,Vector<Point>>();
        int index_4 = 0;
        SortedLinList res;
        PPoint p;
		
        try
        {	 
            FileWriter fstream = new FileWriter("log.txt");
            BufferedWriter out = new BufferedWriter(fstream);
            
            while(hset.size() > (index_4))
            {
                Point pp = hset.get(index_4);
                
                p = new PPoint(2);
                res = new SortedLinList();
                
                p.data[0] = pp.getX();
                p.data[1] = pp.getY();
                
                tc.rt.point_query(p, res);
                
                if(res.get_first() != null)
                {
                    out.write(Float.toString(pp.getX()) + " " + Float.toString(pp.getY()) + " ");
                    out.write(Integer.toString(((Data)(res.get_first())).id)+res.get_first().toString());
                    out.newLine();
                    
                    if(m.containsKey(((Data)(res.get_first())).id))
                    {
                        m.get(((Data)(res.get_first())).id).add(pp);
                    }
                    else
                    {
                        Vector<Point> vp;
                        vp = new Vector<Point>();
                        vp.add(pp);
                        m.put(((Data)(res.get_first())).id, vp);
                    }
                }
                index_4++;
            }
            out.close();
		}
        
        catch (Exception e)//Catch exception if any
        {
            System.err.println("Error: " + e.getMessage());
        }
        
		return m;
	}
}
