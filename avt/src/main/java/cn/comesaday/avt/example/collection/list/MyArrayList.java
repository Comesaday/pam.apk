package cn.comesaday.avt.example.collection.list;

import java.io.Serializable;

/**
 * <描述>
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-05-24 11:07
 */
public class MyArrayList<E> implements ListInterface<E>, Serializable {

    private int size  = 0;

    private static final int DEFAULT_CAPACITY = 10;

    private static final Object[] DEFAULT_ELEMENTS = {};

    transient Object[] elements;

    public MyArrayList() {
        this.elements = DEFAULT_ELEMENTS;
    }

    public MyArrayList(int capacity) {
        if (capacity > 0) {
            this.elements = new Object[capacity];
        } else if (capacity == 0) {
            this.elements = DEFAULT_ELEMENTS;
        } else if (capacity < 0) {
            throw new IllegalArgumentException("init capacity error");
        }
    }

    @Override
    public boolean exist(int index) {
        if (index > 0 && index < this.size) {
            return true;
        }
        return false;
    }

    public void add(E element) {
        int prepareCapacity = this.size + 1;
        if (prepareCapacity > this.elements.length) {
            // 需要扩容
            expandCapacity(prepareCapacity);
        }
        this.elements[this.size] = element;
        this.size++;
    }

    @Override
    public void add(int index, E element) {
        if (!exist(index)) {
            throw new IndexOutOfBoundsException("index out of size");
        }
        this.add((E) this.elements[this.size]);
        for (int i = this.size; i > index; i--) {
            this.elements[i] = this.elements[i - 1];
        }
        this.elements[index] = element;
    }

    @Override
    public void delete(int index) {
        if (!exist(index)) {
            throw new IndexOutOfBoundsException("index out of size");
        }
        for (int i = index; i < this.size; i++) {
            this.elements[i] = this.elements[i + 1];
        }
        this.elements[index] = null;
    }

    @Override
    public void delete(E element) {
        for (int i = 0; i < this.size; i++) {
            if (this.elements[i] == element) {
                delete(i);
            }
        }
    }

    public E get(int index) {
        if (index > this.size) {
            throw new ArrayIndexOutOfBoundsException("index is bigger than size");
        } else if (index < 0) {
            throw new IllegalArgumentException("illegal index");
        }
        return (E) this.elements[index];
    }

    @Override
    public void update(int index, E element) {
        if (!exist(index)) {
            throw new IndexOutOfBoundsException("index out of size");
        }
        this.elements[index] = element;
    }

    private void expandCapacity(int prepareCapacity) {
        int dataLength = prepareCapacity - 1;
        if (this.elements.length == 0) {
            prepareCapacity = DEFAULT_CAPACITY;
        }
        Object[] oldElements = this.elements;
        this.elements = new Object[prepareCapacity];
        System.arraycopy(oldElements, 0, elements, 0, dataLength);
    }

    @Override
    public int size() {
        return this.size;
    }
}
