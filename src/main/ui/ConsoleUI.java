package ui;

import model.Game;
import model.GameObject;
import model.MovingObject;

import java.awt.event.KeyEvent;
import java.util.Scanner;

public class ConsoleUI {
    private Game game;
    private Scanner input;

    // EFFECTS: runs the game in console UI
    public ConsoleUI() {
        game = new Game();
        runGame();
    }

    // MODIFIES: this
    // EFFECTS: processes use input
    // source: Teller App
    private void runGame() {
        boolean keepGoing = true;
        String command;
        input = new Scanner(System.in);
        displayInitMenu();

        while (keepGoing) {
            displayMenu();
            command = input.next().toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
    }

    private void processCommand(String command) {
        switch (command) {
            case "m":
                displayMoveMenu();
                movePlayer();
                game.update();
                break;
            case "o":
                viewObjects();
                break;
            case "w":
                viewWalls();
                break;
            case "s":
                game.handleKey(KeyEvent.VK_SPACE);
                System.out.println("bullet fired!");
                game.update();
                break;
            default:
                System.out.println("command not recognized");
                break;
        }
    }

    private void viewWalls() {
        System.out.println("Walls:");
        for (GameObject wall : game.getWalls()) {
            System.out.println("\t(" + wall.getPosX() + ", " + wall.getPosY() + ")");
        }
    }

    private void viewObjects() {
        System.out.println("Objects:");
        for (MovingObject go : game.getMovingObjects()) {
            System.out.println("\t" + go.getName() + ": (" + go.getPosX() + ", " + go.getPosY() + ")");
        }
    }

    private void movePlayer() {
        String command;
        command = input.next().toLowerCase();
        switch (command) {
            case "u":
                game.getPlayer().setDy(1);
                game.getPlayer().setDy(0);
                break;
            case "d":
                game.getPlayer().setDy(-1);
                game.getPlayer().setDy(0);
                break;
            case "r":
                game.getPlayer().setDy(0);
                game.getPlayer().setDy(1);
                break;
            case "l":
                game.getPlayer().setDy(0);
                game.getPlayer().setDy(-1);
                break;
        }
    }

    private void displayMoveMenu() {
        System.out.println("Which direction would you like to change your direction to?");
        System.out.println("\nSelect from:");
        System.out.println("\tu -> up");
        System.out.println("\td -> right");
        System.out.println("\tr -> right");
        System.out.println("\tl -> left\n");
    }

    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.print("\tm -> move   ");
        System.out.print("\to -> view objects   ");
        System.out.print("\tw -> view walls ");
        System.out.print("\ts -> fire bullet    ");
        System.out.print("\tq -> quit\n");
    }

    private void displayInitMenu() {
        System.out.println("Game started");
        System.out.println("After every refresh of the game, you will can:\n - move the player in your desired "
                + "direction,\n - view a list of all objects in the game (enemies, walls, player) along with their "
                + "coordinates\n - fire a bullet in the direction the player is facing\n - view a list of all the "
                + "coordinates in the game on which walls are located (you cannot go through them)");
    }
}