package battleship;

import java.sql.SQLOutput;

public class GameBoard {
    int rows = 10;
    int columns = 10;
    char[][] board = new char[rows][columns];

    public GameBoard() {
        generateGameBoard();
    }

    public void generateGameBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = '~';
            }
        }
    }

    public char getCell(int row, int col) {
        return board[row][col];
    }

    public boolean insertShip(Ship ship) {
        if (ship.partCoordinates != null) {
            int[][] partCoordinates = ship.getPartCoordinates();
            boolean shipNearby = isShipNearby(ship);
            if(!shipNearby) {
                for (int i = 0; i < partCoordinates.length; i++) {
                    int partXCoordinate = partCoordinates[i][0];
                    int partYCoordinate = partCoordinates[i][1];
                    board[partXCoordinate][partYCoordinate] = 'O';
                }
            }
            return !shipNearby;
        } else {
            return false;
        }
    }

    private boolean isShipNearby(Ship ship) {
        if (ship.getPartCoordinates() == null) {
            return false;
        }
//
        //[Char][Num] partCoordinates

        boolean exactCoordinates = checkSameCoordinate(ship.getPartCoordinates());
        boolean nearbyCoordinates = checkNearbyCoordinates(ship);

        if (exactCoordinates || nearbyCoordinates) {
            System.out.println("\nError! You placed it too close to another one." +
                    " Try again:\n");
        }
        return exactCoordinates || nearbyCoordinates;
        //boolean check = isShipNearby(ship.position, partCoordinates);
    }


    //Function checks if there exists a ship at the exact coordinates
    private boolean checkSameCoordinate (int[][] partCoordinates) {
        int Xcoordinate, Ycoordinate;

        for (int i = 0; i < partCoordinates.length; i++) {
            Xcoordinate = partCoordinates[i][0];
            Ycoordinate = partCoordinates[i][1];

            if (board[Xcoordinate][Ycoordinate] == 'O'){
                return true;
            }
        }
        return false;
    }

    //Function checks if there is an adjacent ship at given coordinates
    private boolean checkNearbyCoordinates (Ship ship) {
        int[][] partCoordinates = ship.getPartCoordinates();

        int length = ship.getLength();
        int[][] cornerPartCoordinates = {
                {partCoordinates[0][0],partCoordinates[0][1]},
                {partCoordinates[length-1][0],partCoordinates[length-1][1]}
        };

        String position = ship.getPosition();
        int Xcoordinate, Ycoordinate;
        boolean shipNearby = false;

        for (int i = 1; i < partCoordinates.length - 1 ; i++) {
            Xcoordinate = partCoordinates[i][0];
            Ycoordinate = partCoordinates[i][1];
            shipNearby = checkPosition(position, Xcoordinate, Ycoordinate);

            if (shipNearby) {
                return true;
            }
        }

        for (int i = 0; i < cornerPartCoordinates.length; i++) {
            Xcoordinate = cornerPartCoordinates[i][0];
            Ycoordinate = cornerPartCoordinates[i][1];
            shipNearby = checkCornerPosition(Xcoordinate, Ycoordinate);

            if (shipNearby) {
                return true;
            }
        }
        return false;
    }

    private boolean checkPosition (String position, int X, int Y) {
        boolean shipNearby = false;

        switch (position) {
            case "horizontal":
                if (X == 0) {
                    if (board[X + 1][Y] == 'O') {
                        shipNearby = true;
                    }
                } else if (X == 9) {
                    if (board[X - 1][Y] == 'O') {
                        shipNearby = true;
                    }
                } else {
                    if (board[X - 1][Y] == 'O') {
                        shipNearby = true;
                    }
                    if (board[X + 1][Y] == 'O') {
                        shipNearby = true;
                    }
                }
                break;

            case "vertical":
                if (Y == 0) {
                    if (board[X][Y + 1] == 'O') {
                        shipNearby = true;
                    }
                } else if (Y == 9) {
                    if (board[X][Y - 1] == 'O') {
                        shipNearby = true;
                    }
                } else {
                    if (board[X][Y - 1] == 'O') {
                        shipNearby = true;
                    }
                    if (board[X][Y + 1] == 'O') {
                        shipNearby = true;
                    }
                }
                break;
        }
        return shipNearby;
    }

    private boolean checkCornerPosition (int X, int Y) {
        int count = 0;
        int[][] coordinates = getCoordinates(X, Y);

        for (int i = 0; i < coordinates.length; i++) {
            int firstCoordinate = coordinates[i][0];
            int secondCoordinate = coordinates[i][1];
            //char letter = (char) (firstCoordinate + 65);
            //System.out.println(letter + ""+ (secondCoordinate + 1) + " = " + board[firstCoordinate][secondCoordinate]);
            if (board[firstCoordinate][secondCoordinate] == 'O') {
                count++;
            }
        }

    //    System.out.println("count: " + count);

        return count >= 1;
    }

    private int[][] getCoordinates(int X, int Y) {
        int[][] coordinates = new int[0][];
        if (X > 0 && X < 9 && Y > 0 && Y < 9) {
//            System.out.println("INSIDE COORDINATES");
            coordinates = new int[][]{
                    {X - 1, Y - 1}, {X - 1, Y}, {X - 1, Y + 1},
                    {X, Y - 1}, {X, Y + 1},
                    {X + 1, Y - 1}, {X + 1, Y}, {X + 1, Y + 1}
            };
        } else if (X == 0 && Y == 0) {
//            System.out.println("A, 1: COORDINATES");
            coordinates = new int[][]{
                    {X, Y + 1},
                    {X + 1, Y}, {X + 1, Y + 1}
            };
        } else if (X == 0 && Y == 9) {
//            System.out.println("A, 10: COORDINATES");
            coordinates = new int[][]{
                    {X, Y - 1},
                    {X + 1, Y - 1}, {X + 1, Y}
            };
        } else if (X == 9 && Y == 0) {
//            System.out.println("J, 1: COORDINATES");
            coordinates = new int[][]{
                    {X - 1, Y}, {X - 1, Y + 1},
                    {X, Y + 1},
            };
        } else if (X == 9 && Y == 9) {
//            System.out.println("J, 10: COORDINATES");
            coordinates = new int[][]{
                    {X - 1, Y - 1}, {X - 1, Y},
                    {X, Y - 1},
            };
        } else if (X == 0) {
//            System.out.println("A: COORDINATES");
            coordinates = new int[][]{
                    {X, Y - 1}, {X, Y + 1}, {X+1, Y-1},
                    {X + 1, Y}, {X + 1, Y + 1}
            };
        } else if (X == 9) {
//            System.out.println("J: COORDINATES");
            coordinates = new int[][]{
                    {X - 1, Y - 1}, {X - 1, Y}, {X - 1, Y + 1},
                    {X, Y - 1}, {X, Y + 1}
            };
        } else if (Y == 0) {
//            System.out.println("1: COORDINATES");
            coordinates = new int[][]{
                    {X - 1, Y}, {X - 1, Y + 1},
                    {X, Y + 1},
                    {X + 1, Y}, {X + 1, Y + 1}
            };
        } else if (Y == 9) {
//            System.out.println("10: COORDINATES");
            coordinates = new int[][]{
                    {X - 1, Y - 1}, {X - 1, Y},
                    {X, Y - 1},
                    {X + 1, Y - 1}, {X + 1, Y}
            };
        }
        return coordinates;
    }

    @Deprecated
    public boolean takeAShot(String shot, IllusionBoardView illusion_board, RealBoardView real_board, Player player) {
        char X = shot.charAt(0);
        int convertedX = UtilityClass.charConversion(X);
        int Y = shot.length() == 3? Integer.parseInt(shot.charAt(1) - '0' + String.valueOf(shot.charAt(2) - '0')) :
                (int) shot.charAt(1) - '0';
         Y -= 1;
//        System.out.println("X: " + convertedX + " | Y: " + Y);

        if (convertedX > 9 || Y > 9 || Y < 0) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            return false;
//            System.out.println("X: " + convertedX + " | Y: " + Y);
        } else {
            String message = "";
            if (board[convertedX][Y] == 'O') {

                board[convertedX][Y] = 'X';
                //SEARCH SHIP
                Ship ship = shipSuccessfulHit(player, shot);
                if (checkBoardState()) {
                    message = "You sank the last ship. You won. Congratulations!";
                } else if (ship.isDestroyed()) {
                    message = "You sank a ship! Specify a new target:";
                } else {
                    message = "You hit a ship!";
                }
            } else if (board[convertedX][Y] == '~') {
                message = "You missed. Try again:";
                board[convertedX][Y] = 'M';
            }

            illusion_board.display();
            System.out.println(message);
//            real_board.display();
            return true;
        }
    }

    public boolean takeAShot(String shot, Player player) {
        char X = shot.charAt(0);
        int convertedX = UtilityClass.charConversion(X);
        int Y = shot.length() == 3? Integer.parseInt(shot.charAt(1) - '0' + String.valueOf(shot.charAt(2) - '0')) :
                (int) shot.charAt(1) - '0';
        Y -= 1;
//        System.out.println("X: " + convertedX + " | Y: " + Y);

        if (convertedX > 9 || Y > 9 || Y < 0) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            return false;
//            System.out.println("X: " + convertedX + " | Y: " + Y);
        } else {
            String message = "";
            if (board[convertedX][Y] == 'O') {
                board[convertedX][Y] = 'X';
                //SEARCH SHIP
                Ship ship = shipSuccessfulHit(player, shot);
                if (checkBoardState()) {
                    message = "You sank the last ship. You won. Congratulations!";
                } else if (ship.isDestroyed()) {
                    message = "You sank a ship! Specify a new target:";
                } else {
                    message = "You hit a ship! Try again:";
                }
            } else if (board[convertedX][Y] == '~') {
                message = "You missed. Try again:";
                board[convertedX][Y] = 'M';
            }

//            IllusionBoardView illusion_board = player.getEnemy_board();
//            illusion_board.display();
            System.out.println(message);
            return true;
        }
    }

    public Ship shipSuccessfulHit (Player player, String shotCoordinates) {
        for (Ship ship : player.getOwnedShips()) {
            for (int i = 0; i < ship.partsOfShip.length; i++) {
                String[] parts = ship.getPartsOfShip();
                if (shotCoordinates.equals(parts[i])) {
                    parts[i] = "X";
                    ship.updateHealth();
                    return ship;
                }
            }
        }
        return null;
    }


    public boolean checkBoardState() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (board[i][j] == 'O') {
                    return false;
                }
            }
        }
        return true;
    }
}
