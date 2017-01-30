package tree.frequency;

/**
 * Created by mingyao on 1/26/17.
 */
public class TermFrequency implements Comparable<TermFrequency> {

    private String term;
    private int frequency;

    public TermFrequency(String term) {
        this.term = term;
        this.frequency = 1;
    }

    public TermFrequency(String term, int frequency) {
        this.term = term;
        this.frequency = frequency;
    }

    public void increase() {
        this.frequency++;
    }

    public int getFrequency() {
        return frequency;
    }

    @Override
    public String toString() {
        return term + " " + frequency;
    }

    @Override
    public int compareTo(TermFrequency o) {
        if (this.frequency < o.frequency) return -1;
        else if (this.frequency == o.frequency) return 0;
        else return 1;
    }
}
