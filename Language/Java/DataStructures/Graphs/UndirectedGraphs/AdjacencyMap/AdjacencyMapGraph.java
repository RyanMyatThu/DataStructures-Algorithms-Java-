package Language.Java.DataStructures.Graphs.UndirectedGraphs.AdjacencyMap;

import Language.Java.DataStructures.Graphs.Edge;
import Language.Java.DataStructures.Graphs.Graph;
import Language.Java.DataStructures.Graphs.Position;
import Language.Java.DataStructures.Graphs.Vertex;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class AdjacencyMapGraph<V,E> implements Graph<V,E> {


    private class InnerVertex<V> implements Vertex<V>{
       private V element;
       private Position<Vertex<V>> p;
       private HashMap<Vertex<V>, Edge<E>> outgoing, incoming;

       public InnerVertex(V element, boolean isDirected){
            this.element = element;
            outgoing = new HashMap<>();
            if(isDirected){
                incoming = new HashMap<>();
            } else {
                incoming = outgoing;
            }
       }

        @Override
        public V getElement() {
            return element;
        }

        @Override
        public void setPosition(Position<Vertex<V>> p) {
            this.p = p;
        }

        @Override
        public Position<Vertex<V>> getPosition() {
            return p;
        }

        public HashMap<Vertex<V> , Edge<E>> getOutgoing(){
            return outgoing;
        }

        public HashMap<Vertex<V> , Edge<E>> getIncoming(){
            return incoming;
        }
        
    }

    private class InnerEdge<E> implements Edge<E>{
      private  E element;
      private Position<Edge<E>> p;
      private  Vertex<V> [] endPoints;

      public InnerEdge(Vertex<V> u, Vertex<V> v, E element){
        if(element == null || u == null || v == null){ throw new IllegalArgumentException("Null values or vertices aren't allowed!");}

        endPoints = (Vertex<V> []) new Vertex[2];
        this.element = element;
        endPoints[0] = u;
        endPoints[1] = v;
      }

        @Override
        public E getElement() {
            return element;
        }

        @Override
        public Vertex<V>[] getEndPoints() {
            return endPoints;
        }

        @Override
        public void setPosition(Position<Edge<E>> p) {
            this.p = p;
        }

        @Override
        public Position<Edge<E>> getPosition() {
            return p;
        }

    }

// -----> The End of Nested Inner Classes Of Vertices And Edges <------

    boolean isDirected;
    LinkedList<Vertex<V>> vertices = new LinkedList<>();
    LinkedList<Edge<E>> edges = new LinkedList();

    public AdjacencyMapGraph(boolean directed){
        isDirected = directed;
    }

    private InnerVertex<V> validate(Vertex<V> v){
        if(v == null) throw new IllegalArgumentException("Vertex can't be null");
        if(!(v instanceof InnerVertex)){
            throw new IllegalArgumentException("Not a valid vertex for this graph");
        }
        InnerVertex<V> vert = (InnerVertex<V>) v;

        if(!vertices.contains(vert)){
            throw new IllegalArgumentException("The given vertex does not belong to this graph");
        }

        return vert;
    }

    private InnerEdge<E> validate(Edge<E> e){
        if(e == null) throw new IllegalArgumentException("Edge cannot be null");
        if(!(e instanceof InnerEdge)){
            throw new IllegalArgumentException("Not a valid edge for this graph");
        }
        InnerEdge<E> edge = (InnerEdge<E>) e;
        Vertex<V> [] endpoints = edge.getEndPoints();
        if (endpoints == null) {
            throw new IllegalArgumentException("Edge endpoints are null");
        }
        for(Vertex<V> endPoint : endpoints){
           try
           { 
            validate(endPoint);

           } catch (IllegalArgumentException excpetion){
            throw new IllegalArgumentException("The given edge does not belong to this graph");

           }
        }
        return edge;
    }


    @Override
    public int numVertices() {
       return vertices.size();
    }

    @Override
    public Iterable<Vertex<V>> vertices() {
        return vertices;
    }

    @Override
    public int numEdges() {
        return edges.size();
    }

    @Override
    public Iterable<Edge<E>> edges() {
        return edges;
    }

    @Override
    public Vertex<V>[] endVertices(Edge<E> e) {
        InnerEdge edge = validate(e);
        return edge.getEndPoints();
    }

    
    @Override
    public Edge<E> getEdge(Vertex<V> u, Vertex<V> v) {
        InnerVertex<V> origin = validate(u);
        return origin.getOutgoing().get(v);
    }

    @Override
    public Vertex<V> opposite(Vertex<V> v,Edge<E> e) {
        InnerEdge<E> edge = validate(e);
        Vertex<V>[] endpoints = edge.getEndPoints();
        if(endpoints[0].equals(v)){
            return endpoints[1];
        } else if (endpoints[1].equals(v)){
            return endpoints[0];
        } else {
            throw new IllegalArgumentException("The given vertex is not incident to this edge");
        }
    }

    @Override
    public Iterable<Edge<E>> outGoingEdges(Vertex<V> v) {
        InnerVertex<V> vert = validate(v);
        return vert.getOutgoing().values();
    }

    @Override
    public Iterable<Edge<E>> inComingEdges(Vertex<V> v) {
        InnerVertex<V> vert = validate(v);
        return vert.getIncoming().values();
    }

    @Override
    public Vertex<V> insertVertex(V element) {
        if (element == null) {
            throw new IllegalArgumentException("Vertex element cannot be null");
        }
        InnerVertex<V> vertex = new InnerVertex<>(element, isDirected);
        vertices.addLast(vertex);
        Position<Vertex<V>> p = new Position<>(vertex, vertices.size() - 1);
        vertex.setPosition(p);
        return vertex;
    }

    @Override
    public void insertEdge(Vertex<V> u, Vertex<V> v, E x) {
        if(getEdge(u,v) == null){
            InnerEdge<E> edge = new InnerEdge<>(u, v, x);
            edges.addLast(edge);
            Position<Edge<E>> p = new Position<>(edge, edges.size() -1);
            edge.setPosition(p);
            InnerVertex<V> origin = validate(u);
            InnerVertex<V> dest = validate(v);
            origin.getOutgoing().put(v , edge);
            dest.getIncoming().put(u, edge);

        } else {
            throw new IllegalArgumentException("The edge between provided vertices exists");
        }
    }

    @Override
    public void removeVertex(Vertex<V> v) {
        InnerVertex<V> vert = validate(v);
        ArrayList<Edge<E>> outgoingEdges = new ArrayList<>(vert.getOutgoing().values());
        ArrayList<Edge<E>> incomingEdges = new ArrayList<> (vert.getIncoming().values());
        for(Edge<E> e : outgoingEdges){
            removeEdge(e);
        }
        for(Edge<E> e : incomingEdges){
            removeEdge(e);
        }
        vertices.remove(v.getPosition().getIndex());
    }

    @Override
    public void removeEdge(Edge<E> e) {
        InnerEdge<E> edge = validate(e);
        Vertex<V>[] endPoints = edge.getEndPoints();
            InnerVertex<V> origin = validate(endPoints[0]);
            InnerVertex<V> dest  = validate(endPoints[1]);
            if(isDirected){
                origin.getOutgoing().remove(dest);
                dest.getIncoming().remove(origin);
            } else {
                origin.getOutgoing().remove(dest);
                dest.getOutgoing().remove(origin);
            }
        }  

    @Override
    public void dfs(Vertex<V> u) {
        InnerVertex<V> vertex = validate(u);
        Stack<Vertex<V>> stack = new Stack<>();
        Set<Vertex<V>> isVisited = new HashSet<>();
        stack.add(vertex);
        while(!stack.isEmpty()){
            Vertex<V> current = stack.pop();
            if(isVisited.add(current)){
                System.out.print(current.getElement() + " ");
                for(Edge<E> edge : outGoingEdges(current)){
                    stack.add(opposite(current, edge));
            }
        }
    }
}

    @Override
    public void bfs(Vertex<V> u) {
        InnerVertex<V> vertex = validate(u);
        Queue<Vertex<V>> queue = new LinkedList<>();
        Set<Vertex<V>> isVisited = new HashSet<>();
        queue.add(vertex);
        while(!queue.isEmpty()){
            Vertex<V> current = queue.remove();
            if(isVisited.add(current)){
                System.out.print(current.getElement() + " ");
                for(Edge<E> edge : outGoingEdges(current)){
                     queue.add(opposite(current, edge));
                }
            }
        }
    }

    @Override
    public void printGraph(){
        
    }
}

