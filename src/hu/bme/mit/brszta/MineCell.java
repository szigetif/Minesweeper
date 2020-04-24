package hu.bme.mit.brszta;

public class MineCell extends Cell {
    @Override
    public int reveal() {
        if (cellState == CellState.DEFAULT) {
            cellState = CellState.REVEALED;
            displayNumber = -1;
            return -1;
        }
        else {
            return 0;
        }
    }
}

