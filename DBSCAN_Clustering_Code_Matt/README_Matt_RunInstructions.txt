******************************************************
** Run instructions for DBSCAN Clustering Code **
****************************************************** 

1. Change directory to top directory.

   cd /path/prior/to/top/directory/DBSCAN_Clustering_Code_Matt

2. From top directory, run the command: 

   make

   or
	
   javac -sourcepath /path/prior/to/top/directory/DBSCAN_Clustering_Code_Matt/sources/ -d classes/ sources/Rstar/* sources/dbscan/*

3. From top directory, then run the command:

   java -cp /path/prior/to/top/directory/DBSCAN_Clustering_Code_Matt/classes/ dbscan/Gui

4. Check top directory for outputs.

----------------------------------------

Alternative run options:

1. If you wish to change the input file, complete run steps 1 and 2 from above and then run the command:

   java -cp /path/prior/to/top/directory/DBSCAN_Clustering_Code_Matt/classes/ dbscan/Gui [Input Filename]

2. If you wish to change the minimum number of craters per group, complete run steps 1 and 2 from above and then run the command:

   java -cp /path/prior/to/top/directory/DBSCAN_Clustering_Code_Matt/classes/ dbscan/Gui [Input Filename] [Min. Crater Number per Cluster]

You must supply an input file to change the minimum number of craters per group. Default values may be used.

3. If you wish to change the map grid spacing, complete run steps 1 and 2 from above and then run the command:

   java -cp /path/prior/to/top/directory/DBSCAN_Clustering_Code_Matt/classes/ dbscan/Gui [Input Filename] [Min. Crater Number per Cluster] [Map Grid Spacing]

You must supply an input file and value for the minimum number of
craters per cluster to change the map grid spacing. Default values may be used.

4. If you wish to change the search radius, complete run steps 1 and 2 from above and then run the command:

   java -cp
   /path/prior/to/top/directory/DBSCAN_Clustering_Code_Matt/classes/
   dbscan/Gui [Input Filename] [Min. Crater Number per Cluster] [Map
   Grid Spacing] [Search Radius]

You must supply an input file, value for the minimum number of
craters per cluster, and a value for the map grid spacing to change
the search radius. Default values may be used.

---------------------------------------

Notes: 

1. Default values for DBSCAN algorithm are as follow:

  Input Filename = input.txt
  Min. Crater Number per Cluster = 3
  Map Grid Spacing = 30
  Search Radius = 20
  

2. If are working on a linux system or mac and wish to change the formatting of a text file created on the Windows platform that is plagued by the carriage-return character "^M". Run the following command:

  sed 's|^M|\[Press enter]
|g' < [Name of Original Text File] > [Name of New Text File] 

3. Makefile should work out of the box. 
