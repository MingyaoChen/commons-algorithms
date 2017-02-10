package utility;

import no.uib.cipr.matrix.Matrix;
import no.uib.cipr.matrix.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mingyao on 2/9/17.
 */
public class VectorUtility {

    /**
     * 结构化打印 Matrix
     *
     * @param lsm
     */
    public static void printMat(Matrix lsm) {
        int numCols = lsm.numColumns();
        int numRows = lsm.numRows();

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                System.out.print(lsm.get(i, j) + ", ");
            }
            System.out.println();
        }
    }

    public static void printMat(double[][] mat) {
        int numRows = mat.length;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                System.out.print(mat[i][j] + ", ");
            }
            System.out.println();
        }
    }

    public static void printVec(Vector idf) {
        int numCols = idf.size();

        for (int i = 0; i < numCols; i++) {
            System.out.print(idf.get(i) + ", ");
        }
    }
    public static void printVec(double[] idf) {
        int numCols = idf.length;

        for (int i = 0; i < numCols; i++) {
            System.out.print(idf[i] + ", ");
        }
    }

    /**
     * Cosine 相似度
     *
     * @param a
     * @param b
     * @return
     */
    private static double cosine(double[] a, double[] b) {
        if (a.length != b.length) new Exception();
        double result = 0.0;
        double up = 0.0;
        double left = 0.0;
        double right = 0.0;
        for (int i = 0; i < a.length; i++) {
            up += a[i] * b[i];
            left += a[i] * a[i];
            right += b[i] * b[i];
        }
        return up / (Math.sqrt(left) * Math.sqrt(right));
    }

    public static List<Integer> intArray2List(int[] ints) {
        List<Integer> intList = new ArrayList<Integer>();
        for (int index = 0; index < ints.length; index++)
        {
            intList.add(ints[index]);
        }
        return intList;
    }


}
