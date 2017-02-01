/*******************************************************************************
Purpose: The purpose of this file is to basically create a three-dimensional
    point (x, y, d) that also has a confidence c (in actuality, this just
    creates a structure for a Point that's comprised of 4 float variables).
*******************************************************************************/

package dbscan;

public class Point 
{
	private Float x;
	private Float y;
    private Float d;
    private Float c;
    
	Point(Float x, Float y, Float d, Float c)
    {
		this.x = x;
	    this.y = y;
	    this.d = d;
	    this.c = c;
    }
	
    
	public Float getX ()
    {
		return x;
    }
    
	public Float getY () 
	{
        return y;
	}
	
	public Float getD ()
    {
		return d;
    }
    
	public Float getC()
	{
		return c;
	}
}