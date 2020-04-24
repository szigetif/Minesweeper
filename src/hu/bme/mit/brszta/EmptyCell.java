package hu.bme.mit.brszta;

public class EmptyCell extends Cell {
    @Override
    public int reveal() {
        if (cellState == CellState.DEFAULT){
            cellState = CellState.REVEALED;
            displayNumber = countNeighbourMines();
            if (displayNumber == 0) revealNeighbours();

            return displayNumber;
        }
        else {
            return 0;
        }
    }

    private int countNeighbourMines() {
        int neighbourMines = 0;
        for (Cell cell : neighbourCells) {
            if (cell instanceof MineCell) neighbourMines++;
        }
        return neighbourMines;
    }
}
