package Language.Java.DataStructures.Graphs;


public interface Vertex<V> {
    V getElement();
    void setPosition(Position<Vertex<V>> p);
    Position<Vertex<V>> getPosition();    
}
