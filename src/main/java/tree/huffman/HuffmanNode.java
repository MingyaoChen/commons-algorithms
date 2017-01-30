package tree.huffman;


/**
 * Created by mingyao on 1/24/17.
 */
public class HuffmanNode implements Comparable<HuffmanNode> {

    long frequency;
    char value;
    HuffmanNode leftChild;
    HuffmanNode rightChild;
    public HuffmanNode(long frequency, char value) {
        this.frequency = frequency;
        this.value = value;

    }
    public HuffmanNode(long frequency, char value, HuffmanNode leftChild, HuffmanNode rightChild) {
        this.frequency = frequency;
        this.value = value;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    @Override
    public int compareTo(HuffmanNode o) {
        if(this.frequency < o.frequency) return -1;
        else if(this.frequency == o.frequency) return 0;
        else return 1;
    }

    @Override
    public String toString() {
        String format = "Node : " + value + " ; Frequency: " + frequency;
        return format;
    }
}
