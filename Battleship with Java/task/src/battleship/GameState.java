package battleship;

import java.util.ArrayList;

public class GameState {
    private ArrayList<Player> playerList = new ArrayList<>();
    private int moveCount = 0;
    private String playerTurn;
    private String winner;


    public GameState(Player player1, Player player2) {
        playerList.add(player1);
        playerList.add(player2);
        setPlayerTurn("Player 1");
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void addMoveCount(int moveCount) {
        this.moveCount += 1;
    }

    public String getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(String playerTurn) {
        this.playerTurn = playerTurn;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
