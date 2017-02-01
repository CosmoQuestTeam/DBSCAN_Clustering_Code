////////////////////////////////////////////////////////////////////////////
//
// Data
////////////////////////////////////////////////////////////////////////////
//
package Rstar;
import java.lang.*;
import java.io.*;
/**
* Data
*/
public class Data implements Comparable
{
  static final int sizeof_dimension = 4;
  static final int sizeof_float     = 4;

  public  float[] data      = null;   // Vector
  public  float   distanz   = 0;      // anything

  public int dimension = 0;      // array size of data[]
  public int id = 0;

//--------------------------------------------------------------------------
  /**
  * Default Constructor
  */
  public Data()
  {
    this.setDimension(Constants.RTDataNode__dimension);
  }
  /**
  * Constructor accept an int _dimension
  */
  public Data(int _dimension)
  {
    this.setDimension(_dimension);
  }

  public Data(int _dimension, int _id)
  {
    this.setDimension(_dimension);
    this.id = _id;
  }


//--------------------------------------------------------------------------
  /**
  * Constructor accept a byte array representing dimension
  */
  protected void setDimension(int _dimension)
  {
    if (_dimension <= 0) _dimension = 1;
    this.dimension = _dimension;
    //this.data = new float[_dimension];
    this.data = new float[_dimension*2];
  }

//--------------------------------------------------------------------------
  /**
  * returns MBR (Minimum Bounding Rect) of the object
  */
  public float[] get_mbr()
  {
  

    float[] f = new float[2 * this.dimension];
    System.arraycopy(data, 0, f, 0, 2*dimension);
    return f;
  }

//--------------------------------------------------------------------------
  /**
  * returns the area of the MBR, for vectors always 0.0
  */
  public float get_area()
  {
    return 0;
  }

//--------------------------------------------------------------------------



//----------------------------------------------------------------------------
  /**
  * Implement Streamable
  * returns amount of needed space in bytes
  */
  public int get_size()
  {
    return this.dimension * 2 * sizeof_float + sizeof_dimension;
  }

//--------------------------------------------------------------------------
  /**
  * print out all data
  */
  public void print()
  {
    System.out.print("[id: <"+this.id+"> ");
    if (this.dimension > 0)
        System.out.print(this.data[0]);
    for (int i = 1; i < this.dimension*2; i++)
        System.out.print(" "+this.data[i]);
    System.out.println("]");
  }
  /**
  * Implements Object.toString()
  */
  public String toString()
  {
    String answer = this.getClass().getName();
    answer = answer+"(distanz="+this.distanz+","+this.dimension+":[";
    answer = answer + "id :" + this.id+" ";
    if (this.dimension > 0)
    answer = answer+this.data[0];
    // for (int i = 1; i < this.dimension; ++i)
    for (int i = 1; i < this.dimension*2; ++i)
        answer = answer+" "+this.data[i];
    answer = answer+"])";
    return answer;
  }

//--------------------------------------------------------------------------
  /**
  * Implements the Comparable interface
  */
  public int compare(Object obj)
  {
    if (! (obj instanceof Data))
    return 1; //??? return arbitary value saying that not equal
    Data other = (Data)obj;
    if (this.distanz > other.distanz) return  1;
    if (this.distanz < other.distanz) return -1;
    return 0;
  }
  /**
  * Override Object.equals(...)
  */
  public boolean equals(Object obj)
  {
    if (! (obj instanceof Data))
    return false;
    Data other = (Data)obj;
    if (this.dimension == other.dimension
    && this.distanz   == other.distanz)
    {
      for (int i = 0; i < this.data.length; ++i)
      if (this.data[i] != other.data[i])
      return false;
      return true;
    }
    return false;
  }

//--------------------------------------------------------------------------
  /**
  * set this to other.clone()
  */
  public Data assign(Data other)
  {
    this.setDimension(other.dimension);
    this.distanz   = other.distanz;
    //for (int i = 0; i < this.dimension; ++i)
    for (int i = 0; i < this.dimension*2; ++i)
        this.data[i] = other.data[i];
    this.id = other.id;
    return this;
  }
  /**
  * Override Object.clone()
  */
  public Object clone()
  {
    Data d = new Data(this.dimension);
    d.distanz = this.distanz;
    //for (int i = 0; i < this.dimension; ++i)
    for (int i = 0; i < this.dimension*2; ++i)
        d.data[i] = this.data[i];
    d.id = this.id;
    return (Object)d;
  }
  

}