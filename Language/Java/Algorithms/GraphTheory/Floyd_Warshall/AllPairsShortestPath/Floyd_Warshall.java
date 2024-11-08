package Language.Java.Algorithms.GraphTheory.Floyd_Warshall.AllPairsShortestPath;

import Language.Java.Algorithms.GraphTheory.Floyd_Warshall.AdjacencyMapGraph;
import Language.Java.DataStructures.Graphs.Edge;
import Language.Java.DataStructures.Graphs.Graph;
import Language.Java.DataStructures.Graphs.Vertex;

public class Floyd_Warshall {

    public static <V,E> void shortestPath(Graph<V,E> g){
        int vertices = g.numVertices();
        int[][] dist = new int[vertices][vertices];

        for(int i = 0; i < vertices; i++){
            for(int j = 0; j < vertices; j++){
                if(i == j){
                    dist[i][j] = 0;
                } else {
                    dist[i][j] = Integer.MAX_VALUE;
                }
            }
        }

        for(Edge<E> e : g.edges()){
            Vertex<V>[] endpoints = e.getEndPoints();
            if(endpoints == null) throw new IllegalArgumentException("Edge without endpoints!");
            Vertex<V> origin = endpoints[0];
            Vertex<V> dest = endpoints[1];
            if(!(e.getElement() instanceof Integer)) throw new IllegalArgumentException("Edge without endpoints!");
            dist[origin.getPosition().getIndex()][dest.getPosition().getIndex()] = (int) e.getElement();
        }

        for(int k = 0 ; k < vertices; k++){
            for(int i = 0; i < vertices; i++){
               for(int j = 0; j < vertices; j++){
                 if(dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE){
                    if(dist[i][j] > dist[i][k] + dist[k][j]){
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                 }
               }
            
            }
        }

        printSolution(dist, g);
        
    }

    private static <V,E> void printSolution(int[][] dist, Graph<V,E> g){
        Vertex<V>[] vertices =(Vertex<V>[]) new Vertex[g.numVertices()];
        for(Vertex<V> v : g.vertices()){
            int index = v.getPosition().getIndex();
            vertices[index] = v;
        }
        System.out.print("   ");
        for(int i = 0; i < vertices.length; i++){
            System.out.print(vertices[i].getElement() + "  ");
        }
        System.out.println();
        for(int i = 0; i < vertices.length; i++){
            System.out.print(vertices[i].getElement() + "  ");
            for(int j = 0 ; j < vertices.length; j++){
                System.out.print(dist[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        AdjacencyMapGraph<Integer, Integer> graph = new AdjacencyMapGraph<>(true);

        // Add vertices (0, 1, 2, 3)
        Vertex<Integer> v0 = graph.insertVertex(0);
        Vertex<Integer> v1 = graph.insertVertex(1);
        Vertex<Integer> v2 = graph.insertVertex(2);
        Vertex<Integer> v3 = graph.insertVertex(3);

        // Add edges with weights
        graph.insertEdge(v0, v1, 5);
        graph.insertEdge(v0, v3, 10);
        graph.insertEdge(v1, v2, 3);
        graph.insertEdge(v2, v3, 1);
        graph.insertEdge(v3, v0, 2); // Creating a cycle for robustness check

        // Print initial graph representation (if printGraph() method is implemented)
        System.out.println("Graph structure:");
        graph.printGraph();

        // Run Floyd-Warshall's shortest path algorithm
        System.out.println("Running Floyd-Warshall algorithm...");
        shortestPath(graph);

        // Expected distances
        System.out.println("\nExpected Shortest Paths:");
        System.out.println("From 0 to 1: 5");
        System.out.println("From 0 to 2: 8 (via 1)");
        System.out.println("From 0 to 3: 9 (via 1 -> 2)");
        System.out.println("From 1 to 3: 4 (via 2)");
        System.out.println("From 2 to 0: 3 (via 3)");
        System.out.println("From 3 to 1: 7 (via 0)");
    }
    
    
}
