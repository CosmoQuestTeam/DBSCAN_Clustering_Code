*********************************************************
** Matt's Edits/Improvements to DBSCAN Clustering Code **
*********************************************************

1. Added quick unix command to run file to change formatting of input data file. Note: Input File has carriage-return character common to files originating in the Windows operating system. New format is aesthetically pleasing and easier to work with.    

2. Restructured the management of source (.java) and class (.class) files (i.e., I've created a top directory and placed source and class files into different subdirectories based on file type and package name).

3. Rewrote parts of the source code, including comments, to improve readability. 

4. In main function, decreased read time of input file by a factor of three by using an implementation of the string function "indexOf()" to parse the data. Original code used the string function "split()" to perform the parsing.

5. Added parameter verification to ensure that invalid values are properly handled.

6. Made the interval spacing of the map grid an argument of the main function. Note: This particular parameter significantly impacts the amount of time needed to create the RTree. In general, the larger the grid spacing the less time taken to create the tree. Haven’t done test to determining scaling.

7. Determined that creating new instances of the SortedLinList in the creation of the hash map was taking too long. Given that this class is an extended class of the LinList class, I replaced the default constructor in the LinList class with an empty constructor. Speed up appears to be a factor of three.

8. Replacing the constructor in the LinList class has also speed up the DBSCAN section of the code! Speed up appears to be a factor of two. Further speed tests required.