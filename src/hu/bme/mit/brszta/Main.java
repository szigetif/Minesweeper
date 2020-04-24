package hu.bme.mit.brszta;

import java.io.IOException;

public class Main implements Runnable {

    GUI gui = new GUI(30,10,2);

    public Main() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        Runnable runnable = new Main();
        Thread thread = new Thread(runnable);
        thread.start();
    }
    @Override
    @SuppressWarnings("InfiniteLoopStatement") //that is to calm intellij
    public void run() {
        while(true){
            gui.repaint();
        }
    }
}