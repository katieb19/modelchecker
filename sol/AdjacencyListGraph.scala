package sol

import src.{Graph, Node}

/**
 * Class for the adjacency list representation of a Graph.
 *
 * @tparam T the type of the nodes' contents
 */
class AdjacencyListGraph[T] extends Graph[T] {

  var visitedNodes: List[ALNode] = List[ALNode]()

  /**
   * An AdjacencyList implementation of a graph node
   *
   * @param contents - the value stored at that node
   * @param getsTo   - the list of nodes which this node gets to
   */
  class ALNode(val contents: T, var getsTo: List[ALNode]) extends Node[T] {
    @Override
    override def addEdge(toNode: Node[T]): Unit = {
      this.getsTo = toNode.asInstanceOf[ALNode] :: this.getsTo
    }

    @Override
    override def getContents(): T = {
      this.contents
    }

    @Override
    override def getNexts(): List[Node[T]] = {
      this.getsTo
    }

  }

  @Override
  override def createNode(contents: T): Node[T] = {
    val newNode = new ALNode(contents, List[ALNode]())
    visitedNodes = newNode :: visitedNodes
    newNode
  }

  @Override
  override def addEdge(fromNode: Node[T], toNode: Node[T]): Unit = {
    fromNode.addEdge(toNode)
  }

  @Override
  override def show(): Unit = {
    for (node <- visitedNodes) {
      if (node.getNexts().nonEmpty) {
        for (edge <- node.getNexts()) {
          println(node.getContents() + "connects to " + "edge" +
            edge.getContents())
        }
      }
      else {
        println(node.getContents())
      }
    }
  }
}


