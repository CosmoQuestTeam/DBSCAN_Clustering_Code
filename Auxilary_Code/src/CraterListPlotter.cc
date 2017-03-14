#include <fstream>
#include <iostream>
#include <stdio.h>
#include <vector>

#include "TArrow.h"
#include "TCanvas.h"
#include "TColor.h"
#include "TEllipse.h"
#include "TGaxis.h"
#include "TH1.h"
#include "TH2.h"
#include "TList.h"
#include "TPaletteAxis.h"
#include "TStyle.h"

#include "CraterListPlotter.h"
#include "Utility.h"

using namespace std;

CraterListPlotter::CraterListPlotter(string InputFilename, int NumberGridSpacing, string OutputFilename)
{
  CraterListFile = InputFilename;
  NumberofGridSpacings = NumberGridSpacing;
  OutputFile = OutputFilename;
}

void CraterListPlotter::PlotCraterPoints()
{
  /****************************************************/
  /* Declaration/Initialization of function variables */
  /****************************************************/
  const int canheight = 1000;
  const int canwidth = 1000;
  const int NRGBs = 5;
  double Blue[NRGBs] = {0.90, 1.00, 0.32, 0.00, 0.00}; // Color blue
  double Green[NRGBs] = {0.50, 0.81, 1.00, 0.57, 0.00}; // Color green
  double Red[NRGBs] = {0.60, 0.00, 0.87, 1.00, 0.85}; // Color red
  double Length[NRGBs] = {0.00, 0.25, 0.45, 0.80, 1.00};
  double maxX;
  double maxY;
  double minX;
  double minY;
  int CraterBinMaximum;
  int nbinX;
  int nbinY;
  int nelements;
  string OutputFilename;
  string PlotFilename;
  TCanvas* can = NULL;
  TCanvas* can2 = NULL;
  TEllipse *Crater = NULL;
  TH2F *hist = NULL;

  /*********************/
  /* Initialize Canvas */
  /*********************/
  can = new TCanvas("Canvas", "Crater Plot w/o Crater Annulus", canwidth, canheight);
  can->SetRightMargin(0.25);
  can->SetLeftMargin(0.15);
  can->SetBottomMargin(0.1);
  can->SetTopMargin(0.1);
  TGaxis::SetMaxDigits(3);

  /*******************/
  /* Initialize Plot */
  /*******************/
  nbinX = NumberofGridSpacings;
  nbinY = NumberofGridSpacings;
  minX = 0.0;
  minY = 0.0;
  maxX = Utility::RoundUp((float)MaxX, (float)nbinX);
  maxY = Utility::RoundUp((float)MaxY, (float)nbinY);
  hist = new TH2F("Histogram", "", nbinX, minX, maxX, nbinY, minY, maxY);

  /******************/
  /* Fill histogram */
  /******************/
  nelements = CraterPoints[0].size();
  for(int i=0; i<(int)CraterPoints.size(); i++)
  {
    double X = (nelements == 4) ? CraterPoints[i][0] : CraterPoints[i][3];
    double Y = (nelements == 4) ? CraterPoints[i][1] : CraterPoints[i][4];
    hist->Fill(X, Y, 1.0);
  }
  
  /*******************************************************/
  /* Get value of bin with the maximum number of craters */
  /*******************************************************/
  CraterBinMaximum = hist->GetMaximumBin();

  /***************/
  /* Color scale */
  /***************/
  TColor::CreateGradientColorTable(NRGBs, Length, Red, Green, Blue, CraterBinMaximum);
  gStyle->SetNumberContours(CraterBinMaximum);
  gStyle->SetOptStat(0);

  /******************/
  /* Graph settings */
  /******************/
  hist->SetTitle("Crater Map w/o Crater Annuli");
  hist->GetXaxis()->SetTitle("X [unit]");
  hist->GetXaxis()->CenterTitle();
  hist->GetYaxis()->SetTitle("Y [unit]");
  hist->GetYaxis()->SetTitleOffset(1.5);
  hist->GetYaxis()->CenterTitle();
  hist->GetZaxis()->SetTitle("Number of craters");
  hist->GetZaxis()->SetTitleOffset(1.5);
  hist->GetZaxis()->CenterTitle();

  /******************/
  /* Draw histogram */
  /******************/
  hist->Draw("COLZ");
  hist->SetMinimum(-1);
  
  /*********************/
  /* Reposition Z axis */
  /*********************/
  can->Update();
  TPaletteAxis *palette = (TPaletteAxis*)hist->GetListOfFunctions()->FindObject("palette");
  palette->SetX1NDC(0.85);
  palette->SetX2NDC(0.9);
  can->Modified();
  can->Update();

  /***************************/
  /* Plot and save histogram */
  /***************************/
  PlotFilename = OutputFile.substr(0, OutputFile.rfind(".ps"))+string("_v1.ps");
  can->SaveAs(PlotFilename.c_str());

  /*****************************************************/
  /* Same histogram but with overplot of crater annuli */  
  /*****************************************************/
  /*****************************************/
  /* Reset Canvas and copy above histogram */
  /*****************************************/
  can2 = new TCanvas("Canvas2", "", canwidth, canheight);
  can2->SetRightMargin(0.25);
  can2->SetLeftMargin(0.15);
  can2->SetBottomMargin(0.1);
  can2->SetTopMargin(0.1);

  /***************************************/
  /* Redraw histogram with same settings */
  /***************************************/
  hist->SetTitle("Crater Map with Crater Annuli");
  hist->Draw("COLZ");

  /*********************/
  /* Reposition Z axis */
  /*********************/
  can2->Update();
  TPaletteAxis *palette2 = (TPaletteAxis*)hist->GetListOfFunctions()->FindObject("palette");
  palette2->SetX1NDC(0.85);
  palette2->SetX2NDC(0.9);
  can2->Modified();
  can2->Update();

  /********************/
  /* Overplot craters */
  /********************/
  for(int i=0; i<(int)CraterPoints.size(); i++)
  {
    Crater = (nelements == 4) ?
      new TEllipse(CraterPoints[i][0], CraterPoints[i][1], 0.5*CraterPoints[i][2], 0.5*CraterPoints[i][2]) :
      new TEllipse(CraterPoints[i][3], CraterPoints[i][4], 0.5*CraterPoints[i][5], 0.5*CraterPoints[i][5]);
    Crater->SetLineColor(kWhite);
    Crater->SetLineWidth(2);
    Crater->SetFillStyle(0);
    Crater->DrawClone();
    delete Crater;
  }

  /***************************/
  /* Plot and save histogram */
  /***************************/
  PlotFilename = OutputFile.substr(0, OutputFile.rfind(".ps"))+string("_v2.ps");
  can2->SaveAs(PlotFilename.c_str());

  /*********************/
  /* Deallocate memory */
  /*********************/
  delete can;
  delete can2;
  delete hist;
  
  return;
}

