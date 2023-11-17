package graph;
import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public abstract class GraphInstanceTest {

    // Existing tests...

    @Test
    public void testAddVertex() {
        Graph<String> graph = emptyInstance();
        assertTrue(graph.add("A"));
        assertTrue(graph.vertices().contains("A"));
    }

    @Test
    public void testRemoveVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        assertTrue(graph.remove("A"));
        assertFalse(graph.vertices().contains("A"));
    }

    @Test
    public void testSetEdge() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");

        // Add edge with weight
        assertEquals(0, graph.set("A", "B", 3));
        assertTrue(graph.targets("A").containsKey("B"));
        assertEquals(3, (int) graph.targets("A").get("B"));

        // Update edge weight
        assertEquals(3, graph.set("A", "B", 5));
        assertEquals(5, (int) graph.targets("A").get("B"));

        // Remove edge
        assertEquals(5, graph.set("A", "B", 0));
        assertFalse(graph.targets("A").containsKey("B"));
    }

    @Test
    public void testRemoveEdge() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");

        graph.set("A", "B", 3);
        assertTrue(graph.targets("A").containsKey("B"));

        assertTrue(graph.set("A", "B", 0) > 0);
        assertFalse(graph.targets("A").containsKey("B"));
    }

    @Test
    public void testSourcesAndTargets() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.add("C");

        graph.set("A", "B", 2);
        graph.set("A", "C", 3);

        Map<String, Integer> sources = graph.sources("B");
        assertEquals(2, (int) sources.get("A"));

        Map<String, Integer> targets = graph.targets("A");
        assertEquals(2, (int) targets.get("B"));
        assertEquals(3, (int) targets.get("C"));
    }

    // Additional tests for other methods...

    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
}
