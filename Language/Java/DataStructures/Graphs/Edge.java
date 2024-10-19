package Language.Java.DataStructures.Graphs;

public interface Edge<E> {

    E getElement();
    Vertex[] getEndPoints();
    void setPosition(Position<Edge<E>> p);
    Position<Edge<E>> getPosition();

}
