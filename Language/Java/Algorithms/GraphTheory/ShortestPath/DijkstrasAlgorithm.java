package Language.Java.Algorithms.GraphTheory.ShortestPath;

import Language.Java.Algorithms.GraphTheory.Floyd_Warshall.AdjacencyMapGraph;
import Language.Java.DataStructures.Graphs.Edge;
import Language.Java.DataStructures.Graphs.Graph;
import Language.Java.DataStructures.Graphs.Vertex;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class DijkstrasAlgorithm{

    private static class VertexDistance<V>{
        Vertex<V> destination;
        int distance;
        VertexDistance(Vertex<V> dest, int dist){
            destination = dest;
            distance = dist;
        }


    }

    public static <V, E> HashMap<Vertex<V>, Integer> searchPath(Vertex<V> vertex, Graph<V,E> graph){
        
        int vertCount = graph.numVertices();
        List<Vertex<V>> vertices = convert(graph.vertices());
        int[] dist = new int[vertCount];
        HashMap<Vertex<V>, Integer> result = new HashMap<>();
        
        HashSet<Vertex<V>> visited = new HashSet<>();
        PriorityQueue<VertexDistance<V>> pq = new PriorityQueue<>(Comparator.comparingInt(v -> v.distance));

        pq.add(new VertexDistance<>(vertex, 0));

        
        for(int i = 0; i < vertCount; i++){
                if(i != vertex.getPosition().getIndex()){
                dist[i] = Integer.MAX_VALUE;
                result.put(vertices.get(i), Integer.MAX_VALUE);
                } else {
                    dist[vertex.getPosition().getIndex()] = 0;
                }
        }
        

        while(!pq.isEmpty()){
            VertexDistance<V> vertexDistance = pq.poll();
            Vertex<V> current = vertexDistance.destination;
            if(visited.contains(current)) continue;
            visited.add(current);
            for(Edge<E> edge : graph.outGoingEdges(current)){
                Vertex<V> dest = graph.opposite(current, edge);
                int weight = (int) edge.getElement();
                int newDistance = dist[current.getPosition().getIndex()] + weight;

                if(newDistance < dist[dest.getPosition().getIndex()]){
                    dist[dest.getPosition().getIndex()] = newDistance;
                    result.put(dest, newDistance);
                    pq.add(new VertexDistance<>(dest,newDistance));
                }

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
            if(dist[i] == Integer.MAX_VALUE){
            System.out.print( " " + "∞" + " ");
            } else {
            System.out.print( " " + dist[i] + " ");
            }
        }
        System.out.println();
        
    }

    static <E> List<E> convert(Iterable<E> items){
        List<E> list = new ArrayList<>();
        for(E item : items){
            list.add(item);
        }
        return list;
    }

    public static void main(String[] args) {
      AdjacencyMapGraph<Integer, Integer> graph = new AdjacencyMapGraph<>(true);

    // Insert vertices
    Vertex<Integer> v0 = graph.insertVertex(0);
    Vertex<Integer> v1 = graph.insertVertex(1);
    Vertex<Integer> v2 = graph.insertVertex(2);
    Vertex<Integer> v3 = graph.insertVertex(3);
    Vertex<Integer> v4 = graph.insertVertex(4); // Disconnected

    // Insert edges with weights
    graph.insertEdge(v0, v1, 4);
    graph.insertEdge(v0, v2, 1);
    graph.insertEdge(v2, v3, 2);

    // Test Dijkstra's algorithm
    System.out.println("Testing Disconnected Graph:");
    HashMap<Vertex<Integer>, Integer> result = searchPath(v0, graph);


    Vertex<Integer> source = v0;
     System.out.println("Calculating shortest distances from the vertex " +  source.getElement());
     result.forEach((Vert,Dist) -> {
        if(Dist != Integer.MAX_VALUE){
        System.out.println("Shortest Distance To " + Vert.getElement() + " is " + Dist);
        } else {
            System.out.println("Cannot reach to vertex " + Vert.getElement());
        }
     });
        
    }
}