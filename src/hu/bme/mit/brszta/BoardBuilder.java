package hu.bme.mit.brszta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BoardBuilder {

    public Board getRandomBoard(int sizeX, int sizeY, int numberOfMines) {
        boolean[][] mineMatrix = createRandomBooleanMatrix(sizeX, sizeY, numberOfMines);
        ArrayList<ArrayList<Cell>> cellMatrix = createCellMatrix(mineMatrix);
        return new Board(cellMatrix);
    }

    public Board getBoardFromBooleanMatrix(boolean[][] booleanMatrix) {
        ArrayList<ArrayList<Cell>> cellMatrix = createCellMatrix(booleanMatrix);
        return new Board(cellMatrix);
    }

    private boolean[][] createRandomBooleanMatrix(int sizeX, int sizeY, int trues){
        // 1D lists can be easily shuffled.
        List<Boolean> initList = new ArrayList<Boolean>();
        for (int i = 0; i < sizeX * sizeY; i++) {
            if (i < trues) {
                initList.add(true);
            } else {
                initList.add(false);
            }
        }
        Collections.shuffle(initList);

        boolean[][] matrix = new boolean[sizeY][sizeX];
        for (int row = 0; row < sizeY; row++) {
            for (int col = 0; col < sizeX; col++) {
                matrix[row][col] = initList.get(row * sizeX + col);
            }
        }
        return matrix;
    }

    private ArrayList<ArrayList<Cell>> createCellMatrix(boolean[][] mineMatrix)
    {
        ArrayList<ArrayList<Cell>> cellMatrix = new ArrayList<ArrayList<Cell>>();
        for (boolean[] row : mineMatrix) {
            ArrayList<Cell> cellRow = new ArrayList<Cell>();
            for (boolean isMine : row) {
                Cell cell;
                if (isMine) {
                    cell = new MineCell();
                }
                else {
                    cell = new EmptyCell();
                }
                cellRow.add(cell);
            }
            cellMatrix.add(cellRow);
        }
        return cellMatrix;
    }
}
