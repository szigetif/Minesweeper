package hu.bme.mit.brszta;
import java.util.List;

public interface IBoard {
    int getSizeX();
    int getSizeY();
    List<Cell> getCells();
  //  int getNumberOfMines(); //why would we need that?
    int getNumberOfFlags();
}
