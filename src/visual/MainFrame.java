package visual;

import javax.swing.*;
import java.awt.*;

/**
 * Created by cotletkaman on 07.11.15.
 */
public class MainFrame {
    private JFrame mainFrame;
    private ChoiceConstrain choiceConstrain;
    private Plate plate;
    private ConfigPlate configPlate;
    private Console console;

    public MainFrame(double widthX , double widthY , double time , double temperature){
        configureWindow(widthX ,  widthY ,  time , temperature);
        addInterElements();
    }

    private void configureWindow(double widthX , double widthY , double time , double temperature){
        mainFrame = new JFrame("MKR");
        choiceConstrain = new ChoiceConstrain();
        plate = new Plate(widthX , widthY , time , temperature ,choiceConstrain);
        configPlate = new ConfigPlate(plate);
        console = new Console(plate);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addInterElements(){
        mainFrame.add(choiceConstrain , BorderLayout.NORTH);
        mainFrame.add(plate , BorderLayout.CENTER);
        mainFrame.add(configPlate , BorderLayout.WEST);
        mainFrame.add(console , BorderLayout.SOUTH);
    }
}
