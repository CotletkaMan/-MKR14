package mkr;

import mkr.mesh.RectangleMesh;

/**
 * Created by cotletkaman on 07.11.15.
 */
public interface StiffnessLineCreator {
    double[] createLineArray(RectangleMesh mesh);
}
