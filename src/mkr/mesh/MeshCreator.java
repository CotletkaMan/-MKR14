package mkr.mesh;

import mkr.mesh.RectangleNode.Position;

/**
 * Created by cotletkaman on 07.11.15.
 */
public class MeshCreator {
    public static RectangleMesh createRectangleFlatMesh(double width , double height , int countX , int countY){
        RectangleMesh mesh = new RectangleMesh();
        int numberNode = 0;
        for(int j = 0 ; j < countY ; j ++){
            for(int i = 0 ; i < countX ; i ++){
                RectangleNode node = new RectangleNode(numberNode , new Point(i * width / countX , j * height / countY));
                if(i - 1 >= 0){
                    mesh.get(numberNode - 1).addNeighbour(node , Position.RIGTH);
                    node.addNeighbour(mesh.get(numberNode - 1) , Position.LEFT);
                }
                if(j - 1 >= 0){
                    mesh.get(numberNode - countX).addNeighbour(node , Position.DOWN);
                    node.addNeighbour(mesh.get(numberNode - countX) , Position.UP);
                }
                mesh.add(node);
                numberNode++;
            }
        }
        return mesh;
    }
}
