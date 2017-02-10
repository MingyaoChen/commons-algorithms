package nlp.bow;

import nlp.Corpus;
import no.uib.cipr.matrix.sparse.LinkedSparseMatrix;

/**
 * Created by mingyao on 2/7/17.
 */
public class CountVectors{

    protected LinkedSparseMatrix cv = null;
    protected Corpus corpus = new Corpus();

    public CountVectors(Corpus corpus) {
        this.corpus = corpus;
        this.compute();
    }

    private void compute() {
        int[][] wordIds = corpus.getDocument();
        int numRows = wordIds.length;
        int numCols = corpus.getVocabulary().size();
        cv = new LinkedSparseMatrix(numRows, numCols);
//        cv.zero();

        for (int i = 0; i < wordIds.length; i++) {
            for (int j = 0; j < wordIds[i].length; j++) {
                double current = cv.get(i, wordIds[i][j]);
                cv.set(i, wordIds[i][j], current + 1);
            }
        }
    }

    public LinkedSparseMatrix getCV() {
        return cv;
    }
}
