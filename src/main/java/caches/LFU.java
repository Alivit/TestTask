package caches;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class LFU<T> implements Cache<T>{

    private int size;

    private final Map<String, Node> hashmap;
    private final Map<String, Integer> counterMap;
    private final Map<String, DoubleLinkedList> frequencyMap;

    public LFU(int capacity){
        size = capacity;
        this.counterMap = new HashMap<>();
        this.hashmap = new HashMap<>();
        this.frequencyMap = new TreeMap<>();
    }

    public T get(final String key){
        Node deletedNode = hashmap.get(key);
        Node node;
        try {
            node = new Node(key, deletedNode.value);
        } catch (NullPointerException e){
            return null;
        }

        int frequency = counterMap.get(key);
        frequencyMap.get(String.valueOf(frequency)).remove(deletedNode);
        if(frequencyMap.get(String.valueOf(frequency)).length() == 0){
            frequencyMap.remove(String.valueOf(frequency));
        }

        hashmap.remove(key);
        counterMap.remove(key);

        hashmap.put(key, node);
        counterMap.put(key, frequency + 1);
        frequencyMap.computeIfAbsent(String.valueOf(frequency + 1), k -> new DoubleLinkedList()).add(node);
        return hashmap.get(key).value;
    }

    public void put(final String key, final T value){
        if (!hashmap.containsKey(key) && size > 0){
            Node node = new Node(key, value);

            if(hashmap.size() == size){
                String lowestCount = frequencyMap.keySet().stream().findFirst().get();
                Node deletedNode = frequencyMap.get(lowestCount).head();

                frequencyMap.get(lowestCount).remove(deletedNode);
                if(frequencyMap.get(lowestCount).length() == 0){
                    frequencyMap.remove(lowestCount);
                }

                String deletedKey = deletedNode.key;
                hashmap.remove(deletedKey);
                counterMap.remove(deletedKey);
            }
            frequencyMap.computeIfAbsent("1", k -> new DoubleLinkedList()).add(node);
            hashmap.put(key, node);
            counterMap.put(key, 1);
        }
        else if(size > 0){
            Node node = new Node(key, value);
            Node deletedNode = hashmap.get(key);

            int frequency = counterMap.get(key);
            frequencyMap.get(String.valueOf(frequency)).remove(deletedNode);
            if(frequencyMap.get(String.valueOf(frequency)).length() == 0){
                frequencyMap.remove(String.valueOf(frequency));
            }

            hashmap.remove(key);
            counterMap.remove(key);

            hashmap.put(key, node);
            counterMap.put(key, frequency + 1);
            frequencyMap.computeIfAbsent(String.valueOf(frequency + 1), k -> new DoubleLinkedList()).add(node);
        }
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
        private int size;
        private Node head, tail;
        public DoubleLinkedList(){
            head = tail = null;
        }

        private void add(final Node node){
            if(head == null){
                head = node;
            } else {
                tail.next = node;
                node.prev = tail;
            }
            tail = node;
        }

        private void remove(Node node){
            if(node.next == null) tail = node.prev;
            else node.next.prev = node.prev;

            if(node.prev == null) tail = node.next;
            else node.prev.next = node.next;
        }

        public int length(){
            return size;
        }

        public Node head() {
            return head;
        }
    }

}
