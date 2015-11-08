
import visual.MainFrame;

import javax.swing.*;

/**
 * Created by cotletkaman on 07.11.15.
 */
public class MainApp {
    public static void main(String[] arg){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame(12 , 4 , 25 , 10);
            }
        });
    }
}
