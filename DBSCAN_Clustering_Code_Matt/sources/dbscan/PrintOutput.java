/*******************************************************************************
 Purpose: This is the output file that has two different modes of output.  The
 detailed one you probably want is the printListWithSD.
 *******************************************************************************/
package dbscan;

/**************************/
/* Built-in java packages */
/**************************/

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

@SuppressWarnings("unchecked")
public class PrintOutput {
    public static void printListLong(Vector<List> cluster, int ind) {
        try {
            FileWriter fstream;         //create the file stream, but don't declare it so we can check to make sure we don't overwrite ... the previous version was: //FileWriter fstream = new FileWriter("out_long.txt");
            BufferedWriter out = null;  //same with the BufferedWriter ... the previous version was: //BufferedWriter out = new BufferedWriter(fstream);

            //Go through a switch statement to make sure that we don't over-write up to 5 files.
            switch (ind) {
                case 1: {
                    fstream = new FileWriter("out_long_1.txt");
                    out = new BufferedWriter(fstream);
                }
                break;
            }


            //This next code block outputs the short version of the clustered results.
            int index = 0;
            for (List<Point> l : cluster) {

                Iterator<Point> j = l.iterator();
                out.write("cluster " + Integer.toString(index) + " :");
                out.newLine();
                while (j.hasNext()) {
                    Point w = j.next();
//					cluster.add(new Label((w.getX() + "," + w.getY()+","+w.getD())));


                    out.write(Float.toString(w.getX()));
                    out.write("\t");
                    out.write(Float.toString(w.getY()));
                    out.write("\t");
                    out.write(Float.toString(w.getD()));
                    out.write("\t");
                    out.write(Float.toString(w.getC()));
                    out.newLine();
                    //Close the output stream
                }

//              out.write(Float.toString(MathUtility.StandardDeviationX(l)));
//              out.newLine();
//              out.write(Float.toString(MathUtility.StandardDeviationY(l)));
//              out.newLine();
//              out.write(Float.toString(MathUtility.StandardDeviationD(l)));
//              out.newLine();
                index++;

            }

            out.close();
        } catch (Exception e) //Catch exception if any
        {
            System.err.println("Error: " + e.getMessage());
        }
    }


    public static void printListShort(Vector<List> cluster, int ind) {
        try {
            FileWriter fstream;         //FileWriter fstream = new FileWriter("out_short.txt");
            BufferedWriter out = null;  //BufferedWriter out = new BufferedWriter(fstream);
            switch (ind) {
                case 1: {
                    fstream = new FileWriter("out_short_1.txt");
                    out = new BufferedWriter(fstream);
                }
                break;
            }


            float weightedMeanSD_X = 0;
            float weightedMeanSD_Y = 0;
            float weightedMeanSD_D = 0;


            //This outputs the long version of the clustered results.
            out.write("Cluster_ID" + "\t" + "X_wm" + "\t" + "X_sd" + "\t" + "Y_wm" + "\t" + "Y_sd" + "\t" + "D_wm" + "\t" + "D_sd" + "\t" + "C_wm" + "\t" + "N");
            out.newLine();
            int index = 1;
            for (List<Point> l : cluster) {
                if (l.size() > 1) {
                    weightedMeanSD_X = MathUtility.WeightedStandardDeviationX(l);
                    weightedMeanSD_Y = MathUtility.WeightedStandardDeviationY(l);
                    weightedMeanSD_D = MathUtility.WeightedStandardDeviationD(l);

                    out.write(Integer.toString(index) + "\t");

                    out.write(Float.toString(MathUtility.weightedMean_x) + "\t");
                    out.write(Float.toString(weightedMeanSD_X) + "\t");

                    out.write(Float.toString(MathUtility.weightedMean_y) + "\t");
                    out.write(Float.toString(weightedMeanSD_Y) + "\t");

                    out.write(Float.toString(MathUtility.weightedMean_d) + "\t");
                    out.write(Float.toString(weightedMeanSD_D) + "\t");

                    out.write(Float.toString(MathUtility.weightedMean_C(l)) + "\t");
                    out.write(Integer.toString(l.size()));

                    out.newLine();
                } else {
                    Iterator<Point> j = l.iterator();
                    Point w = j.next();

                    weightedMeanSD_X = w.getX();
                    weightedMeanSD_Y = w.getY();
                    weightedMeanSD_D = w.getD();

                    out.write(Integer.toString(index) + "\t");

                    out.write(Float.toString(weightedMeanSD_X) + "\t");
                    out.write("0\t");

                    out.write(Float.toString(weightedMeanSD_Y) + "\t");
                    out.write("0\t");

                    out.write(Float.toString(weightedMeanSD_D) + "\t");
                    out.write("0\t");

                    out.write("1.0\t");
                    out.write(Integer.toString(l.size()));

                    out.newLine();
                }
                index++;
            }

            out.close();

            //Print to the command line how many clusters the code found.
            System.out.println("Total clusters found: " + (index - 1)); //-1 because the loop type increments the counter one past the total number of clusters
        } catch (Exception e) //Catch exception if any
        {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
