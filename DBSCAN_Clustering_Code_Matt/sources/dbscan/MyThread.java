package dbscan;

/**************************/
/* Built-in java packages */
/**************************/

import Rstar.TreeCreation;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/*************************/
/* User-defined packages */
/*************************/

public class MyThread extends Thread {
    /*************************************************/
    /* Declaration/Initialization of class variables */
    /*************************************************/
    public Vector<List> trl = new Vector<List>();
    private HashMap<Integer, Vector<Point>> m;
    private int index;
    private TreeCreation tc;
    private Vector<Point> a;

    public MyThread(HashMap<Integer, Vector<Point>> m, TreeCreation tc, Vector<Point> b, int index) {
        this.a = b;
        this.m = m;
        this.tc = tc;
        this.index = index;
    }

    public void run() {
        dbscan db;
        Float radius = 20.0f;
        int minpoints = 3;
        trl.clear();
        db = new dbscan();
        trl.addAll(db.applyDbscan(m, tc, a, minpoints, radius));
        PrintOutput.printListLong(trl, index);
        PrintOutput.printListShort(trl, index);
    }
}
