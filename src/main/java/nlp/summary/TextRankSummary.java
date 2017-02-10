package nlp.summary;

import nlp.Corpus;
import nlp.bow.TFIDF;
import no.uib.cipr.matrix.Matrix;
import seg.ikanalyzer.core.Seg;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by mingyao on 2/7/17.
 */
public class TextRankSummary {

    private final static double d = 0.85f;
    private final static int maxIter = 200;
    private final static double min_diff = 0.001;


    private double[][] simMat;
    private double[] prs;
    private List<String> sentences = new ArrayList<>();
    private TreeMap<Double, String> tops = new TreeMap<>(Collections.reverseOrder());

    private TextRankSummary(List<String> sentences, double[][] simMat) {
        this.sentences = sentences;
        this.simMat = simMat;
        this.compute();
    }

    private void compute() {
        int docSize = sentences.size();
        if (simMat.length != simMat[0].length) new Exception();
        if (docSize != simMat.length) new Exception();

        prs = new double[docSize];
        for (int i = 0; i < docSize; i++) {
            prs[i] = 1;
        }

        for (int m = 0; m < maxIter; m++) {
            double[] newprs = new double[docSize];
            for (int i = 0; i < simMat.length; i++) {
                double[] line = simMat[i];
                int excludeIndex = i;
                double value = 0.0;
                double weightSum = 0.0;
                for (int j = 0; j < line.length; j++) {
                    if (j != excludeIndex) {
                        value += line[j] * prs[j];
                        weightSum += line[j];
                    }
                }
                value = weightSum * d + (1 - d);
                newprs[i] = value;
            }
            prs = newprs;
        }
    }

    public List<String> getTopSentences(int size) {
        for (int i = 0; i < prs.length; i++) {
            tops.put(prs[i], sentences.get(i));
        }
        List<String> topSentences = new ArrayList<>();
        Set<Double> values = tops.keySet();
        size = Math.min(values.size(), size);

        Iterator<Double> ite = values.iterator();
        for ( int i = 0; i < size; i ++) {
            double key = ite.next();
            topSentences.add(tops.get(key) + " -> " + key);
        }

        return topSentences;
    }


    public static void main(String args[]) throws Exception {

        Corpus corpus = Corpus.getSample();
        List<String> body = Arrays.asList(Corpus.getSampleData().split("[，。\n]"));

        // 计算Cosine相似度矩阵
        TFIDF tfidf = new TFIDF(corpus);
        Matrix lsm = tfidf.getTFIDF();
        double[][] simMat = computeSimMat(lsm);
        TextRankSummary trs = new TextRankSummary(body, simMat);
        List<String> senteneces = trs.getTopSentences(10);
        for (String sentence : senteneces) {
            System.out.println(sentence);
        }
    }

    static double[][] computeSimMat(Matrix lsm) {
        double[][] simMat = new double[lsm.numRows()][lsm.numRows()];
        int dim = lsm.numColumns();

        for (int i = 0; i < simMat.length; i++) {
            for (int j = 0; j < simMat.length; j++) {

                double top = 0.0;
                double botLeft = 0.0;
                double botRight = 0.0;
                for (int m = 0; m < dim; m++) {
                    top += lsm.get(i, m) * lsm.get(j, m);
                    botLeft += lsm.get(i, m) * lsm.get(i, m);
                    botRight += lsm.get(j, m) * lsm.get(j, m);
                }
                simMat[i][j] = top / Math.sqrt(botLeft) / Math.sqrt(botRight);
            }
        }
        return simMat;
    }
}
