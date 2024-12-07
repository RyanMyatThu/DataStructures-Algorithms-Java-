package Language.Java.DataStructures.Graphs.TypesOfGraphs.AdjancencyList;

import Language.Java.DataStructures.Graphs.Edge;
import Language.Java.DataStructures.Graphs.Vertex;

public class AdjacencyListGraphTest {
    public static void main(String[] args) {
             // Create a directed graph with a capacity of 5
        AdjacencyListGraph<String, Integer> graph = new AdjacencyListGraph<>( true);

        // Test 1: Adding vertices
        System.out.println("Test 1: Adding vertices");
        Vertex<String> v1 = graph.insertVertex("A");
        Vertex<String> v2 = graph.insertVertex("B");
        Vertex<String> v3 = graph.insertVertex("C");
        System.out.println("Vertices added: A, B, C");
        System.out.println("Expected number of vertices: 3");
        System.out.println("Actual number of vertices: " + graph.numVertices());
        
        // Test 2: Adding edges
        System.out.println("\nTest 2: Adding edges");
        graph.insertEdge(v1, v2, 1); // A -> B
        graph.insertEdge(v2, v3, 2); // B -> C
        System.out.println("Edges added: A -> B, B -> C");
        System.out.println("Expected number of edges: 2");
        System.out.println("Actual number of edges: " + graph.numEdges());

        // Print the adjacency matrix
        System.out.println("\nAdjacency List after adding edges:");
        graph.printGraph();

        // Test 3: DFS Traversal
        System.out.print("\nTest 3: DFS starting from A: ");
        graph.dfs(v1); // Expected output: A B C
        System.out.println();

        // Test 4: BFS Traversal
        System.out.print("\nTest 4: BFS starting from A: ");
        graph.bfs(v1); // Expected output: A B C
        System.out.println();

        // Test 5: Removing an edge
        System.out.println("\nTest 5: Removing edge A -> B");
        Edge<Integer> edgeToRemove = graph.getEdge(v1, v2);
        graph.removeEdge(edgeToRemove);
        
        System.out.println("Expected number of edges after removal: 1");
        System.out.println("Actual number of edges after removal: " + graph.numEdges());
        
        // Print the adjacency matrix after edge removal
        System.out.println("\nAdjacency List after removing edge A -> B:");
        graph.printGraph();

        // Test 6: Removing a vertex
        System.out.println("\nTest 6: Removing vertex B");
        graph.removeVertex(v2);
        
        System.out.println("Expected number of vertices after removal: 2");
        System.out.println("Actual number of vertices after removal: " + graph.numVertices());
        
        // Print the adjacency matrix after vertex removal
        System.out.println("\nAdjacency List after removing vertex B:");
        graph.printGraph();

        // Test 7: Adding back vertex and creating new edges
        System.out.println("\nTest 7: Adding back vertex B and creating new edges");
        Vertex<String> v4 = graph.insertVertex("B"); // Add back vertex B
        graph.insertEdge(v1, v4, 3); // A -> B (new edge)
        
        System.out.println("Expected number of vertices after adding back B: 3");
        System.out.println("Actual number of vertices after adding back B: " + graph.numVertices());
        
        // Print the adjacency matrix after adding back vertex B
        System.out.println("\nAdjacency List after adding back vertex B:");
        graph.printGraph();


        System.out.println();


    }
    
}
