package src

/**
 * Trait for an abstracted node in a graph
 * @tparam T - the type of its contents
 */
trait Node[T] {

  /**
   * Returns the contents at this node.
   * @return contents of type T
   */
  def getContents(): T

  /**
   * Finds all nodes connected to this node by outgoing edges
   * @return a List[Node[T]] of all nodes that the current node connects to
   */
  def getNexts(): List[Node[T]]

  /**
   * Adds an outgoing directional edge from this node to another node.
   * @param toNode - the node that the current node connects to
   */
  def addEdge(toNode: Node[T]): Unit
}