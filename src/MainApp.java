import mkr.MKR;
import mkr.constrain.Constrain;
import mkr.constrain.FirstType;
import mkr.constrain.HeatType;
import visual.MainFrame;

import javax.swing.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by cotletkaman on 07.11.15.
 */
public class MainApp {
    public static void main(String[] arg){
       /* MKR mkr = new MKR(5 , 5 , 5 , 5);
        double[] init = new double[25];
        for(int i = 0 ; i < init.length ; i++)
            init[i] = 10;
        HashSet<Constrain> constrains = new HashSet<Constrain>();
        constrains.add(new HeatType(12 , 10));
        constrains.add(new FirstType(12 , 100));
        HashMap<Double , double[]> result = mkr.calculateMKR(init , constrains , 4 , 20);
        for(Map.Entry<Double , double[]> line : result.entrySet()) {
            for (int i = 0; i < line.getValue().length; i++)
                System.out.printf("%-2.3f " ,line.getValue()[i]);
            System.out.println();
        }*/
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame();
            }
        });
    }
}
