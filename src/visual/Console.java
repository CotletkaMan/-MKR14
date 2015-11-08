package visual;

import javax.swing.*;
import java.awt.*;

/**
 * Created by cotletkaman on 08.11.15.
 */
public class Console extends JPanel{
    private Plate plate;
    private JButton prevButton;
    private JTextField timeField;
    private JButton nextButton;

    public Console(Plate plate){
        this.plate = plate;
        prevButton = new JButton("<<<");
        nextButton = new JButton(">>>");
        timeField = new JTextField(5);
        timeField.setEnabled(false);
        timeField.setMaximumSize(new Dimension(100 , 25));
        this.add(prevButton);
        this.add(timeField);
        this.add(nextButton);
    }


}
