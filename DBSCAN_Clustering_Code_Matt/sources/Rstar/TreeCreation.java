/********************************************/
/* Modified from Test.java created by Nikos */
/********************************************/
package Rstar;

/**************************/
/* Built-in java packages */
/**************************/

public class TreeCreation {
    /*************************************************/
    /* Declaration/Initialization of class variables */
    /*************************************************/
    public int displaylevel = 199; // Matt Note: I think this is used for the gui
    public RectFrame f; // Matt Note: I think this is used for the gui
    public RTree rt;
    private Float interval;
    private int dimension;

    /****************************/
    /* TreeCreation Constructor */

    /****************************/
    public TreeCreation(int dimension, Float image_size, Float interval) {
        /****************************************************/
    /* Declaration/Initialization of function variables */
        /****************************************************/
        boolean response;
        Data d = null;
        float const1;
        int index_rect;
        // int interval_1;
        // int interval_2;
        // int interval_3;
        // int interval_4;
        // int interval_5;
        // int interval_6;
        // int interval_7;
        // int interval_8;
        // int interval_9;
        // int interval_10;
        // int iterations = 0;
        // int tasks = 0;
        int numRecLine;

        /*********************/
	/* Initialize R-tree */
        /*********************/
        this.dimension = dimension;
        rt = new RTree(dimension);

        /********************************************/
	/* Initializing/Determining constant values */
        /********************************************/
        this.interval = interval;
        numRecLine = (int) (image_size / interval);
        const1 = numRecLine * interval;
        response = (const1 < image_size);

        /****************************************************************/
	/* Code commented out below is Matt's attempt to speed up code. */
	/* Yielded same result :) and timing :(.                        */
        /****************************************************************/

        // /*********************************************************/
        // /* Determine optimal number of iterations for inner loop */
        // /*********************************************************/
        // for (int i=10; i>0; i--)
        // {
        //     if ((numRecLine % i) == 0)
        //     {
        // 	tasks = i;
        // 	iterations = (int) (numRecLine/tasks);
        // 	break;
        //     }
        // }

        // /***************/
        // /* Create tree */
        // /***************/
        // interval_2 = 2*interval;
        // interval_3 = 3*interval;
        // interval_4 = 4*interval;
        // interval_5 = 5*interval;
        // interval_6 = 6*interval;
        // interval_7 = 7*interval;
        // interval_8 = 8*interval;
        // interval_9 = 9*interval;
        // interval_10 = 10*interval;
        // index_rect = 0;
        // for (int j=0; j<numRecLine; j++)
        // {
        //     float const2 = j*interval;
        //     for(int i=0; i<iterations; i++)
        //     {
        // 	float const3 = i*tasks*interval;
        // 	switch (tasks) {
        // 	case 10: rt.insert(FillDataVector(dimension, index_rect, const3, const3+interval, const2, const2+interval));
        // 	         index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval, const3+interval_2, const2, const2+interval));
        // 		 index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval_2, const3+interval_3, const2, const2+interval));
        // 		 index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval_3, const3+interval_4, const2, const2+interval));
        // 		 index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval_4, const3+interval_5, const2, const2+interval));
        // 		 index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval_5, const3+interval_6, const2, const2+interval));
        // 		 index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval_6, const3+interval_7, const2, const2+interval));
        // 		 index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval_7, const3+interval_8, const2, const2+interval));
        // 		 index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval_8, const3+interval_9, const2, const2+interval));
        // 		 index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval_9, const3+interval_10, const2, const2+interval));
        // 		 index_rect++;
        // 		 break;
        // 	case 9:  rt.insert(FillDataVector(dimension, index_rect, const3, const3+interval, const2, const2+interval));
        // 	         index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval, const3+interval_2, const2, const2+interval));
        // 		 index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval_2, const3+interval_3, const2, const2+interval));
        // 		 index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval_3, const3+interval_4, const2, const2+interval));
        // 		 index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval_4, const3+interval_5, const2, const2+interval));
        // 		 index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval_5, const3+interval_6, const2, const2+interval));
        // 		 index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval_6, const3+interval_7, const2, const2+interval));
        // 		 index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval_7, const3+interval_8, const2, const2+interval));
        // 		 index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval_8, const3+interval_9, const2, const2+interval));
        // 		 index_rect++;
        // 		 break;
        // 	case 8:  rt.insert(FillDataVector(dimension, index_rect, const3, const3+interval, const2, const2+interval));
        // 	         index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval, const3+interval_2, const2, const2+interval));
        // 		 index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval_2, const3+interval_3, const2, const2+interval));
        // 		 index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval_3, const3+interval_4, const2, const2+interval));
        // 		 index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval_4, const3+interval_5, const2, const2+interval));
        // 		 index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval_5, const3+interval_6, const2, const2+interval));
        // 		 index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval_6, const3+interval_7, const2, const2+interval));
        // 		 index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval_7, const3+interval_8, const2, const2+interval));
        // 		 index_rect++;
        // 		 break;
        // 	case 7:  rt.insert(FillDataVector(dimension, index_rect, const3, const3+interval, const2, const2+interval));
        // 	         index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval, const3+interval_2, const2, const2+interval));
        // 		 index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval_2, const3+interval_3, const2, const2+interval));
        // 		 index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval_3, const3+interval_4, const2, const2+interval));
        // 		 index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval_4, const3+interval_5, const2, const2+interval));
        // 		 index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval_5, const3+interval_6, const2, const2+interval));
        // 		 index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval_6, const3+interval_7, const2, const2+interval));
        // 		 index_rect++;
        // 		 break;
        // 	case 6:  rt.insert(FillDataVector(dimension, index_rect, const3, const3+interval, const2, const2+interval));
        // 	         index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval, const3+interval_2, const2, const2+interval));
        // 		 index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval_2, const3+interval_3, const2, const2+interval));
        // 		 index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval_3, const3+interval_4, const2, const2+interval));
        // 		 index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval_4, const3+interval_5, const2, const2+interval));
        // 		 index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval_5, const3+interval_6, const2, const2+interval));
        // 		 index_rect++;
        // 		 break;
        // 	case 5:  rt.insert(FillDataVector(dimension, index_rect, const3, const3+interval, const2, const2+interval));
        // 	         index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval, const3+interval_2, const2, const2+interval));
        // 		 index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval_2, const3+interval_3, const2, const2+interval));
        // 		 index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval_3, const3+interval_4, const2, const2+interval));
        // 		 index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval_4, const3+interval_5, const2, const2+interval));
        // 		 index_rect++;
        // 		 break;
        // 	case 4:  rt.insert(FillDataVector(dimension, index_rect, const3, const3+interval, const2, const2+interval));
        // 	         index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval, const3+interval_2, const2, const2+interval));
        // 		 index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval_2, const3+interval_3, const2, const2+interval));
        // 		 index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval_3, const3+interval_4, const2, const2+interval));
        // 		 index_rect++;
        // 		 break;
        // 	case 3:  rt.insert(FillDataVector(dimension, index_rect, const3, const3+interval, const2, const2+interval));
        // 	         index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval, const3+interval_2, const2, const2+interval));
        // 		 index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval_2, const3+interval_3, const2, const2+interval));
        // 		 index_rect++;
        // 		 break;
        // 	case 2:  rt.insert(FillDataVector(dimension, index_rect, const3, const3+interval, const2, const2+interval));
        // 	         index_rect++;
        // 		 rt.insert(FillDataVector(dimension, index_rect, const3+interval, const3+interval_2, const2, const2+interval));
        // 		 index_rect++;
        // 		 break;
        // 	default: rt.insert(FillDataVector(dimension, index_rect, const3, const3+interval, const2, const2+interval));
        // 	         index_rect++;
        // 		 break;
        // 	}
        //     }
        //     if (response)
        //     {
        //  	rt.insert(FillDataVector(dimension, index_rect, const1, image_size, const2, const2+interval));
        //  	index_rect++;
        //     }
        // }
        // if (response)
        // {
        //     for (int i=0; i<numRecLine; i++)
        //     {
        // 	float const3 = i*interval;
        // 	rt.insert(FillDataVector(dimension, index_rect, const3, const3+interval, const1, image_size));
        // 	index_rect++;
        //     }
        //     if (response)
        //     {
        // 	rt.insert(FillDataVector(dimension, index_rect, const1, image_size, const1, image_size));
        // 	index_rect++;
        //     }
        // }

        /***************/
	/* Create tree */
        /***************/
        index_rect = 0;
        for (int j = 0; j < numRecLine; j++) {
            Float const2 = j * interval;
            for (int i = 0; i < numRecLine; i++) {
                Float const3 = i * interval;
                rt.insert(FillDataVector(dimension, index_rect, const3, const3 + interval, const2, const2 + interval));
                index_rect++;
            }
            if (response) {
                rt.insert(FillDataVector(dimension, index_rect, const1, image_size, const2, const2 + interval));
                index_rect++;
            }
        }
        if (response) {
            for (int i = 0; i < numRecLine; i++) {
                Float const3 = i * interval;
                rt.insert(FillDataVector(dimension, index_rect, const3, const3 + interval, const1, image_size));
                index_rect++;
            }
            if (response) {
                rt.insert(FillDataVector(dimension, index_rect, const1, image_size, const1, image_size));
                index_rect++;
            }
        }
    }

    /***********************************/
    /* Function that fills data vector */

    /***********************************/
    public Data FillDataVector(int dim, int index, float x1, float x2, float y1, float y2) {
        /****************************************************/
	/* Declaration/Initialization of function variables */
        /****************************************************/
        Data a = null;

        /********************/
	/* Fill data vector */
        /********************/
        a = new Data(dim, index);
        a.data[0] = x1;
        a.data[1] = x2;
        a.data[2] = y1;
        a.data[3] = y2;

        return a;
    }

    public void exit(int exitcode) {
        if ((rt != null) && (exitcode == 0))
            rt.delete();
        System.exit(0);
    }
}
