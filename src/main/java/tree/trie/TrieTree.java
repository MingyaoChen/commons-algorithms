package tree.trie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Trie树的大小取决于要统计文本的字符个数
 *  Created by mingyao on 1/26/17.
 */
public class TrieTree<T> {
    TrieNode<T> root = new TrieNode<>(' ');
    int valueSize = 0;
    static Logger logger = LoggerFactory.getLogger(TrieTree.class);

    /**
     * @param keys 字符数组 单标一个单词
     * @param value 单词对应的对象
     */
    public void put(char[] keys, T value) {
        if (keys.length == 0) {
            logger.error("Key should not be empty");
            new NullPointerException();
        }

        TrieNode tmp = root;
        for (int i = 0; i <  keys.length - 1 ; i ++) {
            TrieNode<T> newNode = new TrieNode<T>(keys[i]);
            tmp.addChild(newNode);
            tmp = tmp.getChild(keys[i]);
        }
        if (tmp.addChild(new TrieNode(keys[keys.length - 1], value, NodeStatus.WORD_END_3)))
            this.valueSize ++;
    }

    /**
     * @param keys 字符数组 （单词）
     * @return
     */
    public T get(char[] keys) {
        TrieNode<T> tmp = root;
        for (char k : keys) {
            if (tmp == null)
                return null;
            tmp = tmp.getChild(k);
        }
        if (tmp == null)
            return null;
        else if (tmp.getStatus() == NodeStatus.WORD_END_3 || tmp.getStatus() == NodeStatus.WORD_MIDDLE_2) {
            return tmp.getValue();
        }
        return null;
    }

    public void format_print() {
        print(root, "    ");
    }

    private void print(TrieNode<T> node, String indent) {
        System.out.println(indent + node.toString());
        for (TrieNode<T> tn : node.getChildren()) {
            print(tn, indent + "    ");
        }
    }

//    public static void main(String[] args) {
//        TrieTree<String> trie = new TrieTree<>();
//        trie.put("陈名耀".toCharArray(), "陈名耀");
//        trie.put("陈名耀的女儿".toCharArray(), "陈名耀的女儿");
//        trie.put("陈名瑞".toCharArray(), "陈名瑞");
//        trie.put("老头陈".toCharArray(), "老头陈");
//        trie.format_print();
//    }

}
