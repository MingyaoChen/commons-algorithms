package nlp.bow;
import nlp.Corpus;
import no.uib.cipr.matrix.sparse.LinkedSparseMatrix;
import no.uib.cipr.matrix.sparse.SparseVector;
import utility.VectorUtility;

import java.util.List;

/**
 * Created by mingyao on 2/9/17.
 */
public class BM25 {

    final static double k1 = 1.5;
    final static double b = 0.75;

    private TFIDF tfidf;
    private Corpus corpus;
    private double avgL = 0.0;
    private LinkedSparseMatrix simMat = null;

    public BM25(Corpus corpus) {
        this.corpus = corpus;
        this.setAvgLength();
    }

    private void setAvgLength() {
        int[][] doc = corpus.getDocument();
        double sumLength = 0.0;
        int docLength = doc.length;
        for ( int i = 0; i < docLength; i ++) {
            sumLength += doc[i].length;
        }
        avgL = sumLength / docLength;
    }

    public LinkedSparseMatrix getSimilarityMatrix() {
        tfidf = new TFIDF(corpus);
        LinkedSparseMatrix cv = tfidf.getCV();
        SparseVector idf = tfidf.getIDF();

        int[][] doc = corpus.getDocument();
        int docLength = doc.length;
        simMat = new LinkedSparseMatrix(docLength, docLength);

        for (int i = 0; i < docLength; i++) {
            for (int j = 0; j < docLength; j++) {
                double value = 0.0;
                List wordList = VectorUtility.intArray2List(doc[j]);
                for (int current : doc[i]) {
                    if (wordList.contains(current)) {
                        double tf = cv.get(j, current);
                        value += idf.get(current) * tf * (k1 + 1) / (tf + (k1 * (1 - b + b * wordList.size() / avgL)));
                    }
                }
                simMat.set(i, j, value);
            }
        }

        return simMat;
    }
}
