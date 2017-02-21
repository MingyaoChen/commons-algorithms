package tree.kdim;

import commons.QuickSort;
import tree.BinaryNode;
import tree.HeapSort;
import utility.VectorUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * K dimension tree： 用于进行向量匹配，进行快速检索
 * KD tree 适用于维度小于20的向量
 * Created by mingyao on 2/13/17.
 */
public class KDTree {
    static Logger logger = LoggerFactory.getLogger(KDTree.class);
    List<double[]> datasets = new ArrayList<>();
    KDNode root = new KDNode();
    int dim = -1;

    /**
     *  初始化
     * @param datasets 向量数据集
     * @param dim 向量维度
     */
    public KDTree(List<double[]> datasets, int dim) {
        if (dim < 2) {
            logger.error("dimension must larger than 0");
            new Exception();
        }
        for (double[] data : datasets) {
            if (data.length != dim) {
                logger.error("Each vector in datasets must the same and be the same as the input dimension");
                System.exit(-1);
            }
        }
        this.datasets = datasets;
        this.dim = dim;
        this.build();
    }

    private void build(){
        root = buildKDtree(datasets, dim);
    }

    private KDNode buildKDtree(List<double[]> data, int dim) {

        if (data.size() == 1) {
            return new KDNode(data.get(0));
        }

        KDNode node = new KDNode();
        int dataSize = data.size();
        double[] vars = new double[dim];
        double[][] vec4EachDim = new double[dim][dataSize];

        for (int i = 0; i < dataSize; i ++) {
            for ( int j = 0; j < dim; j ++) {
                vec4EachDim[j][i] = data.get(i)[j];
            }
        }

        // 计算方差
        int splitAxis = -1;
        double maxVar = -1.0;
        for (int i = 0; i < dim; i ++) {
            double currentVar = VectorUtility.variance(vec4EachDim[i]);
            if( currentVar > maxVar) {
                splitAxis = i;
                maxVar = currentVar;
            }
        }

        // 所有文档在 split 这个点上的值集合
        double[] valueInThatDim = vec4EachDim[splitAxis];
        List<Double> valueInThatDimList = VectorUtility.doubleArray2List(valueInThatDim);

        QuickSort.sort(valueInThatDim);
        double midValue = valueInThatDim[valueInThatDim.length / 2];
        int midIndex = valueInThatDimList.indexOf(midValue);


        node.setObj(data.get(midIndex));
        node.setSplit(splitAxis);

        // 获取左范围、右范围
        node.setLeftRangeStart(valueInThatDim[0]);
        node.setLeftRangeEnd(midValue);

        node.setRightRangeStart(midValue);
        node.setRightRangeEnd(valueInThatDim[valueInThatDim.length - 1]);

        // 获取左区域值
        data.remove(midIndex);
        List<double[]> leftSub = new ArrayList<>();
        List<double[]> rightSub = new ArrayList<>();
        for (double[] record: data) {
            if (record[splitAxis] >= midValue)
                rightSub.add(record);
            else
                leftSub.add(record);
        }

        if (leftSub.size() != 0)
            node.setLeft(buildKDtree(leftSub, dim));
        if (rightSub.size() != 0)
            node.setRight(buildKDtree(rightSub, dim));
        return node;
    }

    public void formaPrint() {
        printNode(root);
    }

    public KDNode getRoot() {
        return root;
    }

    private void printNode(BinaryNode node) {
        if (node == null)
            return;
        double[] value = (double[])node.getObj();
        String out = "( ";
        for (double v : value) {
            out = out + v + ", ";
        }
        out += ")";
        System.out.println(out);
        printNode(node.getLeft());
        printNode(node.getRight());
    }

    public static void main(String args[]) {
        List<double[]> data = new ArrayList<>();
        double[] tmp = {7.0, 2.0};
        double[] tmp1 = {5.0, 4.0};
        double[] tmp2 = {9.0, 6.0};
        double[] tmp3 = {2.0, 6.0};
        double[] tmp4 = {4.0, 7.0};
        double[] tmp5 = {8.0, 1.0};
        data.add(tmp3);
        data.add(tmp4);
        data.add(tmp1);
        data.add(tmp5);
        data.add(tmp);
        data.add(tmp2);

        KDTree kdTree = new KDTree(data, 2);
        KDNode root = kdTree.getRoot();
        System.out.println(" ");
    }
}
