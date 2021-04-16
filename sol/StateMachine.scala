package sol

import src.{Graph, Node}

import scala.collection.mutable.ListBuffer


// ------------- DO NOT CHANGE --------------------
import org.apache.commons.csv._

import java.io.{File, FileReader, IOException}
import scala.collection.JavaConverters.iterableAsScalaIterableConverter
import scala.collection.mutable
// ------------------------------------------------

/**
 * Class of the State Machine.
 *
 * @param stateGraph - an object which implements Graph stored inside a
 *                   stateMachine.
 */
class StateMachine(stateGraph: Graph[StateData]) {

  /**
   * field that sets the startState of the graph
   */
  private var startState: Option[Node[StateData]] = None

  /**
   * Initializes a StateMachine from CSV files.
   *
   * @param nodeCSV    - the filename of the CSV containing node information
   * @param edgesCSV   - the filename of the CSV containing edges information
   * @param startState - the starting state of the state machine (the id of the
   *                   node)
   */
  def initFromCSV(nodeCSV: String, edgesCSV: String, startState: String) {
    val format = CSVFormat.RFC4180.withHeader();
    var edgeList: ListBuffer[String] = new ListBuffer[String]()

    // Parse nodeCSV
    val idMap = new mutable.HashMap[String, Node[StateData]]()
    try {
      val parser: CSVParser = new CSVParser(new FileReader(new File(nodeCSV)),
        format)
      val records = parser.getRecords().asScala.toList
      for (record <- records) {
        idMap(record.get(0)) = stateGraph.createNode(
          new StateData(record.get(1).split(";").toList))

        if (record.get(0).equals(startState)) {
          this.startState = Some(idMap(record.get(0)))
        }
      }
    }
    catch {
      case e: IOException => println("error reading " + nodeCSV)
    }

    // Parse edgeCSV
    try {
      val parser: CSVParser = new CSVParser(new FileReader(new File(edgesCSV)),
        format)
      val records = parser.getRecords().asScala.toList
      for (record <- records) {
        stateGraph.addEdge(idMap(record.get(0)), idMap(record.get(1)))
        edgeList += record.get(0)
        edgeList += record.get(1)
      }
    }
    catch {
      case e: IOException => println("error reading" + edgesCSV)
    }

    for (edge <- edgeList) {
      if (!idMap.contains(edge)) {
        throw new RuntimeException("Edge doesn't exist")
      }
    }
  }

  /**
   * A method that sets the starting node of the graph
   *
   * @param startNode - A node of StateData that is set as the starting node
   */
  def setStart(startNode: Node[StateData]): Unit = {
    startState = Option(startNode)
  }

  /**
   * A method that adds a state to the graph
   *
   * @param content - A list of Strings that make up the node
   * @return - the Node that is created
   */
  def addState(content: List[String]): Node[StateData] = {
    val state = new StateData(content)
    stateGraph.createNode(state)
  }

  /**
   * A method that adds edges between the nodes
   *
   * @param from - the node that the edge is originating from
   * @param to   - the node the edge is going to
   */
  def addTransition(from: Node[StateData], to: Node[StateData]): Unit = {
    stateGraph.addEdge(from, to)
  }

  /**
   * A method that checks that a case always occurs in a graph
   *
   * @param checkNode - a method that returns a boolean and is inputted a
   *                  StateData object
   * @return None if checkNode returns true for each node or Some(node) for
   *         the node that checkNode returns false for
   */
  def checkAlways(checkNode: (StateData => Boolean)):
  Option[Node[StateData]] = {
    var check = List(startState)
    var visited = Set[Node[StateData]]()

    def helpNode(node: Node[StateData]): Option[Node[StateData]] = {
      while (check.nonEmpty) {
        val currNode = check.head.head
        check = check.tail
        if (!checkNode(currNode.getContents())) {
          return Some(currNode)
        }
        else {
          visited += currNode
          for (node <- currNode.getNexts()) {
            if (!visited.contains(node)) {
              check = Some(node) :: check
            }
          }
        }
      }
      None
    }

    startState match {
      case None => throw new IllegalArgumentException("node doesn't exist")
      case Some(node) => helpNode(node)
    }
  }

  /**
   * A method that checks that a case always occurs in a graph
   *
   * @param checkNode - a method that returns a boolean and is inputted a
   *                  StateData object
   * @return None if checkNode returns false for each node or Some(node) for
   *         the node that checkNode returns true for
   */
  def checkNever(checkNode: StateData => Boolean): Option[Node[StateData]] = {
    checkAlways(state => !checkNode(state))
  }
}