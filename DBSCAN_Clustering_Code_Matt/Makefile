#Makefile

TOPDIR = $(dir $(abspath $(lastword $(MAKEFILE_LIST))))
CLASSDIR = $(TOPDIR)classes
SOURCEDIR = $(TOPDIR)sources
RSTARSOURCES = $(SOURCEDIR)/Rstar
DBSCANSOURCES = $(SOURCEDIR)/dbscan

JC = javac
JFLAGS = -sourcepath $(SOURCEDIR) -d $(CLASSDIR)

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = $(RSTARSOURCES)/BranchList.java \
          $(RSTARSOURCES)/Comparable.java \
          $(RSTARSOURCES)/ComparableInteger.java \
          $(RSTARSOURCES)/Constants.java \
          $(RSTARSOURCES)/Data.java \
          $(RSTARSOURCES)/DataStruct.java \
          $(RSTARSOURCES)/DirEntry.java \
          $(RSTARSOURCES)/LinList.java \
          $(RSTARSOURCES)/Node.java \
          $(RSTARSOURCES)/PPoint.java \
          $(RSTARSOURCES)/ProgramDriver.java \
          $(RSTARSOURCES)/QueryDialog.java \
          $(RSTARSOURCES)/QueryFrame.java \
          $(RSTARSOURCES)/RFramedArea.java \
          $(RSTARSOURCES)/RTDataNode.java \
          $(RSTARSOURCES)/RTDirNode.java \
          $(RSTARSOURCES)/RTNode.java \
          $(RSTARSOURCES)/RTree.java \
          $(RSTARSOURCES)/RectArea.java \
          $(RSTARSOURCES)/RectFrame.java \
          $(RSTARSOURCES)/SLink.java \
          $(RSTARSOURCES)/SortMbr.java \
          $(RSTARSOURCES)/Sortable.java \
          $(RSTARSOURCES)/SortedLinList.java \
          $(RSTARSOURCES)/Traceable.java \
          $(RSTARSOURCES)/TreeCreation.java \
          $(DBSCANSOURCES)/DBSCANSteepestDescent.java \
          $(DBSCANSOURCES)/DivideArray.java \
          $(DBSCANSOURCES)/Gui.java \
          $(DBSCANSOURCES)/HashmapSearch.java \
          $(DBSCANSOURCES)/MathUtility.java \
          $(DBSCANSOURCES)/Mergesort.java \
          $(DBSCANSOURCES)/MyThread.java \
          $(DBSCANSOURCES)/Point.java \
          $(DBSCANSOURCES)/PrintOutput.java \
          $(DBSCANSOURCES)/Utility.java \
          $(DBSCANSOURCES)/dbscan.java

classes: $(CLASSES:.java=.class)

clean:
	$(RM) $(CLASSDIR)/Rstar/*.class $(CLASSDIR)/dbscan/*.class
