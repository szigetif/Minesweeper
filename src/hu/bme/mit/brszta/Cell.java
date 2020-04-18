package hu.bme.mit.brszta;
import java.util.List;
import java.util.ArrayList;

enum CellState {
    DEFAULT,
    MARKED,
    REVEALED
}

public class Cell {

    private CellState cellState; //DEFAULT or MARKED or REVEALED
    private Boolean isMine; //updated by Board class
    private int displayNumber; //number of adjacent mines, calculated by the Board constructor and modified through get()
    private List<Cell> neighbourCells;

    public Cell(Boolean isMine) { //constructor
        this.cellState = CellState.DEFAULT;
        this.isMine = isMine; //from Board constructor
        this.neighbourCells = new ArrayList<Cell>(); // 2D matrix
    }

    public CellState getState(){
        return cellState;
    }
 
    public Boolean isMine(){
        return isMine;
    }

    public void addNeighbourCell(Cell neighbourCell) {
        neighbourCells.add(neighbourCell);
    }

    public void initDisplayNumber()
    {
        int sum = 0;
        for (Cell cell : neighbourCells) {
            if (cell.isMine()) sum++;
        }
        displayNumber = sum;
    }

    public int reveal(){
        if (isMine) {
            return -1; //mine is indicated by -1
        } 
        else {
            return displayNumber;
        }
    }

    public CellState mark(){ //switch between MARKED and DEFAULT

        if(cellState == CellState.MARKED) cellState = CellState.DEFAULT;
        if(cellState == CellState.DEFAULT) cellState = CellState.MARKED;

        return cellState;
    }

}