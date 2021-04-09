package sol

import src.{Graph, Node}

/**
 * Class for the adjacency list representation of a Graph.
 *
 * @tparam T the type of the nodes' contents
 */
class AdjacencyListGraph[T] extends Graph[T] {

  /**
   * An AdjacencyList implementation of a graph node
   * @param contents - the value stored at that node
   * @param getsTo - the list of nodes which this node gets to
   */
  class ALNode(val contents: T, var getsTo: List[ALNode]) extends Node[T] {
    @Override
    override def addEdge(toNode: Node[T]): Unit = ???

    @Override
    override def getContents(): T = ???

    @Override
    override def getNexts(): List[Node[T]] = ???

}
  @Override
  override def createNode(contents: T): Node[T] = ???

  @Override
  override def addEdge(fromNode: Node[T], toNode: Node[T]): Unit = ???

  @Override
  override def show(): Unit = ???
}
