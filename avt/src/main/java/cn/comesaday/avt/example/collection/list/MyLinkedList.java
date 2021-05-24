package cn.comesaday.avt.example.collection.list;

import java.io.Serializable;

/**
 * <描述> MyLinkedList
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-05-24 14:15
 */
public class MyLinkedList<E> implements ListInterface<E>, Serializable {

    private int size = 0;

    transient Node<E> first;

    transient Node<E> last;

    public MyLinkedList() {

    }

    static class Node<E> {

        E element;

        Node<E> next;

        Node<E> prev;

        public Node(E element, Node<E> next, Node<E> prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }

        public Node(E element) {
            this.element = element;
        }

        public boolean hasNext() {
            return this.next != null;
        }
    }

    @Override
    public boolean exist(int index) {
        if (index < 0 || index > this.size) {
            return false;
        }
        return true;
    }

    @Override
    public void add(E element) {
        Node<E> node = new Node<>(element, null, this.last);
        if (null == this.first) {
            this.first = this.last = node;
        } else {
            this.last.next = node;
            this.last = node;
        }
        this.size++;
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public void delete(int index) {

    }

    @Override
    public void delete(E element) {

    }

    @Override
    public E get(int index) {
        if (index < 0 || index > this.size - 1) {
            throw new IllegalArgumentException("out of linkedlist");
        }
        if (null == this.first) {
            return null;
        }
        int in = 0;
        Node<E> node = this.first;
        while (null != node) {
            if (in == index) {
                return node.element;
            }
            node = node.next;
            in++;
        }
        return null;
    }

    @Override
    public void update(int index, E element) {

    }

    @Override
    public int size() {
        return this.size;
    }
}
