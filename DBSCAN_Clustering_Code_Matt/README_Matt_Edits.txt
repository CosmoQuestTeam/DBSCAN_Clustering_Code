*********************************************************
** Matt's Edits/Improvements to DBSCAN Clustering Code **
*********************************************************

1. Added quick unix command to run file to change formatting of input data file. Note: Input File has carriage-return character common to files originating in the Windows operating system. New format is aesthetically pleasing and easier to work with.    

2. Restructered the management of source (.java) and class (.class) files (i.e., I've created a top directory and placed source and class files into different subdirectories based on file type and package name).

3. Rewrote parts of the source code, including comments, to improve readability. 

4. In main function, decreased read time of input file by a factor of three by using an implementation of the string function "indexOf()" to parse the data. Original code used the string function "split()" to perform the parsing.

5. Added parameter verification to ensure that invalid values is properly handled.