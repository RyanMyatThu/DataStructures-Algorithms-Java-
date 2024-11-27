package Language.Java.Algorithms.GraphTheory.ShortestPath;

import Language.Java.Algorithms.GraphTheory.Floyd_Warshall.AdjacencyMapGraph;
import Language.Java.DataStructures.Graphs.Edge;
import Language.Java.DataStructures.Graphs.Graph;
import Language.Java.DataStructures.Graphs.Vertex;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DijkstrasAlgorithm{

    public static <V, E> HashMap<Vertex<V>, Integer> searchPath(Vertex<V> vertex, Graph<V,E> graph){
        
        int distFromSource = 0;
        int vertices = graph.numVertices();
        int[] dist = new int[vertices];
        HashMap<Vertex<V>, Integer> result = new HashMap<>();
        
        HashSet<Vertex<V>> visited = new HashSet<>();
        Queue<Vertex<V>> queue = new LinkedList();

        queue.add(vertex);

        
        for(int i = 0; i < vertices; i++){
                dist[i] = Integer.MAX_VALUE;
        }
        dist[vertex.getPosition().getIndex()] = 0;

        while(!queue.isEmpty()){
            Vertex<V> current = queue.poll();
            List<Edge<E>> outgoing = convert(graph.outGoingEdges(current));
            if(!outgoing.isEmpty()){
            Edge<E> min = outgoing.get(0);
            for(Edge<E> edge : outgoing){
                Vertex<V> dest = graph.opposite(current, edge);
                if(graph.getEdge(vertex, current) != null && dist[dest.getPosition().getIndex()] == Integer.MAX_VALUE){
                    dist[dest.getPosition().getIndex()] = distFromSource + (int) edge.getElement();
                    result.put(dest, distFromSource + (int) edge.getElement());
                    graph.insertEdge(vertex, dest, null);
                }
                if(dist[dest.getPosition().getIndex()] == Integer.MAX_VALUE){
                    dist[dest.getPosition().getIndex()] = (int) edge.getElement();
                    result.put(dest,(int) edge.getElement());
                }
                if(graph.getEdge(vertex,current) != null && dist[dest.getPosition().getIndex()] > distFromSource + (int) edge.getElement() ){
                    dist[dest.getPosition().getIndex()] = distFromSource + (int) edge.getElement();
                    result.put(dest, distFromSource + (int) edge.getElement());
                }
                if((int) min.getElement() > (int) edge.getElement()){
                    min = edge;
                }
            }
            distFromSource += (int) min.getElement();
            Vertex<V> next = graph.opposite(current, min);
           
            if(!visited.contains(next)){
            queue.add(next);
            }
            visited.add(current);
        }
    }
       printSolution(vertex , dist , graph);
       return result;
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
            System.out.print( " " + dist[i] + " ");
        }
        System.out.println();
        
    }

    static <E> List<Edge<E>> convert(Iterable<Edge<E>> edges){
        List<Edge<E>> list = new ArrayList<>();
        for(Edge<E> edge : edges){
            list.add(edge);
        }
        return list;
    }

    public static void main(String[] args) {
        AdjacencyMapGraph<String, Integer> graph1 = new AdjacencyMapGraph<>(true);

        Vertex<String> v0 = graph1.insertVertex("A");
        Vertex<String> v1 = graph1.insertVertex("B");
        Vertex<String> v2 = graph1.insertVertex("C");
        Vertex<String> v3 = graph1.insertVertex("D");
        
        // Add edges with weights
        graph1.insertEdge(v0, v1, 4);
        graph1.insertEdge(v0, v2, 1);
        graph1.insertEdge(v1, v2, 2);
        graph1.insertEdge(v1, v3, 5);
        graph1.insertEdge(v2, v3, 8);
        
        
        System.out.println("Graph 1 structure:");

      Vertex<String> source = v0;
      HashMap<Vertex<String>, Integer> result =  searchPath(source, graph1);
    System.out.println("Calculating shortest distances from the vertex " +  source.getElement());
     result.forEach((Vert,Dist) -> {
        System.out.println("Shortest Distance To " + Vert.getElement() + " is " + Dist);
     });
        
    }
}