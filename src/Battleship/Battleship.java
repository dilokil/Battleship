package Battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Battleship {
    char[][] field;
    List<Ship> shipList;

    public Battleship() {
        this.field = new char[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                field[i][j] = '~';
            }
        }

        shipList = new ArrayList<>();

        shipList.add(new Ship("Aircraft Carrier", 5));
        shipList.add(new Ship("Battleship", 4));
        shipList.add(new Ship("Submarine", 3));
        shipList.add(new Ship("Cruiser", 3));
        shipList.add(new Ship("Destroyer", 2));
    }

    public void arrangeShip() {
        for (Ship ship : shipList) {
            if (!ship.isOnField()) {
                this.addToField(ship);
                this.printField();
            }
        }
    }

    public void printField() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        char symb = 'A';
        for (int i = 0; i < 10; i++) {
            System.out.print(symb);
            for (int j = 0; j < 10; j++) {
                System.out.print(" " + field[i][j]);
            }
            symb++;
            System.out.println();
        }
    }

    private void addToField(Ship ship) {
        boolean isValidCoordinates = false;
        String startCoordinates = "";
        String endCoordinates = "";
        System.out.printf("Enter the coordinates of the %s (%d cells):\n", ship.getMark(), ship.getLength());
        while (!isValidCoordinates) {
            Scanner scanner = new Scanner(System.in);
            startCoordinates = scanner.next();
            endCoordinates = scanner.next();
            isValidCoordinates = this.setCoordinatesToShip(startCoordinates, endCoordinates, ship);
        }
        this.drawShip(ship);
    }

    private boolean setCoordinatesToShip(String startCoordinates, String endCoordinates, Ship ship) {

        if (!this.checkCoordinate(startCoordinates) || !this.checkCoordinate(endCoordinates)) {
            return false;
        }
        Coordinate start = new Coordinate(startCoordinates);
        Coordinate end = new Coordinate(endCoordinates);

        if (((end.getX() - start.getX() == 0) || (end.getY() - start.getY() == 0)) &&
                ((Math.abs(end.getX() - start.getX()) == ship.getLength() - 1) ||
                        (Math.abs(end.getY() - start.getY()) == ship.getLength() - 1))) {
            if (Coordinate.isLess(end, start)) {
                Coordinate temp = new Coordinate(end);
                end.changeCoordinate(start.getX(), start.getY());
                start.changeCoordinate(temp.getX(), temp.getY());
            }
        } else {
            System.out.println("Error! Incorrect coordinates! Try again:");
            return false;
        }
        Coordinate startCheck = new Coordinate(start);
        Coordinate endCheck = new Coordinate(end);

        startCheck.changeCoordinate(start.getX() - 1, start.getY() - 1);
        endCheck.changeCoordinate(end.getX() + 1, end.getY() + 1);

        for (int i = startCheck.getX(); i <= endCheck.getX(); i++) {
            for (int j = startCheck.getY(); j <= endCheck.getY(); j++) {
                if (this.field[i][j] != '~') {
                    System.out.println("Error! Wrong ship location! Try again:");
                    return false;
                }
            }
        }
        ship.setCoordinates(start, end);
        return true;
    }

    private void drawShip(Ship ship) {
        Coordinate start = new Coordinate(ship.getStartCoordinates());
        Coordinate end = new Coordinate(ship.getEndCoordinates());
        for (int i = start.getX(); i <= end.getX(); i++) {
            for (int j = start.getY(); j <= end.getY(); j++) {
                this.field[i][j] = 'O';
            }
        }
    }

    public void makeOneShot() {
        System.out.println("Take a shot!");
        Scanner scanner = new Scanner(System.in);
        boolean isValidCoordinate = false;
        String stringCoordinates = "";
        while (!isValidCoordinate) {
            stringCoordinates = scanner.next();
            isValidCoordinate = checkCoordinate(stringCoordinates);
        }
        Coordinate coordinate = new Coordinate(stringCoordinates);
        if (this.field[coordinate.getX()][coordinate.getY()] == 'O') {
            this.field[coordinate.getX()][coordinate.getY()] = 'X';
            this.printField();// Убрать после тестирования
            System.out.println("You hit a ship!");
        } else {
            this.field[coordinate.getX()][coordinate.getY()] = 'M';
            this.printField();// Убрать после тестирования
            System.out.println("You missed!");
        }
        // Убрать после тестирования
    }

    public boolean checkCoordinate(String strCoord) {
        Coordinate coordinate;
        try {
            coordinate = new Coordinate(strCoord);
        } catch (NumberFormatException e) {
            System.out.println("Error! Incorrect input! " + e.getMessage() + " Try again:");
            return false;
        }
        if (!coordinate.checkRange()) {
            System.out.println("Error! Incorrect input! Out of range! Try again:");
            return false;
        }
        return true;
    }
}

class Coordinate {
    int x;
    int y;

    public Coordinate(String stringCoord) throws NumberFormatException {
        if (stringCoord == null || stringCoord.length() < 2) {
            throw new NumberFormatException("Incorrect string coordinates!");
        }
        this.x = (int) stringCoord.charAt(0) - 65;
        this.y = Integer.parseInt(stringCoord.substring(1)) - 1;
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(Coordinate source) {
        this.x = source.getX();
        this.y = source.getY();
    }

    public boolean checkRange() {
        return (x >= 0 && x < 10) && (y >= 0 && y < 10);
    }

    public void changeCoordinate(int newX, int newY) {
        if (newX >= 0 && newX < 10) {
            this.x = newX;
        }
        if (newY >= 0 && newY < 10) {
            this.y = newY;
        }
    }

    public static boolean isLess(Coordinate lhs, Coordinate rhs) {
        return lhs.getX() < rhs.getX() || lhs.getY() < rhs.getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}

class Ship {
    private String mark;
    private int length;
    private Coordinate startCoordinates;
    private Coordinate endCoordinates;
    private boolean onField;

    public Ship(String mark, int length) {
        this.mark = mark;
        this.length = length;
        this.onField = false;
    }

    public boolean isOnField() {
        return onField;
    }

    public void setCoordinates(Coordinate startCoordinates, Coordinate endCoordinates) {
        this.onField = true;
        this.startCoordinates = new Coordinate(startCoordinates);
        this.endCoordinates = new Coordinate(endCoordinates);
    }

    public Coordinate getStartCoordinates() {
        return startCoordinates;
    }

    public Coordinate getEndCoordinates() {
        return endCoordinates;
    }

    public String getMark() {
        return mark;
    }

    public int getLength() {
        return length;
    }

}

