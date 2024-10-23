package Language.Java.DataStructures.Graphs.UndirectedGraphs.AdjacencyMatrix;

import Language.Java.DataStructures.Graphs.UndirectedGraphs.AdjacencyMap.AdjacencyMapGraph;
import Language.Java.DataStructures.Graphs.Vertex;

public class AdjacencyMatrixTest {
    public static void main(String[] args) {
       AdjacencyMapGraph<Integer, Integer> graph = new AdjacencyMapGraph<>(false);
       Vertex<Integer> vertexA = graph.insertVertex(0);
        Vertex<Integer> vertexB = graph.insertVertex(1);
        graph.insertEdge(vertexA, vertexB, 10);
        Vertex<Integer> vertexC = graph.insertVertex(3);
        Vertex<Integer> vertexD = graph.insertVertex(2);
        Vertex<Integer> vertexE = graph.insertVertex(4);
        graph.insertEdge(vertexA, vertexC, 20);
        graph.insertEdge(vertexC, vertexD, 13);
        graph.insertEdge(vertexD, vertexE, 51);
        graph.dfs(vertexA);
        System.out.println();
        graph.bfs(vertexA);
    }
}
