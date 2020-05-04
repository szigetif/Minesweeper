package hu.bme.mit.brszta;

import java.io.IOException;
import javax.swing.UIManager;


public class Main implements Runnable {

    GUI gui = new GUI();

    public Main() {
    }

    public static void main(String[] args) {

        try { //create the UI theme according to the OS
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Runnable runnable = new Main();
        Thread thread = new Thread(runnable);
        thread.start();
    }
    @Override
    public void run() {
        while(!gui.win_flag && !gui.lose_flag){
            //in case of losing or winning stop repainting
            // the timer freezes in this way
            gui.window_frame.repaint(); //repaint every half second for the timer
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}