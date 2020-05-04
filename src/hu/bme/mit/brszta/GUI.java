package hu.bme.mit.brszta;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class GUI { //contains all the classes necessary to build the GUI

    public Window_Frame window_frame;
    private Panel panel;
    private ButtonGroup difficulties;

    private Board board;
    private Map<Key, Cell> cell_lookup_table;
    private Date start_clock;
    public boolean win_flag, lose_flag;

    public GUI() {
        window_frame = new Window_Frame(); //creating the Jframe containing all GUI elements
        lose_flag = false; //initializing win/lose flags
        win_flag = false;
    }

    public class Button implements ActionListener{ //New Game button

        @Override
        public void actionPerformed(ActionEvent e) {
            BoardBuilder builder = new BoardBuilder();
            //choosing between difficulty levels with a Radiobutton Group
            if(difficulties.getSelection().getActionCommand().equals("easy")) {
                board = builder.getRandomBoard(9, 9, 10);
            }
            else if(difficulties.getSelection().getActionCommand().equals("medium")) {
                board = builder.getRandomBoard(16, 16, 40);
            }
            else { //if "hard"
                board = builder.getRandomBoard(36, 36, 99);
            }
            //overwriting the menu panel with the playing panel
            try {
                panel = new Panel(); //playing panel
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            start_clock = new Date(); //starting the game clock
            window_frame.setContentPane(panel);
            window_frame.pack(); //sizing the frame with layout manager according to the needs of its subcomponents
        }
    }

    public class Window_Frame extends JFrame { //it is the window

        public Window_Frame() {

            Main_menu main_menu = new Main_menu(); //create the menu panel
            this.add(main_menu, BorderLayout.CENTER); //connects panel to window, centers panel in window
            this.pack(); //sizing the frame with layout manager according to the needs of its subcomponents
            this.setTitle("Minesweeper");
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close program when GUI window closed
            this.setVisible(true);
            this.setResizable(true);
            this.setLocationRelativeTo(null); //centers window
        }

    }

    public class Panel extends JPanel{

        private Map<String, BufferedImage> dictionary_of_images;

        public Panel() throws IOException {

            BufferedImage all_img = ImageIO.read(new File("./all.gif")); //importing the "sprite"
            dictionary_of_images = new HashMap<>(); //creating the list of images with names
            cell_lookup_table = new HashMap<>(); //creating the list of cells with coordinates
            Click click = new Click(); //using our Click class to handle left/right clicks

            //cropping from the "sprite" all images and placing them in a dictionary
            dictionary_of_images.put("top_left_corner", all_img.getSubimage(0,81,10,10));
            dictionary_of_images.put("horizontal_border", all_img.getSubimage(40,81,16,10));
            dictionary_of_images.put("top_right_corner", all_img.getSubimage(10,81,10,10));
            dictionary_of_images.put("long_vertical_border", all_img.getSubimage(134,39,10,32));

            dictionary_of_images.put("face_default", all_img.getSubimage(0,55,26,26));
            dictionary_of_images.put("face_lost", all_img.getSubimage(78,55,26,26));
            dictionary_of_images.put("face_win", all_img.getSubimage(104,55,26,26));

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
            dictionary_of_images.put("revealed_bomb_cell", all_img.getSubimage(64,39,16,16));
            dictionary_of_images.put("empty_cell_zero", all_img.getSubimage(0,23,16,16));
            dictionary_of_images.put("empty_cell_one", all_img.getSubimage(16,23,16,16));
            dictionary_of_images.put("empty_cell_two", all_img.getSubimage(32,23,16,16));
            dictionary_of_images.put("empty_cell_three", all_img.getSubimage(48,23,16,16));
            dictionary_of_images.put("empty_cell_four", all_img.getSubimage(64,23,16,16));
            dictionary_of_images.put("empty_cell_five", all_img.getSubimage(80,23,16,16));
            dictionary_of_images.put("empty_cell_six", all_img.getSubimage(96,23,16,16));
            dictionary_of_images.put("empty_cell_seven", all_img.getSubimage(112,23,16,16));
            dictionary_of_images.put("empty_cell_eight", all_img.getSubimage(128,23,16,16));

            //creating whitespace around the game panel
            this.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
            this.setLayout(new GridLayout(0, 1));

            this.addMouseListener(click); //connecting to the game panel the click handling Click class

        }
        @Override
        public Dimension getPreferredSize() {
            //setting the size of the panel so that the window.pack() method can size the window according to this
            return new Dimension(20 + board.getSizeX() * 16, 62 + board.getSizeY() * 16 );
        }

        @Override
        public void paintComponent(Graphics g) { //drawing up the game field
            super.paintComponent(g);
            //drawing the frame of the field, like borders and win/lose face
            g.drawImage(dictionary_of_images.get("top_left_corner"), 0, 0, this);
            for (int i=0; i < board.getSizeX(); i++) {
                g.drawImage(dictionary_of_images.get("horizontal_border"), 10 + i * 16, 0, this);
            }
            g.drawImage(dictionary_of_images.get("top_right_corner"), 10 + board.getSizeX() * 16, 0, this);
            g.drawImage(dictionary_of_images.get("long_vertical_border"), 0, 10, this);
            g.drawImage(dictionary_of_images.get("long_vertical_border"), 10 + board.getSizeX() * 16, 10, this);
            g.setColor(Color.decode("#C0C0C0"));
            g.drawRect(10, 10, board.getSizeX() * 16, 31);
            //faces changes in case of winning/losing
            if (lose_flag) {
                g.drawImage(dictionary_of_images.get("face_lost"), 10 + board.getSizeX() * 8 - 13, 13, this);
            }
            else if (win_flag) {
                g.drawImage(dictionary_of_images.get("face_win"), 10 + board.getSizeX() * 8 - 13, 13, this);
            }
            else {
                g.drawImage(dictionary_of_images.get("face_default"), 10 + board.getSizeX() * 8 - 13, 13, this);
            }
            //converting the number of placed flags to digits
            int r = board.countMines() - board.countFlags();
            g.drawImage(dictionary_of_images.get(String.valueOf((((r - r % 100)) / 100) % 10 ))
                    ,board.getSizeX() * 16 + 10 - 4 - 39, 14, this);
            g.drawImage(dictionary_of_images.get(String.valueOf(((r % 100 - r % 10) / 10 ) % 10 ))
                    ,board.getSizeX() * 16 + 10 - 4 - 26, 14, this);
            g.drawImage(dictionary_of_images.get(String.valueOf(r % 10))
                    ,board.getSizeX() * 16 + 10 - 4 - 13, 14, this);

            //converting the elapsed time since start to digits
            int time = (int) ((new Date().getTime() - start_clock.getTime()) / 1000);

            g.drawImage(dictionary_of_images.get(String.valueOf((((time - time % 100)) / 100) % 10 ))
                    ,14 , 14, this);
            g.drawImage(dictionary_of_images.get(String.valueOf(((time % 100 - time % 10) / 10 ) % 10 ))
                    ,14 + 13, 14, this);
            g.drawImage(dictionary_of_images.get(String.valueOf(time % 10))
                    ,14 + 2 * 13, 14, this);
            //drawing more parts of the frame, like borders and corners
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
            //drawing the cells
            for (int row=0; row < board.getSizeY(); row++) {
                for (int column=0; column < board.getSizeX(); column++) {

                    int cell_cord_x = 10 + column * 16;
                    int cell_cord_y = 52 + row * 16;

                    //creating an entry in the look up table for every cell so that upon clicking them they could be more easily accessed
                    if(!cell_lookup_table.containsKey(new Key(cell_cord_x, cell_cord_y))) {
                        cell_lookup_table.put(new Key(cell_cord_x, cell_cord_y), board.getCell(row, column));
                    }

                    if(board.getCell(row, column).cellState == CellState.DEFAULT) {
                        if(lose_flag && board.getCell(row, column).isMine() ) {
                            g.drawImage(dictionary_of_images.get("revealed_bomb_cell"), cell_cord_x, cell_cord_y, this);
                        }
                        else {
                            g.drawImage(dictionary_of_images.get("default_cell"), cell_cord_x, cell_cord_y, this);
                        }
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
    }

    public class Main_menu extends JPanel{ //menu panel to choose from game settings

        private ButtonGroup modes;
        private JTextField IP_address;
        JRadioButton host;
        JRadioButton guest;

        public Main_menu() {
            //radio buttons can be enabled/disabled when single/multi player mode have been chosen
            Change_Radio_Buttons radio_event = new Change_Radio_Buttons();

            //creating difficulty options as buttongroup
            //assigning string indicators to them so that they can be queried in Button class
            difficulties = new ButtonGroup();
            JRadioButton difficulties_easy = new JRadioButton("Easy", false);
            difficulties_easy.setActionCommand("easy");
            JRadioButton difficulties_medium = new JRadioButton("Medium", false);
            difficulties_medium.setActionCommand("medium");
            JRadioButton difficulties_hard = new JRadioButton("Hard", false);
            difficulties_hard.setActionCommand("hard");
            difficulties.add(difficulties_easy);
            difficulties.add(difficulties_medium);
            difficulties.add(difficulties_hard);

            JLabel label_difficulties = new JLabel("Difficulty: ");

            modes = new ButtonGroup();
            JRadioButton single = new JRadioButton("Single player", false);
            single.setActionCommand("single");
            JRadioButton multi = new JRadioButton("Multiplayer", false);
            multi.setActionCommand("multi");
            modes.add(single);
            modes.add(multi);
            single.addActionListener(radio_event);
            multi.addActionListener(radio_event);

            JLabel label_modes = new JLabel("Game modes: ");

            ButtonGroup connection = new ButtonGroup();
            host = new JRadioButton("Host", false);
            guest = new JRadioButton("Guest", false);
            connection.add(host);
            connection.add(guest);


            JLabel label_role = new JLabel("Role: ");


            JLabel label_connection = new JLabel("Host IP: ");
            IP_address = new JTextField("192.168.0.123");


            JButton button = new JButton("new game");

            //creating the layout with layoutmanager, it is basically a grid
            this.setBorder(BorderFactory.createEmptyBorder(30,30,30,30)); //create whitespace around
            this.setLayout(new GridBagLayout());
            GridBagConstraints constrains = new GridBagConstraints();
            constrains.fill = GridBagConstraints.HORIZONTAL;

            //placing the menu elements in the grid
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
            constrains.insets = new Insets(0,0,10,0); //whitespace on the bottom of the element
            this.add(difficulties_hard, constrains);

            constrains.gridx = 0;
            constrains.gridy = 4;
            this.add(label_modes, constrains);
            constrains.gridx = 1;
            constrains.gridy = 4;
            constrains.insets = new Insets(0,0,0,0);
            this.add(single, constrains);
            constrains.gridx = 1;
            constrains.gridy = 5;
            constrains.insets = new Insets(0,0,10,0);
            this.add(multi, constrains);

            constrains.gridx = 0;
            constrains.gridy = 6;
            this.add(label_role, constrains);
            constrains.gridx = 1;
            constrains.gridy = 6;
            constrains.insets = new Insets(0,0,0,0);
            this.add(host, constrains);
            constrains.gridx = 1;
            constrains.gridy = 7;
            constrains.insets = new Insets(0,0,10,0);
            this.add(guest, constrains);

            constrains.gridx = 0;
            constrains.gridy = 8;
            this.add(label_connection, constrains);
            constrains.gridx = 1;
            constrains.gridy = 8;
            constrains.insets = new Insets(0,0,10,0);
            this.add(IP_address, constrains);

            constrains.gridx = 1;
            constrains.gridy = 9;
            this.add(button, constrains);

            //creating the button from our Button class
            Button button_class = new Button();
            //connecting the action listener of our Button class to the button element in the menu panel
            button.addActionListener(button_class);

        }

        public class Change_Radio_Buttons implements ActionListener {
            //changing between single/multi player options disables/enables connection options (host/guest/IP)

            @Override
            public void actionPerformed(ActionEvent e) {
                if(modes.getSelection().getActionCommand().equals("single")) {
                    host.setEnabled(false);
                    guest.setEnabled(false);
                    IP_address.setEditable(false);
                }
                else { //if "multi"
                    host.setEnabled(true);
                    guest.setEnabled(true);
                    IP_address.setEditable(true);
                }

            }
        }


    }

    public class Click implements MouseListener { //to handle the right and left mouse click on cells
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
            //find clicked cell with the coordinates of the cursor
            clicked_cell = cell_lookup_table.get(new Key(cell_x, cell_y));
            //reveal cell on left click and check if it's a mine or not
            if (SwingUtilities.isLeftMouseButton(e)) {
                clicked_cell.reveal();
                if(clicked_cell.displayNumber == -1) { //you lost
                    lose_flag = true;
                }
            }
            //flag cell on right click and check if it was the last missing mine
            if (SwingUtilities.isRightMouseButton(e)){
                clicked_cell.flag();
                System.out.print(board.countMines() - board.countRevealedMines() + "\n");
                if(board.countMines() - board.countRevealedMines() == 0){ //you won
                    win_flag = true;
                }
            }
            panel.repaint(); //repaint game panel on every click
        }

        //other mouse events are not needed here
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

    public static class Key { //object containing two integers
        //its purpose is to create a lookup table to easily access Cells with the knowledge of their xy coordinates
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
