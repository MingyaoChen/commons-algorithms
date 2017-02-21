package tree.kdim;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.VectorUtility;

import java.util.*;

/**
 * 基于KD tree的最近邻向量计算
 * Created by mingyao on 2/13/17.
 */
public class NearestVector {

    static Logger logger = LoggerFactory.getLogger(NearestVector.class);
    KDTree kdTree;
    Stack<KDNode> searchPath = new Stack<>();
    int dim = -1;
    double minDis = Double.MAX_VALUE;

    public NearestVector(List<double[]> data, int dim) {
        kdTree = new KDTree(data, dim);
        this.dim = dim;
    }

    private void initSearchPath(double[] vec) {

        KDNode root = kdTree.getRoot();

        KDNode tmp = root;
        while (tmp != null) {
            searchPath.push(tmp);
            int dim = tmp.getSplit();
            if (dim == -1) {
                break;
            }
            double[] nodeValue = (double[]) tmp.getObj();
            double pivotValue = nodeValue[dim];
            if (vec[dim] >= pivotValue)
                tmp = (KDNode) tmp.getRight();
            else
                tmp = (KDNode) tmp.getLeft();
        }
    }

    private double[] backTracking(double[] vec) {
        KDNode node;
        double[] nearst = new double[vec.length];
        while (!searchPath.isEmpty()) {
            node = searchPath.pop();
            double[] current = (double[]) node.getObj();
            double dis = VectorUtility.euclid(vec, current);
            if (dis < minDis) {
                nearst = current;
                minDis = dis;
            }
            if (node.getLeft() != null && node.getRight() != null) {
                int split = node.getSplit();
                double[] obj = (double[]) node.getObj();
                if (Math.abs(obj[split] - vec[split]) < dis) {
                    KDNode sib;
                    if (vec[split] >= obj[split])
                        sib = (KDNode) node.getLeft();
                    else
                        sib = (KDNode) node.getRight();
                    double sibDis = VectorUtility.euclid(vec, (double[]) sib.getObj());
                    if (sibDis < minDis) {
                        nearst = (double[]) sib.getObj();
                        minDis = sibDis;
                    }
                }
            }
        }
        return nearst;
    }

    public double[] top(double[] vec) {
        if (vec.length != dim) {
            logger.error("vector dim must be the same");
            new Exception();
            System.exit(-1);
        }
        initSearchPath(vec);
        return backTracking(vec);
    }

    public double getDistance() {
        if (minDis == Double.MAX_VALUE) {
            logger.warn("Not calculated yet, bro !! please run top function first");
        }
        return minDis;
    }

    /**
     * 测试专用
     *
     * @param args
     * @throws Exception
     */
//    public static void main(String... args) throws Exception {
//        String path = System.getProperty("user.dir") + "/src/main/java/tree/kdim/disease_topic.out";
//        FileInputStream fis = new FileInputStream(path);
//        InputStreamReader isr = new InputStreamReader(fis, "UTF8");
//        BufferedReader br = new BufferedReader(isr);
//        String line = "";
//        Map<String, double[]> data = new HashMap<>();
//        while ((line = br.readLine()) != null) {
//            String[] items = line.split("\\t");
//            String[] sss = items[1].substring(1, items[1].length() - 1).replaceAll("\\([0-9]+,", "").replaceAll("\\)", "").trim().split(",");
//            double[] aaa = new double[sss.length];
//            for (int i = 0; i < 0; i++) {
//                aaa[i] = Double.parseDouble(sss[i].trim());
//            }
//            data.put(items[0], aaa);
//        }
//        List<double[]> data2 = new ArrayList(data.values());
//        System.out.println("data size: " + data2.size());
//
//        long start = System.currentTimeMillis();
//        for (double[] i : data2) {
//            List<double[]> tmp = new ArrayList<>(data2);
//            tmp.remove(i);
//            NearestVector nv = new NearestVector(tmp, 20);
//            double[] result = nv.top(i);
//            System.out.println(nv.getDistance());
//        }
//        long end = System.currentTimeMillis();
//        System.out.println("dis calculate time usage: " + (end - start));

//        long start = System.currentTimeMillis();
//        for (double[] i : data2) {
//            List<double[]> tmp = new ArrayList<>(data2);
//            tmp.remove(i);
//            double maxSim = Double.MIN_VALUE;
//            for (double[] j : tmp) {
//                double sim = VectorUtility.cosine(j, i);
//                if (sim > maxSim) maxSim = sim;
//            }
//            System.out.println(maxSim);
//        }
//        long end = System.currentTimeMillis();
//        System.out.println("dis calculate time usage: " + (end - start));

//    }
}
