package hu.bme.mit.brszta;

import java.util.List;

public class Main {

    public static void main(String[] args) {
	// write your code here

        Board board_obj = new Board(3, 3, 7); //create board

        List<List<Cell>> all_cells = board_obj.getCells(); //create list of all the cells, only to query their properties
        for (List<Cell> row_cell : all_cells) {
            for (Cell cell : row_cell) {
                System.out.println("[mine = " + cell.get_mine() + "] [coordinates= " + cell.get_coordinates() + "] [neighbouring mines= " + cell.reveal() + "]\n");

            }
        }



    }
}
