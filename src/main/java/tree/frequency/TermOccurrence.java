package tree.frequency;

import tree.trie.TrieTree;

import java.util.Arrays;
import java.util.List;

/**
 * Created by mingyao on 1/30/17.
 */
public class TermOccurrence implements Occurrence {

    TrieTree<TermFrequency> termTire = new TrieTree<>();

    @Override
    public void addTerm(String term) {
        TermFrequency tf = termTire.get(term.toCharArray());
        if (tf != null) {
            tf.increase();
            termTire.put(term.toCharArray(), tf);
        } else {
            termTire.put(term.toCharArray(), new TermFrequency(term));
        }
    }

    @Override
    public void addAll(List<String> corpus) {
        for (String term : corpus) {
            addTerm(term);
        }
    }

    @Override
    public int getFrequency(char[] term) {
        TermFrequency tf = termTire.get(term);
        if (tf == null) return -1;
        else return tf.getFrequency();
    }

    public void print() {
        termTire.format_print();
    }

//    public static void main(String[] args) {
//        TermOccurrence ocur = new TermOccurrence();
//        List<String> terms = Arrays.asList("陈名耀", "陈名瑞", "陈名耀", "陈名瑞的儿子", "李沛宇", "李沛");
//        ocur.addAll(terms);
//        ocur.print();
//    }
}
