# Adjacency Map Graph Implementation

---

## How does adjacency map graph work?

Among various ways of implementation, adjacency map graphs serves as a solid choice since adjacency matrix implementation performs badly when handling very largely connected graphs due to its O(V^2^) space complexity where `V` is the number of vertices. Whereas, adjacency map performs at O( V + E ) space complexity where `V` is the number of vertices and `E` is the number of edges.

> However, adjacency matrix performs operations like search, add (edge), and delete with constant time.

---

## Use Cases

- Adjacency Matrix: Suitable for dense graphs, where E ≈ V^2^, or when fast access is critical.

- Adjacency Map: Ideal for sparse graphs, where E ≪ V^2^ , or when memory efficiency is a concern.

---

## Implementation

Before implementing the main class, we'd need to mention the interfaces used : `Edge` , `Vertex` and `Graph` . 

### Edge Interface

```  Java
public interface Edge<E> {

    E getElement();
    Vertex[] getEndPoints();
    void setPosition(Position<Edge<E>> p);
    Position<Edge<E>> getPosition();

}
```

Here's an overview of what kind of operations we expect from an edge : 

- Get the value of the edge (Weight)
- Get the end points of the edge (The two vertices connected by the edge)
- Position or Index of the edge to use in the corresponding implementation (In our case, a hash map)

### Vertex Interface

``` Java
public interface Vertex<V> {
    V getElement();
    void setPosition(Position<Vertex<V>> p);
    Position<Vertex<V>> getPosition();    
}
```
Here's an overview of what kind of operations we expect from a vertex : 

- Get the value of the vertex
- Get the Position or Index of the vertex to use in the corresponding implementation (In our case, a hash map)

### Graph Interface

``` Java

public interface Graph <V,E> {
     int numVertices();
     Iterable<Vertex<V>> vertices();
     int numEdges();
     Iterable<Edge<E>> edges();
     Edge<E> getEdge(Vertex<V> u, Vertex<V> v);
     Vertex<V>[] endVertices(Edge<E> e);
     Vertex<V> opposite(Vertex<V> v ,Edge<E> e);
     Iterable<Edge<E>> outGoingEdges(Vertex<V> v);
     Iterable<Edge<E>> inComingEdges(Vertex<V> v);
     Vertex<V> insertVertex(V element);
     void insertEdge(Vertex<V> u, Vertex<V> v, E x);
     void removeVertex(Vertex<V> v);
     void removeEdge(Edge<E> e);
     void dfs(Vertex<V> u);
     void bfs(Vertex<V> u);
     void printGraph();
}
```

Here's an overview of what kind of operations we expect from a graph : 

- Get the number of vertices
- Get the list of vertices within this graph
- Get the number of edges
- Get the list of edges within this graph
- Get the endpoints of a certain edge within the graph
- Get the other vertex connected to given vertex and edge.
- Get the outgoing edges of a certain vertex
- Get the incoming edges of a certain vertex
- Insert a vertex into the graph
- Connect two vertices with an edge (Insert Edge)
- Remove a vertex
- Remove an edge
- DFS (Depth First Search Traversal)
- BFS (Breadth First Search Traversal)
- Print graph (Optional)

---

## Adjacency Map Graph Class

``` Java

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
        InnerEdge<E> edge = validate(e);
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

```

---

## The inner classes (InnerVertex and InnerEdge)

To implement our interfaces we would need to create two inner classes. 

#### InnerVertex

``` Java

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
```

#### Inner Edge

``` Java
private class InnerEdge<E> implements Edge<E>{
      private  E element;
      private Position<Edge<E>> p;
      private  Vertex<V> [] endPoints;

      public InnerEdge(Vertex<V> u, Vertex<V> v, E element){

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
```

> We will use the above `private` classes in our `AdjacencyMapGraph` class.

---

### Attributes Of The AdjacencyMapGraph class

