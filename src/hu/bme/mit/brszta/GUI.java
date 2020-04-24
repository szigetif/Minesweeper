package hu.bme.mit.brszta;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GUI extends JFrame { //it is the window

    private Panel panel;
    private JButton button;
    private JLabel label_time;
    private JLabel label_mines;
    private Board board;

    public GUI(int sizeX, int sizeY, int numberOfMines) throws IOException { //IOException because the ImageIO.read wanted it...
        board = new Board(sizeX, sizeY, numberOfMines);
        panel = new Panel(); //the thing in the window
        this.add(panel, BorderLayout.CENTER); //connects panel to window, centers panel in window
        this.pack(); //sizing the frame with layout manager according to the needs of its subcomponents
        this.setTitle("Minesweeper");
        //this.setMinimumSize(new Dimension(100, 100)); //possibly should be avoided
        //this.setSize(1000, 1000); //possibly should be avoided
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(true);
        this.setLocationRelativeTo(null); //centers window

        button.addActionListener(new ActionListener(){ //add actionlistener to button with anonymous class
            public void actionPerformed(ActionEvent e){
                label_time.setText("Welcome to Javapoint.");
            }
        });


    }

    public class Panel extends JPanel{

        private BufferedImage all_img;
        Map<String, BufferedImage> dictionary_of_images;

        public Panel() throws IOException {
            button = new JButton("new game");

            label_time = new JLabel("time: ");
            label_mines = new JLabel("mines: ");
            all_img = ImageIO.read(new File("../all.gif"));
            dictionary_of_images = new HashMap<>();

            dictionary_of_images.put("top_left_corner", all_img.getSubimage(0,81,10,10));
            dictionary_of_images.put("horizontal_border", all_img.getSubimage(40,81,16,10));
            dictionary_of_images.put("top_right_corner", all_img.getSubimage(10,81,10,10));
            dictionary_of_images.put("long_vertical_border", all_img.getSubimage(134,39,10,32));
            //dictionary_of_images.put("silver_background", all_img.getSubimage(0,23,4,4));
            dictionary_of_images.put("zero", all_img.getSubimage(0,0,13,23));

            dictionary_of_images.put("default_cell", all_img.getSubimage(0,39,16,16));


            dictionary_of_images.put("cell_vertical_border", all_img.getSubimage(134,39,10,16));





            dictionary_of_images.put("right_corner_border", all_img.getSubimage(66,81,10,10));
            dictionary_of_images.put("left_corner_border", all_img.getSubimage(56,81,10,10));




            this.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
            this.setLayout(new GridLayout(0, 1));
            this.add(button);
            this.add(label_time);
            this.add(label_mines);

        }
        @Override
        public void paintComponent(Graphics g) {

            g.drawImage(dictionary_of_images.get("top_left_corner"), 0, 0, this);
            for (int i=0; i < board.getSizeX(); i++) {
                g.drawImage(dictionary_of_images.get("horizontal_border"), 10 + i * 16, 0, this);
            }
            g.drawImage(dictionary_of_images.get("top_right_corner"), 10 + board.getSizeX() * 16, 0, this);
            g.drawImage(dictionary_of_images.get("long_vertical_border"), 0, 10, this);
            g.drawImage(dictionary_of_images.get("long_vertical_border"), 10 + board.getSizeX() * 16, 10, this);
            g.setColor(Color.decode("#C0C0C0"));
            g.drawRect(10, 10, board.getSizeX() * 16, 31); //akár teljes háttérnek előre?

            for (int i=0; i < 6; i++) {
                if(i > 2) {
                    g.drawImage(dictionary_of_images.get("zero"),board.getSizeX() * 16 + 10 - 4 - (i-2) * 13, 14, this);
                }
                else {
                    g.drawImage(dictionary_of_images.get("zero"), 14 + i * 13, 14, this);
                }

            }




        }


    }

}
