package gertrude.gui;

import java.io.IOException;

import gertrude.Gertrude;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The main entry point for the GUI application.
 */
public class Main extends Application {
    private Gertrude gertrude = new Gertrude();

    @Override
    public void start(Stage stage) {
        try {
            stage.setTitle("Gertrude"); // Fixed typo in title
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            assert fxmlLoader != null : "FXML Loader cannot be null"; // Adding assertions to validate assumptions
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setGertrude(gertrude); // inject the Gertrude instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
