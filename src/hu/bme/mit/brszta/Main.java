package hu.bme.mit.brszta;

import java.io.IOException;
import javax.swing.UIManager;


public class Main implements Runnable {

    GUI gui = new GUI();


    public Main() throws IOException {
    }

    public static void main(String[] args) throws IOException {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Runnable runnable = new Main();
        Thread thread = new Thread(runnable);
        thread.start();
    }
    @Override
    @SuppressWarnings("InfiniteLoopStatement") //that is to calm down intellij
    public void run() {
        while(!gui.win_flag && !gui.lose_flag){
            gui.window_frame.repaint();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}