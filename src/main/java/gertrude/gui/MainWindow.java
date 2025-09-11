package gertrude.gui;

import gertrude.Gertrude;
import gertrude.gui.components.DialogBox;
import gertrude.interactions.GertrudeResponse;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Gertrude gertrude;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image gertrudeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Gertrude instance */
    public void setGertrude(Gertrude g) {
        gertrude = g;
        String welcomeMessage = gertrude.init();
        addGertrudeDialog(welcomeMessage);
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing
     * Gertrude's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        GertrudeResponse response = gertrude.getResponse(input);
        addUserDialog(input);
        addGertrudeDialog(response.getMessage());
        userInput.clear();
        if (response.isExit()) {
            showGoodbyeAndExit();
        }
    }

    private void addGertrudeDialog(String text) {
        dialogContainer.getChildren().add(DialogBox.getGertrudeDialog(text, gertrudeImage));
    }

    private void addUserDialog(String text) {
        dialogContainer.getChildren().add(DialogBox.getUserDialog(text, userImage));
    }

    public void showGoodbyeAndExit() {
        PauseTransition pause = new PauseTransition(Duration.seconds(2)); // Pause for 2 seconds
        pause.setOnFinished(event -> System.exit(0)); // Exit after the pause
        pause.play(); // Start the pause
    }
}
