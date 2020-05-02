package hu.bme.mit.brszta;

import java.io.IOException;
import javax.swing.UIManager;


public class Main implements Runnable {

    GUI gui = new GUI(30,20,243);


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
    @SuppressWarnings("InfiniteLoopStatement") //that is to calm intellij
    public void run() {
        while(true){
            gui.repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}