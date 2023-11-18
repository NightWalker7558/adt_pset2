/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class ConcreteVerticesGraphTest extends GraphInstanceTest {

    @Override
    public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }

    @Test
    public void testGraphOperations() {
        Graph<String> graph = emptyInstance();

        assertTrue(graph.add("A"));
        assertTrue(graph.add("B"));
        assertTrue(graph.add("C"));
        assertFalse(graph.add("A"));

        assertEquals(0, graph.set("A", "B", 5));
        assertEquals(0, graph.set("A", "C", 3));
        assertEquals(5, graph.set("A", "B", 8));
        assertEquals(3, graph.set("A", "C", 0));

        assertTrue(graph.remove("A"));
        assertFalse(graph.remove("A"));

        graph.add("D");
        graph.add("E");
        Set<String> vertices = graph.vertices();
        assertTrue(vertices.contains("B"));
        assertTrue(vertices.contains("C"));
        assertTrue(vertices.contains("D"));
        assertTrue(vertices.contains("E"));
        assertFalse(vertices.contains("A"));

        graph.set("B", "D", 7);
        graph.set("E", "B", 2);

        Map<String, Integer> sources = graph.sources("B");
        assertEquals(1, sources.size());
        assertTrue(sources.containsKey("E"));
        assertEquals(2, (int) sources.get("E"));

        Map<String, Integer> targets = graph.targets("B");
        assertEquals(1, targets.size());
        assertTrue(targets.containsKey("D"));
        assertEquals(7, (int) targets.get("D"));

        assertEquals(0, graph.targets("C").size());
    }

    @Test
    public void testVertexOperations() {
        Vertex vertex = new Vertex("X");

        assertEquals("X", vertex.getLabel());

        vertex.addEdge("Y", 5);
        vertex.addEdge("Z", 3);

        Map<String, Integer> edges = vertex.getEdges();
        assertEquals(2, edges.size());
        assertEquals(5, (int) edges.get("Y"));
        assertEquals(3, (int) edges.get("Z"));

        vertex.removeEdge("Y");
        assertEquals(1, vertex.getEdges().size());

        vertex.removeEdge("Z");
        assertEquals(0, vertex.getEdges().size());
    }
}
