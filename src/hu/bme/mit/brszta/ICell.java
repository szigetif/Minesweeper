package hu.bme.mit.brszta;

public interface ICell {
    enum State {
        DEFAULT,
        MARKED,
        REVEALED
    }

    State getState();

    /**
     * Reveals the cell, and returns its content.
     *
     * @return  cell content, can be "", "1" to "8" or "BOMB"
     */
    String reveal();

    /**
     * Cycle through Cell states DEFAULT and MARKED.
     *
     * @return  cell state
     */
    State mark();
}
