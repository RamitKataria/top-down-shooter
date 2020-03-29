package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
        // uncomment the next line and comment the previous to use Command Line Interface
        //new ConsoleUI();
    }

    // EFFECTS: Load the layout and set up the stage, scene and controller
    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched as an applet.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages and will not be embedded in the browser.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameLayout.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Game");
        primaryStage.setScene(new Scene(root, 1080, 720));
        GraphicalUI controller = loader.getController();
        controller.setUpUI(primaryStage);
        primaryStage.show();
    }
}
