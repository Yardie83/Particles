import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by Hermann Grieder on 28.09.2016.
 * <p>
 * Individual particle to be spawned.
 * Has a charge either positive or negative.
 * Holds position, direction and velocity data.
 */

public class Particle {

    private int gravity;
    private Vector2d location;
    private Vector2d velocity;
    private Vector2d acceleration;
    private int charge;
    private float radius;
    private boolean showLines;
    private boolean isGraviton = false;

    public Particle(float x, float y, int charge, float radius, int gravity) {

        this.charge = charge;
        this.radius = radius;
        this.gravity = gravity;

        location = new Vector2d(x, y);
        velocity = new Vector2d(0, 0);
        acceleration = new Vector2d(0, 0);

        isGraviton = false;
    }

    public Particle(float x, float y, float radius, int gravity) {

        this.radius = radius;
        this.gravity = gravity;
        location = new Vector2d(x, y);
        isGraviton = true;
    }

    public void update() {
        velocity.add(acceleration);
        velocity.speedLimit(7);
        location.add(velocity);
    }

    public void bounce() {
        if (location.getX() <= 0 || location.getX() >= Main.WIDTH) {
            velocity.setX(velocity.getX() * -1);
        }
        if (location.getY() <= 0 || location.getY() >= Main.HEIGHT) {
            velocity.setY(velocity.getY() * -1);
        }
    }

    public void passThroughWalls() {
        if (location.getX() <= 0) location.setX(Main.WIDTH - radius);
        if (location.getY() <= 0) location.setY(Main.HEIGHT - radius);
        if (location.getX() >= Main.WIDTH) location.setX(0);
        if (location.getY() >= Main.HEIGHT) location.setY(0);
    }

    public void display(GraphicsContext gc) {
        String chargeText;
        // Draw the particles
        if (charge == 0) {
            gc.setFill(Color.BLUE);
             chargeText = String.valueOf(this.getGravity());
        } else {
            gc.setFill(Color.RED);
             chargeText = "-" + String.valueOf(this.getGravity());
        }

        if (isGraviton) {
            gc.setFill(Color.BLACK);
            gc.fillText("You can drag the Graviton. Try it!", 20, Main.HEIGHT-30);
        }

        gc.fillOval(location.getX(), location.getY(), radius, radius);

        if (!isGraviton) {
            Vector2d direction = velocity.copy();
            direction.setMag(10);
            if (showLines) {
                gc.setStroke(Color.BLACK);
                gc.strokeLine(
                        location.getX() + (radius / 2),
                        location.getY() + (radius / 2),
                        location.getX() + direction.getX() + (radius / 2),
                        location.getY() + direction.getY() + (radius / 2));

                gc.setStroke(Color.GREEN);
                gc.strokeLine(
                        location.getX() + (radius / 2),
                        location.getY() + (radius / 2),
                        location.getX() + (50 * acceleration.getX()) + (radius / 2),
                        location.getY() + (50 * acceleration.getY()) + (radius / 2));

                gc.setFill(Color.BLACK);
                gc.fillText(chargeText,
                        location.getX(),
                        location.getY());

            }
        }
    }

    public Vector2d getLocation() {
        return location;
    }

    public int getCharge() {
        return charge;
    }

    public void addForce(Vector2d force) {
        this.acceleration.add(force);
    }

    public Vector2d getAcceleration() {
        return acceleration;
    }


    public void setShowLines(boolean showLines) {
        this.showLines = showLines;
    }

    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public float getRadius() {
        return radius;
    }
}
