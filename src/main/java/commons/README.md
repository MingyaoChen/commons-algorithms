# 一些常用的小算法

###该package下包含以下算法

- 字符串编辑距离
- 最长连续子串
- 最长连续子序列

```
char[] A = "直肠脱垂".toCharArray();
char[] B = "直肠粘膜脱垂".toCharArray();

// 最长连续子串
LongestCommonSubstring.compute(B, A);

// 最长连续子序列
System.out.print(LongestCommonSequence.compute(B, A));

// 最小编辑距离
EditDistance.compute(a, b)
```
- 快速排序
```
double[] data = { 88.0, 0.2,0.8, 0.55, 0.89, 100.0, 90.0}; // 测试数据
QuickSort.sort(data);
for (double d : data) {
    System.out.print(d + ", ");
}
```