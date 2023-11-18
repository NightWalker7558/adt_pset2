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
    //   Represents a graph with a list of vertices and their connections
    // Representation invariant:
    //   No two vertices have the same label
    // Safety from rep exposure:
    //   Vertices list is private and final, no direct exposure

    // Constructor
    public ConcreteVerticesGraph() {
        // Empty constructor, as the graph starts with no vertices initially
    }

    // Check representation invariant
    private void checkRep() {
        Set<String> labels = new HashSet<>();
        for (Vertex vertex : vertices) {
            if (labels.contains(vertex.getLabel())) {
                throw new RuntimeException("Duplicate vertex label found: " + vertex.getLabel());
            }
            labels.add(vertex.getLabel());
        }
    }

    @Override
    public boolean add(String vertex) {
        if (!containsVertex(vertex)) {
            vertices.add(new Vertex(vertex));
            checkRep();
            return true;
        }
        return false;
    }

    @Override
    public int set(String source, String target, int weight) {
        Vertex sourceVertex = findVertex(source);
        Vertex targetVertex = findVertex(target);

        if (sourceVertex == null || targetVertex == null) {
            throw new IllegalArgumentException("Source or target vertex not found");
        }

        int previousWeight = sourceVertex.getEdges().getOrDefault(target, 0);
        if (weight != 0) {
            sourceVertex.addEdge(target, weight);
        } else {
            sourceVertex.removeEdge(target);
        }
        checkRep();
        return previousWeight;
    }

    @Override
    public boolean remove(String vertex) {
        Vertex toRemove = findVertex(vertex);
        if (toRemove != null) {
            vertices.remove(toRemove);
            for (Vertex v : vertices) {
                v.removeEdge(vertex);
            }
            checkRep();
            return true;
        }
        return false;
    }

    @Override
    public Set<String> vertices() {
        Set<String> labels = new HashSet<>();
        for (Vertex vertex : vertices) {
            labels.add(vertex.getLabel());
        }
        return labels;
    }

    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = new HashMap<>();
        for (Vertex vertex : vertices) {
            if (vertex.getEdges().containsKey(target)) {
                sources.put(vertex.getLabel(), vertex.getEdges().get(target));
            }
        }
        return sources;
    }

    @Override
    public Map<String, Integer> targets(String source) {
        Vertex sourceVertex = findVertex(source);
        if (sourceVertex != null) {
            return sourceVertex.getEdges();
        }
        return Collections.emptyMap();
    }

    private boolean containsVertex(String label) {
        return findVertex(label) != null;
    }

    private Vertex findVertex(String label) {
        for (Vertex vertex : vertices) {
            if (vertex.getLabel().equals(label)) {
                return vertex;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "ConcreteVerticesGraph{" +
                "vertices=" + vertices +
                '}';
    }
}


/**
 * Represents a vertex in the graph.
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 */
class Vertex {
    private final String label;
    private final Map<String, Integer> edges; // Mapping from target vertex label to edge weight

    // Abstraction function:
    //   Represents a vertex with a unique label and outgoing edges to other vertices with their weights
    // Representation invariant:
    //   label != null, edges != null
    // Safety from rep exposure:
    //   Fields are private and immutable
    
    // Constructor
    public Vertex(String label) {
        this.label = label;
        this.edges = new HashMap<>();
    }
    
    // Check representation invariant
    private void checkRep() {
        assert label != null : "Vertex label cannot be null";
        assert edges != null : "Edges map cannot be null";
    }

    // Add an outgoing edge from this vertex to another with the given weight
    public void addEdge(String target, int weight) {
        edges.put(target, weight);
        checkRep();
    }

    // Remove an outgoing edge from this vertex to another
    public void removeEdge(String target) {
        edges.remove(target);
        checkRep();
    }

 // Get all outgoing edges from this vertex
    public Map<String, Integer> getEdges() {
        return new HashMap<>(edges); // Return a copy to avoid exposing internal representation
    }

    // Get the label of this vertex
    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "label='" + label + '\'' +
                ", edges=" + edges +
                '}';
    }
}

