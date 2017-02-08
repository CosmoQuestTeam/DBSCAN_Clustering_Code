#include <fstream>
//#include <gsl/gsl_rng.h>
#include <iomanip>
#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <vector>

using namespace std;

int main(int args, char* argv[])
{
  /****************************************************/
  /* Declaration/Initialization of function variables */
  /****************************************************/
  const float diameter = 1200; // Unit: variable unit of length
  //const gsl_rng_type* T;
  //gsl_rng* r;
  float image_size; // Size of region (Unit: variable unit of length)
  int NumberofCraters; // Number of craters to generate
  ofstream File;
  string buffer;
  string OutputFilename;
  vector < float > row(4, 0.);
  vector < vector <float> > CraterPoints; // Four dimensional point (x, y, z) and confidence
  
  /***********************/
  /* Retrieve parameters */
  /***********************/
  if(args != 4)
  {
    printf("Usage: %s [Number of Craters] [Size of single dimension] [Output Filename]\n", argv[0]);
    cout << "Now terminating program..." << endl;
    return -1;
  }
  NumberofCraters = atoi(argv[1]);
  image_size = atof(argv[2]);
  OutputFilename = string(argv[3]);
  
  /*********************************/
  /* Setup random number generator */
  /*********************************/
  //gsl_rng_env_setup();
  //T = gsl_rng_default;
  //r = gsl_rng_alloc (T);
  srand (time(NULL));

  /*****************/
  /* Generate list */
  /*****************/
  for(int i=0; i<NumberofCraters; i++)
  {
    //row[0] = gsl_rng_uniform(r)*(image_size);
    //row[1] = gsl_rng_uniform(r)*(image_size);
    //row[2] = gsl_rng_uniform(r)*(diameter);
    row[0] = ((double)rand()/(double)(RAND_MAX))*(image_size); // x coordinate (Unit: variable unit of length)
    row[1] = ((double)rand()/(double)(RAND_MAX))*(image_size); // y coordinate (Unit: variable unit of length)
    row[2] = ((double)rand()/(double)(RAND_MAX))*(diameter); // diameter (Unit: variable unit of length)
    row[3] = 1; // Confidence
    CraterPoints.push_back(row);
  }

  /*************************/
  /* Open file to write to */
  /*************************/
  File.open(OutputFilename.c_str());

  /*******************************/
  /* Write crater points to file */
  /*******************************/
  File << "X\tY\tD\tC" << endl;
  for(int i=0; i<NumberofCraters; i++)
  {
    File << setprecision(4) << setw(7) << left << CraterPoints[i][0] << "\t" << setprecision(4) << setw(7) << left << CraterPoints[i][1] << "\t" << setprecision(5) << setw(7) << left << CraterPoints[i][2] << "\t" << left << CraterPoints[i][3] << endl;
  }
  File.close();

  return 0;
}
