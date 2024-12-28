package battleship;

public class IllusionBoardView {
    private GameBoard board;

    public IllusionBoardView(GameBoard board) {
        this.board = board;
    }


    public void display() {
        System.out.println();
        System.out.print(" ");
        for (int i = 0; i < 10; i++) {
            System.out.print(" " + (i + 1));
        }
        System.out.println();

        for (int i = 0; i < board.columns; i++) {
            char letter = (char) ('A' + i);
            System.out.printf("%c", letter);
            for (int j = 0; j < board.rows; j++) {
                char currentCell = board.getCell(i,j);
                if (currentCell == 'M' || currentCell == 'X'){
                    System.out.print(" " + currentCell);
                } else {
                    System.out.print(" " + '~');
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
