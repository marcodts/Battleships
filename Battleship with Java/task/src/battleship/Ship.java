package battleship;


public class Ship {
    //Attributes
    private final String shipName;
    private String coordinateA;
    private String coordinateB;
    private int length;
    protected String[] partsOfShip;
    protected int[][] partCoordinates;
    private String position;
    private boolean isDestroyed;


    //Constructor

    public Ship(String shipName, String coordinateA, String coordinateB, int length){
        this.shipName = shipName;
        this.coordinateA = coordinateA;
        this.coordinateB = coordinateB;
        boolean valid = setLength(length);
        if(valid) { //Set the length
            setPartsOfShip(); //Determine and set the parts of the ship
            //this.position = alignment;
            setPartCoordinates(partsOfShip);
            isDestroyed = false;
        }
    }

    public String getShipName() {
        return shipName;
    }

    public int getLength() {
        return length;
    }

    public int getNumCoordinateValue(String numInCoordinate) {
        int suffix, prefix, combinedNum;
        prefix = numInCoordinate.charAt(1) - '0';
        if (numInCoordinate.length() == 3) {
            suffix = numInCoordinate.charAt(2) - '0';

            String numberString = prefix + String.valueOf(suffix);
            combinedNum = Integer.parseInt(numberString);
        } else {
            combinedNum = prefix;
        }
        return combinedNum;
    }

    public char getCharCoordinateValue(String charInCoordinate) {
        return charInCoordinate.charAt(0);
    }

    public boolean setLength(int requiredLength) {
        char firstChar = getCharCoordinateValue(coordinateA);
        int firstNum = getNumCoordinateValue(coordinateA);
        char secondChar = getCharCoordinateValue(coordinateB);
        int secondNum = getNumCoordinateValue(coordinateB);

        int numLength;
        if (firstChar == secondChar && firstNum != secondNum) {
            numLength = Math.abs(firstNum - secondNum) + 1;
        } else if (firstChar != secondChar && firstNum == secondNum){
            numLength = Math.abs(firstChar - secondChar) + 1;
        } else {
            numLength = 0;
        }

        if (firstChar != secondChar && firstNum != secondNum) {
            System.out.println("\nError! Wrong ship location! Try again:\n");
            return false;
        }

        if (numLength == requiredLength) {
            this.length = requiredLength;
            return true;
        } else {
            System.out.println("\nError! Wrong length of the " + getShipName() + "! Try again:\n");
            return false;
        }
    }

    public String getCoordinateA() {
        return coordinateA;
    }

    public void setCoordinateA(String coordinateA) {
        this.coordinateA = coordinateA;
    }

    public String getCoordinateB() {
        return coordinateB;
    }

    public void setCoordinateB(String coordinateB) {
        this.coordinateB = coordinateB;
    }



    public boolean validityCheck() {
        char firstChar = getCharCoordinateValue(coordinateA);
        int firstNum = getNumCoordinateValue(coordinateA);
        char secondChar = getCharCoordinateValue(coordinateB);
        int secondNum = getNumCoordinateValue(coordinateB);
//        System.out.println("First String: " + firstChar + firstNum);
//        System.out.println("Second String: " + secondChar + secondNum);
        //check coordinate

        boolean valInBound = isValInBound(firstNum, secondNum, firstChar, secondChar);
        String alignment = checkAlignment(firstNum, secondNum, firstChar, secondChar);
        boolean passedCheck = false;
//        System.out.println(alignment + " X " + valInBound);
        if (alignment.equals("illegal") || !valInBound) {
            System.out.println("\nError! Wrong ship location! Try again:\n");
        } else {
            passedCheck = true;
        }
        return passedCheck;
    }

    private boolean isValInBound(int firstNum, int secondNum, char firstChar,char secondChar) {
        boolean firstCondition = false, secondCondition = false, thirdCondition = false, conditionCheck = false;
        //check values if not within A -> J and between 1-10
        //int value of char then check ASCII
        if ((firstNum >= 1 && firstNum <= 10) && (secondNum >= 1 && secondNum <= 10)) {
            firstCondition = true;
        }
        if ((firstChar >= 65 && firstChar < 75) && (secondChar >= 65 && secondChar < 75)){
            secondCondition = true;
        }
        if (firstCondition && secondCondition) {
            conditionCheck = true;
        }
        return conditionCheck;
    }

    public String checkAlignment (int firstNum, int secondNum, char firstChar, char secondChar) {
        String alignment;
        if (firstChar == secondChar && firstNum != secondNum) { // A1 A4
            alignment = "horizontal";
        } else if (firstChar != secondChar && firstNum == secondNum) { //A1 D1
            alignment = "vertical";
        } else { //checks diagonal e.g. A1 C3
            alignment = "illegal";
        }
        position = alignment;
        return alignment;
    }

    public String checkOrder (int firstNum, int secondNum, char firstChar, char secondChar) {
        String order;
        if (firstChar > secondChar ^ firstNum > secondNum) { //A4 A1
            order = "descending";
        } else if (firstChar < secondChar ^ firstNum < secondNum) { //D1 A1
            order = "ascending";
        } else {
            order = "illegal";
        }
        return order;
    }

