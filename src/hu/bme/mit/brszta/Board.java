package hu.bme.mit.brszta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board{

    private int sizeX;
    private int sizeY;
    private int numberOfMines;
    private ArrayList<ArrayList<Cell>> cellMatrix;

    public Board(int sizeX, int sizeY, int numberOfMines){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.numberOfMines = numberOfMines;

        List<Cell> initCellList = new ArrayList<Cell>();
        for (int i = 0; i < sizeX*sizeY; i++) {
            if (i < numberOfMines) {
                initCellList.add(new Cell(true));
            }
            else {
                initCellList.add(new Cell(false));
            }
        }
        Collections.shuffle(initCellList);

        cellMatrix = new ArrayList<ArrayList<Cell>>();
        for (int row = 0; row < sizeY; row++) {
            ArrayList<Cell> cellRow = new ArrayList<Cell>();
            for (int col = 0; col < sizeX; col++) {
                cellRow.add(initCellList.get(row * sizeY + col));
            }
            cellMatrix.add(cellRow);
        }

        initNeighbourCells();
        initDisplayNumbers();
    }

    private void initNeighbourCells() {
        // Connect the adjacent cells to each cell in the cellMatrix.
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

    private void initDisplayNumbers() {
        for (List<Cell> cellRow : cellMatrix) {
            for (Cell cell : cellRow) {
                cell.initDisplayNumber();
            }
        }
    }

    public int getSizeX(){
        return sizeX;
    }

    public int getSizeY(){
        return sizeY;
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }

    public int getNumberOfFlags() {
        int flags=0;
        for (List<Cell> cellRow : cellMatrix) {
            for (Cell cell : cellRow) {
                if (cell.getState() == CellState.MARKED) flags++;
            }
        }
        return flags;
    }
}