package sol

import tester.Tester

/**
 * A testing object to test the AdjacencyListGraph and StateMachine classes
 */
object ModelCheckingTest {

  /**
   * A method that tests the create node method
   *
   * @param t - Tester
   */
  def testCreateNode(t: Tester): Unit = {
    val stringGraph = new AdjacencyListGraph[String]
    val nodeY = new stringGraph.ALNode("Yay", List())
    t.checkExpect(stringGraph.createNode("Yay"), nodeY)
    t.checkExpect(stringGraph.visitedNodes, List(nodeY))
    val node2 = new stringGraph.ALNode("Wow", List())
    t.checkExpect(stringGraph.createNode("Wow"), node2)
    t.checkExpect(stringGraph.visitedNodes, List(node2, nodeY))
  }

  /**
   * A method that tests the add edge method in a node
   *
   * @param t - Tester
   */
  def testNodeAddEdge(t: Tester): Unit = {
    val stringGraph = new AdjacencyListGraph[String]
    val nodeY = new stringGraph.ALNode("Yay", List())
    val nodeAdd = new stringGraph.ALNode("Added", List())
    val remainEmpty = new stringGraph.ALNode("empty", List())
    nodeY.addEdge(nodeAdd)
    t.checkExpect(nodeY.getsTo, List(nodeAdd))
    nodeAdd.addEdge(nodeY)
    t.checkExpect(nodeAdd.getsTo, List(nodeY))
    nodeY.addEdge(remainEmpty)
    t.checkExpect(nodeY.getsTo, List(remainEmpty, nodeAdd))
    t.checkExpect(remainEmpty.getsTo, List())
  }

  /**
   * A method that tests the add node method for a graph
   *
   * @param t - Tester
   */
  def testGraphAddNode(t: Tester): Unit = {
    val stringGraph = new AdjacencyListGraph[String]
    val nodeY = new stringGraph.ALNode("Yay", List())
    val nodeAdd = new stringGraph.ALNode("Added", List())
    val remainEmpty = new stringGraph.ALNode("empty", List())
    val anotherToY = new stringGraph.ALNode("WOWOW", List(remainEmpty))
    stringGraph.addEdge(nodeY, nodeAdd)
    t.checkExpect(nodeY.getsTo, List(nodeAdd))
    t.checkExpect(nodeAdd.getsTo, List())
    stringGraph.addEdge(nodeY, anotherToY)
    t.checkExpect(nodeY.getsTo, List(anotherToY, nodeAdd))
    t.checkExpect(anotherToY.getsTo, List(remainEmpty))
    t.checkExpect(remainEmpty.getsTo, List())
  }

  /**
   * A method that tests the get nexts method
   *
   * @param t - Tester
   */
  def testGetNexts(t: Tester): Unit = {
    val stringGraph = new AdjacencyListGraph[String]
    val nodeY = new stringGraph.ALNode("Yay", List())
    val nodeAdd = new stringGraph.ALNode("Added", List())
    val remainEmpty = new stringGraph.ALNode("empty", List())
    t.checkExpect(remainEmpty.getNexts(), List())
    stringGraph.addEdge(nodeY, nodeAdd)
    t.checkExpect(nodeY.getNexts(), List(nodeAdd))
    t.checkExpect(nodeAdd.getNexts(), List())
    stringGraph.addEdge(nodeY, remainEmpty)
    t.checkExpect(nodeY.getNexts(), List(remainEmpty, nodeAdd))
    t.checkExpect(remainEmpty.getNexts(), List())
  }


  /**
   * A method that tests the get contents method
   *
   * @param t - Tester
   */
  def testGetContents(t: Tester): Unit = {
    val stringGraph = new AdjacencyListGraph[String]
    val nodeY = new stringGraph.ALNode("Yay", List())
    val listGraph = new AdjacencyListGraph[List[String]]
    val nodeList = new listGraph.ALNode(List("wow", "yay"), List())
    val numberGraph = new AdjacencyListGraph[Int]
    val numNode = new numberGraph.ALNode(1, List())
    t.checkExpect(nodeY.getContents(), "Yay")
    t.checkExpect(nodeList.getContents(), List("wow", "yay"))
    t.checkExpect(numNode.getContents(), 1)
  }


