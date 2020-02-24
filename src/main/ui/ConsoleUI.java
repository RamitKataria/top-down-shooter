package ui;

import model.Game;
import model.GameObject;
import model.Player;
import persistence.Reader;
import persistence.Writer;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class ConsoleUI {
    private static final String GAME_SAVE_FILE_LOC = "./data/gamesave.json";
    private Game game;
    private Scanner input;

    // EFFECTS: runs the game in console UI
    public ConsoleUI() {
        runGame();
    }

    // MODIFIES: this
    // EFFECTS: processes use input
    // source: Teller App
    private void runGame() {
        boolean keepGoing = true;
        String command;
        input = new Scanner(System.in);
        loadGame();

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

    private void loadGame() {
        try {
            game = Reader.readGame(new File(GAME_SAVE_FILE_LOC));
        } catch (FileNotFoundException e) {
            game = new Game();
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
        } else if ("f".equals(command)) {
            fireBullet();
        } else if ("w".equals(command)) {
            System.out.println("Game refreshed");
            game.update();
        } else if ("s".equals(command)) {
            saveGame();
        } else if ("r".equals(command)) {
            game = new Game();
            System.out.println("game restarted");
        } else if ("d".equals(command)) {
            deleteSavedGame();
        } else {
            displayNotRecognizedMessage();
        }
    }

    private void deleteSavedGame() {
    }

    // EFFECTS: print the message associated with unrecognized command
    private void displayNotRecognizedMessage() {
        System.out.println("command not recognized");
    }

    // EFFECTS: saves the current state of game
    private void saveGame() {
        try {
            Writer writer = new Writer(new File(GAME_SAVE_FILE_LOC));
            writer.write(game);
            writer.close();
            System.out.println("Game saved successfully to file " + GAME_SAVE_FILE_LOC);
        } catch (IOException e) {
            System.out.println("Unable to save game to file " + GAME_SAVE_FILE_LOC);
        }
    }

    // MODIFIES: this
    // EFFECTS: add a bullet object to the game and update the game
    private void fireBullet() {
        game.handleKey(KeyEvent.VK_SPACE);
        System.out.println("bullet fired!");
        game.update();
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
        System.out.print("\tf -> fire bullet    ");
        System.out.print("\tw -> wait   ");
        System.out.print("\ts -> save   ");
        System.out.print("\tr -> restart");
        System.out.print("\tq -> quit\n");
    }

    // EFFECTS: print the initial instructions
    private void displayInitInstructions() {
        System.out.println("Game started");
        System.out.println("After every refresh of the game, you can:\n - change the direction of the player\n"
                + " - view a list of all objects in the game (enemies, walls, player, bullets) along with their "
                + "coordinates\n - fire a bullet in the direction the player is facing\n - do nothing and wait "
                + "for the next refresh\n - save the current state of the game\n - restart the game");
        System.out.println("NOTE: you can only keep 1 game saved at a time.");
    }
}