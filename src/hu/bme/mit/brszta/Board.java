package hu.bme.mit.brszta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board{
    private List<Boolean> mineList;
    private Cell[][] cellMatrix;

    public Board(Cell[][] cellMatrix){
        this.cellMatrix = cellMatrix;
        initNeighbourCells();
    }

    private void initNeighbourCells() {
        // Connect the adjacent cells to each cell in the cellMatrix.
        int sizeX = getSizeX();
        int sizeY = getSizeY();
        for (int row = 0; row < sizeY; row++) {
            for (int col = 0; col < sizeX; col++) {
                Cell cell = cellMatrix[row][col];
                if (row != 0 && col != 0)
                    cell.addNeighbourCell(cellMatrix[row-1][col-1]);
                if (row != 0)
                    cell.addNeighbourCell(cellMatrix[row-1][col]);
                if (row != 0 && col != sizeX-1)
                    cell.addNeighbourCell(cellMatrix[row-1][col+1]);
                if (col != 0)
                    cell.addNeighbourCell(cellMatrix[row][col-1]);
                if (col != sizeX-1)
                    cell.addNeighbourCell(cellMatrix[row][col+1]);
                if (row != sizeY-1 && col != 0)
                    cell.addNeighbourCell(cellMatrix[row+1][col-1]);
                if (row != sizeY-1)
                    cell.addNeighbourCell(cellMatrix[row+1][col]);
                if (row != sizeY-1 && col != sizeX-1)
                    cell.addNeighbourCell(cellMatrix[row+1][col+1]);
            }
        }
    }

    public int getSizeX(){
        return cellMatrix[0].length;
    }

    public int getSizeY(){
        return cellMatrix.length;
    }

    public int countMines() {
        int count = 0;
        for (Cell[] cellRow : cellMatrix) {
            for (Cell cell : cellRow) {
                if (cell instanceof MineCell) count++;
            }
        }
        return count;
    }

    public int countFlags() {
        int count = 0;
        for (Cell[] cellRow : cellMatrix) {
            for (Cell cell : cellRow) {
                if (cell.cellState == CellState.FLAGGED) count++;
            }
        }
        return count;
    }

    public int countRevealed() {
        int count = 0;
        for (Cell[] cellRow : cellMatrix) {
            for (Cell cell : cellRow) {
                if (cell.cellState == CellState.REVEALED) count++;
            }
        }
        return count;
    }

    public Cell getCell(int row, int col) {
        return cellMatrix[row][col];
    }
}