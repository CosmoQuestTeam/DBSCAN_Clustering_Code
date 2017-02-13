#include <fstream>
#include <iomanip>
#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <string>
#include <vector>

#include "GetData.h"
#include "Result.h"
#include "Utility.h"

using namespace std;

int GetData::GetmyResultSize()
{
  return myResult.size();
}

Result GetData::GetmyResult(int i)
{
  return myResult[i];
} 

vector <Result> GetData::GetmyResult()
{
  return myResult;
}  

void GetData::RetrieveReferenceData(string Filename)
{
  /****************************************************/
  /* Declaration/Initialization of function variables */
  /****************************************************/
  double DBST;
  double FRT;
  double HMCT;
  double SR;
  double sum_DBST = 0;
  double sum_FRT = 0;
  double sum_HMCT = 0;
  double sum_TCT = 0;
  double sum_TT = 0;
  double TCT;
  double TT;
  ifstream InFile;
  int counter1;
  int counter2;
  int counter3;
  int MC;
  int MS;
  int NC;
  int NClus;
  string buffer;
  string FN;
  vector <int> NCraters(6);
  vector <Result> vecResult;

  /*****************************/
  /* Open and read-in Filename */
  /*****************************/
  if(Utility::FileExists(Filename))
    InFile.open(Filename.c_str());
  else
  {
    printf("Error in function main: File %s does not exist! Now terminating simulation ...\n", Filename.c_str());
    exit(EXIT_FAILURE);
  }

  /*************/
  /* Constants */
  /*************/
  FN = "";
  MC = 3;
  MS = 30;
  NCraters[0] = 100;
  NCraters[1] = 1000;
  NCraters[2] = 10000;
  NCraters[3] = 50000;
  NCraters[4] = 75000;
  NCraters[5] = 150000;
  SR = 20.0;

  /************************************************/
  /* Parse data and store in Result type variable */
  /************************************************/
  counter1 = 1;
  counter2 = 0;
  counter3 = 0;
  while(getline(InFile, buffer))
  {
    int index = counter1;
    switch(index)
    {
    case 2:
      {
	FRT = atof(buffer.substr(buffer.find("took")+5, buffer.length()-buffer.find("seconds")+2).c_str());
	sum_FRT += FRT;
      }break;
    case 4:
      {
	TCT = atof(buffer.substr(buffer.find("took")+5, buffer.length()-buffer.find("seconds")+2).c_str());
	sum_TCT += TCT;
      }break;
    case 6:
      {
	HMCT = atof(buffer.substr(buffer.find("took")+5, buffer.length()-buffer.find("seconds")+2).c_str());
	sum_HMCT += HMCT;
      }break;
    case 8:
      {
	DBST = atof(buffer.substr(buffer.find(":")+2).c_str());
	sum_DBST += DBST;
      }break;
    case 9:
      {
	NClus = atoi(buffer.substr(buffer.find(":")+2).c_str());
      }break;
    case 10:
      {
	TT = atof(buffer.substr(buffer.find("took")+5, buffer.length()-buffer.find("seconds")+2).c_str());
	sum_TT += TT;
      }break;
    }
    if(counter1 == 12)
    {
      counter2++;
      counter1 = 0;
    }
    counter1++;
    if(counter2 == 5)
    {
      FRT = sum_FRT/5.;
      TCT = sum_TCT/5.;
      HMCT = sum_HMCT/5.;
      DBST = sum_DBST/5.;
      TT = sum_TT/5.;

      NC = NCraters[counter3];
      Result tempResult(FN, NC, MC, MS, SR, FRT, TCT, HMCT, DBST, NClus, TT);
      vecResult.push_back(tempResult);
      counter3++;

      /************************************/
      /* Reset counter2 and sum variables */
      /************************************/
      counter2 = 0;
      sum_FRT = 0;
      sum_TCT = 0;
      sum_HMCT = 0;
      sum_DBST = 0;
      sum_TT = 0;
    }
  }
  
  /**********************************/
  /* Push result to myResult vector */
  /**********************************/
  myResult = vecResult;

  return;
}

