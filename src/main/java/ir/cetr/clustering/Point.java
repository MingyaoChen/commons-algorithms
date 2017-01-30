package ir.cetr.clustering;

import ir.cetr.Pair;

/**
 * Created by mingyao on 1/18/17.
 */



public class Point extends Pair {
    private int cluster_number = 0;
    private String tag = "";
    private String clearFilter = "<.*?>";

    public Point(double x, double y) {
        this.setX(x);
        this.setY(y);
    }
    public Point(String tag, double x, double y) {
        this.setTag(tag);
        this.setX(x);
        this.setY(y);
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setCluster(int n) {
        this.cluster_number = n;
    }

    public int getCluster() {
        return this.cluster_number;
    }

    //Calculates the distance between two points.
    protected static double distance(Point p, Point centroid) {
        return Math.sqrt(Math.pow((centroid.getY() - p.getY()), 2) + Math.pow((centroid.getX() - p.getX()), 2));
    }

    public String toString() {
//        return "("  + tag.replaceAll(clearFilter, "") + "\t"+ x + "\t" + y + ")";
        return tag.replaceAll(clearFilter, "");
    }
}