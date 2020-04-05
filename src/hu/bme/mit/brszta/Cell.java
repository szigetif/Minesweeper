package hu.bme.mit.brszta;
import java.util.Hashtable;


public class Cell implements ICell{

    private CellState cellState; //DEFAULT or MARKED or REVEALED
    private Boolean mine; //updated by Board class
    private Hashtable<String, Integer> cell_coordinates; //2d coordinates of cells with labels "x" and "y", possibly can be switched from Hashtable to Points class
    private int number_of_neighbours; //number of neigbouring mines, calculated by the Board constructor and modified through get()

    public Cell(Boolean mine, Hashtable<String, Integer> cell_coordinates){ //constructor
        cellState = CellState.DEFAULT;
        this.mine = mine; //from Board constructor
        this.cell_coordinates = cell_coordinates; //from Board constructor
        number_of_neighbours = 0; //later from Board constructor

    }

    public CellState getState(){ //get method
        return cellState;
    }

    public Boolean get_mine(){ //get method
        return mine;
    }

    public Hashtable<String, Integer> get_coordinates(){return cell_coordinates;}

    public void set_number_of_neighbours(int number_of_neighbours){this.number_of_neighbours = number_of_neighbours;}

    public int reveal(){
        if(mine){
            return -1; //mine is indicated by -1
        }
        else{
            return number_of_neighbours; //if it is not a mine, then Board constructor calculates the number of neighbouring mines
            }
        }

    public CellState mark(){ //switch between MARKED and DEFAULT

        if(cellState == CellState.MARKED) cellState = CellState.DEFAULT;

        if(cellState == CellState.DEFAULT) cellState = CellState.MARKED;

        return cellState;
    }

}
