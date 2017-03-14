#include <algorithm>
#include <fstream>
#include <iomanip>
#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <string>
#include <TArc.h>
#include <TAttImage.h>
#include <TCanvas.h>
#include <TImage.h>
#include <TROOT.h>
#include <vector>

#include "Cluster.h"
#include "Utility.h"

using namespace std;

int main(int args, char* argv[])
{
  /****************************************************/
  /* Declaration/Initialization of function variables */
  /****************************************************/
  float f1, f2, f3, f4, f5, f6, f7;
  ifstream InFile;
  int cropheight;
  int cropwidth;
  int i1, i2;
  int index;
  string buffer;
  string canname;
  string ClusterListFilename;
  string format;
  string myImage;
  string OutputFileLong;
  string OutputFileShort;
  string PlotFilename;
  TCanvas *can = NULL;
  TImage *img = NULL;
  TImage *img1 = NULL;
  TImage *img2 = NULL;
  vector <Cluster> Clusters;
  vector <float> ClusterMeanConfidence;
  vector <float> ClusterMeanDiameter;
  vector <float> ClusterMeanXPosition;
  vector <float> ClusterMeanYPosition;
  vector <float> ClusterSDDiameter;
  vector <float> ClusterSDXPosition;
  vector <float> ClusterSDYPosition;
  vector <float> row(4, 0.0);
  vector <int> ClusterID;
  vector <int> ClusterMemberSize;
  vector < vector <float> > ClusterList;
  vector < vector <float> > ClusterMembers;

  /***********************/
  /* Retrieve parameters */
  /***********************/
  if(args != 5)
  {
    printf("Usage: %s [List of cluster parameters] [DBSCAN Output Long Version] [DBSCAN Output Short Version] [Image Filename]\n", argv[0]);
    cout << "Now terminating program..." << endl;
    return -1;
  }
  ClusterListFilename = string(argv[1]);
  OutputFileLong = string(argv[2]);
  OutputFileShort = string(argv[3]);
  myImage = string(argv[4]);

  /*******************************************/
  /* Open and read-in data from cluster list */
  /*******************************************/
  if(Utility::FileExists(ClusterListFilename))
    InFile.open(ClusterListFilename.c_str());
  else
  {
    printf("Error in function main: File %s does not exist! Now terminating simulation ...\n", ClusterListFilename.c_str());
    exit(EXIT_FAILURE);
  }

  format = "%f %f %f %f";
  while(getline(InFile, buffer))
  {
    int nelements = sscanf(buffer.c_str(), format.c_str(), &f1, &f2, &f3, &f4);
    if(nelements == 4)
    {
      row[0] = f1;
      row[1] = f2;
      row[2] = f3;
      row[3] = f4;
      ClusterList.push_back(row);
    }
  }
  InFile.close();

  /*********************************************/
  /* Open and read-in data from OutputFileLong */
  /*********************************************/
  if(Utility::FileExists(OutputFileLong))
    InFile.open(OutputFileLong.c_str());
  else
  {
    printf("Error in function main: File %s does not exist! Now terminating simulation ...\n", OutputFileLong.c_str());
    exit(EXIT_FAILURE);
  }
 
  format = "%f %f %f %f";
  while(getline(InFile, buffer))
  {
    int nelements = sscanf(buffer.c_str(), format.c_str(), &f1, &f2, &f3, &f4);
    if(nelements == 4)
    {
      row[0] = f1;
      row[1] = f2;
      row[2] = f3;
      row[3] = f4;
      ClusterMembers.push_back(row);
    }
  }
  InFile.close();
  
  /**********************************************/
  /* Open and read-in data from OutputFileShort */
  /**********************************************/
  if(Utility::FileExists(OutputFileShort))
    InFile.open(OutputFileShort.c_str());
  else
  {
    printf("Error in function main: File %s does not exist! Now terminating simulation ...\n", OutputFileShort.c_str());
    exit(EXIT_FAILURE);
  }
 
  format = "%d %f %f %f %f %f %f %f %d";
  while(getline(InFile, buffer))
  {
    int nelements = sscanf(buffer.c_str(), format.c_str(), &i1, &f1, &f2, &f3, &f4, &f5, &f6, &f7, &i2);
    if(nelements == 9)
    {
      ClusterID.push_back(i1);
      ClusterMeanXPosition.push_back(f1);
      ClusterSDXPosition.push_back(f2);
      ClusterMeanYPosition.push_back(f3);
      ClusterSDYPosition.push_back(f4);
      ClusterMeanDiameter.push_back(f5);
      ClusterSDDiameter.push_back(f6);
      ClusterMeanConfidence.push_back(f7);
      ClusterMemberSize.push_back(i2);
    }
  }
  InFile.close();

  /***********************************/
  /* Store data in cluster container */
  /***********************************/
  int counter = 0;
  for(int i=0; i<(int)ClusterID.size(); i++)
  {
    Cluster myCluster(ClusterID[i], ClusterMeanXPosition[i], ClusterSDXPosition[i], ClusterMeanYPosition[i], ClusterSDYPosition[i], ClusterMeanDiameter[i], ClusterSDDiameter[i], ClusterMeanConfidence[i], ClusterMemberSize[i], vector< vector <float> > (&ClusterMembers[counter], &ClusterMembers[counter+ClusterMemberSize[i]]));
    Clusters.push_back(myCluster);
    counter += ClusterMemberSize[i];
  }

  /*****************/
  /* Read-in image */
  /*****************/
  img = TImage::Open(myImage.c_str());
  if(!img)
  {
    printf("Error in function main: Could not load image %s! Now terminating simulation ...\n", myImage.c_str());
    exit(EXIT_FAILURE);
  }

  /*****************************************************/
  /* Draw original image showing completeness of marks */
  /*****************************************************/
  img->SetConstRatio(kFALSE);
  img->SetImageQuality(TAttImage::kImgBest);
  img->Draw("N");
  img->SetEditable(kTRUE);

  /**************/
  /* Crop image */
  /**************/
  cropheight = img->GetWidth();//img->GetHeight();
  cropwidth = img->GetWidth();
  img->Crop(0, 0, cropwidth, cropheight);

  /***********************/
  /* Extract canvas name */
  /***********************/
  index = myImage.rfind("/")+1;
  buffer = myImage.substr(index);
  index = buffer.rfind(".");
  canname = buffer.erase(index, 1);

  /*******************/
  /* Retrieve canvas */
  /*******************/
  can = (TCanvas*)gROOT->GetListOfCanvases()->FindObject(canname.c_str());
  can->SetFixedAspectRatio();

  /***********************/
  /* Plot and save image */
  /***********************/
  //PlotFilename = OutputFile.substr(0, OutputFile.rfind(".ps"))+string("_v1.ps");
  PlotFilename = "myPlot.jpg";
  can->SaveAs(PlotFilename.c_str());
  delete can;

  /*******************************************************/
  /* Draw first image showing marks within cropped image */
  /*******************************************************/
  canname = "img1";
  img1 = (TImage*)img->Clone(canname.c_str());
  img1->SetConstRatio(kFALSE);
  img1->SetImageQuality(TAttImage::kImgBest);
  img1->Draw("N");
  img1->SetEditable(kTRUE);

  /**************************/
  /* Overplot crater annuli */
  /**************************/
  img1->DrawLine(0, 0, 0, 10, "#000000", 1);
  for(int i=0; i<(int)ClusterList.size(); i++)
  {
    if((ClusterList[i][0] <= cropwidth) && (ClusterList[i][1] <= cropheight))
      img1->DrawCircle(ClusterList[i][0], ClusterList[i][1], 0.5*ClusterList[i][2], "#FF0000", 5);
  }

  /*******************/
  /* Retrieve canvas */
  /*******************/
  can = (TCanvas*)gROOT->GetListOfCanvases()->FindObject(canname.c_str());
  can->SetFixedAspectRatio();

  /***********************/
  /* Plot and save image */
  /***********************/
  //PlotFilename = OutputFile.substr(0, OutputFile.rfind(".ps"))+string("_v1.ps");
  PlotFilename = "myPlot1.jpg";
  can->SaveAs(PlotFilename.c_str());
  delete can;

  /********************************************/
  /* Draw second image showing DBSCAN results */
  /********************************************/
  canname = "img2";
  img2 = (TImage*)img->Clone(canname.c_str());
  img2->SetConstRatio(kFALSE);
  img2->SetImageQuality(TAttImage::kImgBest);
  img2->Draw("N");
  img2->SetEditable(kTRUE);

  /******************************/
  /* Overplot DBSCAN results:   */
  /* Green circles for clusters */
  /* Red circles for craters    */
  /******************************/
  img2->DrawLine(0, 0, 0, 10, "#000000", 1);
  for(int i=0; i<(int)Clusters.size(); i++)
  {
    Cluster myCluster = Clusters[i];

    if((myCluster.ClusterMeanXPosition <= cropwidth) && (myCluster.ClusterMeanYPosition <= cropheight))
    {
      img2->DrawCircle(myCluster.ClusterMeanXPosition, myCluster.ClusterMeanYPosition, 0.5*myCluster.ClusterMeanDiameter, "#00FF00", 5);
      for(int j=0; j<myCluster.ClusterMemberSize; j++)
      {
	if((myCluster.ClusterCraterMembers[j][0] <= cropwidth) && (myCluster.ClusterCraterMembers[j][1] <= cropheight))
	  img2->DrawCircle(myCluster.ClusterCraterMembers[j][0], myCluster.ClusterCraterMembers[j][1], 0.5*myCluster.ClusterCraterMembers[j][2], "#FF0000", 5);
      }
    }
  }

  /*******************/
  /* Retrieve canvas */
  /*******************/
  can = (TCanvas*)gROOT->GetListOfCanvases()->FindObject(canname.c_str());
  can->SetFixedAspectRatio();

  /***********************/
  /* Plot and save image */
  /***********************/
  //PlotFilename = OutputFile.substr(0, OutputFile.rfind(".ps"))+string("_v1.ps");
  PlotFilename = "myPlot2.jpg";
  can->SaveAs(PlotFilename.c_str());

  /*********************/
  /* Deallocate memory */
  /*********************/
  delete can;
  delete img;
  delete img2;
  Clusters.clear();
  ClusterMeanConfidence.clear();
  ClusterMeanDiameter.clear();
  ClusterMeanXPosition.clear();
  ClusterMeanYPosition.clear();
  ClusterSDDiameter.clear();
  ClusterSDXPosition.clear();
  ClusterSDYPosition.clear();
  row.clear();
  ClusterID.clear();
  ClusterMemberSize.clear();
  ClusterList.clear();
  ClusterMembers.clear();

  return 0;
}
