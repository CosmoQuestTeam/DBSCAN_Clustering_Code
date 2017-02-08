#include <fstream>
#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <string>
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
    return true;
  }
  InFile.close();

  return false;
}
