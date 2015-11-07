package mkr.mesh;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by cotletkaman on 07.11.15.
 */
public class Node implements Iterable<Node>{
    private int nodeNumber;
    private Point point;
    protected ArrayList<Node> neighbours;

    public Node(int nodeNumber , Point point){
        this.nodeNumber = nodeNumber;
        this.point = point;
        this.neighbours = new ArrayList<Node>();
    }

    public int getNodeNumber(){
        return nodeNumber;
    }

    public Point getPoint(){
        return point;
    }

    public Node getNormalNode(){
        int x = 0 , y = 0;
        for(Node node : this){
            x += node.getPoint().X();
            y += node.getPoint().Y();
        }

        Point point = new Point(x / this.countNeighbours() , y / this.countNeighbours());
        double distance = Double.MAX_VALUE;
        Node neighbor = null;

        for(Node node : this){
            if(distance < node.getPoint().distance(point)){
                distance = node.getPoint().distance(point);
                neighbor = node;
            }
        }

        return neighbor;
    }

    public int countNeighbours(){
        return neighbours.size();
    }

    public void addNeighbour(Node node){
        neighbours.add(node);
    }

    public Iterator<Node> iterator(){
        return neighbours.iterator();
    }

}
