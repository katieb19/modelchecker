Outline of Code:

The AdjacencyListGraph class extends the Graph class and is a representation
of a graph using an adjacency list. Within it, are methods allowing the user
to create nodes and add edges to the nodes in the list. In the State Data class,
it creates objects that represent the labels in a state/node. There is also a
method that checks if the labels in a state have a certain term. Lastly, the
State Machine class takes in a graph of state data. An example of an inputted
graph would be one created in the AdjacencyListGraph class. The methods in
the State Machine class check if certain types of nodes occur in the graph
based on a start node.

ModelCheckingTest.scala documentation:

This file maintains testing for methods in my AdjacencyListGraph and
StateMachine files.

Additionally, I made street-light-2-edges.csv and street-light-2-nodes.csv.
I manually recreated both this and the street-light-nodes.csv graphs in my
tester file.


Written Questions:

1) The worst-case running time of my checkNever method is quadratic in n where
n is the number of nodes in the graph reachable from the start state. The
runtime is quadratic because in the worst case, the inputted checkNode method
has to be run on every Node reachable from the start state. So, the method
performs the same computation as it loops through the nodes.

2) If I didn't know which state would violate checkNever, it wouldn't change
the worst-case running time. Because we wouldn't know where the state would
violate checkNever, the method may need to run through all of the nodes before
returning the violating node. If the violating node was the last node checked,
this would represent the worst-case running time. So, because we don't have
an idea on where the state would be, this wouldn't change worst-case running
time.

3) If we had to return the path from the start state to the error state,
it would make the run time slower if an error state exists. This is because
in addition to finding the error state, a method would have to find the path
from the start state to the error state. This would add time as the method
would have to loop through the nodes and find how the start state is connected
to the error state. Additionally, this process would add more space to the
program as it would have to create a list to keep track of the path.


4) I would have the return type of checkNever and checkAlways be a list of
nodes. The list would have the nodes from the start state necessary to get to
the error state. The last node in the list would show the error state.

5) You implemented one of the two Graph representations
(Adjacency List or Adjacency Matrix). Why did you implement the one that you
did (honesty here is just fine)?

   I implemented the adjacency list graph representations because I felt
   more comfortable implementing a list rather than a 2-d array. Additionally,
   I was able to better understand the connections between Nodes through the
   field of edges within the nodes.

   State one performance-related advantage to each representation as a contrast
   the other.

   One advantage of the adjacency list representation is that it would take
   less time to count how many edges a node has. This is because you can
   take the length of the list of nodes in the field for a node in the list.
   One advantage of the adjacency matrix is that it takes less time to find
   if there is an edge between two nodes. This is because you would have to
   loop through to find a specific node and then loop through the list of nodes
   it connects to. In the adjacency matrix, the indexes of the nodes can be
   easily taken to return whether there is an edge between them.

   Briefly explain any programmer/coding-facing tradeoffs between them

   Handling changes to the graph would be more difficult in the adjacency
   matrix. For example, adding a node would be difficult if the number of
   nodes exceeded the maxSize. Additionally, adding a node would mean adding
   a new column and row in the matrix. However, for the adjacency list, adding
   a node would just mean appending the node to the list.