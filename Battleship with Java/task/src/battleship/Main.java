package battleship;
import java.util.Scanner;

public class Main {
    
    public static void passTurn(Scanner scanner) {
        System.out.println("Press Enter and pass the move to another player");
        System.out.println("...");
        scanner.nextLine();
        clearScreen();
    }

    public static String passTurn(GameState state, String nextPlayer) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press Enter and pass the move to another player");
        System.out.println("...");
        scanner.nextLine();
        clearScreen();
        state.setPlayerTurn(nextPlayer);
        return state.getPlayerTurn();
    }


    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameBoard player1_board = new GameBoard();
        GameBoard player2_board = new GameBoard();

        RealBoardView real_board_player1 = new RealBoardView(player1_board);
        IllusionBoardView enemy_board_player1 = new IllusionBoardView(player2_board);

        RealBoardView real_board_player2 = new RealBoardView(player2_board);
        IllusionBoardView enemy_board_player2 = new IllusionBoardView(player1_board);

        Player player1 = new Player(player1_board, real_board_player1, enemy_board_player1);
        Player player2 = new Player(player2_board, real_board_player2, enemy_board_player2);
        GameState gameState = new GameState(player1, player2);

        String[] shipTypes = {"Aircraft Carrier", "Battleship", "Submarine", "Cruiser", "Destroyer"};

        System.out.println("Player 1, place your ships on the game field");
        real_board_player1.display();
        for (String ship : shipTypes) {
            player1.generateShip(ship);
        }
        
        passTurn(scanner);

        System.out.println("Player 2, place your ships to the game field");
        real_board_player2.display();
        for (String ship : shipTypes) {
            player2.generateShip(ship);
        }

        passTurn(scanner);

        do {
            boolean turnSuccess = false;
            boolean inputSuccess = false;

            String turn = gameState.getPlayerTurn();
            
            switch (turn) {
                case "Player 1":
                    enemy_board_player1.display();
                    System.out.println("---------------------");
                    real_board_player1.display();
                    break;
                case "Player 2":
                    enemy_board_player2.display();
                    System.out.println("---------------------");
                    real_board_player2.display();
                    break;
            }

            String input;
            do {
                switch (turn) {
                    case "Player 1":
                        System.out.println("Player 1, it's your turn: ");
                        input = scanner.next();
                        inputSuccess = player2_board.takeAShot(input, player2);

                        if (inputSuccess) {
                            passTurn(gameState, "Player 2");
                            if (player2_board.checkBoardState()) {
                                gameState.setWinner("Player 1");
                            }
                        }
                        break;
                    case "Player 2":
                        System.out.println("Player 2, it's your turn: ");
                        input = scanner.next();
                        inputSuccess = player1_board.takeAShot(input, player1);
                        if (inputSuccess) {
                            passTurn(gameState, "Player 1");
                            if (player1_board.checkBoardState()) {
                                gameState.setWinner("Player 2");
                            }
                        }
                        break;
                }
                turnSuccess = inputSuccess;
            } while (!turnSuccess);
        } while (gameState.getWinner() == null);
        String winner = gameState.getWinner();
        System.out.println(winner + " sank the last ship." + winner + " won. Congratulations!");
    }
}

