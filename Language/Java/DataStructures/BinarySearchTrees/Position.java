package Language.Java.DataStructures.BinarySearchTrees;

public class Position <V> {
   private V element;
   private int index;

    public Position(V element, int index){
        this.element = element;
        this.index = index;
    }

    

    public V getElement(){
        return element;
    }
    public int getIndex(){
        return index;
    }
    
}
