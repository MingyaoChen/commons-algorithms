package dynamicProgramming;

/**
 * Created by mingyao on 1/21/17.
 */
public class EditDistance {
    /**
     *  O(mn)
     * @param a
     * @param b
     * @return
     */
    private int edit_distance(char[] a, char[] b) {
        // let b as the short one;
        char[] tmp;
        if (a.length < b.length) {
            tmp = b;
            b = a;
            a = tmp;
        }

        int lenA = a.length;
        int lenB = b.length;
        int i,j, old, temp;
        int[] m = new int[lenB + 1];
        for( j= 0; j <= b.length; j ++) {
            m[j] = j;
        }

        for (i = 1; i <= lenA; i++) {
            old = i - 1;
            m[0] = i;
            for (j = 1; j <= lenB; j++) {
                temp = m[j];
                if (a[i-1] == b[j-1]) {
                    m[j] = old;
                } else {
                    m[j] = Math.min(m[j], Math.min(m[j-1], old)) + 1;
                }
                old = temp;
            }
        }
        return m[lenB];
    }

    public static int compute(char[] a, char[] b) {
        EditDistance ed = new EditDistance();
        return ed.edit_distance(a, b);
    }

//    public static void main(String args[]) {
////        char[] a = "1933 年，东渡日本 19 岁的吴清源迎战当时的日本棋坛霸主、已经 60 岁的本因坊秀哉，开局三招即是日本人从未见过的三三、星、天元布阵，快速进击逼得对方连连暂停“打卦”和弟子商量应对之策。随后以“新布局”开创棋坛新纪元。难道阿尔法狗会再造一个“新新布局”？".toCharArray();
////        char[] b = "虚竹在天龙八部里自填一子，无意中以“自杀”破解“珍笼”棋局，逍遥子方才亲传掌门之位。难道以后“阿尔法狗”要出任逍遥派掌门了？".toCharArray();
//        char[] a = "直肠脱垂".toCharArray();
//        char[] b = "直肠粘膜脱垂".toCharArray();
//        long startTime = System.currentTimeMillis();
//        System.out.println(EditDistance.compute(a, b));
//        long endTime = System.currentTimeMillis();
//        System.out.println(endTime - startTime);
//    }
}
