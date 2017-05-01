package Rstar;

/**************************/
/* Built-in java packages */
/**************************/

/*************************************************************************************/
/* R-tree class                                                                      */
/*                                                                                   */
/* the header of the RTree is organised as follows:                                  */
/* +-----------+-------------+---------------+---------------+--------------+------+ */
/* | dimension | num_of_data | num_of_dnodes | num_of_inodes | root_is_data | root | */
/* +-----------+-------------+---------------+---------------+--------------+------+ */
/*                                                                                   */

/*************************************************************************************/
public final class RTree {
    /*************************************************/
    /* Declaration/Initialization of class variables */
    /*************************************************/
    boolean re_level[];           // if re_level[i] is true, there was a reinsert on the ith level
    boolean root_is_data;         // true, if root is a data page
    byte header[];
    //  CachedBlockFile file;	  // storage manager for harddisc blocks
    float node_weight[];          // weight for simulation of cache
    int akt;                      // # of actually got data (get_next)
    int dimension;                // dimension of the data's
    int num_of_data;              // # of stored data
    int num_of_dnodes;              // # of stored data pages
    int num_of_inodes;              // # of stored directory pages
    int page_access = 0;
    int root;                     // block # of root node
    LinList re_data_cands = new LinList(); // data entries to reinsert -> see insert()
    RTNode root_ptr;              // root-node
    //protected int user_header;

    /*********************/
    /* RTree Constructor */

    /*********************/
    RTree(int _dimension) {
        dimension = _dimension;
        node_weight = new float[20];
        num_of_data = num_of_inodes = num_of_dnodes = 0;
        root_is_data = true;
        root_ptr = null;
        root_ptr = new RTDataNode(this);
        root = root_ptr.block;
    }

    /******************************************/
    /* Function returns number of stored data */

    /******************************************/
    int get_num() {
        return num_of_data;
    }

    /*****************************************************************************/
    /* Function inserts a new data entry into the tree. Insertion is propagated  */
    /* to the root node. If the root overflows, it has to be split into 2 nodes  */
    /* and a new root will be introduced, to hold the split nodes.               */

    /*****************************************************************************/
    void insert(Data d) {
        /****************************************************/
    /* Declaration/Initialization of function variables */
        /****************************************************/
        Data d_cand; // temp duplicates of d
        Data dc; // temp duplicates of d
        DirEntry de; // temp Object used to consruct new root dir entries when SPLIT takes place
        float nmbr[]; // root_ptr MBR
        int counter;
        int nroot; // block # of nroot_ptr
        int split_root = Constants.NONE;  // return of root_ptr.insert(d)
        RTDirNode nroot_ptr; // new root when the root is SPLIT
        RTNode sn[] = new RTNode[1]; // potential new node when SPLIT takes place


        // load root into memory
       
        /*
        * no overflow occured until now. re_level array indicates if a re_insert
        * has been done at the specific level of the tree. Initially all entries
        * in this array should be set to false
        */
        re_level = new boolean[root_ptr.level + 1];
        for (int i = 0; i <= root_ptr.level; i++)
            re_level[i] = false;

        /*
        * insert d into re_data_cands as the first entry to insert
        * make a copy of d because it shouldnt be erased later
        */
        dc = (Data) d.clone(); // make datacopy of d
        re_data_cands.insert(dc); //insert the datacopy into the
        //list of pending to be inserted data
        counter = -1;
        while (re_data_cands.get_num() > 0) {
            // first try to insert data, then directory entries
            d_cand = (Data) re_data_cands.get_first();
            if (d_cand != null) {
                // since erase deletes the according data element of the
                // list, we should make a copy of the data before
                // erasing it
                dc = (Data) d_cand.clone();
                re_data_cands.erase();

                // start recursive insert from root
                split_root = ((Node) root_ptr).insert(dc, sn);
            } else
                Constants.error("RTree::insert: inconsistent list re_data_cands", true);

            if (split_root == Constants.SPLIT)
            /*
            * insert has lead to split --> create new root having as sons
            * old root and sn
            */ {
                //initialize new root
                nroot_ptr = new RTDirNode(this);
                nroot_ptr.son_is_data = root_is_data;
                nroot_ptr.level = (short) (root_ptr.level + 1);
                nroot = nroot_ptr.block;

                // a new direntry is introduced having as son the old root
                de = new DirEntry(dimension, root_is_data, this);
                nmbr = ((Node) root_ptr).get_mbr();
                // store the mbr of the root to the direntry
                System.arraycopy(nmbr, 0, de.bounces, 0, 2 * dimension);
                de.son = root_ptr.block;
                de.son_ptr = root_ptr;
                de.son_is_data = root_is_data;
                de.num_of_data = ((Node) root_ptr).get_num_of_data();
                // add de to the new root
                nroot_ptr.enter(de);

                // a new direntry is introduced having as son the brother(split) of the old root
                de = new DirEntry(dimension, root_is_data, this);
                nmbr = ((Node) sn[0]).get_mbr();
                System.arraycopy(nmbr, 0, de.bounces, 0, 2 * dimension);
                de.son = sn[0].block;
                de.son_ptr = sn[0];
                de.son_is_data = root_is_data;
                de.num_of_data = ((Node) sn[0]).get_num_of_data();
                nroot_ptr.enter(de);

                // replace the root of the tree with the new node
                root = nroot;
                root_ptr = nroot_ptr;

                //System.out.println("New root direntries:");
                //for (int l = 0; l < root_ptr.get_num(); l++)
                //{
                //    for (int k=0; k<2*dimension; k++)
                //        System.out.print(((RTDirNode)root_ptr).entries[l].bounces[k] + " ");
                //    System.out.println(" ");
                //}

                // the new root is a directory node
                root_is_data = false;
            }

            /*************************************************/
            /* Go to the next data object to be (re)inserted */
            /*************************************************/
            counter++;
        }

        /************************************************************/
        /* Increase number of data elements in tree after insertion */
        /************************************************************/
        num_of_data++;
    }

