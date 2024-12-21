# Adjacency Matrix Graph

---

## How Does Adjacency Matrix Graph Work?

Adjacency Matrix Graphs are pretty useful when dealing with dense graph structures in which a relatively small amount of vertices are heavily connected with one another. It is because adjacency matrix data structure has a space complexity of O(V^2^) where V is the number of vertices. Despite its downsides, it is recommended to learn how adjacency matrix works.

> The bright side of adjacency matrix is that the implementation performs perations like search, add (edge), and delete with constant time.

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

## Adjacency Matrix Graph Class

```Java

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
        InnerEdge<E> edge = validate(e);
        Vertex<V>[] endPoints = edge.getEndPoints();
        if(endPoints == null){
            throw new IllegalArgumentException("Edge without endpoints!");
        }
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
        
        int indexToRemove = vert.getPosition().getIndex();

        vertices.remove(indexToRemove);
        vertexCount--;

        for(int i = indexToRemove; i < vertices.size(); i++){
            InnerVertex<V> vertex = (InnerVertex<V>) vertices.get(i);
            Position<Vertex<V>> newPosition = new Position<>(vertex, i);
            vertex.setPosition(newPosition);
        }
        
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

        edges.remove(edge);
        
        
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
                    if(edge != null){
                    queue.add(opposite(current, edge));
                    }
                }
            }
        }
    }

    @Override
    public void printGraph(){
        for (Edge<E>[] matrix : adjMatrix) {
            for (Edge<E> edge : matrix) {
                if (edge != null) {
                    System.out.print(1 + " ");
                } else {
                    System.out.print(0 + " ");
                }
            }
            System.out.println();
        }
    }

    private InnerVertex<V> validate(Vertex<V> vertex){
        if(vertex == null) throw new IllegalArgumentException("Null vertex not allowed!");
        if(!(vertex instanceof InnerVertex)){
            throw new IllegalArgumentException("Not a valid vertex for this graph");
        }
        InnerVertex<V> vert = (InnerVertex<V>) vertex;
        if(!vertices.contains(vert)){
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
```

---

## The inner classes (InnerVertex and InnerEdge)

To implement our interfaces we would need to create two inner classes. 

#### InnerVertex

```Java
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
```

#### Inner Edge

```Java
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
```

> We will use the above `private` classes in our `AdjacencyMatrixGraph` class.

---

### Attributes Of The AdjacencyMapGraph class


```Java
   private boolean isDirected;
   private Edge<E>[][] adjMatrix;
   private LinkedList<Vertex<V>> vertices;
   private LinkedList<Edge<E>> edges;
   private int vertexCount;
   private int maxVertices;
```

#### Constructor

```Java
    public AdjacencyMatrixGraph(int capacity, boolean isDirected){
        maxVertices = capacity;
        vertexCount = 0;

        this.isDirected = isDirected;
        adjMatrix = (Edge<E>[][]) new Edge[maxVertices][maxVertices];
        vertices = new LinkedList<>();
        edges = new LinkedList<>();
    }
```

---

### Validate Edges And Vertices

Before diving into the main processes that we want to perform with our adjacency map graph, we'd need to consider checking the provided vertices and edges with their corresponding graphs. After all, we can't connect two vertices from two separate graphs! 

Here's the `private` method that verify if a provided vertex belongs to the graph that we want to perform an operation on.

```Java
    private InnerVertex<V> validate(Vertex<V> vertex){
        if(vertex == null) throw new IllegalArgumentException("Null vertex not allowed!");
        if(!(vertex instanceof InnerVertex)){
            throw new IllegalArgumentException("Not a valid vertex for this graph");
        }
        InnerVertex<V> vert = (InnerVertex<V>) vertex;
        if(!vertices.contains(vert)){
            throw new IllegalArgumentException("Given vertex doesn't belong to this graph");  
        }     
        return vert;
    }
```

Here's the `private` method that verify if a provided edge belongs to the graph that we want to perform an operation on.

```Java
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
```

As you can see, both of these methods will return an object which can be either `InnerVertex<V>` or `InnerEdge<E>` depending on which object we are verifying.

---

### Actual Graph Operations

Here are pretty basic ones like obtaining the number of vertices or edges, getting a list of vertices, getting a list of edges, get the two vertices connected by a given edge, and getting the edge connected between two given vertices.

```Java
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
```


### Opposite

The method opposite gives the other vertex connected to a given vertex by the given edge. To obtain that vertex, we need to check for it using the endpoints of the given edge. Since an edge only has two endpoints, the array endpoints would be of length `2`. `endpoints[0]` will be the source vertex and `endpoints[1]` will be the destination vertex. Of course, we can't really distinguish source and destination in an undirected graph, so our method must be robust.

```Java
    @Override
    public Vertex<V> opposite(Vertex<V> v, Edge<E> e) {
        InnerVertex<V> vert = validate(v);
        InnerEdge<E> edge = validate(e);
        Vertex<V>[] endPoints = edge.getEndPoints();
        if(endPoints == null){
            throw new IllegalArgumentException("Edge without endpoints!");
        }
        if(endPoints[0].equals(vert)){
            return endPoints[1];
        } else if(endPoints[1].equals(vert)){
            return endPoints[0];
        }
        return null;
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

It's pretty straightforward to add a vertex into a graph. We just need to add the new vertex into our `vertices` list and mark its position, then update it by using the `setPosition(...)` method. But before we do anything above, we must check if our vertex count is less than our maximum capacity. We could also add a new method which will resize the graph.

```Java
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
```

Inserting an edge is also pretty straightforward. Before we do anything, we would have to check if an edge **Already** exists between the vertices we want to connect. If there isn't one, then we will add the new edge into our `edges` list and mark its position, then update it by using the `setPosition(...)` method. After that we convert the given two vertices into `InnerVertex` objects. Namely, `origin` and `dest` (Destination). To place the edge connection in our adjacency matrix we would need two indices: first is the index of our `origin` vertex and second is the index of our `dest` vertex. Then we add our connection in that exact place. If undirected, we would have to do the same but at the exact place of `adjmatrix[index of dest][index of origin] = edge value`.

```Java
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
```

---

### Removing Vertices And Edges

Removing vertices coincides with removing edges, because whenever we remove a vertices, all edges or all connects from it and to it must be removed. First, we get the list of all outgoing and incoming edges. We will remove every edge in these lists. Then we get the index of the vertex we are removing. We will remove the vertex from the list of vertices by using that index. (Decrement the vertex count). 

```Java
    @Override
    public void removeVertex(Vertex<V> v) {
        InnerVertex<V> vert = validate(v);
        for(Edge<E> edge : vert.getOutgoing().values()){
            removeEdge(edge);
        }
        
        for(Edge<E> edge : vert.getIncoming().values()){
            removeEdge(edge);
        }
        
        int indexToRemove = vert.getPosition().getIndex();

        vertices.remove(indexToRemove);
        vertexCount--;

        for(int i = indexToRemove; i < vertices.size(); i++){
            InnerVertex<V> vertex = (InnerVertex<V>) vertices.get(i);
            Position<Vertex<V>> newPosition = new Position<>(vertex, i);
            vertex.setPosition(newPosition);
        }
        
    }
```

```Java
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
        edges.remove(edge);
    }
```