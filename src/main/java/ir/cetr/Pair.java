package ir.cetr;

/**
 * Created by mingyao on 1/18/17.
 */
public class Pair {
    protected double x;
    protected double y;

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String toString() {
        return "("+x+","+y+")";
    }

}
