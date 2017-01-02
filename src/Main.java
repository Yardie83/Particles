import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by Hermann Grieder on 28.09.2016.
 * <p>
 * Main class of the program.
 */

public class Main extends Application {

    public static final int WIDTH = 1080;
    public static final int HEIGHT = 600;

    @Override
    public void start(Stage stage) throws Exception {

        Model model = new Model();
        View view = new View(stage, model);
        new Controller(model, view);
        view.show();
    }
}
