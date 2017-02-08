/* HEADER FILE: GetData.h */

#ifndef GETDATA_H
#define GETDATA_H

class Result;
class GetData
{
  /***********************************************************/
  /* Declaration/Initialization of class variables/functions */
  /***********************************************************/
 public :
  int GetmyResultSize();
  Result GetmyResult(int i);
  std::vector <Result> GetmyResult();
  void RetrieveReferenceData(std::string Filename);
  void RetrieveTestData(std::string Filename);

 private :
  std::vector <Result> myResult;
};

#endif
