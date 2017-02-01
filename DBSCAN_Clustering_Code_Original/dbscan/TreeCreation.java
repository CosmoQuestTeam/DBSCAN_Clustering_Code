/* Modified from Test.java created by Nikos
 */

package Rstar;

import java.awt.*;

public class TreeCreation {
	public TreeCreation(int dimension,Float image_size) {
		this.dimension = dimension;

       		// initialize tree
		rt = new RTree(dimension);

		Data d;

		// create a new rectangle
		// copy the rectangle's coords into d's data
		int interval = 30;
		float total_x = image_size;
		float total_y = image_size;
		int numCol = 0;
		int numRow = 0;
		int numRecLine = 0;
		numRecLine = (int) (total_x / interval);
		int index_rect = 0;
		while (numCol * interval < total_y) {
			if ((numCol + 1) * interval < total_y) {
				for (int i = 0; i < numRecLine; i++) {
					if ((numRow + 1) * interval < total_x) {
						d = new Data(dimension, index_rect);
						d.data = new float[dimension * 2];
						d.data[0] = i * interval;
						d.data[1] = interval + i * interval;
						d.data[2] = numCol * interval;
						d.data[3] = numCol * interval + interval;
						// d.print();
						rt.insert(d);
						numRow++;
						index_rect++;
					} else {
						d = new Data(dimension, index_rect);
						d.data = new float[dimension * 2];
						d.data[0] = i * interval;
						d.data[1] = total_x;
						d.data[2] = numCol * interval;
						d.data[3] = numCol * interval + interval;
						// d.print();
						rt.insert(d);
						numRow++;
						index_rect++;
					}

				}
				if (numRecLine * interval < total_x) {
					d = new Data(dimension, index_rect);
					d.data = new float[dimension * 2];
					d.data[0] = numRecLine * interval;
					d.data[1] = total_x;
					d.data[2] = numCol * interval;
					d.data[3] = numCol * interval + interval;
					// d.print();
					rt.insert(d);
					index_rect++;
				}
				numRow = 0;
			} else {
				for (int i = 0; i < numRecLine; i++) {
					if ((numRow + 1) * interval < total_x) {
						d = new Data(dimension, index_rect);
						d.data = new float[dimension * 2];
						d.data[0] = i * interval;
						d.data[1] = interval + i * interval;
						d.data[2] = numCol * interval;
						d.data[3] = total_y;
						// d.print();
						rt.insert(d);
						numRow++;
						index_rect++;
					} else {
						d = new Data(dimension, index_rect);
						d.data = new float[dimension * 2];
						d.data[0] = i * interval;
						d.data[1] = total_x;
						;
						d.data[2] = numCol * interval;
						d.data[3] = total_y;
						// d.print();
						rt.insert(d);
						numRow++;
						index_rect++;
					}

				}
				if (numRecLine * interval < total_x) {
					d = new Data(dimension, index_rect);
					d.data = new float[dimension * 2];
					d.data[0] = numRecLine * interval;
					d.data[1] = total_x;
					d.data[2] = numCol * interval;
					d.data[3] = total_y;
					// d.print();
					rt.insert(d);
					index_rect++;
				}

			}
			numCol++;
		}

		// Create the Rectangle Display Window
		// f = new RectFrame(this);
		// f.pack();
		// f.show();

	}

	public void exit(int exitcode) {
		if ((rt != null) && (exitcode == 0))
			rt.delete();
		System.exit(0);
	}

	public RTree rt;
	public RectFrame f;
	// public QueryFrame qf;
	public int displaylevel = 199;
	private int dimension;
}
