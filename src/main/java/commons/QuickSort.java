package commons;

/**
 * Created by mingyao on 2/13/17.
 */
public class QuickSort {
    private QuickSort() {}

    private void quickSort(double[] data, int left, int right) {
        int dp;
        if (left < right) {
            dp = partition(data, left, right);
            quickSort(data, left, dp - 1);
            quickSort(data, dp + 1, right);
        }

    }
    private int partition(double[] n, int left, int right) {
        double pivot = n[left];
        while (left < right) {
            while (left < right && n[right] >= pivot)
                right--;
            if (left < right)
                n[left++] = n[right];
            while (left < right && n[left] <= pivot)
                left++;
            if (left < right)
                n[right--] = n[left];
        }
        n[left] = pivot;
        return left;
    }

    public static void sort(double[] data) {
        QuickSort qs = new QuickSort();
        qs.quickSort(data, 0, data.length - 1);
    }

    /**
     * 测试专用
     * @param args
     */
    public static void main(String ... args) {
        double[] data = { 88.0, 0.2,0.8, 0.55, 0.89, 100.0, 90.0};
        QuickSort.sort(data);
        for (double d : data) {
            System.out.print(d + ", ");
        }
    }
}
