package visual;

import mkr.MKR;
import mkr.constrain.*;
import utility.Pair;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class Plate extends JPanel {
    private Point[] point;
    private double widthX = 200;
    private double widthY = 200;
    private double time = 20;
    private double temperature = 10;
    private double[] initCondition;
    private ChoiceColor choiceColor = new ChoiceColor(0 , 700);

    private int meshX = 10;
    private int meshY = 10;
    private double stepT = 1;

    private int alignX = 5;
    private int alignY = 5;

    private ArrayList<Pair<Double , double[]>> data = null;
    private int currentFrame = 0;

    private ChoiceConstrain choiceConstrain;

    HashMap<Integer , Pair<State , Double>> constrains;

    public Plate(ChoiceConstrain choiceConstrain){
        constrains = new HashMap<Integer , Pair<State , Double>>();
        this.choiceConstrain = choiceConstrain;
        setBackground(Color.WHITE);
        createPoint();
        configuration();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int width = getWidth() - 2 * alignX;
        int heigth = getHeight() - 2* alignY;
        Graphics2D g2 = (Graphics2D)g;

        if(data != null){

        }

        g2.setColor(Color.black);
        g2.drawRect(alignX , alignY , width , heigth);
        int count = 0;
        for(int i = 0 ; i <= meshY ; i++)
            for (int j = 0 ; j <= meshX ; j++) {
                point[count].move(j * width / meshX + alignX, i * heigth / meshY + alignY);
                g2.fillOval(point[count].x - 2 , point[count].y - 2 , 4 , 4);
                count++;
            }

        for(Map.Entry<Integer , Pair<State , Double>> entry : constrains.entrySet()){
            g2.setColor(entry.getValue().getX().getColor());
            g2.fillOval(point[entry.getKey()].x - 5 , point[entry.getKey()].y - 5 , 10 , 10);
        }
        g2.setColor(Color.black);
    }

    private void createPoint(){
        point = new Point[(meshX + 1)* (meshY + 1)];
        for(int i = 0 ; i < point.length ; i++)
            point[i] = new Point();
    }

    public void setMeshX(int meshX){
        this.meshX = meshX;
        createPoint();
        constrains = new HashMap<Integer , Pair<State , Double>>();
        repaint();
    }

    public void setMeshY(int meshY){
        this.meshY = meshY;
        createPoint();
        constrains = new HashMap<Integer , Pair<State , Double>>();
        repaint();
    }

    public void setTime(double time){
        stepT = time;
    }

    private void configuration(){
        this.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Pair<State , Double> state = choiceConstrain.getState();
                double dimension = Double.MAX_VALUE;
                int numberPoint = 0;
                for(int  i = 0 ; i < point.length ; i++){
                    if(dimension > point[i].distance(e.getPoint())){
                        dimension = point[i].distance(e.getPoint());
                        numberPoint = i;
                    }
                }
                switch (state.getX()){
                    case DELETE:    constrains.remove(numberPoint);
                                    repaint();
                                    break;
                    case FOO:       break;
                    default:        constrains.remove(numberPoint);
                                    constrains.put(numberPoint , state);
                                    repaint();
                }

            }
        });
    }

    public void start(){
        initCondition = new double[(meshY + 1) * (meshX + 1)];
        for(int i = 0 ; i < initCondition.length ; i++)
            initCondition[i] = temperature;

        HashSet<Constrain> constrainSet = new HashSet<Constrain>();
        for(Map.Entry<Integer , Pair<State , Double>> entry : constrains.entrySet()){
            switch (entry.getValue().getX()){
                case VALUE:     constrainSet.add(new FirstType(entry.getKey() , entry.getValue().getY()));
                                break;
                case HEAT:      constrainSet.add(new HeatType(entry.getKey() , entry.getValue().getY()));
                                break;
                case HEATBOUND: constrainSet.add(new HeatBoundaryType(entry.getKey() , entry.getValue().getY()));
                                break;
                case THIRD:     constrainSet.add(new ThirdType(entry.getKey() , entry.getValue().getY()));
                                break;
            }
        }
        MKR mkr = new MKR(widthX , widthY , meshX , meshY);
        data = mkr.calculateMKR(initCondition , constrainSet , stepT , time);
        currentFrame = 0;
        repaint();
    }
}
