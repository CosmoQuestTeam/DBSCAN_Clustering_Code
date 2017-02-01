1. cd to the "src" directory (main one).

2. Need to copy all the Rstar files into the dbscan folder.

3. cd to the dbscan folder, then run:  javac *.java

4. cd up again to the src folder.

5. Duplicate dbscan folder and rename the copy to "Rstar".  Or run: cp -r dbscan Rstar

6. Back to the Terminal, you're in that upper-level folder, and you have your input file ("Input_80.txt").  Run: java dbscan.Gui

7. Should be good to go now ...


OR:

Go to the main directory with the dbscan, Rstar folders, and your input file.  Then run:
> cd dbscan; javac *.java; cd ..; rm -r Rstar; cp -r dbscan Rstar; java dbscan.Gui


ALTERNATIVELY:  When running "java dbscan.Gui", you can attach two inputs.  The first is the minimum number of points for a group to be considered a cluster to be output, while the second is the name of the input file.  The DEFAULT if these are not entered is "3" and "input.txt".