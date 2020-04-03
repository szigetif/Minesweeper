package hu.bme.mit.brszta;
import java.util.List;

public interface IBoard {
    int getSizeX();
    int getSizeY();
    List<ICell> getCells();
    int getNumberOfMines();
    int getNumberOfFlags();
}
