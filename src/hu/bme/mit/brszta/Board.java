package hu.bme.mit.brszta;

//import java.util.ArrayList;
//import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
//import java.util.Collections;
import java.util.*;
import java.lang.Math;

public class Board implements IBoard{

    private Hashtable<String, Integer> table_size; //size of table in dictionary, labelled with "x" and "y"
    private int mines; //might be redundant if getNumberOfMines() remains unimplemented

    //private List<Cell> list_of_cells; //all the cell objects in a list
    private List<List<Cell>> list_of_cells;

    public Board(int size_x, int size_y, int mines) { //constructor
        table_size = new Hashtable<>(); //create dictionary for size of table
        table_size.put("x", size_x); //get the size of table from user
        table_size.put("y", size_y); //get the size of table from user
        this.mines = mines; //get the number of mines from user
        list_of_cells = new ArrayList<>(); //create list of cell objects

        List<Boolean> init_mines = Collections.nCopies(mines, true); //create list of true values for the cells with mines
        List<Boolean> init_no_mines = Collections.nCopies(size_x * size_y - mines, false); //create list of false values for the other cells
        init_mines = Stream.concat(init_mines.stream(), init_no_mines.stream()).collect(Collectors.toList()); //concatenate the two previous lists
        Collections.shuffle(init_mines); //shuffle the content of the list to create a random compilation of cells with mines and no mines

        for (int i = 0; i < size_y; i++) {
            list_of_cells.add(new ArrayList<>());
            for (int j = 0; j < size_x; j++) {
                Hashtable<String, Integer> cell_coordinates = new Hashtable<>();
                cell_coordinates.put("x", j); //write in the x coordinate
                cell_coordinates.put("y", i); //write in the y coordinate
                Cell cell = new Cell(init_mines.get(i * size_x + j), cell_coordinates);
                list_of_cells.get(i).add(cell);
            }
        }

        for (List<Cell> row_cell : list_of_cells) { //calculate number of neighbouring mines
            for (Cell cell : row_cell) {
                if (cell.get_mine())
                    continue; //if there is a mine, no need for calculation -> number of neighbouring mines = -1 per definiton
                int number_of_neighbours = 0;
                int cord_x = cell.get_coordinates().get("x"); //get coordinates of inspected cell
                int cord_y = cell.get_coordinates().get("y"); //get coordinates of inspected cell
                for (List<Cell> row_neighbor_cell : list_of_cells) {
                    for (Cell neighbor : row_neighbor_cell) { //iterate through all other cells with mines and see if they are adjacent to the inspected cell
                        if (!neighbor.get_mine())
                            continue; //if there is no mine in a cell, then no need to check if it is adjacent or not
                        int sub_cord_x = neighbor.get_coordinates().get("x"); //get coordinates of compared cell
                        int sub_cord_y = neighbor.get_coordinates().get("y"); //get coordinates of compared cell
                        if (Math.abs(cord_x - sub_cord_x) < 2 && Math.abs(cord_y - sub_cord_y) < 2)
                            number_of_neighbours++;
                        //check if they are adjacent, if so then we found a neighbouring mine!

                    }
                }
                cell.set_number_of_neighbours(number_of_neighbours);
                //after all other cells were iterated through, we have the number of neighbouring mines and modify the corresponding variable of the cell object

            }

        }
    }

    public int getSizeX(){
        return table_size.get("x");
    }

    public int getSizeY(){
        return table_size.get("y");
    }

    public List<List<Cell>> getCells(){
        return list_of_cells;
    }

   // int getNumberOfMines(); //I don't see yet the point of it

    public int getNumberOfFlags(){ //counts the number of flagged cells
        int flags=0;
        for (List<Cell> list_cell : list_of_cells) {
            for (Cell cell : list_cell) {
                if (cell.getState() == Cell.CellState.MARKED) flags++;
            }
        }
        return flags;
    }
}
