/*********************************************************************************/
/* Purpose: The purpose of this class is to basically create a three-dimensional */
/* point (x, y, d) that also has a confidence c (in actuality, this just         */
/* creates a structure for a Point that's comprised of 4 float variables).       */
/*********************************************************************************/
package dbscan;

public class Point 
{
    /*************************************************/
    /* Declaration/Initialization of class variables */
    /*************************************************/
    private Float c;
    private Float d;
    private Float x;
    private Float y;
    
    /*********************/
    /* Point Constructor */
    /*********************/
    Point(Float x, Float y, Float d, Float c)
    {
	this.x = x;
	this.y = y;
	this.d = d;
	this.c = c;
    }

    /***********************************/
    /* Function that gets x coordinate */
    /***********************************/
    public Float getX ()
    {
	return x;
    }
    
    /***********************************/
    /* Function that gets y coordinate */
    /***********************************/
    public Float getY () 
    {
        return y;
    }
	
    /***********************************/
    /* Function that gets d coordinate */
    /***********************************/
    public Float getD ()
    {
	return d;
    }
    
    /***********************************/
    /* Function that gets c coordinate */
    /***********************************/
    public Float getC()
    {
	return c;
    }
}
