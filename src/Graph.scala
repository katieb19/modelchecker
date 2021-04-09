package src

/**
 * Trait for an abstracted graph.
 *
 * @tparam T the type of the nodes' contents
 */
trait Graph[T] {

  /**
   * Adds a node to the graph with specified contents. Does not create an edge automatically.
   * @param contents - the contents inside the new node
   * @return the Node[T] added to the graph
   */
  def createNode(contents: T): Node[T]

  /**
   * Adds a directional (one way) edge that connects two nodes
   * @param fromNode - the node that contains the edge
   * @param toNode - the node that the edge points to
   */
  def addEdge(fromNode: Node[T], toNode: Node[T]): Unit

  /**
   * Method which prints the graph.
   */
  def show(): Unit
}