    /********************************************/
    /* Return the ith data element in the tree. */
    /* --> Propagate to the root node.          */

    /********************************************/
    Data get(int i) {
        /****************************************************/
	/* Declaration/Initialization of function variables */
        /****************************************************/
        Data d;

        /***************************/
        /* Propagate to root node  */
	/* to retrieve ith element */
        /***************************/
        d = ((Node) root_ptr).get(i);

        return d;
    }

    /**
     * Print the objects in the tree that intersect with the parameter mbr.
     * --> Propagate to the root node.
     */
    void region(float mbr[]) {


        ((Node) root_ptr).region(mbr);
    }

    /**
     * Print the objects in the tree that intersect with the parameter point.
     * --> Propagate to the root node.
     */
    void point_query(float p[]) {


        ((Node) root_ptr).point_query(p);
    }


    /**
     * Return to Nearest the object in the tree that is nearest
     * to parameter point. --> Propagate to the root node.
     */
    void NearestNeighborQuery(PPoint QueryPoint, PPoint Nearest) {
        float/*[]*/ nearest_distanz/* = new float[1]*/;


        nearest_distanz/*[0]*/ = Constants.MAXREAL;

        ((Node) root_ptr).NearestNeighborSearch(QueryPoint, Nearest, nearest_distanz);
    }

    /**
     * Return to res the objects in the tree that are nearest
     * to parameter point. --> Propagate to the root node.
     */
    void NearestNeighborQuery(PPoint QueryPoint, SortedLinList res) {
        float nearest_distanz;


        nearest_distanz = Constants.MAXREAL;

        ((Node) root_ptr).NearestNeighborSearch(QueryPoint, res, nearest_distanz);
    }

    /**
     * Return to res the k objects in the tree that are nearest
     * to parameter point. --> Propagate to the root node.
     */
    void k_NearestNeighborQuery(PPoint QueryPoint, int k, SortedLinList NeighborList) {
        int i, l, n;
        float nearest_distanz;
        Data p;

        nearest_distanz = Constants.MAXREAL;

        // NeighborListListe vorbereiten
        NeighborList.set_sorting(true);

        for (i = 0; i < k; i++) {
            p = new Data(dimension);
            for (l = 0; l < dimension; l++)
                p.data[l] = Constants.MAXREAL;
            p.distanz = Constants.MAXREAL;
            NeighborList.insert(p);
        }

        ((Node) root_ptr).NearestNeighborSearch(QueryPoint, NeighborList, nearest_distanz);
    }

    /************************************/
    /* Return to res the objects in the */
    /* tree that intersect with the     */
    /* parameter point.                 */
    /* --> Propagate to the root node.  */

    /************************************/
    public void point_query(PPoint p, SortedLinList res) {
        page_access = 0;
        ((Node) root_ptr).point_query(p, res);
    }

    /**
     * Return to res the objects in the tree that intersect with the parameter circle.
     * --> Propagate to the root node.
     */
    public void rangeQuery(PPoint center, float radius, SortedLinList res) {
        page_access = 0;


        ((Node) root_ptr).rangeQuery(center, radius, res);
    }

    /**
     * Return to res the objects in the tree that intersect with the parameter ring,
     * defined by the circles provided that radius1<radius2.
     * --> Propagate to the root node.
     */
    void ringQuery(PPoint center, float radius1, float radius2, SortedLinList res) {
        page_access = 0;


        ((Node) root_ptr).ringQuery(center, radius1, radius2, res);
    }

    /**
     * Return to res the objects in the tree that intersect with the parameter mbr.
     * --> Propagate to the root node.
     */
    void rangeQuery(float mbr[], SortedLinList res) {
        page_access = 0;


        ((Node) root_ptr).rangeQuery(mbr, res);
    }


    void range_nnQuery(float mbr[], SortedLinList res, PPoint Nearest) {
        PPoint center;
        float distanz = Constants.MAXREAL;
        boolean success = false;
        int i;

        center = new PPoint(dimension);
        for (i = 0; i < dimension; i++)
            center.data[i] = (mbr[2 * i] + mbr[2 * i + 1]) / (float) 2.0;


        ((Node) root_ptr).range_nnQuery(mbr, res, center, distanz, Nearest, success);
    }


    void overlapping(float p[], int nodes_t[]) {

        ((Node) root_ptr).overlapping(p, nodes_t);
    }


    /**
     * Return to nodes_a[] the # of nodes at each level of the tree.
     * --> Propagate to the root node.
     */
    void nodes(int nodes_a[]) {
        ((Node) root_ptr).nodes(nodes_a);
    }


    protected void delete() {

        if (root_ptr != null) {
            ((Node) root_ptr).delete();
        }


        System.out.println("Rtree saved: num_of_data = " + num_of_data +
                " num_of_inodes = " + num_of_inodes +
                " num_of_dnodes = " + num_of_dnodes);
    }
}
