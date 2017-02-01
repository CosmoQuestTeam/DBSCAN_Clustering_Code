package dbscan;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import Rstar.TreeCreation;

public class MyThread extends Thread
{
	
	private  Vector<Point> a;
	private  HashMap<Integer, Vector<Point>> m;
	private  TreeCreation tc;
	public  Vector<List> trl = new Vector<List>(); 
	private  int index;
    
	public MyThread(HashMap<Integer, Vector<Point>> m,
                    TreeCreation tc,Vector<Point> b,int index)
    {
        this.a = b;
        this.m = m;
        this.tc = tc;
        this.index = index;
	}
    
	public void run()
    {
		dbscan db;
		trl.clear();
		db = new dbscan();
	    trl.addAll(db.applyDbscan(m, tc, a));
		PrintOutput.printListLong(trl,index);
		PrintOutput.printListShort(trl,index);
	}
    
}