void CraterListPlotter::ReadCraterList()
{
  /****************************************************/
  /* Declaration/Initialization of function variables */
  /****************************************************/
  float maxX = 0;
  float maxY = 0;
  ifstream InFile;
  string buffer;
  string format;
  vector < vector <float> > tempCraterPoints;

  /***********************************/
  /* Open and read-in OutputFileList */
  /***********************************/
  if(Utility::FileExists(CraterListFile))
    InFile.open(CraterListFile.c_str());
  else
  {
    printf("Error in function ReadCraterList: File %s does not exist! Now terminating simulation ...\n", CraterListFile.c_str());
    exit(EXIT_FAILURE);
  }
  
  bool test = ((int)(CraterListFile.rfind("_tagged.txt")) > 0);
  format = (test) ? "%f %f %f %f %f %f %f" : "%f %f %f %f";
  while(getline(InFile, buffer))
  {
    float v1, v2, v3, v4, v5, v6, v7;
    int nelements = (test) ? sscanf(buffer.c_str(), format.c_str(), &v1, &v2, &v3, &v4, &v5, &v6, &v7) : sscanf(buffer.c_str(), format.c_str(), &v1, &v2, &v3, &v4);
    if(nelements == 4)
    {
      vector <float> row(4, 0.0);
      row[0] = v1; // Crater x position
      row[1] = v2; // Crater y position
      row[2] = v3; // Crater diameter
      row[3] = v4; // Confidence
      tempCraterPoints.push_back(row);
      
      /********************************/
      /* Retrieve max (x, y) position */
      /********************************/
      maxX = (maxX > v1+0.5*v3) ? maxX : v1+0.5*v3;
      maxY = (maxY > v2+0.5*v3) ? maxY : v2+0.5*v3;
    }
    if(nelements == 7)
    {
      vector <float> row(7, 0.0);
      row[0] = v1; // Cluster ID
      row[1] = v2; // Cluster center x position
      row[2] = v3; // Cluster center y position
      row[3] = v4; // Crater x position
      row[4] = v5; // Crater y position
      row[5] = v6; // Crater diameter
      row[6] = v7; // Confidence
      tempCraterPoints.push_back(row);

      /********************************/
      /* Retrieve max (x, y) position */
      /********************************/
      maxX = (maxX > v4+0.5*v6) ? maxX : v4+0.5*v6;
      maxY = (maxY > v5+0.5*v6) ? maxY : v5+0.5*v6;
    }
  }
  InFile.close();

  /******************************/
  /* Record max (x, y) position */
  /******************************/
  MaxX = maxX;
  MaxY = maxY;

  /**********************************/
  /* Record list to class container */
  /**********************************/
  CraterPoints = tempCraterPoints;

  return;
}
