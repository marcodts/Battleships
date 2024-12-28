package battleship;
import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    private ArrayList<Ship> ownedShips = new ArrayList<>();
    private GameBoard board;
    private RealBoardView true_pov;
    private IllusionBoardView enemy_pov;

    public Player() {}

    public Player(GameBoard board, RealBoardView true_pov, IllusionBoardView enemey_pov) {
        this.board = board;
        this.true_pov = true_pov;
        this.enemy_pov = enemey_pov;
    }

    public GameBoard getBoard() {
        return board;
    }

    public RealBoardView getPlayer_board() {
        return true_pov;
    }


    public IllusionBoardView getEnemy_board() {
        return enemy_pov;
    }


    public void addShip(Ship ship) {
        ownedShips.add(ship);
    }

    public void displayOwnedShips() {
        String display = "";
        for (Ship ship : ownedShips) {
            display += ship.getShipName() + " ";
        }
        System.out.println(display);
    }

    public void displayShipStatus() {
        String display = "";
        for (Ship ship : ownedShips) {
            display += ship.isDestroyed() + " ";
        }
        System.out.println(display);
    }

    public ArrayList<Ship> getOwnedShips() {
        return ownedShips;
    }

    public void setOwnedShips(ArrayList<Ship> ownedShips) {
        this.ownedShips = ownedShips;
    }

    public void generateShip(String shipType) {
        int cells = getShipCells(shipType);
        boolean success = false;
        Scanner scanner = new Scanner(System.in);
        String firstCoordinate;
        String secondCoordinate;

        do {
            System.out.println("Enter the coordinates of " + shipType + " (" + cells + " cells):");
            firstCoordinate = scanner.next();
            secondCoordinate = scanner.next();
            Ship ship = null;
            switch(shipType){
                case "Aircraft Carrier":
                    ship = new AirCraftCarrier(firstCoordinate, secondCoordinate);
                    break;
                case "Battleship":
                    ship = new Battleship(firstCoordinate, secondCoordinate);
                    break;
                case "Submarine":
                    ship = new Submarine(firstCoordinate, secondCoordinate);
                    break;
                case "Cruiser":
                    ship = new Cruiser(firstCoordinate, secondCoordinate);
                    break;
                case "Destroyer":
                    ship = new Destroyer(firstCoordinate, secondCoordinate);
                    break;
            }
            if (ship.partsOfShip != null) {
                success = board.insertShip(ship);
                if (success) {
                    addShip(ship);
                    true_pov.display();
                }
            }
        } while (!success);
    }

    public int getShipCells(String shipType) {
        int cells = 0;
        if (shipType.equals("Aircraft Carrier")) {
            cells = 5;
        } else if (shipType.equals("Battleship")) {
            cells = 4;
        } else if (shipType.equals("Submarine")) {
            cells = 3;
        } else if (shipType.equals("Cruiser")) {
            cells = 3;
        } else if (shipType.equals("Destroyer")) {
            cells = 2;
        }
        return cells;
    }


}