    public String checkOrderAndAlignment(String alignment, String order) {
        String orderAndPosition;
        if (alignment.equals("horizontal") && order.equals("ascending")) { //A1 A2 A3 A4
            orderAndPosition = "H-A";
        } else if (alignment.equals("horizontal") && order.equals("descending")) { //A4 A3 A2 A1
            orderAndPosition = "H-D";
        } else if (alignment.equals("vertical") && order.equals("ascending")) { //A1 B1 C1 D1
            orderAndPosition = "V-A";
        } else if (alignment.equals("vertical") && order.equals("descending")) { //D1 C1 B1 A1
            orderAndPosition = "V-D";
        } else {
            orderAndPosition = "illegal";
        }
        return orderAndPosition;
    }

    public String[] generateParts(){
        //checkValidity
        //check coordinate
        char firstChar = getCharCoordinateValue(coordinateA);
        int firstNum = getNumCoordinateValue(coordinateA);
        char secondChar = getCharCoordinateValue(coordinateB);
        int secondNum = getNumCoordinateValue(coordinateB);

        //Horizontal or Vertical Alignment
        String shipPosition = checkAlignment(firstNum, secondNum, firstChar, secondChar);

        //Ascending or Descending Order
        String shipOrder = checkOrder(firstNum, secondNum, firstChar, secondChar);

        //Generates String from Order and Position
        String generateShip = checkOrderAndAlignment(shipPosition, shipOrder);

        String[] partsOfShip = new String[this.getLength()];

        partsOfShip[0] = firstChar + String.valueOf(firstNum);
        partsOfShip[this.getLength() - 1] = secondChar + String.valueOf(secondNum);
        switch (generateShip) {
            case "H-A" -> {
                for (int i = 1; i < this.getLength() - 1; i++) {
                    partsOfShip[i] = firstChar + String.valueOf(firstNum + i);
                }
            }
            case "H-D" -> {
                for (int i = 1; i < this.getLength() - 1; i++) {
                    partsOfShip[i] = firstChar + String.valueOf(firstNum - i);
                }
            }
            case "V-A" -> {
                for (int i = 1; i < this.getLength() - 1; i++) {
                    partsOfShip[i] = (char) (firstChar + i) + String.valueOf(firstNum);
                }
            }
            case "V-D" -> {
                for (int i = 1; i < this.getLength() - 1; i++) {
                    partsOfShip[i] = (char) (firstChar - i) + String.valueOf(firstNum);
                }
            }
        }
        return partsOfShip;
    }


    public String[] getPartsOfShip() {
        return partsOfShip;
    }

    public void displayPartsOfShip(){
        boolean generationIsValid = validityCheck();
        if (generationIsValid) {
            System.out.print("Parts: ");
            for (int i = 0; i < partsOfShip.length; i++) {
                System.out.print(partsOfShip[i] + " ");
            }
            System.out.println();
            System.out.println();
        }
    }

    public void setPartsOfShip() {
        boolean generationIsValid = validityCheck();
//        System.out.println(generationIsValid);
        if (generationIsValid) {
            this.partsOfShip = generateParts();
//            System.out.println();
//            System.out.println("Length: " + getLength());
//            displayPartsOfShip();
        }
    }

    public void setPartCoordinates(String[] partsOfShip) {
        if (partsOfShip != null) {
            partCoordinates = new int[partsOfShip.length][2];
            for (int i = 0; i < partCoordinates.length; i++) {
                //NEED ERROR HANDLER FOR THINGS NOT A-J
                partCoordinates[i][0] = UtilityClass.charConversion(partsOfShip[i].charAt(0));
                partCoordinates[i][1] = getNumCoordinateValue(partsOfShip[i]) - 1;
            }
        }
    }
    public void displayPartCoordinates() {
        if (partsOfShip != null) {
            for (int i = 0; i < partCoordinates.length; i++) {
                System.out.println(partCoordinates[i][0] + " " + partCoordinates[i][1]);
            }
        }
    }

    public int[][] getPartCoordinates() {
        return partCoordinates;
    }

    public String getPosition() {
        return position;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void updateHealth() {
        int count = 0;
        for (String parts : partsOfShip) {
            if (parts == "X") {
                count++;
            }
        }
        if (count == partsOfShip.length) {
            isDestroyed = true;
        }
    }
}

//Subclass of Ship

class AirCraftCarrier extends Ship{
    public AirCraftCarrier(String coordinateA, String coordinateB){
        super(ShipType.CARRIER.getName(), coordinateA, coordinateB, ShipType.CARRIER.getSize());
    }
}

class Battleship extends Ship{
    public Battleship(String coordinateA, String coordinateB){
        super(ShipType.BATTLESHIP.getName(), coordinateA, coordinateB, ShipType.BATTLESHIP.getSize());
    }
}

class Submarine extends Ship{
    public Submarine (String coordinateA, String coordinateB){
        super(ShipType.SUBMARINE.getName(), coordinateA, coordinateB, ShipType.SUBMARINE.getSize());
    }
}

class Cruiser extends Ship{
    public Cruiser (String coordinateA, String coordinateB){
        super(ShipType.CRUISER.getName(), coordinateA, coordinateB, ShipType.CRUISER.getSize());
    }
}

class Destroyer extends Ship{
    public Destroyer (String coordinateA, String coordinateB){
        super(ShipType.DESTROYER.getName(), coordinateA, coordinateB, ShipType.DESTROYER.getSize());
    }
}