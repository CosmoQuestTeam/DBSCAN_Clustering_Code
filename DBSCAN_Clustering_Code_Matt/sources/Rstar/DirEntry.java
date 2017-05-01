////////////////////////////////////////////////////////////////////////
// DirEntry
////////////////////////////////////////////////////////////////////////

/**
 * DirEntry implements the entries of a directory node (RTDirNode)
 * <p>
 * the info of the DirEntry in a RTDirNode block is organised as follows:
 * +-------------+-----+------------------------+-----+-------------+
 * | bounces[0]  | ... | bounces[2*dimension-1] | son | num_of_data |
 * +-------------+-----+------------------------+-----+-------------+
 */


package Rstar;

class DirEntry {
    RTree my_tree;                      // pointer to my R-tree
    int son;                            // block # of son
    RTNode son_ptr;                    // pointer to son if in main mem.
    boolean son_is_data;                // TRUE, if son is a data page
    int dimension;                      // dimension of the box
    float bounces[];                    // the mbr of the box
    int son_level;                      // level of the node pointed to
    int num_of_data;                    // amount of data entries behind the

    // son of this entry
    DirEntry(int _dimension, boolean son_is_data, RTree rt) {
        dimension = _dimension;
        this.son_is_data = son_is_data;
        my_tree = rt;
        bounces = new float[2 * dimension];
        son_ptr = null;
        num_of_data = 0;
    }

    /**
     * copy the contents of this to another Direntry object
     */
    void copyTo(DirEntry target) {
        target.dimension = dimension;
        target.son = son;
        target.son_ptr = son_ptr;
        target.son_level = son_level;
        target.son_is_data = son_is_data;
        target.num_of_data = num_of_data;
        System.arraycopy(bounces, 0, target.bounces, 0, 2 * dimension);
    }

    /**
     * Checks if point v is inside the entry's MBR
     */
    boolean is_inside(float v[]) {
        int i;

        for (i = 0; i < dimension; i++) {
            if (v[i] < bounces[2 * i] ||        // upper limit
                    v[i] > bounces[2 * i + 1])      // lower limit
                return false;
        }
        return true;
    }

    /**
     * Tests if the parameter mbr is inside or overlaps the MBR of the entry
     */
    int section(float mbr[]) {
        boolean inside;
        boolean overlap;
        int i;

        overlap = true;
        inside = true;
        for (i = 0; i < dimension; i++) {
            if (mbr[2 * i] > bounces[2 * i + 1] ||
                    mbr[2 * i + 1] < bounces[2 * i])
                overlap = false;
            if (mbr[2 * i] < bounces[2 * i] ||
                    mbr[2 * i + 1] > bounces[2 * i + 1])
                inside = false;
        }
        if (inside)
            return Constants.INSIDE;
        else if (overlap)
            return Constants.OVERLAP;
        else
            return Constants.S_NONE;
    }

    /**
     * returns the son_ptr (the node this entry points to
     * if the node is not in main memory, it it read
     * from disk (see RTDirNode/RTDataNode constructor)
     */
    RTNode get_son() {

        return son_ptr;
    }

    /**
     * returns true if the entry intersects the circle
     */
    boolean section_circle(PPoint center, float radius) {
        return Constants.section_c(dimension, bounces, center, radius);
    }

    /**
     * returns true if the entry intersects the ring
     */
    boolean section_ring(PPoint center, float radius1, float radius2) {
        return Constants.section_ring(dimension, bounces, center, radius1, radius2);
    }

    public void delete() {
        if (son_ptr != null)
            ((Node) son_ptr).delete();
    }
}