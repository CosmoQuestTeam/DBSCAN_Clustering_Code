#include <gsl/gsl_randist.h>
#include <gsl/gsl_rng.h>
#include <iostream>
#include <vector>

#include "CraterListGenerator.h"

using namespace std;

CraterListGenerator::CraterListGenerator(int NC, float IS)
{
  NumberofCraters = NC;
  image_size = IS;
}

CraterListGenerator::CraterListGenerator(int NC, int NClus, float IS)
{
  NumberofCraters = NC;
  NumberofClusters = NClus;
  image_size = IS;
}

vector <int> CraterListGenerator::GetClusterIDs()
{
  return ClusterIDs;
}

vector < vector <float> > CraterListGenerator::GetClusterCenters()
{
  return ClusterCenters;
}

vector < vector <float> > CraterListGenerator::GetCraterPoints()
{
  return CraterPoints;
}

void CraterListGenerator::ListGenerator1()
{
  /****************************************************/
  /* Declaration/Initialization of function variables */
  /****************************************************/
  const float diameter = 1200; // Unit: variable unit of length
  const gsl_rng_type* T;
  gsl_rng* r;
  vector < float > row(4, 0.0);
  vector < vector <float> > tempCraterPoints; // Two dimensional point (x, y), diameter, and confidence
    
  /*********************************/
  /* Setup random number generator */
  /*********************************/
  gsl_rng_env_setup();
  T = gsl_rng_default;
  r = gsl_rng_alloc (T);

  /***********************/
  /* Generate event list */
  /***********************/
  for(int i=0; i<NumberofCraters; i++)
  {
    row[0] = gsl_rng_uniform(r)*(image_size); // x coordinate (Unit: variable unit of length)
    row[1] = gsl_rng_uniform(r)*(image_size); // y coordinate (Unit: variable unit of length)
    row[2] = gsl_rng_uniform(r)*(diameter); // diameter (Unit: variable unit of length)
    row[3] = 1; // Confidence

    /****************/
    /* Record point */
    /****************/
    tempCraterPoints.push_back(row);
  }

  /**********************************/
  /* Record list to class container */
  /**********************************/
  CraterPoints = tempCraterPoints;

  return;
}

void CraterListGenerator::ListGenerator2()
{
  /*******************************************/
  /* Declaration/Initialization of variables */
  /*******************************************/
  const float MeanDiameter = 25; // Crater mean diameter
  const float SigmaDiameter = 0.1*MeanDiameter; // Crater diameter standard deviation
  const gsl_rng_type* T;
  gsl_rng* r;
  vector <float> row(4, 0.0);
  vector <int> tempClusterIDs;
  vector < vector <float> > tempClusterCenters;
  vector < vector <float> > tempCraterPoints; // Two dimensional point (x, y), diameter, and confidence

  /*********************************/
  /* Setup random number generator */
  /*********************************/
  gsl_rng_env_setup();
  T = gsl_rng_default;
  r = gsl_rng_alloc (T);

  /**************************/
  /* Choose cluster centers */
  /**************************/
  for(int i=0; i<NumberofClusters; i++)
  {
    float p[2] = {gsl_rng_uniform(r)*(image_size), gsl_rng_uniform(r)*(image_size)};
    tempClusterCenters.push_back(vector<float>(p, p+(sizeof p)/(sizeof p[0])));
  }
  for(int i=0; i<(int)tempClusterCenters.size(); i++)
    cout << "Cluster center " << i << ": (" << tempClusterCenters[i][0] << ", " << tempClusterCenters[i][1] << ")." << endl;

  /********************************************************************************/
  /* Generate event list (Note: Steps to generate list provided by Stuart Robbin) */
  /********************************************************************************/
  for(int i=0; i<NumberofCraters; i++)
  {
    /*****************************************/
    /* Randomly choose one of the cluster    */
    /* centers to place current point nearby */
    /*****************************************/
    int index = gsl_rng_uniform_int(r, NumberofClusters);
    tempClusterIDs.push_back(index);

    /*********************************************/
    /* Using a gaussian distribution, choose the */ 
    /* (x, y) position of the crater relative to */
    /* the randomly chosen center from previous  */
    /* step                                      */
    /*********************************************/
    float SigmaX = 0.05*tempClusterCenters[index][0];
    float SigmaY = 0.05*tempClusterCenters[index][1];
    float x = tempClusterCenters[index][0]+gsl_ran_gaussian(r, SigmaX);
    float y = tempClusterCenters[index][1]+gsl_ran_gaussian(r, SigmaY);
    row[0] = (x >= 0) ? x : 0; // x coordinate (Unit: variable unit of length)
    row[1] = (y >= 0) ? y : 0; // y coordinate (Unit: variable unit of length)

    /*********************************************/
    /* Using a gaussian distribution, choose the */
    /* diameter of the crater and set the value  */
    /* of confidence to 1                        */
    /*********************************************/
    row[2] = MeanDiameter+gsl_ran_gaussian(r, SigmaDiameter); // diameter (Unit: variable unit of length)
    row[3] = 1; // Confidence

    /****************/
    /* Record point */
    /****************/
    tempCraterPoints.push_back(row);
  }

  /*****************************************************/
  /* Record list and cluster index to class containers */
  /*****************************************************/
  ClusterCenters = tempClusterCenters;
  ClusterIDs = tempClusterIDs;
  CraterPoints = tempCraterPoints;

  return;
}
