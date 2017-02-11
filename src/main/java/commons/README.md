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