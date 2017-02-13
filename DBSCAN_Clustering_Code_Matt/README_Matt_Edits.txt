*********************************************************
** Matt's Edits/Improvements to DBSCAN Clustering Code **
*********************************************************

1. Added quick unix command to run file to change formatting of input data file. Note: Input File has carriage-return character common to files originating in the Windows operating system. New format is aesthetically pleasing and easier to work with.    

2. Restructured the management of source (.java) and class (.class) files (i.e., I've created a top directory and placed source and class files into different subdirectories based on file type and package name).

3. Rewrote parts of the source code, including comments, to improve readability. 

4. In main function, decreased read time of input file by a factor of three by using an implementation of the string function "indexOf()" to parse the data. Original code used the string function "split()" to perform the parsing.

5. Added parameter verification to ensure that invalid values are properly handled.

6. Made the interval spacing of the map grid an argument of the main function. Note: This particular parameter significantly impacts the amount of time needed to create the RTree. In general, the larger the grid spacing the less time taken to create the tree. Haven’t done test to determining scaling.

7. Determined that creating new instances of the SortedLinList in the creation of the hash map was taking too long. Given that this class is an extended class of the LinList class, I replaced the default constructor in the LinList class with an empty constructor. 

8. Replacing the constructor in the LinList class has also speed up the DBSCAN section of the code! 

9. Made the map search radius an argument of the main function. Note: This particular parameter along with the interval spacing mention in (6.) significantly impacts the amount of time needed to run the DBSCAN section of the code.  Will be performing speed tests soon.

10. Wrote some testing tools (using a combination of bash and c++) to perform speed tests. Based on the tests, I'm happy to report that the current version of the code runs faster! To view the speed test look at the file entitled "AllResults.txt" stored in the directory /path/leading/to/directory/Auxilary_Code/bin. When comparing my version with parameters (Minimum Craters per Cluster: 3 - Map Grid Spacing: 30 units - Search Radius: 20 units) to the original code that is hard coded with the same parameters, there is a factor of two speed up for reading in the input file, there is a small speed up in the creation of the RTree, there is a significant speed up in the creation of the hasmap, and there is a small speed up in the DBSCAN section of the code. 

11. Found major bug in code while doing further testing! Integer variable "minpt" in the dbscan class utilizes a dangerous method of assigning values to a variable. Calling functions in the dbscan class from classes that are not called Gui results in incorrect behavior. Problem resolved by making integer variable "minpt" a passable parameter to the one function in the dbscan class.

12. Wrote some more testing tools (in c++) to create simulated crater datasets where the number of clusters can be defined by the user. In addition, I wrote some code that maps the density (not exactly density but close enough) of crater points. 
