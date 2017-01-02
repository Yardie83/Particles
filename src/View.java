import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by Hermann Grieder on 28.09.2016.
 * <p>
 * Creates the layout of the application.
 * Draws the particles after every cycle of the Timeline.
 */
public class View {

    private Model model;
    private Stage stage;
    private Button btnGo;
    private Slider sliderParticleCount;
    private GraphicsContext gc;
    private CheckBox checkBoxBorders;
    private Canvas canvas;
    private CheckBox toggleLines;

    private CheckBox checkBoxGraviton;
    private ToggleGroup tg;

    public View(Stage stage, Model model) {
        this.stage = stage;
        this.model = model;

        createLayout();
    }

    private void createLayout() {

        VBox root = new VBox();

        HBox settingsBox = new HBox(20);
        settingsBox.setPadding(new Insets(10, 10, 10, 10));
        settingsBox.setAlignment(Pos.CENTER);


        tg = new ToggleGroup();
        RadioButton rb = new RadioButton("Inverse square");
        rb.setToggleGroup(tg);
        rb.setSelected(true);
        RadioButton rb2 = new RadioButton("Linear");
        rb2.setToggleGroup(tg);

        Label lblNumOfParticles = new Label("Number of Particles:");
        sliderParticleCount = new Slider(0, 100, 0);
        sliderParticleCount.setMajorTickUnit(10);
        sliderParticleCount.setMinorTickCount(5);
        sliderParticleCount.setShowTickMarks(true);
        sliderParticleCount.setShowTickLabels(true);
        sliderParticleCount.setSnapToTicks(true);
        sliderParticleCount.setBlockIncrement(1.0);

        Label lblParticlesSelected = new Label(String.valueOf((int) sliderParticleCount.getValue()));
        lblParticlesSelected.setMinWidth(50);

        checkBoxGraviton = new CheckBox("Toggle Graviton");
        checkBoxGraviton.setSelected(false);

        checkBoxBorders = new CheckBox("Toggle Walls");
        toggleLines = new CheckBox("Show vectors");
        btnGo = new Button("Charge!");
        btnGo.setMinWidth(70);

        settingsBox.getChildren().addAll(
                rb,
                rb2,
                lblNumOfParticles,
                sliderParticleCount,
                lblParticlesSelected,
                checkBoxBorders,
                toggleLines,
                checkBoxGraviton,
                btnGo);

        Pane canvasContainer = new Pane();
        canvasContainer.setStyle("-fx-background-color: lightslategrey");
        canvasContainer.setMinWidth(Main.WIDTH);
        canvasContainer.setMaxWidth(Main.WIDTH);
        canvasContainer.setMinHeight(Main.HEIGHT);
        canvasContainer.setMaxHeight(Main.HEIGHT);
        canvas = new Canvas(Main.WIDTH, Main.HEIGHT);
        gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        canvasContainer.getChildren().add(canvas);
        root.getChildren().addAll(settingsBox, canvasContainer);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Particles");
    }

    public void show() {
        stage.show();
    }

    public void update() {
        // Clear the whole canvas between frames
        clearCanvas();

        ArrayList<Particle> particles = model.getParticles();
        if (model.hasGraviton()) {
            Particle graviton = model.getGraviton();
            graviton.display(gc);
        }

        for (Particle p : particles) {
            p.display(gc);
        }
    }

    public void clearCanvas() {
        gc.clearRect(0, 0, Main.WIDTH, Main.HEIGHT);
    }

    public Button getBtnGo() {
        return btnGo;
    }

    public Slider getParticleCountSlider() {
        return sliderParticleCount;
    }

    public CheckBox getCheckBoxBorders() {
        return checkBoxBorders;
    }

    public CheckBox getToggleLines() {
        return toggleLines;
    }

    public CheckBox getCheckBoxGraviton() {
        return checkBoxGraviton;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public ToggleGroup getToggleGroup(){
        return tg;
    }
}

