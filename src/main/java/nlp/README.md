# NLP算法包

### LDA
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

### Document Summary
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
### 文档向量化
- TFIDF
矩阵使用Matrix Toolkit Java包提供数据结构进行存储
[Matrix Toolkit Java](https://github.com/fommil/matrix-toolkits-java)
```
Corpus corpus = Corpus.getSample();
TFIDF tfidf = new TFIDF(corpus);
// 返回值由系数矩阵存储 使用
LinkedSparseMatrix lsm = tfidf.getTFIDF();
VectorUtility.printMat(lsm);
```

### CRF
```

```