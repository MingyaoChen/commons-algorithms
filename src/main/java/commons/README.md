# 一些常用的小算法

###该package下包含以下算法

- 字符串编辑距离
- 最长连续子串
- 最长连续子序列

```
// 最长连续子串
String A = "直肠脱垂";
String B = "直肠粘膜脱垂";
LongestCommonSubstring.compute(B, A);

// 最长连续子序列
char[] A = "乳房肿瘤".toCharArray();
char[] B = "乳房恶性肿瘤".toCharArray();
System.out.print(LongestCommonSequence.compute(B, A));

// 最小编辑距离
char[] A = "乳房肿瘤".toCharArray();
char[] B = "乳房恶性肿瘤".toCharArray();
EditDistance.compute(a, b)
```