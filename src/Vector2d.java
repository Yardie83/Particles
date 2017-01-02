/**
 * Created by Hermann Grieder on 28.09.2016.
 * <p>
 * A two-dimensional spacial vector with double values.
 * Consists of x and y values and a magnitude.
 * Provides needed vector math methods such as add,
 * the dot product etc.
 */
public class Vector2d {

    private float x;
    private float y;
    private float magnitude;

    public Vector2d(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2d(Vector2d v) {
        this.x = v.getX();
        this.y = v.getY();
    }

    public void add(Vector2d v) {
        this.x += v.getX();
        this.y += v.getY();
    }

    public void sub(Vector2d v) {

        this.x -= v.getX();
        this.y -= v.getY();
    }

    public static Vector2d sub(Vector2d v1, Vector2d v2){
        return new Vector2d(v1.getX()-v2.getX(),v1.getY()-v2.getY());
    }

    public Vector2d mult(float scalar) {
        this.x = scalar * this.x;
        this.y = scalar * this.y;
        return this;
    }

    public Vector2d div(float scalar) {
        this.x = this.x / scalar;
        this.y = this.x / scalar;

        return this;
    }

    public float mag() {
        this.magnitude = (float)Math.sqrt((x * x) + (y * y));
        return this.magnitude;
    }

    public float magSq() {
        return (x * x + y * y);
    }

    public void normalize() {
        mag();
        this.x = this.x / magnitude;
        this.y = this.y / magnitude;
    }

    public void setMag(float scalar) {
        normalize();
        mult(scalar);
    }

    public void speedLimit(float max) {

        if (magSq() > max * max) {
            normalize();
            mult(max);
        }
    }

    public Vector2d copy() {
        return new Vector2d(this.x, this.y);
    }


    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
