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
    private Node[] nodes;

    public MyHashMap(int size)  {
        size = resize(size);
        this.nodes = new Node[size];
        this.threshold = Math.round(size * DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap() {
        this.nodes = new Node[capacity];
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

    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    private void putData(int hash, K key, V value) {
        if (this.size + 1 > this.threshold) {
            // 扩容
            expandCapacity();
        }
        // 计算元素在数据组中存放的位置
        int index = (hash % this.capacity);
        Node currentNode = this.nodes[index];
        if (null == currentNode) {
            // 当前位置未开始存放
            currentNode = new Node(hash, key, value, null);
            this.nodes[index] = currentNode;
            this.size++;
        } else {
            // 元素是否已存在
            boolean exist = false;
            // 当前位置已开始存放
            currentNode = this.nodes[index];
            while (null != currentNode) {
                if (currentNode.hash == hash && currentNode.key == key) {
                    // 数据重复,更新
                    exist = true;
                    currentNode.key = key;
                    currentNode.value = value;
                    break;
                }
                currentNode = currentNode.next;
            }
            // 数据非重复,尾插
            if (!exist) {
                while (null != currentNode.next) {
                    currentNode = currentNode.next;
                }
                Node node = new Node(hash, key, value, null);
                currentNode.next = node;
                this.size++;
            }
        }
    }

    private void expandCapacity() {
    }


    @Override
    public void get(K key) {

    }
}
