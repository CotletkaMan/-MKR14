package visual;

import mkr.MKR;
import mkr.constrain.*;
import utility.Pair;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
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
    private ChoiceColor choiceColor = new ChoiceColor(0 , 500); /// <--- всегда нужно проверять когда меняешь граничные условия. Нужно устанавливать диапазон значений из МКР.

    private int meshX = 10;
    private int meshY = 10;
    private double stepT = 1;

    private int alignX = 5;
    private int alignY = 5;

    private ArrayList<Pair<Double , double[]>> data = null;
    private int currentFrame = 0;

    private ChoiceConstrain choiceConstrain;

    HashMap<Integer , Pair<State , Double>> constrains;

    public Plate(double widthX , double widthY , double time , double temperature , ChoiceConstrain choiceConstrain){
        this.widthX = widthX;
        this.widthY = widthY;
        this.time = time;
        this.temperature = temperature;
        constrains = new HashMap<Integer , Pair<State , Double>>();
        this.choiceConstrain = choiceConstrain;
        setBackground(Color.WHITE);
        createPoint();
        configuration();
        createInitConstrain();
    }

    public void paintComponent(Graphics g2){
        super.paintComponent(g2);
        int width = getWidth() - 2 * alignX;
        int heigth = getHeight() - 2* alignY;

        if(data != null){
            double[] temperatures = data.get(currentFrame).getY();
            for(int i = 0 ; i < temperatures.length ; i++){
                g2.setColor(choiceColor.getColor(temperatures[i]));
                double x1 = 0  ,x2 = 0 , y1 = 0 , y2 = 0;
                if(i % (meshX + 1) > 0)
                    x1 = (point[i].x - point[i-1].x)/2 + 1;
                if(i % (meshX + 1) < meshX)
                    x2 = (point[i + 1].x - point[i].x)/2 + 1;
                if(i / (meshX + 1) > 0)
                    y1 = (point[i].y - point[i - meshX - 1].y)/2 + 1;
                if(i / (meshX + 1) < meshY)
                    y2 = (point[i + meshX + 1].y - point[i].y)/2 + 1;

                g2.fillRect((int)(point[i].x - x1) , (int)(point[i].y - y1) , (int)(x1 + x2) , (int)(y1 + y2));


            }
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
        data = null;
        constrains = new HashMap<Integer , Pair<State , Double>>();
        createInitConstrain();
        repaint();
    }

    public void setMeshY(int meshY){
        this.meshY = meshY;
        createPoint();
        data = null;
        constrains = new HashMap<Integer , Pair<State , Double>>();
        createInitConstrain();
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
        try {
            BufferedWriter reader = new BufferedWriter(new FileWriter("report.txt"));
            for(Pair<Double , double[]> array : data){
                reader.write("Time :: " + array.getX() + "\n");
                for(int i = 0 ; i < array.getY().length ; i++) {
                    reader.write(array.getY()[i] + " ");
                    if((i + 1) % (meshX + 1) == 0)
                        reader.write("\n");
                }
                reader.write("\n");
            }
            reader.close();
        }
        catch(Exception e){
            System.err.println("Error in open file");
        }
        currentFrame = 0;
        repaint();
    }

    public void next(){
        if(data !=  null) {
            currentFrame = (currentFrame + 1) % data.size();
            repaint();
        }
    }

    public void prev(){
        currentFrame--;
            if(data != null && currentFrame < 0)
                currentFrame = data.size() - 1;
        if(data != null)
            repaint();
    }

    public double getTime(){
        if(data != null)
            return data.get(currentFrame).getX();
        else
            return 0;
    }

    private void createInitConstrain(){
        for(int i = 0 ; i <= meshY; i ++)
            constrains.put(i * (meshX + 1) , new Pair<State, Double>(State.VALUE , 500.));
        for(int i = 1 ; i < meshY ; i++)
            constrains.put((i + 1) * (meshX + 1) - 1, new Pair<State, Double>(State.VALUE , 500.));
        for(int i = 0 ; i <= meshX; i++)
            constrains.put((meshX + 1) * meshY + i , new Pair<State, Double>(State.HEATBOUND , 0.));
        for(int i = 0 ; i <= meshX; i++)
            if(i < meshX / 2)
                constrains.put(i , new Pair<State, Double>(State.VALUE , 500.));
            else
                constrains.put(i , new Pair<State, Double>(State.HEATBOUND , 0.));
    }
}
