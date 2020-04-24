package hu.bme.mit.brszta;
import java.util.List;
import java.util.ArrayList;

enum CellState {
    DEFAULT,
    FLAGGED,
    REVEALED
}

public abstract class Cell {

    public CellState cellState;
    protected int displayNumber;
    protected List<Cell> neighbourCells;

    public Cell() {
        this.cellState = CellState.DEFAULT;
        this.neighbourCells = new ArrayList<Cell>(); // 2D matrix
    }

    public void addNeighbourCell(Cell neighbourCell) {
        neighbourCells.add(neighbourCell);
    }

    public abstract int reveal();

    protected void revealNeighbours() {
        // Return value is not important because none of them are mines.
        for (Cell cell : neighbourCells) {
            if (cell.cellState == CellState.DEFAULT) {
                cell.reveal();
            }
        }
    }

    public CellState flag() {
        if(cellState == CellState.FLAGGED) cellState = CellState.DEFAULT;
        if(cellState == CellState.DEFAULT) cellState = CellState.FLAGGED;

        return cellState;
    }
}