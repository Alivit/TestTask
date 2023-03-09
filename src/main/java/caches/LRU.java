package caches;

import java.util.HashMap;
import java.util.Map;

public class LRU<T> implements Cache<T>{
    private int size;
    private final int capacity;

    private final Map<String, Node> hashmap;
    private final DoubleLinkedList queue;


    LRU(final int capacity){
        this.capacity = capacity;
        this.hashmap = new HashMap<>();
        this.queue = new DoubleLinkedList();
        this.size = 0;
    }

    public T get(final String key){
         Node node = hashmap.get(key);
         if(node == null)
             return null;
         queue.toHead(node);
         return hashmap.get(key).value;
    }

    public void put(final String key, final T value){
        Node current = hashmap.get(key);
        if(current != null){
            current.value = value;
            queue.toHead(current);
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
            node.next = tail;
            tail.prev = node;
            tail = node;
        }

        public void toHead(final Node node){
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

        private void removeFromTail(){
            if(tail == null){
                return;
            }

            System.out.println("Удалённый ключ: " + tail.key);
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
