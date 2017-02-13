/* HEADER FILE: CraterListGenerator.h */

#ifndef CRATERLISTGENERATOR_H
#define CRATERLISTGENERATOR_H

class CraterListGenerator
{
  /*************************************************/
  /* Declaration/Initialization of class variables */
  /*************************************************/
 public :
  CraterListGenerator(int NC, float IS);
  CraterListGenerator(int NC, int NClus, float IS);
  std::vector <int> GetClusterIDs();
  std::vector < std::vector <float> > GetClusterCenters();
  std::vector < std::vector <float> > GetCraterPoints();
  void ListGenerator1();
  void ListGenerator2();

 private :
  float image_size; // Approximate size of region (Unit: variable unit of length)
  int NumberofCraters; // Number of craters to generate
  int NumberofClusters; // Number of clusters to generate
  std::vector <int> ClusterIDs; // Cluster ID
  std::vector < std::vector <float> > ClusterCenters; // Center positions of all clusters
  std::vector < std::vector <float> > CraterPoints; // Two dimensional point (x, y), diameter, and confidence
};

#endif
