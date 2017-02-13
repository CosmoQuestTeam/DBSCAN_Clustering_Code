#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <string>
#include <vector>

#include "CraterListPlotter.h"

using namespace std;

int main(int args, char* argv[])
{
  /****************************************************/
  /* Declaration/Initialization of function variables */
  /****************************************************/
  int NumberofGridSpacings;
  string InputFilename;
  string OutputFilename;

  /***********************/
  /* Retrieve parameters */
  /***********************/
  if(args != 4)
  {
    printf("Usage: %s [Input crater list filename] [Number of grid spacings] [Output plot filename]\n", argv[0]);
    cout << "Now terminating program..." << endl;
    return -1;
  }
  InputFilename = string(argv[1]);
  NumberofGridSpacings = atof(argv[2]);
  OutputFilename = string(argv[3]);

  /******************************/
  /* Initialize crater plotting */
  /******************************/
  CraterListPlotter myPlotter(InputFilename, NumberofGridSpacings, OutputFilename);

  /***********************/
  /* Read-in crater list */
  /***********************/
  myPlotter.ReadCraterList();
  
  /****************/
  /* Plot craters */
  /****************/
  myPlotter.PlotCraterPoints();

  return 0;
}
