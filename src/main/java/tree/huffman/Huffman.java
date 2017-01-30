package tree.huffman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 *  Huffman树为完全二叉树
 *  Created by mingyao on 1/24/17.
 */
public class Huffman {
    private HuffmanNode root;
    private Queue<HuffmanNode> forest = new PriorityQueue<>();
    static Logger logger = LoggerFactory.getLogger(Huffman.class);

    /**
     * 构造Huffman树，通过优先队列存储森林
     *
     * @param a
     * @param size
     */
    public void create(HuffmanNode a[], int size) {
        for (int i = 0; i < size; i++) {
            forest.add(a[i]);
        }
        for (int i = 0; i < size - 1; i++) {
            HuffmanNode left = forest.poll();
            HuffmanNode right = forest.poll();
            HuffmanNode node = new HuffmanNode(left.frequency + right.frequency, '0', left, right);
            forest.add(node);
        }
        root = forest.poll();
    }

    /**
     * 结构化输出huffman树
     */
    public void print() {
        if (root == null) {
            logger.error("Huffman tree not initialized");
            new Exception();
        }
        print(root, "    ");
    }

    private void print(HuffmanNode pnode, String indent) {
        if (pnode != null) {
            System.out.println(indent + pnode);
            print(pnode.leftChild, indent + "    ");
            print(pnode.rightChild, indent + "    ");
        }

    }

    public void preOrder() {
        preOrder(root);
    }

    private void preOrder(HuffmanNode pnode) {
        if (pnode != null) {
            logger.info("" + pnode.value);
            preOrder(pnode.leftChild);
            preOrder(pnode.rightChild);
        }
    }

    public void inOrder() {
        inOrder(root);
    }

    private void inOrder(HuffmanNode pnode) {
        if (pnode != null) {
            preOrder(pnode.leftChild);
            logger.info("" + pnode.value);
            preOrder(pnode.rightChild);
        }
    }

    public void postOrder() {
        postOrder(root);
    }

    private void postOrder(HuffmanNode pnode) {
        if (pnode != null) {
            preOrder(pnode.leftChild);
            preOrder(pnode.rightChild);
            logger.info("" + pnode.value);
        }
    }

//    public static void main(String args[]) {
//        Huffman huff = new Huffman();
//        int a[] = {10, 20, 30, 40};
//        char v[] = {'中', '国', '人', '民'};
//        HuffmanNode[] nodes = new HuffmanNode[4];
//        for (int i = 0; i < nodes.length; i++) {
//            HuffmanNode node = new HuffmanNode(a[i], v[i]);
//            nodes[i] = node;
//        }
//        huff.create(nodes, 4);
//        huff.print();
//    }
}
