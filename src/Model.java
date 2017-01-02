import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Hermann Grieder on 28.09.2016.
 * <p>
 * Creates the particles. Calculates and updates the location
 * of each particle for each cycle of the Timeline.
 */
public class Model {

    private ArrayList<Particle> particles;
    private boolean closedBorders;
    private boolean showLines;
    private boolean hasGraviton;
    private Particle graviton;
    private boolean squareAttration = true;


    public Model() {
        particles = new ArrayList<>();
        hasGraviton = false;
    }

    public ArrayList<Particle> createParticles(double value) {

        Random random = new Random();

        graviton = new Particle(Main.WIDTH / 2, Main.HEIGHT / 2, 20, 20);

        for (int i = 0; i < value; i++) {
            Particle p = new Particle(random.nextInt(800) + 1, random.nextInt(500) + 1, random.nextInt(2), 10, random.nextInt(10)+1);
            particles.add(p);
        }
        return particles;
    }

    public void update() {

        for (Particle p1 : particles) {
            if (closedBorders) p1.bounce();
            if (!closedBorders) p1.passThroughWalls();
            p1.setShowLines(showLines);
            if (hasGraviton) {
                Vector2d dir = Vector2d.sub(graviton.getLocation(), p1.getLocation());
                float d = dir.mag();
                d = constrain(d, 5, 10);
                if (squareAttration) d = d*d;
                dir.normalize();
                dir.mult(graviton.getGravity() / d);
                p1.addForce(dir);
            }

            for (Particle p2 : particles) {
                if (p1 != p2) {
                    Vector2d dir = Vector2d.sub(p2.getLocation(), p1.getLocation());
                    float d = dir.mag();
                    d = constrain(d, 5, 10);
                    if (squareAttration) d = d*d;
                    dir.normalize();
                    int gravity = p2.getGravity();
                    if (p1.getCharge() == p2.getCharge()) {
                        gravity = -1 * gravity;
                    }
                    dir.mult(gravity / d);
                    p1.addForce(dir);
                }
            }
            p1.update();
        }
    }

    private float constrain(float d, int mindistance, int maxdistance) {
        if (d < mindistance) {
            return mindistance;
        }
        if (d > maxdistance) {
            return maxdistance;
        }
        return d;
    }

    public ArrayList<Particle> getParticles() {
        return particles;
    }

    public void setBordersClosed(boolean closedBorders) {
        this.closedBorders = closedBorders;
    }

    public void clearAcceleration() {
        for (Particle p : particles)
            p.getAcceleration().mult(0);
    }

    public void setShowLines(boolean showLines) {
        this.showLines = showLines;
    }

    public void setHasGraviton(boolean hasGraviton) {
        this.hasGraviton = hasGraviton;
    }

    public Particle getGraviton() {
        return graviton;
    }

    public boolean hasGraviton() {
        return hasGraviton;
    }

    public void setSquareAttration(boolean isSquareAttraction){
        this.squareAttration = isSquareAttraction;
    }

    public boolean getSquareAttraction(){
        return squareAttration;
    }
}
