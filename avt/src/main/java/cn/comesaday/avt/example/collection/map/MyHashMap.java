package cn.comesaday.avt.example.collection.map;

/**
 * <描述> MyHashMap
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-05-24 16:14
 */
public class MyHashMap<K, V> implements MapInfance<K, V> {

    // map长度
    private int size = 0;

    // 默认初始容量
    private static final int DEFAULT_INITAL_CAPACITY = 16;

    // 默认负载因子
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    // 扩容阈值
    private int threshold;

    // 动态容量
    private int capacity = DEFAULT_INITAL_CAPACITY;

    // 动态负载因子
    private float loadFactor = DEFAULT_LOAD_FACTOR;

    // 链表数组
    private Node[] elements;

    public MyHashMap(int size)  {
        size = resize(size);
        this.elements = new Node[size];
        this.threshold = Math.round(size * DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap() {
        this.elements = new Node[capacity];
        this.threshold = Math.round(capacity * loadFactor);
    }

    private int resize(int size) {
        int s = 0;
        if (size == 0) {
            return size;
        }
        while (Math.pow(2, s) < size) {
            s++;
        }
        return s + 1;
    }

    static class Node<K, V> {

        final int hash;
        K key;
        V value;
        Node<K, V> next;

        public Node(int hash, K key, V value, Node next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void put(K key, V value) {
        putData(hash(key), key, value);
    }

    private int hash(K key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    private void putData(int hash, K key, V value) {
        // 计算元素在数据组中存放的位置
        int index = (hash % this.capacity);
        Node currentNode = this.elements[index];
        if (null == currentNode) {
            // 当前位置未开始存放
            Node newNode = new Node(hash, key, value, null);
            this.elements[index] = newNode;
            this.size++;
            return;
        }
        // 当前位置已开始存放
        for (Node node = currentNode; null != node; node = node.next) {
            if (currentNode.hash == hash && currentNode.key == key) {
                // 数据重复,更新
                currentNode.key = key;
                currentNode.value = value;
                return;
            }
        }
        // 数据非重复,尾插-哈希碰撞
        expandCapacity();
        Node newNode = new Node(hash, key, value, currentNode);
        this.elements[index] = newNode;
        this.size++;
    }

    private void expandCapacity() {
        if (this.size < this.threshold) {
            // 不会超过阈值-不扩容
            return;
        }
        // 更新容量
        this.capacity = this.capacity * 2;
        // 扩容阈值
        this.threshold = Math.round(this.capacity * DEFAULT_LOAD_FACTOR);
        Node[] oldElements = this.elements;
        Node[] newElements = new Node[this.capacity];
        System.arraycopy(oldElements, 0, newElements, 0, oldElements.length);
        this.elements = newElements;
    }


    @Override
    public V get(K key) {
        int hash = hash(key);
        int index = hash % this.capacity;
        if (index < 0 || index > this.elements.length) {
            return null;
        }
        Node head = this.elements[index];
        if (null == head) {
            return null;
        }
        for (Node node = head; null != node; node = node.next) {
            if (node.hash == hash && node.key == key) {
                return (V) node;
            }
        }
        return null;
    }
}
