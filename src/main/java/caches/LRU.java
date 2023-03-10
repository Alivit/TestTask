package caches;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс кэша по реализацией алгоритма lru
 */
public class LRU<T> implements Cache<T>{
    /**
     * Поля хранящие размер кэша
     */
    private int size;
    private final int capacity;

    /**
     * Поле ключ-карт со значениями
     */
    private final Map<String, Node> hashmap;
    /**
     * Поле с двухсвязанным списком
     */
    private final DoubleLinkedList queue;


    public LRU(final int capacity){
        this.capacity = capacity;
        this.hashmap = new HashMap<>();
        this.queue = new DoubleLinkedList();
        this.size = 0;
    }

    /**
     * Метод который извлекает эллемент из кэша и возвращает пользователю
     *
     * @param key ключ нужного значения
     * @return возвращает нужное значение
     */
    public T get(final String key){
         Node node = hashmap.get(key);
         if(node == null)
             return null;
         queue.addToHead(node);
         return hashmap.get(key).value;
    }

    /**
     * Метод вставки элемента в коллекцию кэша
     * Если место в кэше заканчивает, а нужно добавить элемент
     * тогда удаляется менне используемый элемент
     *
     * @param key ключ элемента
     * @param value значение элеметна
     */
    public void put(final String key, final T value){
        Node current = hashmap.get(key);
        if(current != null){
            current.value = value;
            queue.addToHead(current);
        }

        if(size == capacity){
            String tailKey = queue.getTail();
            queue.removeFromTail();
            hashmap.remove(tailKey);
            size--;
        }

        Node node = new Node(key, value);
        queue.add(node);
        hashmap.put(key,node);
        size++;
    }

    /**
     * Вспомогательный класс для опредения положения элемента
     */
    private class Node {
        String key;
        T value;
        Node next, prev;
        public Node(final String key, final T value){
            this.key = key;
            this.value = value;
            this.next = null;
            this.prev = null;
        }
    }

    /**
     * Вспомогательный класс двухсвязанного списка
     */
    private class DoubleLinkedList{
        private Node head, tail;
        public DoubleLinkedList(){
            head = tail = null;
        }

        private void add(final Node node){
            if(tail == null){
                head = tail = node;
                return;
            }
            node.next = head;
            head.prev = node;
            head = node;
        }

        /**
         * Добавление элемента в список
         */
        public void addToHead(final Node node){
            if(head == node){
                return;
            }


            if(node == tail){
                tail = tail.prev;
                tail.next = null;
            }else {
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }

            node.prev = null;
            node.next = head;
            head.prev = node;
            head = node;
        }

        /**
         * Удаления элемента из списка
         */
        private void removeFromTail(){
            if(tail == null){
                return;
            }

            System.out.println("Deleted key: " + tail.key);
            if (head == tail){
                head = tail = null;
            }else {
                tail = tail.prev;
                tail.next = null;
            }
        }

        private String getTail(){
            return tail.key;
        }
    }
}
