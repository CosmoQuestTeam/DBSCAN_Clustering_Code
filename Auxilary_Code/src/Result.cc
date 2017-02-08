#include <stdio.h>
#include <stdlib.h>
#include <string>

#include "Result.h"

using namespace std;

Result::Result(string FN, int NC, int MC, int MS, double SR, double FRT, double TCT, double HMCT, double DBST, int NClus, double TT)
{
  Filename = FN;
  nCraters = NC;
  minCraters = MC;
  mapSpacing = MS;
  searchRadius = SR;
  fileReadTime = FRT;
  treeCreationTime = TCT;
  hashMapCreationTime = HMCT;
  dbscanTime = DBST;
  nClusters = NClus;
  totalTime = TT;
}
