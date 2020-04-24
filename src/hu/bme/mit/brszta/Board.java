package hu.bme.mit.brszta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board{
    private List<Boolean> mineList;
    private ArrayList<ArrayList<Cell>> cellMatrix;

    public Board(ArrayList<ArrayList<Cell>> cellMatrix){
        this.cellMatrix = cellMatrix;
        initNeighbourCells();
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