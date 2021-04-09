package sol

import src.{Graph, Node}

/**
 * Matrix implementation of a graph.
 * @param maxSize the maximum size of the graph, used for creating the array
 * @tparam T the type of the nodes' contents
 */
class MatrixGraph[T](val maxSize: Int) extends Graph[T] {

  /**
   * Class for a node in adjacency matrix graph.
   * @param contents - the node's contents
   * @param index - the index in the matrix that corresponds to this node
   */
  class MNode(val contents: T, val index: Int) extends Node[T] {
    @Override
    override def getContents(): T = ???

    @Override
    override def getNexts(): List[Node[T]] = ???

    @Override
    override def addEdge(toNode: Node[T]): Unit = ???
}

  @Override
  override def createNode(contents: T): Node[T] = ???

  @Override
  override def addEdge(fromNode: Node[T], toNode: Node[T]): Unit = ???

  @Override
  override def show(): Unit = ???
}
