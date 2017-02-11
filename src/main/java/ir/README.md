#网页信息提取

###正文提取算法
- [CETR - Content Extraction via Tag Ratios](https://pdfs.semanticscholar.org/9049/a8326b42f6902a6e33da595b1bd27cc9d586.pdf)
```
例子：

// 字符串数组 (每一行为一行的html标签)
String[] arrs = ...

// 提取正文内容
List<String> main = ContentExtractorTagRatio.compute(arrs);
```

- ChildTagCount: 根据最多一阶子节点获取列表正文
```
例子:
String tagStr = ChildTagCount.udf(String html)
```