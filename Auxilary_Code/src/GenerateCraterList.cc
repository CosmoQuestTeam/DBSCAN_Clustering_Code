#include <fstream>
#include <iomanip>
#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <string>
#include <vector>

#include "CraterListGenerator.h"

using namespace std;

int main(int args, char* argv[])
{
  /****************************************************/
  /* Declaration/Initialization of function variables */
  /****************************************************/
  float image_size; // Approximate size of region (Unit: variable unit of length)
  int NumberofCraters; // Number of craters to generate
  int NumberofClusters; // Number of clusters to generate
  ofstream File;
  string OutputFilename;
  vector <int> ClusterIDs; // Cluster IDs
  vector < vector <float> > ClusterCenters; // Center positions of all clusters
  vector < vector <float> > CraterPoints; // Two dimensional point (x, y), diameter, and confidence
  
  /*********************/
  /* Verify parameters */
  /*********************/
  if((args != 4) && (args != 5))
  {
    printf("Usage: %s [Number of Craters] [Size of single dimension] [Output Filename]\n", argv[0]);
    cout << "or" << endl;
    printf("Usage: %s [Number of Craters] [Number of Clusters] [Size of single dimension] [Output Filename]\n", argv[0]);
    cout << "Now terminating program..." << endl;
    return -1;
  }

  /**********************/
  /* Create crater list */
  /**********************/
  if(args == 4)
  {
    /***********************/
    /* Retrieve parameters */
    /***********************/
    NumberofCraters = atoi(argv[1]);
    image_size = atof(argv[2]); 
    OutputFilename = string(argv[3]);
    
    /*****************/
    /* Generate list */
    /*****************/
    CraterListGenerator myList(NumberofCraters, image_size);
    myList.ListGenerator1();
    CraterPoints = myList.GetCraterPoints();
  }
  else
  {
    /***********************/
    /* Retrieve parameters */
    /***********************/
    NumberofCraters = atoi(argv[1]);
    NumberofClusters = atoi(argv[2]);
    image_size = atof(argv[3]);
    OutputFilename = string(argv[4]);
    
    /*****************/
    /* Generate list */
    /*****************/
    CraterListGenerator myList(NumberofCraters, NumberofClusters, image_size);
    myList.ListGenerator2();
    ClusterCenters = myList.GetClusterCenters();
    ClusterIDs = myList.GetClusterIDs();
    CraterPoints = myList.GetCraterPoints();
  }

  /*************************/
  /* Open file to write to */
  /*************************/
  string NewOutputFilename = OutputFilename.substr(0, OutputFilename.rfind(".txt"))+string(".txt");
  File.open(NewOutputFilename.c_str());

  /*******************************/
  /* Write crater points to file */
  /*******************************/
  File << "X\tY\tD\tC" << endl;
  for(int i=0; i<NumberofCraters; i++)
  {
    File << setprecision(4) << setw(7) << left << CraterPoints[i][0] << "\t" 
	 << setprecision(4) << setw(7) << left << CraterPoints[i][1] << "\t" 
	 << setprecision(5) << setw(7) << left << CraterPoints[i][2] << "\t" 
	 << left << CraterPoints[i][3] << endl;
  }
  File.close();

  if(args == 5)
  {
    /*************************/
    /* Open file to write to */
    /*************************/
    NewOutputFilename = OutputFilename.substr(0, OutputFilename.rfind(".txt"))+string("_tagged.txt");
    File.open(NewOutputFilename.c_str());

    /*******************************/
    /* Write crater points to file */
    /*******************************/
    File << "ClusterID\tClusterX\tClusterY\tX\tY\tD\tC" << endl;
    for(int i=0; i<NumberofCraters; i++)
    {
      File << left << ClusterIDs[i] << "\t\t" 
	   << setprecision(4) << setw(7) << left << ClusterCenters[ClusterIDs[i]][0] << "\t\t"
	   << setprecision(4) << setw(7) << left << ClusterCenters[ClusterIDs[i]][1] << "\t\t"
	   << setprecision(4) << setw(7) << left << CraterPoints[i][0] << "\t" 
	   << setprecision(4) << setw(7) << left << CraterPoints[i][1] << "\t" 
	   << setprecision(5) << setw(7) << left << CraterPoints[i][2] << "\t" 
	   << left << CraterPoints[i][3] << endl;
    }
    File.close();
  }

  return 0;
}
