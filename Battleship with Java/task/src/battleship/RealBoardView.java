package battleship;

public class RealBoardView {
    private GameBoard board;

    public RealBoardView(GameBoard board) {
        this.board = board;
    }


    public void display() {
        System.out.println();
        System.out.print(" ");
        for (int i = 0; i < board.columns; i++) {
            System.out.print(" " + (i + 1));
        }
        System.out.println();

        for (int i = 0; i < board.columns; i++) {
            char letter = (char) ('A' + i);
            System.out.printf("%c", letter);
            for (int j = 0; j < board.rows; j++) {
                System.out.print(" " + board.getCell(i,j));
            }
            System.out.println();
        }
        System.out.println();
    }
}

/*
    TODO:
        Player class = Ownership of ships, where ship parts are in an array
            e.g. Player 1 = {{A1, A2, A3, A4, A5}, {B3, B4, B5}, {J1, J2, J3}}
                 Player1 = {Carrier, Submarine, ..., Destroyer}

 */