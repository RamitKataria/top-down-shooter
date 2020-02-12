package ui;

import model.Game;
import model.GameObject;
import model.Player;

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
        displayInitInstructions();

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

    // MODIFIES: this
    // EFFECTS: performs actions based on commands
    private void processCommand(String command) {
        if ("m".equals(command)) {
            displayMoveMenu();
            movePlayer();
        } else if ("o".equals(command)) {
            viewObjects();
        } else if ("s".equals(command)) {
            fireBullet();
        } else if ("w".equals(command)) {
            game.update();
        } else {
            displayNotRecognizedMessage();
        }
    }

    // EFFECTS: print the message associated with unrecognized command
    private void displayNotRecognizedMessage() {
        System.out.println("command not recognized");
    }

    // MODIFIES: this
    // EFFECTS: add a bullet object to the game and update the game
    private void fireBullet() {
        game.handleKey(KeyEvent.VK_SPACE);
        System.out.println("bullet fired!");
        game.update();
    }

    // EFFECTS: print the positions of all the walls
    private void viewWalls() {
        System.out.println("Walls:");
        for (GameObject wall : game.getWalls()) {
            System.out.println("\t(" + wall.getPosX() + ", " + wall.getPosY() + ")");
        }
    }

    // EFFECTS: print the list of all moving objects in the game
    private void viewObjects() {
        System.out.println("Objects:");
        for (GameObject go : game.getMovingObjects()) {
            printListing(go);
        }
        for (GameObject go : game.getWalls()) {
            printListing(go);
        }
    }

    // EFFECTS: print a listing of a game object with its name and location
    private void printListing(GameObject go) {
        System.out.println("\t" + go.getName() + ": (" + go.getPosX() + ", " + go.getPosY() + ")");
    }

    // MODIFIES: this
    // EFFECTS: get input from the user for specific movement and then move the player
    private void movePlayer() {
        String command = input.next().toLowerCase();
        Player player = game.getPlayer();
        if ("u".equals(command)) {
            player.setDx(0);
            player.setDy(1);
        } else if ("d".equals(command)) {
            player.setDx(0);
            player.setDy(-1);
        } else if ("r".equals(command)) {
            player.setDx(1);
            player.setDy(0);
        } else if ("l".equals(command)) {
            player.setDx(-1);
            player.setDy(0);
        } else if ("s".equals(command)) {
            player.setDx(0);
            player.setDy(0);
        } else {
            displayNotRecognizedMessage();
            return;
        }
        System.out.println("Direction changed");
        game.update();
    }

    // EFFECTS: print the menu for selecting player movement
    private void displayMoveMenu() {
        System.out.println("Which direction would you like to change your direction to?");
        System.out.println("\nSelect from:");
        System.out.println("\tu -> up");
        System.out.println("\td -> down");
        System.out.println("\tr -> right");
        System.out.println("\tl -> left");
        System.out.println("\ts -> stop");
    }

    // EFFECTS: print the main menu
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.print("\tm -> move   ");
        System.out.print("\to -> view objects   ");
        System.out.print("\ts -> fire bullet    ");
        System.out.print("\tw -> wait   ");
        System.out.print("\tq -> quit\n");
    }

    // EFFECTS: print the initial instructions
    private void displayInitInstructions() {
        System.out.println("Game started");
        System.out.println("After every refresh of the game, you can:\n - change the direction of the player\n"
                + " - view a list of all objects in the game (enemies, walls, player, bullets) along with their "
                + "coordinates\n - fire a bullet in the direction the player is facing\n - do nothing and wait "
                + "for the next refresh");
    }
}