package Language.Java.DataStructures.Graphs.UndirectedGraphs.AdjacencyMap;

import Language.Java.DataStructures.Graphs.Vertex;

public class AdjacencyMapTest {
    public static void main(String[] args) {
        AdjacencyMapGraph<String, Integer> graph = new AdjacencyMapGraph<>(false);
        Vertex<String> vertexA = graph.insertVertex("A");
        Vertex<String> vertexB = graph.insertVertex("B");
        graph.insertEdge(vertexA, vertexB, 12);
        System.out.println(graph.getEdge(vertexA, vertexB).getElement());
    }
    
}
