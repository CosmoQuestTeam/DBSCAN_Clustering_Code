/********************************************/
/* Modified from Test.java created by Nikos */
/********************************************/
package Rstar;

/**************************/
/* Built-in java packages */
/**************************/
import java.awt.*;

public class TreeCreation 
{
    /*************************************************/
    /* Declaration/Initialization of class variables */
    /*************************************************/
    public int displaylevel = 199; // Matt Note: I think this is used for the gui
    public RectFrame f; // Matt Note: I think this is used for the gui
    public RTree rt;
    private int dimension;

    /****************************/
    /* TreeCreation Constructor */
    /****************************/
    public TreeCreation(int dimension, Float image_size)
    {	
	/****************************************************/
	/* Declaration/Initialization of function variables */
	/****************************************************/
	boolean response;
	Data d;
	float const1;
	int index_rect;
	int interval;
	int numRecLine;

	/*********************/
	/* Initialize R-tree */
	/*********************/
	this.dimension = dimension;
	rt = new RTree(dimension);
	
	/**************************/
	/* Create a new rectangle */
	/**************************/
	interval = 30; // Why interval of 30 units?
	numRecLine = (int) (image_size/interval);
	index_rect = 0;
	const1 = numRecLine*interval;
	response = (const1 < image_size);
	for (int j=0; j<numRecLine; j++)
	{
	    float const2 = j*interval;
	    for (int i=0; i<numRecLine; i++) 
	    {
		d = new Data(dimension, index_rect);
		d.data[0] = i*interval;
		d.data[1] = d.data[0]+interval;
		d.data[2] = const2;
		d.data[3] = d.data[2]+interval;
		d.print();
		rt.insert(d);
		index_rect++;
	    }
	    if (response) 
	    {
		d = new Data(dimension, index_rect);
		d.data[0] = const1;
		d.data[1] = image_size;
		d.data[2] = const2;
		d.data[3] = d.data[2]+interval;
		d.print();
		rt.insert(d);
		index_rect++;
	    }
	}
	if (response)
	{    
	    for (int i=0; i<numRecLine; i++)
	    {
		d = new Data(dimension, index_rect);
		d.data[0] = i*interval;
		d.data[1] = d.data[0]+interval;
		d.data[2] = const1;
		d.data[3] = image_size;
		d.print();
		rt.insert(d);
		index_rect++;
	    }
	    if (response)
	    {
		d = new Data(dimension, index_rect);
		d.data[0] = const1;
		d.data[1] = image_size;
		d.data[2] = const1;
		d.data[3] = image_size;
		d.print();
		rt.insert(d);
		index_rect++;
	    }
	}
    }
    
    public void exit(int exitcode)
    {
	if ((rt != null) && (exitcode == 0))
	    rt.delete();
	System.exit(0);
    }
}
