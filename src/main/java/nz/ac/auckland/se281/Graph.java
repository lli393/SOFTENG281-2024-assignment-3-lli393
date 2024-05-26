package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/** This class is the main entry point for graph. */
public class Graph {
  private Map<String, List<String>> adjacenciesMap;

  /** This method is the constructor of the Graph class. */
  public Graph() {
    this.adjacenciesMap = new LinkedHashMap<>();
  }

  /**
   * This method adds a vertex to the graph.
   *
   * @param node the node of country
   */
  public void addVertex(String node) {
    // add node if not yet, with an empty linkedlist for adjacencies
    adjacenciesMap.putIfAbsent(node, new LinkedList<>());
  }

  /**
   * This method adds an edge between two nodes.
   *
   * @param node1 the node of country
   * @param node2 the node of adjacencies
   */
  public void addEdge(String node1, String node2) {
    // create a node1 if not yet and a linkedlist for adjacencies
    addVertex(node1);
    // put node 2 into adjacencies linkedlist for node 1
    adjacenciesMap.get(node1).add(node2);
  }

  /**
   * This method detects the shortest path from source to destination.
   *
   * @param startNode the node of source country
   * @param endNode the node of destination country
   * @return returns the shortest path from source to destination.
   */
  public List<String> detectPathFromSource(String startNode, String endNode) {
    List<String> visited = new ArrayList<>();
    Queue<String> queue = new LinkedList<>();
    // key = child, value = parent(closer to root)
    Map<String, String> parentMap = new HashMap<>();
    // list to store the closest path
    List<String> path = new LinkedList<>();
    queue.add(startNode);
    visited.add(startNode);

    while (!queue.isEmpty()) {

      String node = queue.poll();

      for (String country : adjacenciesMap.get(node)) {

        if (!visited.contains(country)) {
          visited.add(country);
          queue.add(country);
          // use country as key to find the node(value)
          parentMap.put(country, node);
        }
        if (country.equals(endNode)) {
          // set current as endNode
          String current = country;
          while (current != null) {
            path.add(current);
            // update current to adjacent countries(closer to root)
            current = parentMap.get(current);
            if (current != null && current.equals(startNode)) {
              path.add(current);
              break;
            }
          }
          Collections.reverse(path);
          return path;
        }
      }
    }
    return null;
  }

  /**
   * This method gets a Set of unique ordered continents based on the map and path informations.
   *
   * @param map contains country name(string) as key and country info(string array) as value.
   *     country info consists country name(index 0), continent(index 1), tax value(index 2)
   *     respectively.
   * @param path the shortest path from source to destination. The path is a list of country names.
   * @return returns a set of unique and ordered continents.
   */
  public Set<String> getContinentInfo(Map<String, String[]> map, List<String> path) {
    // create a list for the continents, need to be unique and ordered
    Set<String> continentList = new LinkedHashSet<>();
    // search through every country in shortest path
    for (String country : path) {
      // add the continent if unique
      continentList.add(map.get(country)[1]);
    }
    // result
    return continentList;
  }

  /**
   * This method gets a integer of total tax user need to pay to travel in shortest path.
   *
   * @param map the map contains country name(string) as key and country info(string array) as
   *     value. country info consists country name(index 0), continent(index 1), tax value(index 2)
   *     respectively.
   * @param path the shortest path from source to destination.
   * @return returns the total tax user need to pay to travel in shortest path.
   */
  public int getTotalTax(Map<String, String[]> map, List<String> path) {
    // initialise total
    int total = 0;
    // source country name
    String sourceCountry = path.get(0);
    // source country tax
    int sourceTax = Integer.parseInt((map.get(sourceCountry)[2]));
    // delete sourceTax from total, doesn't have to pay
    total -= sourceTax;
    for (String country : path) {
      // add tax from every country
      total += Integer.parseInt(map.get(country)[2]);
    }
    // result
    return total;
  }
}