  // FOr the throw exception make new csv files with an edge reference id thats not a node
  /**
   * A method that tests the check always method
   *
   * @param t - Tester
   */
  def testCheckAlways(t: Tester): Unit = {
    //Set up manual graph that replicates nodes 0 through 3 from
    // street-light-nodes.csv
    val ManualStartGraphInput = new AdjacencyListGraph[StateData]
    val StateManual = new StateMachine(ManualStartGraphInput)
    val node0 = StateManual.addState(List("bigAveRed", "smallStGreen"))
    val node1 = StateManual.addState(List("bigAveRed", "smallStYellow"))
    val node2 = StateManual.addState(List("bigAveGreen", "smallStRed"))
    val node3 = StateManual.addState(List("bigAveYellow", "smallStRed"))
    StateManual.addTransition(node0, node1)
    StateManual.addTransition(node1, node2)
    StateManual.addTransition(node2, node3)
    StateManual.addTransition(node3, node0)
    StateManual.setStart(node0)
    //Set up empty manual graph
    val EmptyManualGraph = new AdjacencyListGraph[StateData]
    val EmptyManual = new StateMachine(EmptyManualGraph)
    //Set up nodes with same bigAve
    val SameState = new AdjacencyListGraph[StateData]
    val SameManual = new StateMachine(SameState)
    val SameNode0 = SameManual.addState(List("bigAveRed", "smallStGreen"))
    val SameNode1 = SameManual.addState(List("bigAveRed", "smallStYellow"))
    SameManual.addTransition(SameNode0, SameNode1)
    SameManual.setStart(SameNode0)
    // Test on manual graphs
    t.checkExpect(StateManual.checkAlways(state =>
      state.hasLabel("bigAveGreen") && state.hasLabel("smallStGreen")),
      Some(node0))
    t.checkExpect(StateManual.checkAlways(state =>
      state.hasLabel("bigAveRed")), Some(node2))
    t.checkExpect(SameManual.checkAlways(state =>
      state.hasLabel("bigAveRed")), None)
    t.checkExpect(SameManual.checkAlways(state =>
      state.hasLabel("smallStYellow")), Some(SameNode0))
    t.checkExpect(SameManual.checkAlways(state =>
      state.hasLabel("WOW")), Some(SameNode0))
    t.checkExpect(SameManual.checkAlways(state =>
      state.hasLabel("smallStGreen")), Some(SameNode1))
    t.checkException(new IllegalArgumentException("node doesn't exist"),
      EmptyManual, "checkAlways",
      (state: StateData) => state.hasLabel("apple"))
    //Manually recreate nodes from street-light-2-nodes/-edges.csv files
    val StLight2Graph = new AdjacencyListGraph[StateData]
    val StLight2 = new StateMachine(StLight2Graph)
    val StNode0 = StLight2.addState(List("bigAveRed", "smallStGreen"))
    val StNode1 = StLight2.addState(List("bigAveGreen", "smallStGreen"))
    val StNode2 = StLight2.addState(List("bigAveYellow", "smallStGreen"))
    val StNode3 = StLight2.addState(List("bigAveGreen", "smallStGreen"))
    val StNode4 = StLight2.addState(List("bigAveRed", "smallStYellow"))
    StLight2.addTransition(StNode0, StNode1)
    StLight2.addTransition(StNode0, StNode2)
    StLight2.addTransition(StNode2, StNode3)
    StLight2.addTransition(StNode3, StNode4)
    StLight2.addTransition(StNode1, StNode4)
    StLight2.setStart(StNode0)
    t.checkExpect(StLight2.checkAlways(state =>
      state.hasLabel("bigAveGreen")),
      Some(StNode0))
    t.checkExpect(StLight2.checkAlways(state =>
      state.hasLabel("smallStGreen")), Some(StNode4))
  }

