/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.*;

/**
 * An implementation of Graph using ConcreteVerticesGraph.
 * @param <String> The type of the graph elements.
 */
public class ConcreteVerticesGraph implements Graph<String> {

  private final List<Vertex> vertices = new ArrayList<>();

  // Abstraction function:
  //   The List<Vertex> vertices represents a graph where each Vertex object
  //   represents a node in the graph and its associated edges.

  // Representation invariant:
  //   vertices list must not be null.

  // Safety from rep exposure:
  //   - The vertices field is private and final.
  //   - Methods that return vertices provide defensive copies.

  /**
   * Constructs a new ConcreteVerticesGraph.
   */
  public ConcreteVerticesGraph() {
    checkRep();
  }

  /**
   * Checks if the representation invariant holds.
   */
  private void checkRep() {
    assert vertices != null : "Vertices list cannot be null";
  }

  /**
   * Adds a vertex to the graph.
   *
   * @param vertex The vertex to be added.
   * @return True if the vertex was added successfully, false if the vertex already exists.
   */
  @Override
  public boolean add(String vertex) {
    // Create a new Vertex only if it doesn't exist
    for (Vertex v : vertices) {
      if (v.toString().equals(vertex)) {
        return false; // Vertex already exists
      }
    }
    Vertex newVertex = new Vertex();
    newVertex.addTarget(vertex, 0); // Adding a self-loop with weight 0
    vertices.add(newVertex);
    return true;
  }

  /**
   * Sets a directed edge with a weight between two vertices in the graph.
   *
   * @param source The source vertex.
   * @param target The target vertex.
   * @param weight The weight of the edge.
   * @return The weight of the edge set, or -1 if the source vertex doesn't exist.
   */
  @Override
  public int set(String source, String target, int weight) {
    for (Vertex v : vertices) {
      if (v.toString().equals(source)) {
        v.addTarget(target, weight);
        return weight;
      }
    }
    return -1; // Source vertex doesn't exist
  }

  /**
   * Removes a vertex from the graph.
   *
   * @param vertex The vertex to be removed.
   * @return True if the vertex was removed successfully, false if the vertex doesn't exist.
   */
  @Override
  public boolean remove(String vertex) {
    for (Vertex v : vertices) {
      if (v.toString().equals(vertex)) {
        vertices.remove(v);
        return true;
      }
    }
    return false; // Vertex not found
  }

  /**
   * Retrieves all vertices in the graph.
   *
   * @return Set containing all vertices in the graph.
   */
  @Override
  public Set<String> vertices() {
    Set<String> allVertices = new HashSet<>();
    for (Vertex v : vertices) {
      allVertices.addAll(v.getSources().keySet());
      allVertices.addAll(v.getTargets().keySet());
    }
    return allVertices;
  }

  /**
   * Retrieves sources for a given target vertex.
   *
   * @param target The target vertex.
   * @return Map of source vertices and their corresponding edge weights, or an empty map if the target vertex doesn't exist.
   */
  @Override
  public Map<String, Integer> sources(String target) {
    Map<String, Integer> sourceMap = new HashMap<>();
    for (Vertex v : vertices) {
      if (v.toString().equals(target)) {
        sourceMap.putAll(v.getSources());
        return sourceMap;
      }
    }
    return Collections.emptyMap(); // Target vertex doesn't exist
  }

  /**
   * Retrieves targets for a given source vertex.
   *
   * @param source The source vertex.
   * @return Map of target vertices and their corresponding edge weights, or an empty map if the source vertex doesn't exist.
   */
  @Override
  public Map<String, Integer> targets(String source) {
    Map<String, Integer> targetMap = new HashMap<>();
    for (Vertex v : vertices) {
      if (v.toString().equals(source)) {
        targetMap.putAll(v.getTargets());
        return targetMap;
      }
    }
    return Collections.emptyMap(); // Source vertex doesn't exist
  }

  /**
   * Returns a string representation of the graph.
   *
   * @return String representation of the graph.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Vertex v : vertices) {
      sb.append(v.toString()).append("\n");
    }
    return sb.toString();
  }
}

/**
 * Represents a vertex in the graph.
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 */
class Vertex {

  private final Map<String, Integer> sources;
  private final Map<String, Integer> targets;

  /**
   * Abstraction function:
   * Represents a vertex in the graph.
   * The sources map holds edges coming into this vertex.
   * The targets map holds edges going out from this vertex.
   */

  /**
   * Representation invariant:
   * sources and targets maps must not be null.
   */

  /**
   * Constructs a new Vertex.
   */
  Vertex() {
    sources = new HashMap<>();
    targets = new HashMap<>();
    checkRep();
  }

  /**
   * Checks if the representation invariant holds.
   */
  private void checkRep() {
    assert sources != null : "Sources map cannot be null";
    assert targets != null : "Targets map cannot be null";
  }

  /**
   * Adds an edge from the source vertex to this vertex with the given weight.
   *
   * @param source The source vertex.
   * @param weight The weight of the edge.
   */
  void addSource(String source, int weight) {
    sources.put(source, weight);
  }

  /**
   * Adds an edge from this vertex to the target vertex with the given weight.
   *
   * @param target The target vertex.
   * @param weight The weight of the edge.
   */
  void addTarget(String target, int weight) {
    targets.put(target, weight);
  }

  /**
   * Removes the edge coming from the source vertex to this vertex.
   *
   * @param source The source vertex.
   */
  void removeSource(String source) {
    sources.remove(source);
  }

  /**
   * Removes the edge going from this vertex to the target vertex.
   *
   * @param target The target vertex.
   */
  void removeTarget(String target) {
    targets.remove(target);
  }

  /**
   * Retrieves the sources map for this vertex.
   *
   * @return The sources map of this vertex.
   */
  public Map<String, Integer> getSources() {
    return new HashMap<>(sources);
  }

  /**
   * Retrieves the targets map for this vertex.
   *
   * @return The targets map of this vertex.
   */
  public Map<String, Integer> getTargets() {
    return new HashMap<>(targets);
  }

  /**
   * Returns a string representation of the Vertex.
   *
   * @return String representation of the Vertex.
   */
  @Override
  public String toString() {
    return "Vertex{" + "sources=" + sources + ", targets=" + targets + '}';
  }
}
