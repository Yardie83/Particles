import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Toggle;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 * Created by Hermann Grieder on 28.09.2016.
 * <p>
 * Controller class. Handles all the JavaFX controls.
 */
public class Controller {


    private boolean animationIsRunning = false;
    private Timeline animationLoop;

    public Controller(Model model, View view) {

        view.getBtnGo().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                view.getParticleCountSlider().setDisable(true);

                animationIsRunning = !animationIsRunning;
                model.setBordersClosed(view.getCheckBoxBorders().isSelected());
                if (animationIsRunning) {

                    double numberOfParticles = view.getParticleCountSlider().getValue();
                    model.getParticles().clear();
                    model.createParticles(numberOfParticles);

                    animationLoop = new Timeline();

                    animationLoop.setCycleCount(Timeline.INDEFINITE);

                    KeyFrame kf = new KeyFrame(
                            Duration.seconds(0.017),
                            new EventHandler<ActionEvent>() {
                                public void handle(ActionEvent event) {
                                    model.update();
                                    view.update();
                                    model.clearAcceleration();
                                }
                            });
                    animationLoop.getKeyFrames().add(kf);

                    view.getBtnGo().setText("Stop");
                    toggleAnimation(animationIsRunning);
                } else {
                    view.getBtnGo().setText("Charge!");
                    view.getParticleCountSlider().setDisable(false);
                    view.clearCanvas();
                    toggleAnimation(animationIsRunning);
                }
            }
        });

        view.getCheckBoxBorders().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                model.setBordersClosed(view.getCheckBoxBorders().isSelected());
            }
        });

        view.getToggleLines().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                model.setShowLines(view.getToggleLines().isSelected());
            }
        });

        view.getCheckBoxGraviton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                model.setHasGraviton(view.getCheckBoxGraviton().isSelected());
            }
        });

        view.getCanvas().setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (model.hasGraviton()) {
                    if ((event.getX() - model.getGraviton().getLocation().getX() <= model.getGraviton().getRadius() + 20)
                            && (event.getY() - model.getGraviton().getLocation().getY()) <= model.getGraviton().getRadius() + 20) {
                        model.getGraviton().getLocation().setX((float) event.getX() - model.getGraviton().getRadius() / 2);
                        model.getGraviton().getLocation().setY((float) event.getY() - model.getGraviton().getRadius() / 2);
                    }
                }
            }
        });

        view.getToggleGroup().selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                model.setSquareAttration(!model.getSquareAttraction());
            }
        });
    }

    private void toggleAnimation(boolean bool) {
        if (bool) {
            animationLoop.play();
        } else {
            animationLoop.stop();
        }
    }
}
