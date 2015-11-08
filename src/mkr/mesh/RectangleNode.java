package mkr.mesh;

public class RectangleNode extends Node {
    public enum Position{
        LEFT  ,UP , RIGTH , DOWN;
    }

    RectangleNode(int number , Point point){
        super(number , point);
        neighbours.add(null);
        neighbours.add(null);
        neighbours.add(null);
        neighbours.add(null);
    }

    public void addNeighbour(Node node , Position position) {
        neighbours.set(position.ordinal() , node);
    }

    @Override
    public void addNeighbour(Node node){
        for(int i = 0 ; i < 5 ; i++)
            if(neighbours.get(i) != null) {
                neighbours.set(i, node);
                return;
            }
    }

    public RectangleNode getNeighbour(Position position){
        return (RectangleNode)neighbours.get(position.ordinal());
    }

    @Override
    public RectangleNode getNormalNode(){
        if(getNeighbour(Position.UP) != null && getNeighbour(Position.DOWN) == null)
            return getNeighbour(Position.UP);
        if(getNeighbour(Position.DOWN) != null && getNeighbour(Position.UP) == null)
            return getNeighbour(Position.DOWN);
        if(getNeighbour(Position.LEFT) != null && getNeighbour(Position.RIGTH) == null)
            return getNeighbour(Position.LEFT);
        if(getNeighbour(Position.RIGTH) != null && getNeighbour(Position.LEFT) == null)
            return getNeighbour(Position.RIGTH);
        return this;
    }

    public boolean isInnerNode(){
        return getNeighbour(Position.LEFT) != null &&
                getNeighbour(Position.RIGTH) != null &&
                getNeighbour(Position.DOWN) != null &&
                getNeighbour(Position.UP) != null;
    }

}
