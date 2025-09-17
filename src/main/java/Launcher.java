import gertrude.gui.Main;
import javafx.application.Application;

/**
 * A launcher class to workaround classpath issues.
 */
public class Launcher {
    /**
     * The main method, which is the entry point of the Java application.
     *
     * @param args the command-line arguments
     */
    public static void main(String... args) {
        Application.launch(Main.class, args);
    }
}
