package Language.Java.DataStructures.Graphs;

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

