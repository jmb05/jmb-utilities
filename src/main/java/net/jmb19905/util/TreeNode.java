package net.jmb19905.util;

import java.util.ArrayList;
import java.util.List;

public class TreeNode<T> {

    private T data;

    private final List<TreeNode<T>> children = new ArrayList<>();

    private TreeNode<T> parent = null;

    public TreeNode(T data) {
        this.data = data;
    }

    public TreeNode<T> addChild(TreeNode<T> child) {
        child.setParent(this);
        this.children.add(child);
        return child;
    }

    public void addChildren(List<TreeNode<T>> children) {
        children.forEach(each -> each.setParent(this));
        this.children.addAll(children);
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }

    public List<TreeNode<T>> getChildren() {
        return children;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private void setParent(TreeNode<T> parent) {
        this.parent = parent;
    }

    public TreeNode<T> getParent() {
        return parent;
    }
}