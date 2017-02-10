package nlp.bow;

import nlp.Corpus;
import no.uib.cipr.matrix.sparse.LinkedSparseMatrix;
import no.uib.cipr.matrix.sparse.SparseVector;
import seg.ikanalyzer.core.Seg;
import utility.VectorUtility;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mingyao on 2/8/17.
 */
public class TFIDF extends CountVectors {

    protected LinkedSparseMatrix tfidf = null;
    protected SparseVector idfvec = null;

    public TFIDF(Corpus corpus) {
        super(corpus);
        this.compute();
    }

    private void compute() {
        idfvec = this.idf(this.cv);
        int numRows = cv.numRows();
        int numColumns = cv.numColumns();

        tfidf = new LinkedSparseMatrix(numRows, numColumns);

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                tfidf.set(i, j, cv.get(i, j) * idfvec.get(j));
            }
        }
    }

    /**
     * 文档集每一个词的 IDF 值
     * 形成IDF向量
     *
     * @param
     * @return 文档数量 x 词典大小 的二维数组
     */

    public SparseVector idf(LinkedSparseMatrix wordCount) {
        int numRows = wordCount.numRows();
        int numColumns = wordCount.numColumns();

        SparseVector accumulated = new SparseVector(numColumns);
        for (int i = 0; i < numColumns; i++) {
            double sum = 0.0;
            for (int j = 0; j < numRows; j++) {
                if (wordCount.get(j, i) != 0)
                    sum += 1;
            }
            accumulated.set(i, Math.log((numRows + 1) / (sum + 1)));
        }
        return accumulated;
    }

    public LinkedSparseMatrix getTFIDF() {
        if (tfidf == null) new Exception();
        return tfidf;
    }

    public SparseVector getIDF() {
        if (idfvec == null) new Exception();
        return idfvec;
    }

    /**
     * 测试专用
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Corpus corpus = Corpus.getSample();
        TFIDF tfidf = new TFIDF(corpus);
        LinkedSparseMatrix lsm = tfidf.getTFIDF();
        VectorUtility.printMat(lsm);
    }
}
