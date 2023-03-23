package caches;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Класс кэша по реализацией алгоритма lfu
 */
public class LFU<T> implements Cache<T>{

    /**
     * Поле которое хранит размер кэша
     */
    private int size;

    /**
     * Поле ключ-карт со значениями
     */
    private final Map<String, Node> hashmap;
    /**
     * Поле ключ-карт с счётчиком
     */
    private final Map<String, Integer> counterMap;
    /**
     * Поле ключ-карт с количеством частоты пользования
     */
    private final Map<String, DoubleLinkedList> frequencyMap;

    public LFU(int capacity){
        size = capacity;
        this.counterMap = new HashMap<>();
        this.hashmap = new HashMap<>();
        this.frequencyMap = new TreeMap<>();
    }

    /**
     * Метод который извлекает эллемент из кэша и возвращает пользователю
     *
     * @param key ключ нужного значения
     * @return возвращает нужное значение
     */
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

    /**
     * Метод вставки элемента в коллекцию кэша
     * Если место в кэше заканчивает, а нужно добавить элемент
     * тогда удаляется менне используемый элемент
     *
     * @param key ключ элемента
     * @param value значение элеметна
     */
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
        private int size;
        private Node head, tail;
        public DoubleLinkedList(){
            head = tail = null;
        }

        /**
         * Добавление элемента в список
         */
        private void add(final Node node){
            if(head == null){
                head = node;
            } else {
                tail.next = node;
                node.prev = tail;
            }
            tail = node;
        }

        /**
         * Удаления элемента из списка
         */
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
