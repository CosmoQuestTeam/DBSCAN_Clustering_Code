This file contains my notes about this code and what should be looked into later.

1. After Di implemented the tree structure and hashtags and all this other stuff, the code runs MUCH faster, where 2-3 million points can be run in an hour, and 10 million in less than a day.  But, the scaling is N^2*log(N) ... not the promised N*log(N).  As far as I can tell, he's just dramatically reduced the coefficient multiplied into that time.  The data on times, where T_MBP are laptop and T_Lin12 are Linux machine and only are for the DBSCAN section (not reading, tree creation, etc.):

N		T_MBP	T_Lin12
9307		0.58	
62,826		4.16	3.77
81,815		6.5	
155,300		16	
694,781		224	
754,242		302	
2,120,377*	2088/2124	1682
2,767,566	4602	
5,125,512**		13,434/13,206	25,110 clusters
-> projection to 10M points: 54,000 sec = 15 hrs

*T_MBP to read was 34.0 sec, tree 1.3 sec, hashmap 85.7 sec.  T_Lin12 was 2.6, 0.92, 63.3 sec.  Had -Xmx set to 6GB and 20GB respectively.
** T_Lin12 to read was 5.8 sec, tree 0.59 sec, hashmap 142.1 sec.  Had -Xmx set to 20GB.

Since the projected time to our current max. is still under a day even on the laptop, and the code runs 10-20% faster on the Windows machine (haven't tested yet on Linux, see below ...), I haven't pursued figuring this out yet.


2. I've gotten part-way through commenting the code and figuring out how it works.  My main thrust initially was getting it to run and removing the GUI, and I happily found that removing the GUI got it to run ~40% faster on my laptop and not 3x slower on the Windows machine, so it was the GUI slowing things down.  It still requires the Gui.java file and runs as "java dbscan.Gui", but it doesn't actually have a GUI.


3. Timing tests on my laptop show that it's much faster on it than my Mac desktop.  It's comparable to or slightly faster than the Windows machine for datasets that take on the order of a minute or less to run.  For larger ones, the Windows machine is 10-20% faster.  Have not yet tested it in Linux since I was in the middle of a week-long mosaic job.

4. This doesn't say it requires a lot of RAM until you run it on 1-2 million points, but giving it a boat-load of RAM speeds things up (probably because Java uses swap space on the disk instead?).  Running with the option -Xmx2000M on the laptop, or -Xmx20000M (20GB) on the Windows/Linux machine makes the code run faster (like, from 500 seconds to 350 seconds or something like that).

5. Despite my request, the code does not appear to be multi-threaded.