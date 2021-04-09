package sol

import src.Graph
import src.Node

// ------------- DO NOT CHANGE --------------------
import org.apache.commons.csv._
import java.io.{File, FileReader, IOException}
import scala.collection.JavaConverters.iterableAsScalaIterableConverter
import scala.collection.mutable
// ------------------------------------------------

/**
 * Class of the State Machine.
 * @param stateGraph - an object which implements Graph stored inside a
 *                   stateMachine.
 */
class StateMachine(stateGraph: Graph[StateData]) {

  // field for the starting state of the state machine
  private var startState : Option[Node[StateData]] = None

  // TODO : Modify initCSV so that it raises an IOException if the edgesCSV
  //  tries to use a state that was not listed in the nodeCSV
  /**
   * Initializes a StateMachine from CSV files.
   * @param nodeCSV - the filename of the CSV containing node information
   * @param edgesCSV - the filename of the CSV containing edges information
   * @param startState - the starting state of the state machine (the id of the
   *                   node)
   */
  def initFromCSV(nodeCSV: String, edgesCSV: String, startState: String) {
    val format = CSVFormat.RFC4180.withHeader();

    // Parse nodeCSV
    val idMap = new mutable.HashMap[String, Node[StateData]]()
    try {
      val parser: CSVParser = new CSVParser(new FileReader(new File(nodeCSV)),
        format)
      val records = parser.getRecords().asScala.toList
      for (record <- records) {
        idMap(record.get(0)) = stateGraph.createNode(new StateData(record.get(1).split(";").toList))

        if(record.get(0).equals(startState)) {
          this.startState = Some(idMap(record.get(0)))
        }
      }
    }
    catch {
      case e: IOException => println("error reading " + nodeCSV)
    }

    // Parse edgeCSV
    try {
      val parser: CSVParser = new CSVParser(new FileReader(new File(edgesCSV)), format)
      val records = parser.getRecords().asScala.toList
      for (record <- records) {
        stateGraph.addEdge(idMap(record.get(0)), idMap(record.get(1)))
      }
    }
    catch {
      case e: IOException => println("error reading" + edgesCSV)
    }
  }

  def checkAlways(checkNode: (StateData => Boolean)): Option[Node[StateData]] = {
    // TODO : Implement!
    None
  }

  def checkNever(checkNode: StateData => Boolean): Option[Node[StateData]] = {
    // TODO : Implement!
    None
  }
}