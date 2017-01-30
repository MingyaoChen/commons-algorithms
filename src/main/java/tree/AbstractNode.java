package tree;

/**
 * Created by mingyao on 1/23/17.
 */
public abstract class AbstractNode <T>{
    protected AbstractNode<T>[] children;
    protected AbstractNode<T> parent;
    protected T obj;

    protected AbstractNode() {

    }

    public AbstractNode<T> getChild(int index) {
        if (index >= children.length ) new IndexOutOfBoundsException();
        return children[index];
    }

    public AbstractNode<T> getParent() {
        return parent;
    }

    public void setParent(AbstractNode<T> parent) {
        this.parent = parent;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}
