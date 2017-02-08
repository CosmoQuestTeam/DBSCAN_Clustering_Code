#Makefile

TOPDIR = $(dir $(abspath $(lastword $(MAKEFILE_LIST))))
BINDIR = $(TOPDIR)bin
INCDIR = $(TOPDIR)include
OBJDIR = $(TOPDIR)obj
SRCDIR = $(TOPDIR)src
SOURCES := $(wildcard $(SRCDIR)/*.cc)
OBJECTS := $(patsubst $(SRCDIR)/%.cc,$(OBJDIR)/%.o,$(SOURCES))
EXECUTABLE1 := $(BINDIR)/CraterListGenerator
EXECUTABLE2 := $(BINDIR)/DBSCANMattsVersionAnalysis
EXE1_OBJS := $(OBJDIR)/CraterListGenerator.o
EXE2_OBJS := $(OBJDIR)/GetData.o $(OBJDIR)/DBSCANMattsVersionAnalysis.o $(OBJDIR)/Result.o $(OBJDIR)/Utility.o

CC = g++
CFLAGS = -g -Wshadow -Wall -Wextra -O3 -march=native -mavx
LDFLAGS = -lm

all : $(OBJECTS) $(EXECUTABLE1) $(EXECUTABLE2)

$(EXECUTABLE1) : $(EXE1_OBJS)
	$(CC) $(CFLAGS) $(LDFLAGS) $(EXE1_OBJS) -o $@

$(EXECUTABLE2) : $(EXE2_OBJS)
	$(CC) $(CFLAGS) $(LDFLAGS) $(EXE2_OBJS) -o $@

$(OBJDIR)/%.o : $(SRCDIR)/%.cc
	$(CC) -c $(CFLAGS) -I$(INCDIR) -MMD $< -o $@

.PHONY : clean

clean:
	rm -rf $(OBJECTS) $(EXECUTABLE1) $(EXECUTABLE2) $(OBJDIR)/*.d
