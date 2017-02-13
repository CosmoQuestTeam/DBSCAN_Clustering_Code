/* HEADER FILE: CraterListPlotter.h */

#ifndef CRATERLISTPLOTTER_H
#define CRATERLISTPLOTTER_H

class CraterListPlotter
{
  /*************************************************/
  /* Declaration/Initialization of class variables */
  /*************************************************/
 public :
  CraterListPlotter(std::string InputFilename, int NumberGridSpacing, std::string OutputFilename);
  void PlotCraterPoints();
  void ReadCraterList();

 private :
  float MaxX;
  float MaxY;
  int NumberofGridSpacings;
  std::string CraterListFile;
  std::vector < std::vector <float> > CraterPoints;
  std::string OutputFile;
};

#endif
