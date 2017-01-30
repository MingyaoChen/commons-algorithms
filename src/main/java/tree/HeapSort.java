package tree;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mingyao on 10/27/16.
 */
public class HeapSort {

    private int heapSize = 0;
    final Logger logger = LoggerFactory.getLogger(HeapSort.class);

    /**
     * 1. 取出数组的前k个数，创建最小堆
     * 2. 循环数组的剩余元素，如果元素(a)比最小堆的根节点大，将a设置成最小堆的根节点
     *
     * @param array
     * @param k
     * @return
     */
    public double[] get(double[] array, int k) {
        // 检查
        if (array == null) {
            logger.error("Array is empty");
            throw new NullPointerException();
        }
        if (array.length == 0) {
            logger.error("Array is empty");
            throw new NullPointerException();
        }
        if (k > array.length) {
            logger.error("Number of k out of bounds", k);
            throw new ArrayIndexOutOfBoundsException();
        }

        // n维数组建立最小堆
        double[] topk = new double[k];
        for (int i = 0; i < k; i++) {
            topk[i] = array[i];
        }
        heapSize = k;
        this.buildMinHeap(topk);

        for (int i = k; i < array.length; i++) {
            if (array[i] > topk[0]) {
                topk[0] = array[i];
                this.buildMinHeap(topk);
            }

        }
        return topk;
    }

    /**
     * 建立最大堆。在数据中，array.length/2+1一直到最后的元素都是叶子元素，
     * 因此从其前一个元素开始，一直到第一个元素，重复调用maxHeapify函数，使其保持最大堆的性质
     *
     * @param array
     */
    private void buildMinHeap(double[] array) {
        for (int i = array.length / 2; i >= 0; i--) {
            minHeapify(array, i);
        }
    }


    /**
     * 保持最大堆性质
     *
     * @param array
     * @param index
     */
    private void minHeapify(double[] array, int index) {
        int left = getLeftChild(index);
        int right = getRightChild(index);
        int smallest = 0;
        if (left < heapSize && array[index] > array[left]) {
            smallest = left;
        } else {
            smallest = index;
        }
        if (right < heapSize && array[right] < array[smallest]) {
            smallest = right;
        }
        if (smallest == index) {
            return;
        } else {
            this.swap(array, smallest, index);
        }
    }

    private int getLeftChild(int i) {
        return 2 * i + 1;
    }

    private int getRightChild(int i) {
        return 2 * i + 2;
    }

    private void swap(double[] array, int from, int to) {
        double temp = array[from];
        array[from] = array[to];
        array[to] = temp;
    }

    public static void printArray(double[] array) {
        System.out.print("{");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            if (i < array.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("}");
    }

//    public static void main(String args[]) {
////        double[] array = {-2.0, -3.0, -100, 7.0, 8.0, -1.0, 5.0, -3.0, 2.0, 6.0, 100, -2.0, 4.0, -9.0, 3.0, 1.0, 0.0};
//        Random rand = new Random(System.currentTimeMillis());
//        List<Double> array = new ArrayList<Double>();
//        for ( int i = 0; i < 10000; i ++) {
//            array.add(rand.nextDouble() * 500.0);
//        }
//        double[] doubleArray = new double[array.size()];
//        for ( int i = 0; i < doubleArray.length; i ++) {
//            doubleArray[i] = array.get(i);
//        }
//
//        HeapSort topN = new HeapSort();
//        long startTime = System.currentTimeMillis();
//        HeapSort.printArray(topN.get(doubleArray, 10));
//        long endTime = System.currentTimeMillis();
//        System.out.println(endTime - startTime);
//
//    }
}
