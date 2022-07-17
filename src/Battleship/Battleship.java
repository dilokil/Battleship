package Battleship;

import OnePerson.OnePerson;

import ClearConsole.ClearConsole;

import java.io.IOException;

public class Battleship {
    private OnePerson firstPlayer;
    private OnePerson secondPlayer;
    private boolean firstPLayerTurn;

    public Battleship() {
        this.firstPlayer = new OnePerson();
        this.secondPlayer = new OnePerson();
        this.firstPLayerTurn = true;
        passMove();
        System.out.print("Player 1, place your ships on the game field\n\n");
        this.firstPlayer.arrangeShip();
        passMove();

        System.out.print("Player 2, place your ships to the game field\n\n");
        this.secondPlayer.arrangeShip();
        passMove();

        while (!firstPlayer.gameIsEnd() && !secondPlayer.gameIsEnd()) {
            if (firstPLayerTurn) {
                secondPlayer.printField(true);
                System.out.println("---------------------");
                firstPlayer.printField(false);
                secondPlayer.makeOneShot();
                passMove();

            } else {
                firstPlayer.printField(true);
                System.out.println("---------------------");
                secondPlayer.printField(false);
                System.out.println("Player 2, it's your turn:");
                firstPlayer.makeOneShot();
                passMove();
            }
            firstPLayerTurn = !firstPLayerTurn;
        }
    }

    private void passMove() {
        System.out.println("Press Enter and pass the move to another player");
        while (true) {
            try {
                if (!(System.in.read() != '\n')) break;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        ClearConsole.ClearConsole();
    }
}