  /**
   * A method that tests the check never method
   *
   * @param t - Testerpl
   */
  def testCheckNever(t: Tester): Unit = {
    // Set up graph with one node
    val OneGraph = new AdjacencyListGraph[StateData]
    val OneState = new StateMachine(OneGraph)
    val onlyNode = OneState.addState(List("bigAveRed", "smallStGreen"))
    OneState.setStart(onlyNode)
    // Test on graph with one node
    t.checkExpect(OneState.checkNever(state =>
      state.hasLabel("bigAveGreen") && state.hasLabel("smallStGreen")),
      None)
    t.checkExpect(OneState.checkNever(state =>
      state.hasLabel("bigAveRed") && state.hasLabel("smallStGreen")),
      Some(onlyNode))
    //Set up manual graph that replicates nodes 0 through 3 from
    // street-light-nodes.csv
    val ManualStartGraphInput = new AdjacencyListGraph[StateData]
    val StateManual = new StateMachine(ManualStartGraphInput)
    val node0 = StateManual.addState(List("bigAveRed", "smallStGreen"))
    val node1 = StateManual.addState(List("bigAveRed", "smallStYellow"))
    val node2 = StateManual.addState(List("bigAveGreen", "smallStRed"))
    val node3 = StateManual.addState(List("bigAveYellow", "smallStRed"))
    StateManual.addTransition(node0, node1)
    StateManual.addTransition(node1, node2)
    StateManual.addTransition(node2, node3)
    StateManual.addTransition(node3, node0)
    StateManual.setStart(node0)
    // Test on StateManual for bad case
    t.checkExpect(StateManual.checkNever(state =>
      state.hasLabel("bigAveGreen") && state.hasLabel("smallStGreen")),
      None)
    // add nodes 4 through 7 street-light-nodes.csv
    val node4 = StateManual.addState(List("bigAveGreen", "smallStYellow"))
    val node5 = StateManual.addState(List("bigAveYellow", "smallStYellow"))
    val node6 = StateManual.addState(List("bigAveRed", "smallStRed"))
    val node7 = StateManual.addState(List("bigAveGreen", "smallStGreen"))
    StateManual.addTransition(node4, node5)
    StateManual.addTransition(node5, node6)
    StateManual.addTransition(node6, node7)
    StateManual.addTransition(node7, node5)
    //Change startState to node 4
    StateManual.setStart(node4)
    t.checkExpect(StateManual.checkNever(state =>
      state.hasLabel("bigAveGreen") && state.hasLabel("smallStGreen")),
      Some(node7))
    t.checkExpect(StateManual.checkNever(state =>
      state.hasLabel("bigAveRed") && state.hasLabel("smallStGreen")),
      None)
    t.checkExpect(StateManual.checkNever(state =>
      state.hasLabel("bigAveGreen")),
      Some(node4))
    //Set up empty manual graph
    val EmptyManualGraph = new AdjacencyListGraph[StateData]
    val EmptyManual = new StateMachine(EmptyManualGraph)
    t.checkException(new IllegalArgumentException("node doesn't exist"),
      EmptyManual, "checkAlways",
      (state: StateData) => state.hasLabel("apple"))
    //Manually recreate nodes from street-light-2-nodes/-edges.csv files
    val StLight2Graph = new AdjacencyListGraph[StateData]
    val StLight2 = new StateMachine(StLight2Graph)
    val StNode0 = StLight2.addState(List("bigAveRed", "smallStGreen"))
    val StNode1 = StLight2.addState(List("bigAveGreen", "smallStGreen"))
    val StNode2 = StLight2.addState(List("bigAveYellow", "smallStYellow"))
    val StNode3 = StLight2.addState(List("bigAveGreen", "smallStGreen"))
    val StNode4 = StLight2.addState(List("bigAveRed", "smallStYellow"))
    StLight2.addTransition(StNode0, StNode1)
    StLight2.addTransition(StNode0, StNode2)
    StLight2.addTransition(StNode2, StNode3)
    StLight2.addTransition(StNode3, StNode4)
    StLight2.addTransition(StNode1, StNode4)
    StLight2.setStart(StNode0)
    t.checkExpect(StLight2.checkNever(state =>
      state.hasLabel("bigAveGreen") && state.hasLabel("smallStGreen")),
      Some(StNode1))
    t.checkExpect(StLight2.checkNever(state =>
      state.hasLabel("smallStYellow")),
      Some(StNode4))
    t.checkExpect(StLight2.checkNever(state =>
      state.hasLabel("bigAveRed")),
      Some(StNode0))
  }


  def main(args: Array[String]): Unit = {
    Tester.run(ModelCheckingTest);
  }

}

