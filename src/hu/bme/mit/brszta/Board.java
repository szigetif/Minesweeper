package hu.bme.mit.brszta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board{

    private int sizeX;
    private int sizeY;
    private int numberOfMines;
    private List<Boolean> mineList;
    private ArrayList<ArrayList<Cell>> cellMatrix;

    public Board(int sizeX, int sizeY, int numberOfMines){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.numberOfMines = numberOfMines;

        List<Boolean> mineList = createMineList();
        cellMatrix = createCellMatrix(mineList);

        initNeighbourCells();
        initDisplayNumbers();
    }

    private List<Boolean> createMineList() {
        List<Boolean> mineList = new ArrayList<Boolean>();
        for (int i = 0; i < sizeX*sizeY; i++) {
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

    private ArrayList<ArrayList<Cell>> createCellMatrix(List<Boolean> mineList)
    {
        cellMatrix = new ArrayList<ArrayList<Cell>>();
        for (int row = 0; row < sizeY; row++) {
            ArrayList<Cell> cellRow = new ArrayList<Cell>();
            for (int col = 0; col < sizeX; col++) {
                Boolean isMine = mineList.get(row * sizeY + col);
                cellRow.add(new Cell(isMine));
            }
            cellMatrix.add(cellRow);
        }
        return cellMatrix;
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

    public int countMines() {
        return numberOfMines;
    }

    public int countFlags() {
        int count = 0;
        for (List<Cell> cellRow : cellMatrix) {
            for (Cell cell : cellRow) {
                if (cell.getState() == CellState.FLAGGED) count++;
            }
        }
        return count;
    }

    public int countRevealed() {
        int count = 0;
        for (List<Cell> cellRow : cellMatrix) {
            for (Cell cell : cellRow) {
                if (cell.getState() == CellState.REVEALED) count++;
            }
        }
        return count;
    }

    public Cell getCell(int row, int col) {
        Cell cell = cellMatrix.get(row).get(col);
        return cell;
    }
}