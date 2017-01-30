package tree.trie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mingyao on 1/26/17.
 */
public class TrieNode<T> {
    private char key;
    private T value;
    private List<TrieNode> children;
    private NodeStatus status;

    public TrieNode(char key) {
        this.key = key;
        this.children = new ArrayList<>();
        this.value = null;
        this.status = NodeStatus.NOT_WORD_1;
    }

    public TrieNode(char key, T value, NodeStatus status) {
        this.key = key;
        this.value = value;
        this.status = status;
        this.children = new ArrayList<>();
    }

    /**
     *  检查当前节点是否存在，更新节点状态
     * @param child
     * @return
     */
    public boolean addChild(TrieNode<T> child) {
        int index = -1;
        boolean isAdd = false;

        // check if node already exists
        for (int i = 0; i < children.size(); i ++) {
            if (children.get(i).key == child.key) {
                index = i;
                break;
            }
        }

        if (index != -1) { // if exists, update node status
            TrieNode<T> current = children.get(index);
            if (child.status == NodeStatus.NOT_WORD_1) {
                if (current.status == NodeStatus.WORD_END_3) {
                    current.status = NodeStatus.WORD_MIDDLE_2;
                }
            }
            else if (child.status == NodeStatus.WORD_END_3) {
                if (current.status != NodeStatus.WORD_END_3) {
                    current.status = NodeStatus.WORD_MIDDLE_2;
                    if (current.value == null)
                        isAdd = true;
                    current.value = child.value;
                }
            }
            children.set(index, current);
        } else {// if not exists, add to collection straight ahead
            this.children.add(child);
            isAdd = true;
        }
        return isAdd;
    }

    public T getValue() {
        return value;
    }

    public List<TrieNode> getChildren() {
        return children;
    }

    public NodeStatus getStatus() {
        return status;
    }

    /**
     *  根据节点的字符获取子节点
     * @param key
     * @return
     */
    public TrieNode<T> getChild(char key) {
        int childSize = children.size();
        for (int i = 0; i < childSize; i ++) {
            if (children.get(i).key == key)
                return children.get(i);
        }
        return null;
    }

    @Override
    public String toString() {
        if(status != NodeStatus.NOT_WORD_1)
            return key + " - " + status + " - " + value.toString();
        else
            return key + " - " + status;
    }
}
