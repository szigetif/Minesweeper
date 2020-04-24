package hu.bme.mit.brszta;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        BoardBuilder builder = new BoardBuilder();
        Board board = builder.getRandomBoard(3, 3, 1);
        board.getCell(0, 0).reveal();
        System.out.println("OK");
    }
}