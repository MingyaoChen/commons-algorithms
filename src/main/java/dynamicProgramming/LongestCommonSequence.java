package dynamicProgramming;

/**
 * Created by mingyao on 1/22/17.
 */
public class LongestCommonSequence {

    static int compute(char[] a, char[] b) {
        char[] tmp;
        if (a.length < b.length) {
            tmp = b;
            b = a;
            a = tmp;
        }

        int lenB = b.length;
        int lenA = a.length;
        int[] m = new int[lenB + 1];
        int i, j, old, tnmp; // maxBefore 记录上一行中当前index之前最大的计数

        // 对第一行进行初始化
        for (j = 0; j < m.length; j++) {
            m[j] = 0;
        }
//        for ( int xx : m) {
//            System.out.print(xx + " ");
//        }
//        System.out.println();

        for (i = 0; i < lenA; i++) {
            old = m[0];
            m[0] = 0;
            for (j = 1; j <= lenB; j++) {
                if (a[i] == b[j - 1]) {
                    tnmp = m[j];
                    m[j] = Math.max(m[j - 1], Math.max(old + 1, m[j]));
                } else {
                    tnmp = m[j];
                    m[j] = Math.max(m[j - 1], Math.max(old, m[j]));
                }
                old = tnmp;
            }
//            for ( int xx : m) {
//                System.out.print(xx + " ");
//            }
//            System.out.println();
        }

        return m[lenB];
    }

    public static void main(String args[]) {
        char[] A = "乳房肿瘤".toCharArray();
        char[] B = "乳房恶性肿瘤".toCharArray();
        System.out.print(LongestCommonSequence.compute(B, A));
    }
}
