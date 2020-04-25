package hu.bme.mit.brszta;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GUI extends JFrame { //it is the window

    private Panel panel;
    private JButton button;
    private JLabel label_time;
    private JLabel label_mines;
    private Board board;
    private Map<Key, Cell> cell_lookup_table;

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
                cell_lookup_table.forEach((key, value) -> System.out.println(key + " " + value));
            }
        });




    }

    public class Panel extends JPanel{

        private BufferedImage all_img;
        private Map<String, BufferedImage> dictionary_of_images;
        private BufferedImage resized;
        private Click click;



        public Panel() throws IOException {
            button = new JButton("new game");

            label_time = new JLabel("time: ");
            label_mines = new JLabel("mines: ");
            all_img = ImageIO.read(new File("../all.gif"));
            dictionary_of_images = new HashMap<>();
            cell_lookup_table = new HashMap<>();
            click = new Click();

            dictionary_of_images.put("top_left_corner", all_img.getSubimage(0,81,10,10));
            dictionary_of_images.put("horizontal_border", all_img.getSubimage(40,81,16,10));
            dictionary_of_images.put("top_right_corner", all_img.getSubimage(10,81,10,10));
            dictionary_of_images.put("long_vertical_border", all_img.getSubimage(134,39,10,32));
            //dictionary_of_images.put("silver_background", all_img.getSubimage(0,23,4,4));
            dictionary_of_images.put("zero", all_img.getSubimage(0,0,13,23));
            dictionary_of_images.put("middle_left_corner", all_img.getSubimage(56,81,10,10));
            dictionary_of_images.put("middle_right_corner", all_img.getSubimage(66,81,10,10));
            dictionary_of_images.put("bottom_left_corner", all_img.getSubimage(20,81,10,10));
            dictionary_of_images.put("bottom_right_corner", all_img.getSubimage(30,81,10,10));
            dictionary_of_images.put("vertical_border", all_img.getSubimage(134,39,10,16));
            dictionary_of_images.put("default_cell", all_img.getSubimage(0,39,16,16));
            dictionary_of_images.put("flagged_cell", all_img.getSubimage(16,39,16,16));
            dictionary_of_images.put("bombdeath_cell", all_img.getSubimage(32,39,16,16));

            resized = resize_bufferedimage(dictionary_of_images.get("zero"), 2, 2);

            this.setBorder(BorderFactory.createEmptyBorder(82 + board.getSizeY() * 16,40 + board.getSizeX() * 16,0,0));
            this.setLayout(new GridLayout(0, 1));
            this.add(button);
            this.add(label_time);
            this.add(label_mines);


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

            for (int i=0; i < 6; i++) {
                if(i > 2) {
                    g.drawImage(dictionary_of_images.get("zero"),board.getSizeX() * 16 + 10 - 4 - (i-2) * 13, 14, this);
                }
                else {
                    g.drawImage(dictionary_of_images.get("zero"), 14 + i * 13, 14, this);
                }
            }
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
                    //new int[] {cell_cord_x, cell_cord_y}

                    if(!cell_lookup_table.containsKey(new Key(cell_cord_x, cell_cord_y))) {

                        cell_lookup_table.put(new Key(cell_cord_x, cell_cord_y), board.getCell(row, column));

                    }

                    if(board.getCell(row, column).getState() == CellState.DEFAULT) {
                        g.drawImage(dictionary_of_images.get("default_cell"), cell_cord_x, cell_cord_y, this);
                    }
                    else if(board.getCell(row, column).getState() == CellState.FLAGGED) {
                        g.drawImage(dictionary_of_images.get("flagged_cell"), cell_cord_x, cell_cord_y, this);
                    }
                    else { //if CellState.REVEALED
                        if(board.getCell(row, column).isMine()) {
                            g.drawImage(dictionary_of_images.get("bombdeath_cell"), cell_cord_x, cell_cord_y, this);
                        }
                    }




                }
            }


            g.drawImage(resized, 70, 70, this);




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
            if (SwingUtilities.isLeftMouseButton(e)) {
                mouse_x = e.getX();
                mouse_y = e.getY();
                for(int i=0; i < board.getSizeX(); i++){
                    if(mouse_x < 10 + (i+1)*16 && mouse_x > 10 + i * 16) cell_x = 10 + i * 16;
                }
                for(int i=0; i < board.getSizeY(); i++){
                    if(mouse_y < 52 + (i+1)*16 && mouse_y > 52 + i * 16) cell_y = 52 + i * 16;
                }
                clicked_cell = cell_lookup_table.get(new Key(cell_x, cell_y));
                clicked_cell.reveal();



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
