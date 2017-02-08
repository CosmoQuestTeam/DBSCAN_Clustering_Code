/* HEADER FILE: Result.h */

#ifndef RESULT_H
#define RESULT_H

class Result
{
  /*************************************************/
  /* Declaration/Initialization of class variables */
  /*************************************************/
 public :
  double treeCreationTime;
  double dbscanTime;
  double hashMapCreationTime;
  double fileReadTime;
  double searchRadius;
  double totalTime;
  int minCraters;
  int mapSpacing;
  int nCraters;
  int nClusters;
  std::string Filename;
  
  Result(std::string FN, int NC, int MC, int MS, double SR, double RFT, double CTT, double HMCT, double DBST, int NClus, double TT);
};

#endif
