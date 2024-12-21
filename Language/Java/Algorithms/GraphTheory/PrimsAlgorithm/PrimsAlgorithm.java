package Language.Java.Algorithms.GraphTheory.PrimsAlgorithm;

import Language.Java.Algorithms.GraphTheory.Floyd_Warshall.AdjacencyMapGraph;
import Language.Java.DataStructures.Graphs.Edge;
import Language.Java.DataStructures.Graphs.Graph;
import Language.Java.DataStructures.Graphs.Vertex;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PrimsAlgorithm {
    
    private static class VertexDistance<V>{
        private Vertex<V> vertex;
        private int weight;

        VertexDistance(Vertex<V> v, int weight){
            vertex = v;
            this.weight = weight;
        }
        Vertex<V> getVertex(){
            return vertex;
        }
        int getWeight(){
            return weight;
        }
    }

    private static class TreeNode<V,E>{
        Vertex<V> u;
        Edge<E> edge;
        Vertex<V> v;

        TreeNode(Vertex<V> u, Edge<E> edge, Vertex<V> v){
            this.u = u;
            this.edge = edge;
            this.v = v;

        }
        Vertex<V> getOrigin(){
            return u;
        }
        Vertex<V> getDest(){
            return v;
        }
        Edge<E> getEdge(){
            return edge;
        }

    }

    private static class Tree<V,E>{
        private List<TreeNode<V,E>> nodes;
        Tree(int capacity){
            nodes = new ArrayList<>(capacity);
        }
        void add(TreeNode<V,E> node){
            nodes.add(node);
        }
        List<TreeNode<V,E>> getNodes(){
            return nodes;
        }
    }

    private static<V,E> Tree<V,E> solution(Graph<V,E> g, Vertex<V> s){
        int vertexCount = g.numVertices();
        PriorityQueue<VertexDistance<V>> pq = new PriorityQueue<>(Comparator.comparingInt(v -> v.weight));
        int[] dist = new int[vertexCount];
        Vertex<V>[] vertices = (Vertex<V>[]) new Vertex[vertexCount];
        boolean[] inMST = new boolean[vertexCount];
        Vertex<V>[] parent = (Vertex<V>[]) new Vertex[vertexCount];
        Tree<V,E> result = new Tree<>(vertexCount);

        for(int i = 0; i < dist.length; i++){
            if(i != s.getPosition().getIndex()){
            dist[i] = Integer.MAX_VALUE;
            } else {
                dist[i] = 0;
            }
        }
        

        for(Vertex<V> v : g.vertices()){
            int index = v.getPosition().getIndex();
            vertices[index] = v;
        }

        pq.add(new VertexDistance<>(s, 0));


        while(!pq.isEmpty()){
            VertexDistance<V> vd = pq.poll();
            Vertex<V> u = vd.getVertex();
            int uIndex = u.getPosition().getIndex();
            
            if(inMST[uIndex]) continue;

            inMST[uIndex] = true;

            for(Edge<E> e : g.outGoingEdges(u)){
                Vertex<V> v = g.opposite(u, e);
                int newWeight = (int) e.getElement();
                int vIndex= v.getPosition().getIndex();
                if(!inMST[vIndex] && newWeight < dist[vIndex]){
                    dist[v.getPosition().getIndex()] = newWeight;
                    parent[vIndex] = u;
                    pq.add(new VertexDistance<>(v, newWeight));
                }
            }
        }
        
        for(int i = 0; i < vertexCount; i++){
            if(parent[i] != null){
                Vertex<V> u = parent[i];
                Vertex<V> v =  vertices[i];
                Edge<E> edge = g.getEdge(u,v);
                result.add(new TreeNode<>(u, edge, v));
            }
        }
        printSolution(result);
        return result;
    }

    static<V,E> void printSolution(Tree<V,E> tree){
        List<TreeNode<V,E>> treeNodes = tree.getNodes();
        for (TreeNode<V,E> treeNode : treeNodes) {
             Vertex<V> u = treeNode.getOrigin();
             Vertex<V> v = treeNode.getDest();
             Edge<E> edge = treeNode.getEdge();
             System.out.println(u.getElement() + " --> " + v.getElement() + " (weight " + edge.getElement() + ")");
        }

    }


    public static void main(String[] args) {
        AdjacencyMapGraph<Integer, Integer> graph = new AdjacencyMapGraph<>(false);

        // Add vertices
        Vertex<Integer> v0 = graph.insertVertex(0);
        Vertex<Integer> v4 = graph.insertVertex(4);
        Vertex<Integer> v3 = graph.insertVertex(3);
        Vertex<Integer> v1 = graph.insertVertex(1);
    
        // Add edges with weights
        graph.insertEdge(v0, v4, 1); // 0 - 4 (weight 1)
        graph.insertEdge(v0, v3, 1); // 0 - 3 (weight 1)
        graph.insertEdge(v3, v1, 1); // 3 - 1 (weight 1)

   
    solution(graph, v0);
    }
}
