package ir.cetr.clustering;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mingyao on 1/18/17.
 */
public class KMeansBuilder {
    private int k = 3;
    private int iteration = 100;
    private List<Point> points = new ArrayList<>();

    public KMeansBuilder setK(int k) {
        this.k = k;
        return this;
    }

    public KMeansBuilder setIteration(int iteration) {
        this.iteration = iteration;
        return this;
    }

    public KMeansBuilder source(List<Point> points) {
        this.points = points;
        return this;
    }
    public KMeansBuilder sourceFile(String fileName) {
        InputStream in = getClass().getClassLoader().getResourceAsStream(fileName);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] items = line.split(";;");
                this.points.add(new Point(items[0], Double.parseDouble(items[1]), Double.parseDouble(items[2])));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public KMeans build() {
        return new KMeans(this.k, this.iteration, this.points);
    }

    public static void main(String args[]) {

        KMeans kmeans = new KMeansBuilder()
                .setK(3)
                .setIteration(10)
                .sourceFile("samplePoints")
                .build();
        kmeans.compute();
    }
}
