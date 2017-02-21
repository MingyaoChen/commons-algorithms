######如果。。。。
######你想使用依存句法分析。。。。
######推荐 [LTP](http://www.ltp-cloud.com/)

# commons-algorithms

### package : Commons
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

### package : IR
- 正文提取算法 [CETR - Content Extraction via Tag Ratios](https://pdfs.semanticscholar.org/9049/a8326b42f6902a6e33da595b1bd27cc9d586.pdf)
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
### package : NLP
NLP算法包

#### LDA
```
// 1. 载入语料
Corpus corpus = Corpus.load("data/mini");
// 2. 初始化
LdaGibbsSampler ldaGibbsSampler = new LdaGibbsSampler(corpus.getDocument(), corpus.getVocabularySize());
// 3. 训练
ldaGibbsSampler.gibbs(10);
// 4. 获得训练后相关数据
double[][] phi = ldaGibbsSampler.getPhi();
Map<String, Double>[] topicMap = LdaUtil.translate(phi, corpus.getVocabulary(), 10);
LdaUtil.explain(topicMap); 
```

#### Document Summary
- 基于TextRank的文档摘要提取, 并提供文档转向量化算法 LDA, TF-IDF, BM25

```
// 1. 初始化TextRank
// 语句列表 -> body : List<String>
// 文档相似度矩阵 -> simMat: double[][]
TextRankSummary trs = new TextRankSummary(body, simMat);

// 返回值组成 "语句" -> 权值
List<String> senteneces = trs.getTopSentences(10);
for (String sentence : senteneces) {
    System.out.println(sentence);
}

```
- 基于LSTM的文本摘要提取 基于论文: [A Neural Attention Model for Abstractive Sentence Summarization](https://arxiv.org/abs/1509.00685)
```
待续
```

#### 文档向量化
- TFIDF
矩阵使用
[Matrix Toolkit Java](https://github.com/fommil/matrix-toolkits-java)包提供数据结构进行存储
```
Corpus corpus = Corpus.getSample();
TFIDF tfidf = new TFIDF(corpus);
// 返回值由系数矩阵存储 使用
LinkedSparseMatrix lsm = tfidf.getTFIDF();
VectorUtility.printMat(lsm);
```

#### CRF
```
待续
```

### package : Tree
- 基于最小堆的topN
- Huffman树 + Huffman编码 (暂时未完成)
- 字典树 + 基于字典树的词频统计
- KD 树 用于查找最邻近字符串

### package : Seg
- IK分词
