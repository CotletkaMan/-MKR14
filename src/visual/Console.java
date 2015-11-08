package visual;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by cotletkaman on 08.11.15.
 */
public class Console extends JPanel{
    private Plate plate;
    private JButton prevButton;
    private JTextField timeField;
    private JButton nextButton;

    public Console(final Plate plate){
        this.plate = plate;
        prevButton = new JButton("<<<");
        nextButton = new JButton(">>>");
        timeField = new JTextField(5);
        timeField.setEnabled(false);
        timeField.setMaximumSize(new Dimension(100 , 25));
        this.add(prevButton);
        this.add(timeField);
        this.add(nextButton);

        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plate.prev();
                timeField.setText(String.format("%.3f" , plate.getTime()));
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plate.next();
                timeField.setText(String.format("%.3f" , plate.getTime()));
            }
        });
    }


}
