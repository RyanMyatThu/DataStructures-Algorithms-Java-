package Language.Java.Algorithms.GraphTheory.Floyd_Warshall.TransitiveClosure;

import Language.Java.Algorithms.GraphTheory.Floyd_Warshall.AdjacencyMapGraph;
import Language.Java.DataStructures.Graphs.Graph;
import Language.Java.DataStructures.Graphs.Vertex;

public class Transitive_Closure {
    public static <V,E> void transitiveClosure(Graph<V,E> g){
        for(Vertex<V> k : g.vertices()){
            for(Vertex<V> i : g.vertices()){
                if(i != k && g.getEdge(i,k) != null){
                    for(Vertex<V> j : g.vertices()){
                        if(i != j && j != k && g.getEdge(k, j) != null){
                            if(g.getEdge(i, j) == null){
                                g.insertEdge(i, j, null);
                            }
                        }
                    }
                }
            }
        }
    }
    public static void main(String[] args) {
        AdjacencyMapGraph<String,Integer> graph = new AdjacencyMapGraph<>(true);
        
        

    }
    
}
