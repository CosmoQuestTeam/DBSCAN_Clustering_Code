/**********************************************************************/
/* Node is a set of functions implemented in RTDirNode and RTDataNode */
/**********************************************************************/
package Rstar;

/**************************/
/* Built-in java packages */
/**************************/

public interface Node {
    boolean is_data_node(); // returns TRUE, if "this" is RTDataNode

    float[] get_mbr(); // returns mbr enclosing whole page

    int get_num_of_data(); // returns number of data entries behind that node

    int insert(Data d, RTNode sn[]); // inserts d recursivly, if there

    // occurs a split, FALSE will be
    // returned and in sn a
    // pointer to the new node
    Data get(int i); // returns the i-th object in the tree lying behind that node

    void delete();

    void NearestNeighborSearch(PPoint point, PPoint Nearest, float nearest_distanz);

    void nodes(int nodes_a[]);

    void overlapping(float p[], int nodes_t[]);

    void point_query(float p[]); // prints all entries equal to p

    void print(); // prints rectangles

    //  public abstract void read_from_buffer(byte buffer[]) throws IOException; // reads data from buffer
    void region(float mbr[]); // prints all entries sectioning mbr
    //  public abstract void write_to_buffer(byte buffer[]) throws IOException; // writes data to buffer

    void NearestNeighborSearch(PPoint point, SortedLinList res, float nearest_distanz);

    //  void neighbours(LinList *sl, float eps, Result *rs, norm_ptr norm); // Determines neighboring points within eps
    void point_query(PPoint p, SortedLinList res);

    void rangeQuery(float mbr[], SortedLinList res);

    void rangeQuery(PPoint center, float radius, SortedLinList res);

    void ringQuery(PPoint center, float radius1, float radius2, SortedLinList res);

    void range_nnQuery(float mbr[], SortedLinList res, PPoint center, float nearest_distanz, PPoint nearest, boolean success);
    //  void writeinfo(FILE *f);
}
