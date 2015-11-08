package visual;

import java.awt.*;

/**
 * Created by cotletkaman on 08.11.15.
 */
public class ChoiceColor {
    private double minValue;
    private double maxValue;

    public ChoiceColor(double min , double max){
        minValue = min;
        maxValue = max;
    }

    public Color getColor(double value){
        if(value < minValue || minValue > maxValue)
            return Color.black;
        double diver = 0;
        double middle = (minValue + maxValue) / 2;
        if(value > middle){
            diver = (maxValue - middle) / (value - middle);
            return new Color((int)(255 * diver) , (int)(255 * (1 - diver)) , 0);
        }
        else{
            diver = (middle - minValue) / (middle - value + 1);
            return new Color(0, (int)(255 * diver) , (int)(255 * (1 - diver)));
        }
    }
}
