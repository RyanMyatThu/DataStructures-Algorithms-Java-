package Language.Java.DataStructures.Graphs.UndirectedGraphs.AdjacencyMatrix;

import Language.Java.DataStructures.Graphs.Edge;
import Language.Java.DataStructures.Graphs.Graph;
import Language.Java.DataStructures.Graphs.Position;
import Language.Java.DataStructures.Graphs.Vertex;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class AdjacencyMatrixGraph<V,E> implements Graph<V, E>{

    private class InnerVertex<V> implements Vertex<V>{
        private V element;
        private Position<Vertex<V>> position;
        private HashMap<Vertex<V>, Edge<E>> outgoing;
        private HashMap<Vertex<V>, Edge<E>> incoming;

        InnerVertex(V element, boolean isDirected){
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
            position = p;
        }

        @Override
        public Position<Vertex<V>> getPosition() {
            return position;
        }

        public HashMap<Vertex<V>, Edge<E>> getOutgoing(){
            return outgoing;
        }

        public HashMap<Vertex<V>, Edge<E>> getIncoming(){
            return incoming;
        }

    }

    private class InnerEdge<E> implements Edge<E>{
        E element;
        Position<Edge<E>> position;
        Vertex<V>[] endpoints;

        InnerEdge(E element, Vertex<V> origin, Vertex<V> destination){
            this.element = element;
            endpoints = (Vertex<V>[]) new Vertex[2];
            endpoints[0] = origin;
            endpoints[1] = destination;
        }

        @Override
        public E getElement() {
            return element;
        }

        @Override
        public Vertex<V>[] getEndPoints() {
           return endpoints;
        }

        @Override
        public void setPosition(Position<Edge<E>> p) {
            position = p;
        }

        @Override
        public Position<Edge<E>> getPosition() {
            return position;
        }

    }

    // --> end of inner classes <--

    // Attributes of AdjacencyMatrixGraph

   private boolean isDirected;
   private Edge<E>[][] adjMatrix;
   private LinkedList<Vertex<V>> vertices;
   private LinkedList<Edge<E>> edges;
   private int vertexCount;
   private int maxVertices;

   public AdjacencyMatrixGraph(int capacity, boolean isDirected){
        maxVertices = capacity;
        vertexCount = 0;

        this.isDirected = isDirected;
        adjMatrix = (Edge<E>[][]) new Edge[maxVertices][maxVertices];
        vertices = new LinkedList<>();
        edges = new LinkedList<>();
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
    public Edge<E> getEdge(Vertex<V> u, Vertex<V> v) {
        InnerVertex<V> origin = validate(u);
        InnerVertex<V> dest = validate(v);
        return adjMatrix[origin.getPosition().getIndex()][dest.getPosition().getIndex()];
    }

    @Override
    public Vertex<V>[] endVertices(Edge<E> e) {
        InnerEdge<E> edge = validate(e);
        return edge.getEndPoints();
    }

    @Override
    public Vertex<V> opposite(Vertex<V> v, Edge<E> e) {
        InnerVertex<V> vert = validate(v);
        Vertex<V>[] endPoints = e.getEndPoints();
        if(endPoints == null){
            throw new IllegalArgumentException("Edge without endpoints!");
        }
        if(vert.getElement().equals(endPoints[0])){
            return endPoints[1];
        } else if(vert.getElement().equals(endPoints[1])){
            return endPoints[0];
        }
        return null;
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
        if(vertexCount >= maxVertices) throw new IllegalArgumentException("Graph is full");
        if(element == null) throw new IllegalArgumentException("Value can't be null");
        InnerVertex<V> newVertex = new InnerVertex<>(element, isDirected);
        vertices.addLast(newVertex);
        Position<Vertex<V>> p = new Position<>(newVertex, vertices.size() - 1);
        newVertex.setPosition(p);
        vertexCount++;
        return newVertex;
    }

    @Override
    public void insertEdge(Vertex<V> u, Vertex<V> v, E x) {
        if(u.getPosition().getIndex() > vertices.size() || v.getPosition().getIndex() > vertices.size()) throw new IllegalArgumentException("Given vertices do not exist");
        if(getEdge(u,v) == null){
            InnerVertex<V> origin = validate(u);
            InnerVertex<V> dest = validate(v);
            InnerEdge<E> edge = new InnerEdge<>(x, origin, dest);
            edges.addLast(edge);
            Position<Edge<E>> p = new Position<>(edge, edges.size()-1);
            edge.setPosition(p);
            origin.getOutgoing().put(v, edge);
            dest.getIncoming().put(u, edge);
            adjMatrix[u.getPosition().getIndex()][v.getPosition().getIndex()] = edge;
            if(!isDirected){
                adjMatrix[v.getPosition().getIndex()][u.getPosition().getIndex()] = edge;
            }
        } else {
            throw new IllegalArgumentException("The edge between provided vertices exists!");
        }
    }

    @Override
    public void removeVertex(Vertex<V> v) {
        InnerVertex<V> vert = validate(v);
        for(Edge<E> edge : vert.getOutgoing().values()){
            removeEdge(edge);
        }
        
        for(Edge<E> edge : vert.getIncoming().values()){
                removeEdge(edge);
        }
        
        vertices.remove(vert.getPosition().getIndex());
        vertexCount--;
        
    }

    @Override
    public void removeEdge(Edge<E> e) {
        InnerEdge<E> edge = validate(e);
        Vertex<V> [] endpoints = edge.getEndPoints();
        InnerVertex<V> origin = (InnerVertex<V>) endpoints[0];
        InnerVertex<V> dest = (InnerVertex<V>) endpoints[1];
        origin.getOutgoing().remove(dest);
        dest.getIncoming().remove(dest);
        adjMatrix[origin.getPosition().getIndex()][dest.getPosition().getIndex()] = null;
        if(!isDirected){
            adjMatrix[dest.getPosition().getIndex()][origin.getPosition().getIndex()] = null;
        }
        
        
    }

    @Override
    public void dfs(Vertex<V> u) {
        InnerVertex<V> vert = validate(u);
        Stack<Vertex<V>> stack = new Stack<>();
        Set<Vertex<V>> isVisited = new HashSet<>();
        stack.add(vert);
        while(!stack.isEmpty()){
            Vertex<V> current = stack.pop();
            int pos = current.getPosition().getIndex();
            if(isVisited.add(current)){
                System.out.print(current.getElement() + " ");
                for(Edge<E> edge : adjMatrix[pos]){
                    if(edge != null){
                        stack.add(opposite(current, edge));
                    }
                }
            }
        }
    }

    @Override
    public void bfs(Vertex<V> u) {
        InnerVertex<V> vert = validate(u);
        Queue<Vertex<V>> queue = new LinkedList<>();
        Set<Vertex<V>> isVisited = new HashSet<>();
        queue.add(vert);
        while(!queue.isEmpty()){
            Vertex<V> current = queue.poll();
            int pos = current.getPosition().getIndex();
            if(isVisited.add(current)){
                System.out.print(current.getElement() + " ");
                for(Edge<E> edge : adjMatrix[pos]){
                    queue.add(opposite(current, edge));
                }
            }
        }


    }

    private InnerVertex<V> validate(Vertex<V> vertex){
        if(vertex == null) throw new IllegalArgumentException("Null vertex not allowed!");
        if(!(vertex instanceof InnerVertex)){
            throw new IllegalArgumentException("Not a valid vertex for this graph");
        }
        InnerVertex<V> vert = (InnerVertex<V>) vertex;
        if(!vert.getPosition().getElement().equals(vertex)){
            throw new IllegalArgumentException("Given vertex doesn't belong to this graph");  
        }     
        return vert;
    }

    private InnerEdge<E> validate(Edge<E> edge){
        if(edge == null) throw new IllegalArgumentException("Null edge not allowed!");
        if(!(edge instanceof InnerEdge)){
            throw new IllegalArgumentException("Not a valid edge for this graph");
        }
        InnerEdge<E> e = (InnerEdge<E>) edge;
        Vertex<V> [] endpoints = e.getEndPoints();
        if (endpoints == null) {
            throw new IllegalArgumentException("Edge endpoints are null");
        }
        for(Vertex<V> v : endpoints){
            validate(v);
        }
        return e;
    }
}