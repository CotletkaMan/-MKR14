package mkr.constrain;

import mkr.mesh.RectangleMesh;
import mkr.mesh.Node;

/**
 * Created by cotletkaman on 07.11.15.
 */
public class HeatBoundaryType extends Constrain {
    public HeatBoundaryType(int ind , double value){
        super(ind , value);
    }

    public double[] createLineArray(RectangleMesh mesh){
        double[] line = new double[mesh.size() + 1];
        line[mesh.size()] = value;
        Node neighbor = mesh.get(index).getNormalNode();
        double dimension = mesh.get(index).getPoint().distance(neighbor.getPoint());
        line[index] = 1./dimension;
        line[neighbor.getNodeNumber()] = -1./dimension;
        return line;
    }
}
