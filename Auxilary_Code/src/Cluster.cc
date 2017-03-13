#include <stdlib.h>
#include <vector>

#include "Cluster.h"

using namespace std;

Cluster::Cluster(int CID, float XMean, float XSD, float YMean, float YSD, float DMean, float DSD, float CMean, int N, vector < vector <float> > CCM)
{
  ClusterID = CID;
  ClusterMemberSize = N;
  ClusterMeanConfidence = CMean;
  ClusterMeanDiameter = DMean;
  ClusterMeanXPosition = XMean;
  ClusterMeanYPosition = YMean;
  ClusterSDDiameter = DSD;
  ClusterSDXPosition = XSD;
  ClusterSDYPosition = YSD;
  ClusterCraterMembers = CCM;
}
