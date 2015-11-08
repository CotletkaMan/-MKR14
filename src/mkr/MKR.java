package mkr;

import mkr.constrain.Constrain;
import mkr.mesh.RectangleMesh;
import mkr.mesh.MeshCreator;
import mkr.mesh.RectangleNode.*;
import utility.Pair;
import utility.matrixSolution;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by cotletkaman on 07.11.15.
 */
public class MKR{
    private RectangleMesh mesh;
    private double[][] stiffnessMatrix;
    public MKR(double width , double height , int countX , int countY){
        mesh = MeshCreator.createRectangleFlatMesh(width , height , countX , countY);
        stiffnessMatrix = createStiffnessMatrix(mesh);
    }

    public ArrayList<Pair<Double , double[]>> calculateMKR(double[] iniCond , HashSet<Constrain> constrains , double countT , double time){
        ArrayList<Pair<Double , double[]>>  result = new ArrayList<Pair<Double , double[]>> ();
        double[][] localStiffnessMatrix = new double[stiffnessMatrix.length][stiffnessMatrix[0].length];
        createLocalCopy(localStiffnessMatrix);
        addInitCondition(localStiffnessMatrix , iniCond , time / countT);
        addCondition(localStiffnessMatrix , constrains);
        double currentTime = 0;
        result.add(new Pair<Double, double[]>(currentTime , iniCond));
        for(int i = 0 ; i < countT ; i++){
            currentTime += time / countT;
            double[] temperature  = matrixSolution.gauss(localStiffnessMatrix);
            result.add(new Pair<Double, double[]>(currentTime , temperature));
            createLocalCopy(localStiffnessMatrix);
            addInitCondition(localStiffnessMatrix , temperature , time / countT);
            addCondition(localStiffnessMatrix , constrains);
        }
        return result;
    }

    private double[][] createLocalCopy(double[][] localCopy){
        for(int i = 0 ; i < stiffnessMatrix.length ; i++)
            System.arraycopy(stiffnessMatrix[i] , 0 , localCopy[i] , 0 , stiffnessMatrix[i].length);
        return localCopy;
    }

    private double[][] createStiffnessMatrix(RectangleMesh mesh){
        double[][] stiffnessMatrix = new double[mesh.size()][mesh.size() + 1];
        for(int i = 0 ; i < stiffnessMatrix.length ; i++){
            if(mesh.get(i).isInnerNode()){
                double distanceX1 = mesh.get(i).getNeighbour(Position.LEFT).getPoint().distance(mesh.get(i).getPoint());
                double distanceX2 = mesh.get(i).getNeighbour(Position.RIGTH).getPoint().distance(mesh.get(i).getPoint());
                stiffnessMatrix[mesh.get(i).getNodeNumber()][mesh.get(i).getNodeNumber()] = (1./distanceX1 + 1./distanceX2) / distanceX1;
                stiffnessMatrix[mesh.get(i).getNodeNumber()][mesh.get(i).getNeighbour(Position.LEFT).getNodeNumber()] = -1./(distanceX2 * distanceX1);
                stiffnessMatrix[mesh.get(i).getNodeNumber()][mesh.get(i).getNeighbour(Position.RIGTH).getNodeNumber()] = -1./(distanceX1 * distanceX1);

                double distanceY1 = mesh.get(i).getNeighbour(Position.UP).getPoint().distance(mesh.get(i).getPoint());
                double distanceY2 = mesh.get(i).getNeighbour(Position.DOWN).getPoint().distance(mesh.get(i).getPoint());
                stiffnessMatrix[mesh.get(i).getNodeNumber()][mesh.get(i).getNodeNumber()] += (1./distanceY1 + 1./distanceY2) / distanceY1;
                stiffnessMatrix[mesh.get(i).getNodeNumber()][mesh.get(i).getNeighbour(Position.UP).getNodeNumber()] = -1./(distanceY2 * distanceY1);
                stiffnessMatrix[mesh.get(i).getNodeNumber()][mesh.get(i).getNeighbour(Position.DOWN).getNodeNumber()] = -1./(distanceY1 * distanceY1);
            }
            else
                stiffnessMatrix[mesh.get(i).getNodeNumber()][mesh.get(i).getNodeNumber()] = 1;
        }
        return stiffnessMatrix;
    }

    private void addInitCondition(double[][] stiffnessMatrix , double[] iniCond , double stepT){
        for(int i = 0 ; i < stiffnessMatrix.length ; i++) {
            if(mesh.get(i).isInnerNode()) {
                stiffnessMatrix[mesh.get(i).getNodeNumber()][mesh.get(i).getNodeNumber()] += 1. / stepT;
                stiffnessMatrix[i][stiffnessMatrix.length] = iniCond[i] / stepT;
            }
        }
    }
    private void addCondition(double[][] stiffnessMatrix ,HashSet<Constrain> constrains){
        for(Constrain constrain : constrains){
            double[] array = constrain.createLineArray(mesh);
            System.arraycopy(array , 0 , stiffnessMatrix[constrain.getNumberNode()] , 0 , array.length);
        }
    }
}
