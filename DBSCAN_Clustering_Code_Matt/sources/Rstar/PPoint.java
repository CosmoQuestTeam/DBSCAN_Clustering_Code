package Rstar;

public class PPoint
{
    /*************************************************/
    /* Declaration/Initialization of class variables */
    /*************************************************/
    public float data[];
    int dimension;
    float distanz = 0.0f;

    /******************************/
    /* Default PPoint Constructor */
    /******************************/
    public PPoint()
    {
        dimension = Constants.RTDataNode__dimension;
        data = new float[dimension];
    }
    
    /********************************************************/
    /* PPoint Constructor: sets user-defined dimensionality */
    /********************************************************/
    public PPoint(int dimension)
    {
        this.dimension = dimension;
        data = new float[dimension];
    }
}