``` Java
    boolean isDirected; // Checks whether our graph is directed or not
    LinkedList<Vertex<V>> vertices = new LinkedList<>(); // A list of vertices within graph
    LinkedList<Edge<E>> edges = new LinkedList(); // A list of edges within graph
```

#### Constructor

``` Java
    public AdjacencyMapGraph(boolean directed){
        isDirected = directed;
    }
```

---

### Validate Edges And Vertices

Before diving into the main processes that we want to perform with our adjacency map graph, we'd need to consider checking the provided vertices and edges with their corresponding graphs. After all, we can't connect two vertices from two separate graphs! 

Here's the `private` method that verify if a provided vertex belongs to the graph that we want to perform an operation on.

``` Java
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
```

Here's the `private` method that verify if a provided edge belongs to the graph that we want to perform an operation on.

``` Java
private InnerEdge<E> validate(Edge<E> e){
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
```
As you can see, both of these methods will return an object which can be either `InnerVertex<V>` or `InnerEdge<E>` depending on which object we are verifying.

---

### Actual Graph Operations

Here are pretty basic ones like obtaining the number of vertices or edges, getting a list of vertices, getting a list of edges, get the two vertices connected by a given edge, and getting the edge connected between two given vertices.


``` Java
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
```
---

### Opposite

The method opposite gives the other vertex connected to a given vertex by the given edge. To obtain that vertex, we need to check for it using the endpoints of the given edge. Since an edge only has two endpoints, the array endpoints would be of length `2`. `endpoints[0]` will be the source vertex and `endpoints[1]` will be the destination vertex. Of course, we can't really distinguish source and destination in an undirected graph, so our method must be robust.


``` Java
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
```

---

### Getting the outgoing and incoming edges

```Java
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
```
---

### Inserting Vertices And Edges

It's pretty straightforward to add a vertex into a graph. We just need to add the new vertex into our `vertices` list and mark its position, then update it by using the `setPosition(...)` method.

```Java
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
```
Inserting an edge is also pretty straightforward. Before we do anything, we would have to check if an edge **Already** exists between the vertices we want to connect. If there isn't one, then we will add the new edge into our `edges` list and mark its position, then update it by using the `setPosition(...)` method. After that we convert the given two vertices into `InnerVertex` objects. Namely, `origin` and `dest` (Destination). Afterwards, we add the destination vertex `v` and the new edge in the outgoing hashmap of our `origin` vertex. Then, we add the origin vertex `u` and the new edge in the incoming hashmap of our `destination` vertex.

```Java
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
```
---

### Removing Vertices And Edges

Removing vertices coincides with removing edges, because whenever we remove a vertices, all edges or all connects from it and to it must be removed. 

```Java
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
```

To remove an edge, we would need the two endpoints. Then we will distinguish them into two vertices `origin` and `dest`. Removing edge can vary according to the type of graph. For example, if we are working with a directed graphs, we would need to remove the connection from both vertices; for the `origin` vertex, we would need to remove the edge from the `outgoing` hashmap and for the `dest` vertex, we would need to remove the edge from the `incoming` hashmap since they are not the same hashmap unlike undirected graphs.

``` Java
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
```

---

### Traversal Algorithms (DFS and BFS)

In order to perform depth-first-search traversal we would need, a stack to keep track of which vertex we should traverse next and a set to keep track of visited vertices.

```java
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
```

In order to perform breadth-first-search traversal we would need a queue to keep track of which vertex we should traverse next and a set to keep track of visited vertices.

```Java
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
```

| Operation | Adjacency Matrix | Adjacency Map |
| ---       |  ---             | ---           |
| Space Complexity | O(V^2^)   | O(V + E)      |
| Adding Vertex | O(V^2^)         | O(1) 
| Adding Edge   | O(1)            | O(1)       |
| Removing Edge | O(1)            | O(1)       |
| Checking For Edge Existance | O(1) | O(1)    |