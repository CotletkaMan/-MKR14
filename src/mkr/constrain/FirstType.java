package mkr.constrain;

import mkr.mesh.RectangleMesh;

/**
 * Created by cotletkaman on 07.11.15.
 */
public class FirstType extends Constrain {
    public FirstType(int index , int value){
        super(index, value);
    }

    public double[] createLineArray(RectangleMesh mesh){
        double[] line = new double[mesh.size() + 1];
        line[index] = 1;
        line[mesh.size()] = value;
        return line;
    }
}
