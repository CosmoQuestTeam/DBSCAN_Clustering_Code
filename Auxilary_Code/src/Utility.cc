#include <fstream>
#include <iostream>
#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <vector>

#include "Utility.h"

using namespace std;

bool Utility::FileExists(string Filename)
{ 
  /*******************************************/
  /* Declaration/Initialization of Variables */
  /*******************************************/
  ifstream InFile;

  /***************************/
  /* Check existance of file */
  /***************************/
  InFile.open(Filename.c_str());
  if(InFile)
  { 
    InFile.close();
    return true; // If file exists
  }
  InFile.close();

  return false; // If file does not exist
}

float Utility::RoundUp(float arg, float nearestMultiple)
{
  /*******************************************/
  /* Declaration/Initialization of Variables */
  /*******************************************/
  float remainder;

  /***************************/
  /* Determine the remainder */
  /***************************/
  remainder = fmod(arg, nearestMultiple);

  return arg+nearestMultiple-remainder;
}
