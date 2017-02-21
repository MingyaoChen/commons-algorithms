package tree;

/**
 * Created by mingyao on 1/23/17.
 */
public abstract class BinaryNode<T> {
    BinaryNode<T> parent;
    BinaryNode<T> left;
    BinaryNode<T> right;
    T obj;

    public BinaryNode(T obj) {
        this.obj = obj;
    }
    public BinaryNode() {}

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public void setLeft(BinaryNode<T> left) {
        this.left = left;
    }

    public BinaryNode<T> getRight() {
        return right;
    }

    public void setRight(BinaryNode<T> right) {
        this.right = right;
    }

    public BinaryNode<T> getLeft() {
        return left;
    }

//    public BinaryNode<T> getParent() {
//        return parent;
//    }
//
//    public void setParent(BinaryNode<T> parent) {
//        this.parent = parent;
//    }


    @Override
    public String toString() {
        String format = "Node : " + obj;
        return format;
    }


}
