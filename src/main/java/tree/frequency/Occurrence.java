package tree.frequency;

import java.util.List;

/**
 * Created by mingyao on 1/26/17.
 */
public interface Occurrence {

    public void addTerm(String term);

    public void addAll(List<String> corpus);

    /**
     * return -1 if term not exists in corpus
     * @param term
     * @return
     */
    public int getFrequency(char[] term);
}
