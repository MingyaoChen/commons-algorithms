package ir.cetr.clustering;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mingyao on 1/18/17.
 */
public class KMeans {
    private int k = 3;
    private int numPoints = 15;
    private int iteration = 100;
    private List<Point> points;
    private List<Cluster> clusters;
    final static Logger logger = LoggerFactory.getLogger(KMeans.class);
    public KMeans(int k, int iteration, List<Point> points) {
        if ( k < 2) {
            logger.error("number of cluster must bigger than 1");
            new Exception();
        }
        this.k = k;
        this.points = points;
        this.iteration = iteration;
        this.numPoints = points.size();
        this.clusters = new ArrayList<>();
        this.init();
    }

    /**
     * Randomly generate k centroids
     */
    private void init() {
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());

        Cluster unchangedCluster = new Cluster(0);
        unchangedCluster.setCentroid(new Point(0.0, 0.0));
        clusters.add(unchangedCluster);

        for (int i = 1; i < k; i ++) {
            Cluster cluster = new Cluster(i);
            Point centroid = points.get(rand.nextInt(points.size()));
            cluster.setCentroid(new Point(centroid.getX(), centroid.getY()));
            clusters.add(cluster);
        }
    }

    public void compute() {
        boolean finished = false;
        int iteration = 0;
        while(! finished) {
            clearCluster();
            assignCluster();
            calculateCentroids();
            iteration ++;

            // calculate the average distance of each point to its cluster
            logger.info("iteration :" + iteration);
            for (Cluster cluster: clusters) {
                Point centroid = cluster.getCentroid();
                int numPoints = cluster.points.size();
                double sumDistance = 0.0;
                for (Point point : cluster.points) {
                    sumDistance += Point.distance(point, centroid);
                }
                double averageDistance = sumDistance / numPoints;
                logger.info("   cluster :" + cluster.getId() + "; num points: " + numPoints + "; average_distance: " + averageDistance + ";");
            }
            if (iteration > this.iteration) {
                finished = true;
                for (Point point: points) {
                    if (point.getCluster() == 0 ) continue;
                    System.out.println(point.getTag());
                }
            }
        }
    }

    private void clearCluster() {
        for (Cluster cluster: clusters) {
            cluster.clear();
        }
    }

    private List<Point> getCentroids() {
        List centroids = new ArrayList(k);
        for(Cluster cluster : clusters) {
            Point aux = cluster.getCentroid();
            Point point = new Point(aux.getX(),aux.getY());
            centroids.add(point);
        }
        return centroids;
    }

    private void assignCluster() {
        double max = Double.MAX_VALUE;
        double min = max;
        int cluster = 0;
        double distance = 0.0;

        for(Point point : points) {
            min = max;
            for(int i = 0; i < k; i++) {
                Cluster c = clusters.get(i);
                distance = Point.distance(point, c.getCentroid());
                if(distance < min){
                    min = distance;
                    cluster = i;
                }
            }
            point.setCluster(cluster);
            clusters.get(cluster).addPoint(point);
        }
    }

    private void calculateCentroids() {
        for (Cluster cluster : clusters) {
            if (cluster.getId() == 0) continue;
            double sumX = 0;
            double sumY = 0;
            List list = cluster.getPoints();
            int n_points = list.size();

            for (Point point : (List<Point>)list) {
                sumX += point.getX();
                sumY += point.getY();
            }

            Point centroid = cluster.getCentroid();
            if ( n_points > 0){
                double newX = sumX / n_points;
                double newY = sumY / n_points;
                centroid.setX(newX);
                centroid.setY(newY);
            }
        }
    }
}
