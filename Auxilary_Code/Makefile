#Makefile

TOPDIR = $(dir $(abspath $(lastword $(MAKEFILE_LIST))))
BINDIR = $(TOPDIR)bin
INCDIR = $(TOPDIR)include
OBJDIR = $(TOPDIR)obj
SRCDIR = $(TOPDIR)src
SOURCES := $(wildcard $(SRCDIR)/*.cc)
OBJECTS := $(patsubst $(SRCDIR)/%.cc,$(OBJDIR)/%.o,$(SOURCES))
EXECUTABLE1 := $(BINDIR)/GenerateCraterList
EXECUTABLE2 := $(BINDIR)/PlotCraterList
EXECUTABLE3 := $(BINDIR)/DBSCANMattsVersionAnalysis
EXE1_OBJS := $(OBJDIR)/CraterListGenerator.o $(OBJDIR)/GenerateCraterList.o
EXE2_OBJS := $(OBJDIR)/CraterListPlotter.o $(OBJDIR)/PlotCraterList.o $(OBJDIR)/Utility.o
EXE3_OBJS := $(OBJDIR)/GetData.o $(OBJDIR)/DBSCANMattsVersionAnalysis.o $(OBJDIR)/Result.o $(OBJDIR)/Utility.o

CC = g++
CFLAGS = -g -Wshadow -Wall -Wextra -O3 -march=native -mavx
LDFLAGS = -lm -lgsl
ROOT = $(ROOTSYS)
ROOTINCDIR = $(ROOT)/include/root
ROOTLIBDIR = $(ROOT)/lib/root
#ROOTCONFIG = `root-config --cflags  --ldflags --libs`
ROOTCONFIG = -L$(ROOTLIBDIR) -lCore -lCint -lRIO -lNet -lHist -lGraf -lGraf3d -lGpad -lTree -lRint -lPostscript -lMatrix -lPhysics -lMathCore -lThread

all : $(OBJECTS) $(EXECUTABLE1) $(EXECUTABLE2) $(EXECUTABLE3)

$(EXECUTABLE1) : $(EXE1_OBJS)
	$(CC) $(CFLAGS) $(LDFLAGS) $(EXE1_OBJS) -o $@

$(EXECUTABLE2) : $(EXE2_OBJS)
	$(CC) $(CFLAGS) $(LDFLAGS) $(ROOTCONFIG) $(EXE2_OBJS) -o $@

$(EXECUTABLE3) : $(EXE3_OBJS)
	$(CC) $(CFLAGS) $(LDFLAGS) $(EXE3_OBJS) -o $@

$(OBJDIR)/%.o : $(SRCDIR)/%.cc
	$(CC) -c $(CFLAGS) -I$(INCDIR) -I$(ROOTINCDIR) -MMD $< -o $@

.PHONY : clean

clean:
	rm -rf $(OBJECTS) $(EXECUTABLE1) $(EXECUTABLE2) $(EXECUTABLE3) $(OBJDIR)/*.d
