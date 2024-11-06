package Language.Java.DataStructures.Graphs.UndirectedGraphs.AdjancencyList;

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

public class AdjacencyListGraph<V,E> implements Graph<V,E> {

    private class InnerVertex<V> implements Vertex<V> {

       private V element;
       private Position<Vertex<V>> position;
       private HashMap<Vertex<V> , Edge<E>> outgoing;
       private HashMap<Vertex<V> , Edge<E>> incoming;
     

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

        public HashMap<Vertex<V> , Edge<E>> getOutgoing(){
            return outgoing;
        }

        public HashMap<Vertex<V> , Edge<E>> getIncoming(){
            return incoming;
        }

    }

    private class InnerEdge<E> implements Edge<E> {

        private E element;
        private Vertex<V>[] endpoints;
        private Position<Edge<E>> position;

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

    // --> end of the inner classes <--

    private LinkedList<Vertex<V>> vertices = new LinkedList<>();
    private LinkedList<Edge<E>> edges = new LinkedList<>();
    private ArrayList<LinkedList<HashMap<Vertex<V>, Edge<E>>>> adjList;
    private boolean isDirected; 
    private int capacity = 10;
    private int verticeCount = 0;

    AdjacencyListGraph(boolean isDirected){
        adjList = new ArrayList<>(capacity);
        for(int i = 0; i < capacity; i++){
            adjList.add(new LinkedList<>());
        }
        this.isDirected = isDirected;
    }

    private InnerVertex<V> validate(Vertex<V> v){
        if(v == null) throw new IllegalArgumentException("Vertex can't be null");
        if(!(v instanceof InnerVertex)){
            throw new IllegalArgumentException("Invalid Vertex");
        }
        InnerVertex<V> vert = (InnerVertex<V>) v;
        if(!vertices.contains(vert)){
            throw new IllegalArgumentException("Vertex Does Not Exist Within This Graph!");
        }
        return vert;
    }
    private InnerEdge<E> validate(Edge<E> e){
        if(e == null) throw new IllegalArgumentException("Edge Cannot be null");
        if(!(e instanceof InnerEdge)){
            throw new IllegalArgumentException("Invalid Edge");
        } 
        InnerEdge<E> edge = (InnerEdge<E>) e;
        Vertex<V>[] endPoints = edge.getEndPoints();
        if(endPoints == null){
            throw new IllegalArgumentException("Invalid Edge");
        }
        for(Vertex<V> endPoint : endPoints){
            if(endPoint != null){
            validate(endPoint);
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
    public Edge<E> getEdge(Vertex<V> u, Vertex<V> v) {
        InnerVertex<V> origin = validate(u);
        return origin.getOutgoing().get(v);
    }

    @Override
    public Vertex<V>[] endVertices(Edge<E> e) {
        InnerEdge<E> edge = validate(e);
        return edge.getEndPoints();
    }

    @Override
    public Vertex<V> opposite(Vertex<V> v, Edge<E> e) {
        InnerVertex<V> vert = validate(v);
        InnerEdge<E> edge = validate(e);
        Vertex<V>[] endPoints = edge.getEndPoints();
        if(endPoints[0].equals(vert)){
            return endPoints[1];
        } else if(endPoints[1].equals(vert)){
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
        if(element == null) throw new IllegalArgumentException("Element cannot be null");
        InnerVertex<V> newVertex = new InnerVertex<>(element, isDirected);
        vertices.addLast(newVertex);
        
        int index = vertices.size() - 1;
        Position<Vertex<V>> position = new Position<>(newVertex, index);
        newVertex.setPosition(position);
        verticeCount++;
        return newVertex;
    }

    @Override
    public void insertEdge(Vertex<V> u, Vertex<V> v, E x) {
        InnerVertex<V> origin = validate(u);
        InnerVertex<V> dest = validate(v);
        InnerEdge<E> edge = new InnerEdge<>(x, origin, dest);
        HashMap<Vertex<V>, Edge<E>> outGoingMap = new HashMap<>();
        outGoingMap.put(dest, edge);
        adjList.get(origin.getPosition().getIndex()).addLast(outGoingMap);
        Position<Edge<E>> edgePosition = new Position<>(edge, adjList.get(origin.getPosition().getIndex()).size() - 1);
        edges.addLast(edge);
        edge.setPosition(edgePosition);
        origin.getOutgoing().put(dest, edge);
        if(isDirected){
            dest.getIncoming().put(origin, edge);
        }
    }

    @Override
    public void removeVertex(Vertex<V> v) {
        InnerVertex<V> vertex = validate(v);
        for(Edge<E> edge : vertex.getOutgoing().values()){
            removeEdge(edge);
        }
        for(Edge<E> edge : vertex.getIncoming().values()){
            removeEdge(edge);
        }
        vertices.remove(vertex.getPosition().getIndex());
        adjList.remove(vertex.getPosition().getIndex());
        verticeCount--;
    }

    @Override
    public void removeEdge(Edge<E> e) {
        InnerEdge<E> edge = validate(e);
        Vertex<V> [] endPoints = edge.getEndPoints();
        adjList.get(endPoints[0].getPosition().getIndex()).remove(edge.getPosition().getIndex());
        InnerVertex<V> origin = validate(endPoints[0]);
        InnerVertex<V> dest = validate(endPoints[1]);
        origin.getOutgoing().remove(dest);
        if(isDirected){
            dest.getIncoming().remove(origin);
        }
        edges.remove(edge);
    }

    @Override
    public void dfs(Vertex<V> u) {
        InnerVertex<V> root = validate(u);
        Stack<Vertex<V>> stack = new Stack<>();
        Set<Vertex<V>> isVisited = new HashSet<>();
        stack.add(root);
        while(!stack.isEmpty()){
            InnerVertex<V> current = validate(stack.pop());
            if(isVisited.add(current)){
                System.out.print(current.getElement() + " ");
                int index = current.getPosition().getIndex();
                for(HashMap<Vertex<V>, Edge<E>> map: adjList.get(index)){
                      Set<Vertex<V>> keys = map.keySet();
                      for(Vertex<V> next : keys){
                        if(next != null){
                        stack.add(next);
                        }
                    }
                } 
                }
            }
        }
    

    @Override
    public void bfs(Vertex<V> u) {
        InnerVertex<V> root = validate(u);
        Queue<Vertex<V>> queue = new LinkedList<>();
        Set<Vertex<V>> isVisited = new HashSet<>();
        queue.add(root);
        while(!queue.isEmpty()){
            InnerVertex<V> current = validate(queue.poll());
            if(isVisited.add(current)){
                System.out.print(current.getElement() + " ");
                int index = current.getPosition().getIndex();
                for(HashMap<Vertex<V>, Edge<E>> map : adjList.get(index)){
                    Set<Vertex<V>> keys = map.keySet();
                    for(Vertex<V> next : keys){
                        if(next != null){
                            queue.add(next);
                        }
                    }

                }

            }
        }
    }

    @Override
    public void printGraph() {
        int i = 0;
        for(LinkedList<HashMap<Vertex<V>, Edge<E>>> list : adjList){
            if(i < verticeCount){
            System.out.print(vertices.get(i).getElement() + " --> ");
            }
            if(list.isEmpty() && i < verticeCount){
                System.out.println();
            }
            for(HashMap<Vertex<V>, Edge<E>> map : list){
                for(HashMap.Entry<Vertex<V>, Edge<E>> entry : map.entrySet()){
                    System.out.print(entry.getKey().getElement() + " --> ");
                }
                System.out.println();  
            }
            i++;
        }
    }

}
