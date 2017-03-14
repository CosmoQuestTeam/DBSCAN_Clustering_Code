/* HEADER FILE: Cluster.h */

#ifndef CLUSTER_H
#define CLUSTER_H

class Cluster
{
  /*************************************************/
  /* Declaration/Initialization of class variables */
  /*************************************************/
 public :
  int ClusterID;
  int ClusterMemberSize;
  float ClusterMeanConfidence;
  float ClusterMeanDiameter;
  float ClusterMeanXPosition;
  float ClusterMeanYPosition;
  float ClusterSDDiameter;
  float ClusterSDXPosition;
  float ClusterSDYPosition;
  std::vector < std::vector <float> > ClusterCraterMembers;
  
  Cluster(int CID, float XMean, float XSD, float YMean, float YSD, float DMean, float DSD, float CMean, int N, std::vector < std::vector <float> > CCM);
};

#endif
