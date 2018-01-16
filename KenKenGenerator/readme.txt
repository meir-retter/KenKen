KenKenGenerator
Meir Retter

This program generates a KenKen puzzle. The size of the puzzle is determined by
a command line argument, and is 6 by default if no argument is given. The puzzle
is outputted in the following format (where the KenKen puzzle is n x n and has r regions):

----------------------------------------------------------------------------
n

r

r lines, each representing a region. Each line contains the region number,
then the operator for that region, and then the goal number for that region.
----------------------------------------------------------------------------

The regions of the puzzle are created by initializing the top left corner to
be part of the first region, and then systematically going through the grid
row by row. For each square cell, there are two possibilities: add it to an
already-made adjacent or make a new region for it. The probability that a new
cell is added to an old region R is given by the reciprocal of an
exponential function of the size of R. This means that as regions get bigger,
the chance of them gaining new cells decreases.

Once the regions are made, the actual numbers that will be in solution must be
decided. To do this, we need to create a randomized grid without any duplicate
numbers in any row or column. This is done by initializing the solution grid
to a trivial solution for its size. For example, if the size is 4, the grid
is initialized to

1 2 3 4
4 1 2 3
3 4 1 2
2 3 4 1.

Then, we use the fact that we can swap any row with another row, or any
column with another column, and maintain our lack of duplicates. The
program makes 100 random row swaps and 100 random column swaps
(alternating between row and column swaps) which provides an excellently
randomized grid.

After this is done, the operator for each region (+, -, *, or /) is chosen.
The program must be careful doing so, because there are some requirements
to keep in mind (- and / can only be assigned to regions of size 2, and
furthermore, for / to be assigned, the larger number must divide the smaller
evenly). Then it is a simple matter to figure out which goal number to assign
to each region (we have all the numbers in the region, as well as the
operator operating on them). That's the end of the process.










