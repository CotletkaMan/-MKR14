package visual;

import javax.swing.*;
import java.awt.*;

/**
 * Created by cotletkaman on 07.11.15.
 */
public class ChoiceConstrain extends Panel {
    private JRadioButton[] valuePointButton;
    private JPanel[] lineButton;
    private JTextField[] textFields;

    public ChoiceConstrain(){
        addInterElements();
    }

    private void addInterElements(){
        valuePointButton = new JRadioButton[5];
        lineButton = new JPanel[5];
        textFields = new JTextField[4];
        valuePointButton[0] = new JRadioButton("Value");
        valuePointButton[1] = new JRadioButton("Heat");
        valuePointButton[2] = new JRadioButton("HeatBound");
        valuePointButton[3] = new JRadioButton("ThirdType");
        valuePointButton[4] = new JRadioButton("Delete");

        ButtonGroup group = new ButtonGroup();
        for(int i = 0 ; i < valuePointButton.length ; i++) {
            group.add(valuePointButton[i]);
        }

        for(int i = 0 ; i < textFields.length ; i++) {
            textFields[i] = new JTextField(5);
            lineButton[i] = new JPanel();
            lineButton[i].add(valuePointButton[i]);
            lineButton[i].add(textFields[i]);
        }
        lineButton[lineButton.length - 1] = new JPanel();
        lineButton[lineButton.length - 1].add(valuePointButton[valuePointButton.length - 1]);

        for(int i = 0 ; i < lineButton.length ; i++)
            this.add(lineButton[i]);
    }

    public Pair<State , Double> getState(){
        for(int i = 0 ; i < valuePointButton.length ; i++){
            if(valuePointButton[i].isSelected())
                try{
                    double value = Double.parseDouble(textFields[i].getText());
                    return new Pair<State, Double>(State.values()[i] , value);
                }
                catch (Exception e){
                    return new Pair<State , Double>(State.FOO , 0.);
                }
        }
        return new Pair<State , Double>(State.FOO , 0.);
    }
}

enum State{
    VALUE , HEAT , HEATBOUND, THIRD , DELETE , FOO;
}

class Pair<X , Y>{
    private X x;
    private Y y;
    public Pair(X x , Y y){
        this.x = x;
        this.y = y;
    }

    public X getX(){
        return x;
    }

    public Y getY(){
        return y;
    }
}