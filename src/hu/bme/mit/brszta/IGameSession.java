package hu.bme.mit.brszta;

public interface IGameSession {
    void init();
    void start();
    void gameOver();
    IBoard getBoard();
}
