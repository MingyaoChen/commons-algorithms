package tree.kdim;

import tree.BinaryNode;

/**
 * Created by mingyao on 2/13/17.
 */
public class KDNode extends BinaryNode {

    int split = -1; // 垂直于分割超平面的方向轴序号
    double[] leftRange = new double[2]; // 该节点所代表的空间范围
    double[] rightRange = new double[2];

    KDNode(double[] obj) {
        super(obj);
    }

    KDNode() {
    }

    public int getSplit() {
        return split;
    }

    public void setSplit(int split) {
        this.split = split;
    }

    public double getLeftRangeStart() {
        return leftRange[0];
    }

    public double getLeftRangeEnd() {
        return leftRange[1];
    }

    public void setLeftRangeStart(double start) {
        this.leftRange[0] = start;
    }

    public void setLeftRangeEnd(double end) {
        this.leftRange[1] = end;
    }

    public double getRightRangeStart() {
        return rightRange[0];
    }

    public double getRightRangeEnd() {
        return rightRange[1];
    }

    public void setRightRangeStart(double start) {
        this.rightRange[0] = start;
    }

    public void setRightRangeEnd(double end) {
        this.rightRange[1] = end;
    }
}
