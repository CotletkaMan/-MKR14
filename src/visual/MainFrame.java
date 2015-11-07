package visual;

import javax.swing.*;
import java.awt.*;

/**
 * Created by cotletkaman on 07.11.15.
 */
public class MainFrame {
    private JFrame mainFrame;
    private ChoiceConstrain choiceConstrain;

    public MainFrame(){
        configureWindow();
        addInterElements();
    }

    private void configureWindow(){
        mainFrame = new JFrame("MKR");
        choiceConstrain = new ChoiceConstrain();
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addInterElements(){
        mainFrame.add(choiceConstrain , BorderLayout.NORTH);
    }
}
