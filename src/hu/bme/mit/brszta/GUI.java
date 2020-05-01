package hu.bme.mit.brszta;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class GUI extends JFrame { //it is the window

    private Panel panel;
    private Main_menu main_menu;
    private JButton button;


    private Board board;
    private BoardBuilder builder;
    private Map<Key, Cell> cell_lookup_table;
    private Date start_clock;

    public GUI(int sizeX, int sizeY, int numberOfMines) throws IOException { //IOException because the ImageIO.read wanted it...

        main_menu = new Main_menu();
        this.add(main_menu, BorderLayout.CENTER); //connects panel to window, centers panel in window
        this.pack(); //sizing the frame with layout manager according to the needs of its subcomponents
        this.setTitle("Minesweeper");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(true);
        this.setLocationRelativeTo(null); //centers window


//        builder = new BoardBuilder();
//        board = builder.getRandomBoard(sizeX, sizeY, numberOfMines);
//        panel = new Panel(); //the thing in the window
//        start_clock =new Date();
//        this.add(panel, BorderLayout.CENTER); //connects panel to window, centers panel in window
//        this.pack(); //sizing the frame with layout manager according to the needs of its subcomponents
//        this.setTitle("Minesweeper");
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setVisible(true);
//        this.setResizable(true);
//        this.setLocationRelativeTo(null); //centers window

        button.addActionListener(new ActionListener(){ //add actionlistener to button with anonymous class
            public void actionPerformed(ActionEvent e){
                cell_lookup_table.forEach((key, value) -> System.out.println(key + " " + value));
            }
        });

    }

    public class Main_menu extends JPanel{

        private CheckboxGroup difficulties;
        private JLabel label_difficulties;

        public Main_menu() {
            button = new JButton("new game");

            difficulties = new CheckboxGroup();
            Checkbox difficulties_easy = new Checkbox("Easy", difficulties, false);
            //difficulties_easy.setBounds(0,0, 50,50);
            Checkbox difficulties_medium = new Checkbox("Medium", difficulties, false);
            //difficulties_medium.setBounds(50,70, 50,50);
            Checkbox difficulties_hard = new Checkbox("Hard", difficulties, false);
            //difficulties_hard.setBounds(50,120, 50,50);

            label_difficulties = new JLabel("Difficulty: ");


            this.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
            this.setLayout(new GridBagLayout());
            GridBagConstraints constrains = new GridBagConstraints();
            constrains.fill = GridBagConstraints.HORIZONTAL;
            constrains.gridx = 0;
            constrains.gridy = 0;
            this.add(label_difficulties, constrains);
            constrains.gridx = 1;
            constrains.gridy = 0;
            this.add(difficulties_easy, constrains);
            constrains.gridx = 1;
            constrains.gridy = 1;
            this.add(difficulties_medium, constrains);
            constrains.gridx = 1;
            constrains.gridy = 2;
            this.add(difficulties_hard, constrains);

            //this.add(button);


        }


    }

    public class Panel extends JPanel{

        private BufferedImage all_img;
        private Map<String, BufferedImage> dictionary_of_images;
        //private BufferedImage resized;
        private Click click;

        public Panel() throws IOException {

            all_img = ImageIO.read(new File("./all.gif"));
            dictionary_of_images = new HashMap<>();
            cell_lookup_table = new HashMap<>();
            click = new Click();

            dictionary_of_images.put("top_left_corner", all_img.getSubimage(0,81,10,10));
            dictionary_of_images.put("horizontal_border", all_img.getSubimage(40,81,16,10));
            dictionary_of_images.put("top_right_corner", all_img.getSubimage(10,81,10,10));
            dictionary_of_images.put("long_vertical_border", all_img.getSubimage(134,39,10,32));

            dictionary_of_images.put("0", all_img.getSubimage(0,0,13,23));
            dictionary_of_images.put("1", all_img.getSubimage(13,0,13,23));
            dictionary_of_images.put("2", all_img.getSubimage(26,0,13,23));
            dictionary_of_images.put("3", all_img.getSubimage(39,0,13,23));
            dictionary_of_images.put("4", all_img.getSubimage(52,0,13,23));
            dictionary_of_images.put("5", all_img.getSubimage(65,0,13,23));
            dictionary_of_images.put("6", all_img.getSubimage(78,0,13,23));
            dictionary_of_images.put("7", all_img.getSubimage(91,0,13,23));
            dictionary_of_images.put("8", all_img.getSubimage(104,0,13,23));
            dictionary_of_images.put("9", all_img.getSubimage(117,0,13,23));


            dictionary_of_images.put("middle_left_corner", all_img.getSubimage(56,81,10,10));
            dictionary_of_images.put("middle_right_corner", all_img.getSubimage(66,81,10,10));
            dictionary_of_images.put("bottom_left_corner", all_img.getSubimage(20,81,10,10));
            dictionary_of_images.put("bottom_right_corner", all_img.getSubimage(30,81,10,10));
            dictionary_of_images.put("vertical_border", all_img.getSubimage(134,39,10,16));

            dictionary_of_images.put("default_cell", all_img.getSubimage(0,39,16,16));
            dictionary_of_images.put("flagged_cell", all_img.getSubimage(16,39,16,16));
            dictionary_of_images.put("bombdeath_cell", all_img.getSubimage(32,39,16,16));
            dictionary_of_images.put("empty_cell_zero", all_img.getSubimage(0,23,16,16));
            dictionary_of_images.put("empty_cell_one", all_img.getSubimage(16,23,16,16));
            dictionary_of_images.put("empty_cell_two", all_img.getSubimage(32,23,16,16));
            dictionary_of_images.put("empty_cell_three", all_img.getSubimage(48,23,16,16));
            dictionary_of_images.put("empty_cell_four", all_img.getSubimage(64,23,16,16));
            dictionary_of_images.put("empty_cell_five", all_img.getSubimage(80,23,16,16));
            dictionary_of_images.put("empty_cell_six", all_img.getSubimage(96,23,16,16));
            dictionary_of_images.put("empty_cell_seven", all_img.getSubimage(112,23,16,16));
            dictionary_of_images.put("empty_cell_eight", all_img.getSubimage(128,23,16,16));


            //resized = resize_bufferedimage(dictionary_of_images.get("zero"), 2, 2);

            this.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
            this.setLayout(new GridLayout(0, 1));

            this.addMouseListener(click);


        }
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(dictionary_of_images.get("top_left_corner"), 0, 0, this);
            for (int i=0; i < board.getSizeX(); i++) { //belevonni többi for-t
                g.drawImage(dictionary_of_images.get("horizontal_border"), 10 + i * 16, 0, this);
            }
            g.drawImage(dictionary_of_images.get("top_right_corner"), 10 + board.getSizeX() * 16, 0, this);
            g.drawImage(dictionary_of_images.get("long_vertical_border"), 0, 10, this);
            g.drawImage(dictionary_of_images.get("long_vertical_border"), 10 + board.getSizeX() * 16, 10, this);
            g.setColor(Color.decode("#C0C0C0"));
            g.drawRect(10, 10, board.getSizeX() * 16, 31); //akár teljes háttérnek előre?

            int r = board.countMines() - board.countFlags();

            g.drawImage(dictionary_of_images.get(String.valueOf((((r - r % 100)) / 100) % 10 ))
                    ,board.getSizeX() * 16 + 10 - 4 - 39, 14, this);
            g.drawImage(dictionary_of_images.get(String.valueOf(((r % 100 - r % 10) / 10 ) % 10 ))
                    ,board.getSizeX() * 16 + 10 - 4 - 26, 14, this);
            g.drawImage(dictionary_of_images.get(String.valueOf(r % 10))
                    ,board.getSizeX() * 16 + 10 - 4 - 13, 14, this);

            int time = (int) ((new Date().getTime() - start_clock.getTime()) / 1000);

            g.drawImage(dictionary_of_images.get(String.valueOf((((time - time % 100)) / 100) % 10 ))
                    ,14 , 14, this);
            g.drawImage(dictionary_of_images.get(String.valueOf(((time % 100 - time % 10) / 10 ) % 10 ))
                    ,14 + 13, 14, this);
            g.drawImage(dictionary_of_images.get(String.valueOf(time % 10))
                    ,14 + 2 * 13, 14, this);


            g.drawImage(dictionary_of_images.get("middle_left_corner"), 0, 42, this);
            g.drawImage(dictionary_of_images.get("middle_right_corner"), 10 + board.getSizeX() * 16, 42, this);
            for (int i=0; i < board.getSizeX(); i++) {
                g.drawImage(dictionary_of_images.get("horizontal_border"), 10 + i * 16, 42, this);
            }
            g.drawImage(dictionary_of_images.get("bottom_left_corner"), 0, 52 + board.getSizeY() * 16, this);
            g.drawImage(dictionary_of_images.get("bottom_right_corner"), 10 + board.getSizeX() * 16, 52 + board.getSizeY() * 16, this);
            for (int i=0; i < board.getSizeY(); i++) {
                g.drawImage(dictionary_of_images.get("vertical_border"), 0 , 52 + i * 16 , this);
                g.drawImage(dictionary_of_images.get("vertical_border"), 10 + board.getSizeX() * 16 , 52 + i * 16 , this);
            }
            for (int i=0; i < board.getSizeX(); i++) {
                g.drawImage(dictionary_of_images.get("horizontal_border"), 10 + i * 16, 52 + board.getSizeY() * 16, this);
            }
            for (int row=0; row < board.getSizeY(); row++) {
                for (int column=0; column < board.getSizeX(); column++) {

                    //create look up table with coordinates to Cell?
                    int cell_cord_x = 10 + column * 16;
                    int cell_cord_y = 52 + row * 16;

                    if(!cell_lookup_table.containsKey(new Key(cell_cord_x, cell_cord_y))) {
                        cell_lookup_table.put(new Key(cell_cord_x, cell_cord_y), board.getCell(row, column));
                    }

                    if(board.getCell(row, column).cellState == CellState.DEFAULT) {
                        g.drawImage(dictionary_of_images.get("default_cell"), cell_cord_x, cell_cord_y, this);
                    }
                    else if(board.getCell(row, column).cellState == CellState.FLAGGED) {
                        g.drawImage(dictionary_of_images.get("flagged_cell"), cell_cord_x, cell_cord_y, this);
                    }
                    else { //if CellState.REVEALED
                        if(board.getCell(row, column).displayNumber == -1) {
                            g.drawImage(dictionary_of_images.get("bombdeath_cell"), cell_cord_x, cell_cord_y, this);
                        }
                        else if(board.getCell(row, column).displayNumber == 0) {
                            g.drawImage(dictionary_of_images.get("empty_cell_zero"), cell_cord_x, cell_cord_y, this);
                        }
                        else if(board.getCell(row, column).displayNumber == 1) {
                            g.drawImage(dictionary_of_images.get("empty_cell_one"), cell_cord_x, cell_cord_y, this);
                        }
                        else if(board.getCell(row, column).displayNumber == 2) {
                            g.drawImage(dictionary_of_images.get("empty_cell_two"), cell_cord_x, cell_cord_y, this);
                        }
                        else if(board.getCell(row, column).displayNumber == 3) {
                            g.drawImage(dictionary_of_images.get("empty_cell_three"), cell_cord_x, cell_cord_y, this);
                        }
                        else if(board.getCell(row, column).displayNumber == 4) {
                            g.drawImage(dictionary_of_images.get("empty_cell_four"), cell_cord_x, cell_cord_y, this);
                        }
                        else if(board.getCell(row, column).displayNumber == 5) {
                            g.drawImage(dictionary_of_images.get("empty_cell_five"), cell_cord_x, cell_cord_y, this);
                        }
                        else if(board.getCell(row, column).displayNumber == 6) {
                            g.drawImage(dictionary_of_images.get("empty_cell_six"), cell_cord_x, cell_cord_y, this);
                        }
                        else if(board.getCell(row, column).displayNumber == 7) {
                            g.drawImage(dictionary_of_images.get("empty_cell_seven"), cell_cord_x, cell_cord_y, this);
                        }
                        else if(board.getCell(row, column).displayNumber == 8) {
                            g.drawImage(dictionary_of_images.get("empty_cell_eight"), cell_cord_x, cell_cord_y, this);
                        }
                    }

                }
            }







        }

        public BufferedImage resize_bufferedimage(BufferedImage img, int scale_x, int scale_y)
        { //https://stackoverflow.com/questions/13605248/java-converting-image-to-bufferedimage

            Image scaled_img = img.getScaledInstance(img.getWidth() * scale_x,
                    img.getHeight() * scale_y, Image.SCALE_DEFAULT);

            // Create a buffered image with transparency
            BufferedImage bimage = new BufferedImage(img.getWidth(null)* scale_x,
                    img.getHeight(null)* scale_y, BufferedImage.TYPE_INT_ARGB);

            // Draw the image on to the buffered image
            Graphics2D bGr = bimage.createGraphics();
            bGr.drawImage(scaled_img, 0, 0, null);
            bGr.dispose();

            // Return the buffered image
            return bimage;
        }

    }

    public class Click implements MouseListener {
        int mouse_x;
        int mouse_y;
        int cell_x;
        int cell_y;
        Cell clicked_cell;

        @Override
        public void mouseClicked(MouseEvent e) {
            mouse_x = e.getX();
            mouse_y = e.getY();
            for(int i=0; i < board.getSizeX(); i++){
                if(mouse_x < 10 + (i+1)*16 && mouse_x > 10 + i * 16) cell_x = 10 + i * 16;
            }
            for(int i=0; i < board.getSizeY(); i++){
                if(mouse_y < 52 + (i+1)*16 && mouse_y > 52 + i * 16) cell_y = 52 + i * 16;
            }
            clicked_cell = cell_lookup_table.get(new Key(cell_x, cell_y));
            if (SwingUtilities.isLeftMouseButton(e)) {
                clicked_cell.reveal();
            }
            if (SwingUtilities.isRightMouseButton(e)){
                clicked_cell.flag();
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    public class Key {
        private final int x;
        private final int y;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Key key = (Key) o;
            return x == key.x &&
                    y == key.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        public Key(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }

}
