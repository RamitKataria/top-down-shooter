package ui;

import model.Game;
import model.GameObject;
import model.Player;
import persistence.Reader;
import persistence.Writer;

import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static model.Game.GAME_SAVE_FILE;
import static model.Game.NEW_GAME_FILE;

public class ConsoleUI {
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
        setUpGame();

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
    // EFFECTS: Load previous saved game if it exists
    //          Otherwise, make a new game
    private void setUpGame() {
        try {
            game = Reader.readGame(GAME_SAVE_FILE);
            if (game == null) {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e) {
            game = getDefaultGame();
        }
    }

    // EFFECTS: return the default game
    private Game getDefaultGame() {
        try {
            return Reader.readGame(NEW_GAME_FILE);
        } catch (FileNotFoundException e) {
            System.out.println("New game file not found");
            e.printStackTrace();
            return new Game();
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
            game = getDefaultGame();
            System.out.println("game restarted");
        } else if ("d".equals(command)) {
            deleteSavedGame();
        } else {
            displayNotRecognizedMessage();
        }
    }

    // EFFECTS: delete the file containing the saved game
    private void deleteSavedGame() {
        boolean success = GAME_SAVE_FILE.delete();
        if (success) {
            System.out.println("Saved game deleted");
        } else {
            System.out.println("Deletion failed");
        }
    }

    // EFFECTS: print the message associated with unrecognized command
    private void displayNotRecognizedMessage() {
        System.out.println("command not recognized");
    }

    // EFFECTS: save the current state of game
    private void saveGame() {
        try {
            new Writer(GAME_SAVE_FILE).write(game);
            System.out.println("Game saved successfully to file " + GAME_SAVE_FILE.getPath());
        } catch (IOException e) {
            System.out.println("Unable to save game to file " + GAME_SAVE_FILE.getPath());
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
        printListing(game.getPlayer(), "Player");
        for (GameObject go : game.getEnemies()) {
            printListing(go, "Enemy");
        }
        for (GameObject go : game.getBullets()) {
            printListing(go, "Bullet");
        }
        for (GameObject go : game.getWalls()) {
            printListing(go, "Wall");
        }
    }

    // EFFECTS: print a listing of a game object with its name and location
    private void printListing(GameObject go, String name) {
        System.out.println("\t" + name + ": (" + go.getPosX() + ", " + go.getPosY() + ")");
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
        System.out.print("\t\tm -> change direction");
        System.out.print("\t\to -> view objects");
        System.out.print("\t\tf -> fire bullet");
        System.out.print("\t\tw -> wait");
        System.out.print("\t\ts -> save");
        System.out.print("\t\tr -> restart");
        System.out.print("\t\td -> delete saved game");
        System.out.print("\t\tq -> quit\n");
    }

    // EFFECTS: print the initial instructions
    private void displayInitInstructions() {
        System.out.println("Game started");
        System.out.println("After every refresh of the game, you can:\n - change the direction of the player\n"
                + " - view a list of all objects in the game (enemies, walls, player, bullets) along with their "
                + "coordinates\n - fire a bullet in the direction the player is facing\n - do nothing and wait "
                + "for the next refresh\n - save the current state of the game\n - restart the game\n - delete "
                + "saved game progress");
    }
}