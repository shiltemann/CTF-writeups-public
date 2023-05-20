---
layout: writeup
title: 'December 6th: Paths'
level:
difficulty:
points:
categories: []
tags: []
flag: ADCTF_G0_go_5hOr7E57_PaTh
---
## Category

Reverse

## Hint

*There are many paths, and search for shortest path from start to goal.*

    (to, cost)

## Challenge

We are given a python script [paths.py](files/paths.py)

This contains a definition of a graph

    E = [
    [(96, 65)],
    [(64, 99), (82, 120), (3, 100), .. ],
    [(24, 88), (91, 67), (58, 112), .. ],
    [(75, 48), (21, 80), (32, 119), (61, 48) ..],
    [(63, 66), (49, 55), (80, 79), (31, 122), (1, 67), (6, 89), (86, 100), ..],
    [(38, 55), (5, 119), (97, 68), (10, 72), (11, 106), ..],
    ...
    ...

where node 0 has a connection to node 96 at a cost of 65
node 1 has connection to node 64 at cost of 99, and a connection to node
82 at cost 120, etc..

## Solution

We need to find a path on this graph from node 0 to 99 of length <=2014.

We wrote a small program to find this path using backtracking
([file](files/findpath.py)):

    #E= <definition from original file>

    start = 0
    goal = 99
    shortest = 2014

    def travel(node, totalcost):
    	global nodes

    	for i in E[node]:
    		## check if goal reached
    		if i[0] == goal and totalcost+i[1] <= shortest:
    			print "yay!! \ntotal cost: "+str(totalcost+i[1])
    			print "walk: ",
    			nodes.append(i[0])
    			print nodes
    			break

    		## backtrack if cost is too high
    		if totalcost+i[1] > shortest:
    			break

    		## go deeper
    		nodes.append(i[0])
    		travel(i[0],totalcost+i[1])


    	## tried all connections from current node, backtrack.
    	nodes.pop()


    nodes=[]
    travel(0,0)

this give us the following output:

    yay!!
    total cost: 2014
    walk:  [96, 94, 72, 70, 69, 89, 18, 46, 22, 92, 79, 59, 74, 97, 58, 82, 35, 85, 30, 87, 25, 40, 41, 7, 99]

Next we feed the solution to the original program to get the flag:

    $ python paths_original.py 96 94 72 70 69 89 18 46 22 92 79 59 74 97 58 82 35 85 30 87 25 40 41 7 99
    the flag is: ADCTF_G0_go_5hOr7E57_PaTh

