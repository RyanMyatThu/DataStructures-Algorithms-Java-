package Language.Java.Algorithms.GraphTheory.TopologicalSort;

import Language.Java.Algorithms.GraphTheory.Floyd_Warshall.AdjacencyMapGraph;
import Language.Java.DataStructures.Graphs.Edge;
import Language.Java.DataStructures.Graphs.Graph;
import Language.Java.DataStructures.Graphs.Vertex;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class TopologicalSort<V,E> {

    static <V,E> void sort(Graph<V,E> g){
        Stack<Vertex<V>> stack = new Stack<>();
        Set<Vertex<V>> recursionSet = new HashSet<>();
        Set<Vertex<V>> visited = new HashSet<>();
        List<V> result = new ArrayList<>();
        for(Vertex<V> v : g.vertices()){
            if(!dfs(recursionSet, stack, v, g, visited)){
                throw new IllegalArgumentException("Cycle Detected! Only Directed Acyclic Graphs Allowed");
            }
        }
      while(!stack.isEmpty()){
        Vertex<V> cur = stack.pop();
        result.add(cur.getElement());
      }
      printSolution(result);

    }

    private static <V,E> boolean dfs(Set<Vertex<V>> recursionSet, Stack<Vertex<V>> stack, Vertex<V> start, Graph<V,E> g, Set<Vertex<V>> visited){
       if(recursionSet.contains(start)){
        return false;
       }
       if(visited.contains(start)){
        return true;
       }
       recursionSet.add(start);
       visited.add(start);
       for(Edge<E> edge : g.outGoingEdges(start)){
            Vertex<V> neighbor = g.opposite(start,edge);
            if(!dfs(recursionSet, stack, neighbor, g, visited)){
                return false;
            }
       }
       recursionSet.remove(start);
       stack.add(start);
       return true;
    }

    private static <V> void printSolution(List<V> result){
        for (Object elem : result) {
            System.out.print(elem + " "); 
        }
    }
    public static void main(String[] args) {
        AdjacencyMapGraph<Integer, Integer> graph = new AdjacencyMapGraph<>(true);
        Vertex<Integer> zero = graph.insertVertex(0);
        Vertex<Integer> one = graph.insertVertex(1);
        Vertex<Integer> two = graph.insertVertex(2);
        Vertex<Integer> three = graph.insertVertex(3);
        Vertex<Integer> four = graph.insertVertex(4);
        Vertex<Integer> five = graph.insertVertex(5);

        graph.insertEdge(five, zero, 1);
        graph.insertEdge(five, two, 1);
        graph.insertEdge(two, three, 1);
        graph.insertEdge(three, one, 1);
        graph.insertEdge(four, zero, 1);
        graph.insertEdge(four, one, 1);
        sort(graph);
        
    }
    
}