void GetData::RetrieveTestData(string Filename)
{
  /****************************************************/
  /* Declaration/Initialization of function variables */
  /****************************************************/
  double DBST;
  double FRT;
  double HMCT;
  double SR;
  double sum_DBST = 0;
  double sum_FRT = 0;
  double sum_HMCT = 0;
  double sum_TCT = 0;
  double sum_TT = 0;
  double TCT;
  double TT;
  ifstream InFile;
  int counter1;
  int counter2;
  int MC;
  int MS;
  int NC;
  int NClus;
  string buffer;
  string FN;
  vector <Result> vecResult;

  /*****************************/
  /* Open and read-in Filename */
  /*****************************/
  if(Utility::FileExists(Filename))
    InFile.open(Filename.c_str());
  else
  {
    printf("Error in function main: File %s does not exist! Now terminating simulation ...\n", Filename.c_str());
    exit(EXIT_FAILURE);
  }

  /************************************************/
  /* Parse data and store in Result type variable */
  /************************************************/
  counter1 = 1;
  counter2 = 0;
  while(getline(InFile, buffer))
  {
    int index = counter1;
    switch(index)
    {
    case 1:
      {
	FN = buffer.substr(buffer.find(":")+2);
	NC = atoi(buffer.substr(buffer.rfind("_")+1, buffer.length()-buffer.rfind(".txt")+2).c_str());
      }break;
    case 2:
      {
	MC = atoi(buffer.substr(buffer.find(":")+2).c_str());
      }break;
    case 3:
      {
	MS = atoi(buffer.substr(buffer.find(":")+2).c_str());
      }break;
    case 4:
      {
	SR = atof(buffer.substr(buffer.find(":")+2).c_str());
      }break;
    case 7:
      {
	FRT = atof(buffer.substr(buffer.find("took")+5, buffer.length()-buffer.find("seconds")+2).c_str());
	sum_FRT += FRT;
      }break;
    case 9:
      {
	TCT = atof(buffer.substr(buffer.find("took")+5, buffer.length()-buffer.find("seconds")+2).c_str());
	sum_TCT += TCT;
      }break;
    case 11:
      {
	HMCT = atof(buffer.substr(buffer.find("took")+5, buffer.length()-buffer.find("seconds")+2).c_str());
	sum_HMCT += HMCT;
      }break;
    case 13:
      {
	DBST = atof(buffer.substr(buffer.find(":")+2).c_str());
	sum_DBST += DBST;
      }break;
    case 14:
      {
	NClus = atoi(buffer.substr(buffer.find(":")+2).c_str());
      }break;
    case 15:
      {
	TT = atof(buffer.substr(buffer.find("took")+5, buffer.length()-buffer.find("seconds")+2).c_str());
	sum_TT += TT;
      }break;
    }
    if(counter1 == 17)
    {
      counter2++;
      counter1 = 0;
    }
    counter1++;
    if(counter2 == 5)
    {
      FRT = sum_FRT/5.;
      TCT = sum_TCT/5.;
      HMCT = sum_HMCT/5.;
      DBST = sum_DBST/5.;
      TT = sum_TT/5.;

      Result tempResult(FN, NC, MC, MS, SR, FRT, TCT, HMCT, DBST, NClus, TT);
      vecResult.push_back(tempResult);
      
      /************************************/
      /* Reset counter2 and sum variables */
      /************************************/
      counter2 = 0;
      sum_FRT = 0;
      sum_TCT = 0;
      sum_HMCT = 0;
      sum_DBST = 0;
      sum_TT = 0;
    }
  }
  
  /**********************************/
  /* Push result to myResult vector */
  /**********************************/
  myResult = vecResult;

  return;
}

