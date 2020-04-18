package hu.bme.mit.brszta;
import java.util.List;
import java.util.ArrayList;

enum CellState {
    DEFAULT,
    MARKED,
    REVEALED
}

public class Cell {

    private CellState cellState;
    private Boolean isMine;
    private int displayNumber;
    private List<Cell> neighbourCells;

    public Cell(Boolean isMine) {
        this.cellState = CellState.DEFAULT;
        this.isMine = isMine;
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
        cellState = CellState.REVEALED;
        if (isMine) {
            return -1; //mine is indicated by -1
        } 
        else {
            return displayNumber;
        }
    }

    public void revealNeighbours() {
        // Modifies their state. Return value is not important because none of them are mines.
        for (Cell cell : neighbourCells) {
            cell.reveal();
        }
    }

    public CellState mark() {
        if(cellState == CellState.MARKED) cellState = CellState.DEFAULT;
        if(cellState == CellState.DEFAULT) cellState = CellState.MARKED;

        return cellState;
    }
}