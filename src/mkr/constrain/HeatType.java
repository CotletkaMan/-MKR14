package mkr.constrain;

import mkr.mesh.RectangleMesh;
import mkr.mesh.Node;


public class HeatType extends Constrain{
    public HeatType(int index, double value){
        super(index, value);
    }

    public double[] createLineArray(RectangleMesh mesh){
        double[] line = new double[mesh.size() + 1];
        line[mesh.size()] = value * mesh.get(index).countNeighbours();
        Node root = mesh.get(index);
        for(Node node : root){
            double distance = root.getPoint().distance(node.getPoint());
            line[index] += 1./distance;
            line[node.getNodeNumber()] -= 1./distance;
        }
        return line;
    }
}
