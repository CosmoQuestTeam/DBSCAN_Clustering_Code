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
  return;
}

void GetData::RetrieveTestData(string Filename)
{
  
  return;
}

