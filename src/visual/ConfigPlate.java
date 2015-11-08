package visual;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by cotletkaman on 07.11.15.
 */
public class ConfigPlate extends JPanel {
    private JLabel labelX;
    private JLabel labelY;
    private JLabel time;
    private JTextField fieldX;
    private JTextField fieldY;
    private JTextField fieldTime;
    private final Plate plate;
    private JButton startButton;

    public ConfigPlate(final Plate plate){
        labelX = new JLabel("NetX");
        labelY = new JLabel("NetY");
        time = new JLabel("CountStep");
        fieldX = new JTextField(5);
        fieldX.setMaximumSize(new Dimension(150, 25));
        fieldX.setText("10");
        fieldY = new JTextField(5);
        fieldY.setText("10");
        fieldY.setMaximumSize(new Dimension(150, 25));
        fieldTime = new JTextField(5);
        fieldTime.setText("2");
        fieldTime.setMaximumSize(new Dimension(150 , 25));
        this.plate = plate;
        startButton = new JButton("run");


        this.setLayout(new BoxLayout(this , BoxLayout.Y_AXIS));

        this.add(labelX);
        this.add(fieldX);
        this.add(labelY);
        this.add(fieldY);
        this.add(time);
        this.add(fieldTime);
        this.add(startButton);

        fieldX.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setMeshX(getIntInField(fieldX));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setMeshX(getIntInField(fieldX));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                setMeshX(getIntInField(fieldX));
            }
        });
        fieldY.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setMeshY(getIntInField(fieldY));

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setMeshY(getIntInField(fieldY));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                setMeshY(getIntInField(fieldY));
            }
        });

        fieldTime.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                plate.setTime(getDoubleInField(fieldTime));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                plate.setTime(getDoubleInField(fieldTime));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                plate.setTime(getDoubleInField(fieldTime));
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plate.start();
            }
        });
    }

    private double getDoubleInField(JTextField field){
        try {
            double value = Double.parseDouble(field.getText());
            return (value > 0)? value : 1;
        }
        catch (Exception d){
            return 1;
        }
    }
    private int getIntInField(JTextField field){
        try {
            int value = Integer.parseInt(field.getText());
            return (value > 0)? value : 1;
        }
        catch (Exception d){
            return 1;
        }
    }

    private void setMeshX(int mesh){
        plate.setMeshX(mesh);
    }

    private void setMeshY(int mesh){
        plate.setMeshY(mesh);
    }
}
