package nz.ac.auckland.se281;

import java.util.*;

public class Graph<String> {
  private Map<String, List<String>> adjacenciesMap;

  public Graph() {
    this.adjacenciesMap = new HashMap<>();
  }

  public void addVertex(String node) {
    adjacenciesMap.putIfAbsent(node, new LinkedList<>());
  }

  public void addEdge(String node1, String node2) {
    addVertex(node1);
    addVertex(node2);
    adjacenciesMap.get(node1).add(node2);
    adjacenciesMap.get(node2).add(node1);
  }

  public List<String> detectPathFromSource(String startNode, String endNode) {
    if (!adjacenciesMap.containsKey(startNode) || !adjacenciesMap.containsKey(endNode)) {
      return null; // Start or end node not present in the map
    }

    // record visited countries
    List<String> visited = new ArrayList<>();
    // record the order of visits
    Queue<String> queue = new LinkedList<>();
    // record the shortest path
    List<String> countryPath = new ArrayList<>();
    Map<String, String> parentMap = new HashMap<>();

    // add root to the queue to search first
    queue.add(startNode);
    // visit the first country(source)
    visited.add(startNode);
    countryPath.add(startNode);

    while (!queue.isEmpty()) {
      // remove first element from queue
      String currentCountry = queue.poll();

      // iterate through adjacent nodes
      for (String adjacentCountry : adjacenciesMap.get(currentCountry)) {
        // if the adjacent country have not been visited
        if (!visited.contains(adjacentCountry)) {
          // add it to the queue and visited
          queue.add(adjacentCountry);
          visited.add(adjacentCountry);
        } else if (!adjacentCountry.equals(parentMap.get(currentCountry))
            && adjacentCountry.equals(endNode)) {
          // closest path detected
          List<String> cyclePath = new ArrayList<>();
          cyclePath.add(adjacentCountry);
          String node = currentCountry;
          while (node != null) {
            cyclePath.add(node);
            node = parentMap.get(node);
            if (node != null && node.equals(adjacentCountry)) {
              cyclePath.add(node);
              break;
            }
          }
          Collections.reverse(cyclePath);
          return cyclePath;
        }
      }
    }
    return null;
  }
}
