package hu.bme.mit.brszta;

public interface ICell {

    enum CellState {
        DEFAULT,
        MARKED,
        REVEALED
    }

    CellState getState();

    /**
     * Reveals the cell, and returns its content.
     *
     * @return  cell content. 0 to 8 represents the number of neighboring mines. -1 means the cell contains a mine.
     */
    int reveal();

    /**
     * Cycle through Cell states DEFAULT and MARKED.
     *
     * @return  cell state
     */
    CellState mark();
}
