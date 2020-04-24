package hu.bme.mit.brszta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board{
    private List<Boolean> mineList;
    private ArrayList<ArrayList<Cell>> cellMatrix;

    public Board(int sizeX, int sizeY, int numberOfMines){
        List<Boolean> mineList = createMineList(sizeX*sizeY, numberOfMines);
        cellMatrix = createCellMatrix(sizeX, sizeY, mineList);

        initNeighbourCells();
    }

    private List<Boolean> createMineList(int len, int numberOfMines) {
        List<Boolean> mineList = new ArrayList<Boolean>();
        for (int i = 0; i < len; i++) {
            if (i < numberOfMines) {
                mineList.add(true);
            }
            else {
                mineList.add(false);
            }
        }
        Collections.shuffle(mineList);
        return mineList;
    }

    private ArrayList<ArrayList<Cell>> createCellMatrix(int sizeX, int sizeY, List<Boolean> mineList)
    {
        cellMatrix = new ArrayList<ArrayList<Cell>>();
        for (int row = 0; row < sizeY; row++) {
            ArrayList<Cell> cellRow = new ArrayList<Cell>();
            for (int col = 0; col < sizeX; col++) {
                Cell cell;
                if (mineList.get(row * sizeY + col)) {
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

    private void initNeighbourCells() {
        // Connect the adjacent cells to each cell in the cellMatrix.
        int sizeX = getSizeX();
        int sizeY = getSizeY();
        for (int row = 0; row < sizeY; row++) {
            for (int col = 0; col < sizeX; col++) {
                Cell cell = cellMatrix.get(row).get(col);
                if (row != 0 && col != 0) 
                    cell.addNeighbourCell(cellMatrix.get(row-1).get(col-1));
                if (row != 0) 
                    cell.addNeighbourCell(cellMatrix.get(row-1).get(col));
                if (row != 0 && col != sizeX-1) 
                    cell.addNeighbourCell(cellMatrix.get(row-1).get(col+1));
                if (col != 0)
                    cell.addNeighbourCell(cellMatrix.get(row).get(col-1));
                if (col != sizeX-1)
                    cell.addNeighbourCell(cellMatrix.get(row).get(col+1));
                if (row != sizeY-1 && col != 0)
                    cell.addNeighbourCell(cellMatrix.get(row+1).get(col-1));
                if (row != sizeY-1)
                    cell.addNeighbourCell(cellMatrix.get(row+1).get(col));
                if (row != sizeY-1 && col != sizeX-1)
                    cell.addNeighbourCell(cellMatrix.get(row+1).get(col+1));
            }
        }
    }

    public int getSizeX(){
        return cellMatrix.get(0).size();
    }

    public int getSizeY(){
        return cellMatrix.size();
    }

    public int countMines() {
        int count = 0;
        for (List<Cell> cellRow : cellMatrix) {
            for (Cell cell : cellRow) {
                if (cell instanceof MineCell) count++;
            }
        }
        return count;
    }

    public int countFlags() {
        int count = 0;
        for (List<Cell> cellRow : cellMatrix) {
            for (Cell cell : cellRow) {
                if (cell.cellState == CellState.FLAGGED) count++;
            }
        }
        return count;
    }

    public int countRevealed() {
        int count = 0;
        for (List<Cell> cellRow : cellMatrix) {
            for (Cell cell : cellRow) {
                if (cell.cellState == CellState.REVEALED) count++;
            }
        }
        return count;
    }

    public Cell getCell(int row, int col) {
        return cellMatrix.get(row).get(col);
    }
}