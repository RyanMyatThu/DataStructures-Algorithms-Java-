package Language.Java.Algorithms.GraphTheory.BellMan_Ford;

import Language.Java.Algorithms.GraphTheory.Floyd_Warshall.AdjacencyMapGraph;
import Language.Java.DataStructures.Graphs.Edge;
import Language.Java.DataStructures.Graphs.Graph;
import Language.Java.DataStructures.Graphs.Vertex;
import java.util.Arrays;

public class BellManFord <V,E> {
     static <V,E> void search(Graph<V,E> g, Vertex<V> source){
        int[] distance = new int[g.numVertices()];
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[source.getPosition().getIndex()] = 0;
      

        for(int i = 0; i < g.numVertices(); i++){
            for(Edge<E> edge : g.edges()){
                Vertex<V> origin = edge.getEndPoints()[0];
                Vertex<V> destination = edge.getEndPoints()[1];
                int weight = (int) edge.getElement();
             
                if(distance[origin.getPosition().getIndex()] != Integer.MAX_VALUE && distance[origin.getPosition().getIndex()] + weight < distance[destination.getPosition().getIndex()]){
                    if(i == g.numVertices() - 1){
                        throw new RuntimeException("Negative cycle detected");
                    }
                    distance[destination.getPosition().getIndex()] =  distance[origin.getPosition().getIndex()] + weight;
                }
            }
        }
        printSolution(source, distance, g);
    }

    
    private static <V,E> void printSolution(Vertex<V> vertex,int[] dist, Graph<V,E> g){
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
        System.out.print(vertex.getElement() + " ");
        for(int i = 0; i < dist.length; i++){
            if(dist[i] == Integer.MAX_VALUE){
            System.out.print( " " + "∞" + " ");
            } else {
            System.out.print( " " + dist[i] + " ");
            }
        }
        System.out.println();
        
    }
    
    public static void main(String[] args) {

        AdjacencyMapGraph<Integer, Integer> graph = new AdjacencyMapGraph<>(true);

        // Add vertices
        Vertex<Integer> v0 = graph.insertVertex(0);
        Vertex<Integer> v1 = graph.insertVertex(1);
        Vertex<Integer> v2 = graph.insertVertex(2);
        Vertex<Integer> v3 = graph.insertVertex(3);
    
        // Add edges with weights
        graph.insertEdge(v0, v1, 4);  // Edge 0 -> 1 with weight 4
        graph.insertEdge(v0, v2, 1);  // Edge 0 -> 2 with weight 1
        graph.insertEdge(v2, v1, 2);  // Edge 2 -> 1 with weight 2
        graph.insertEdge(v1, v3, 5);  // Edge 1 -> 3 with weight 5
        graph.insertEdge(v2, v3, 8);  // Edge 2 -> 3 with weight 8
      //  graph.insertEdge(v3, v1, -10); // Negative-weight edge 3 -> 1
    
        System.out.println("Testing Bellman-Ford Algorithm:");
        try {
            // Run Bellman-Ford algorithm from vertex v0
            search(graph, v0);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
}
