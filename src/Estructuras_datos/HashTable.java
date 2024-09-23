package Estructuras_datos;

import java.util.LinkedList;

class HashTable<K, V> {
    private static final int INITIAL_CAPACITY = 16; // Tamaño inicial
    private LinkedList<Entry<K, V>>[] buckets;

    // Clase Entry para almacenar pares clave-valor
    static class Entry<K, V> {
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    @SuppressWarnings("unchecked")
    public HashTable() {
        buckets = new LinkedList[INITIAL_CAPACITY]; // Inicializar las listas enlazadas
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    // Función hash para calcular el índice de la clave
    private int hash(K key) {
        return Math.abs(key.hashCode() % INITIAL_CAPACITY);
    }

    // Insertar un nuevo elemento en la tabla
    public void put(K key, V value) {
        int bucketIndex = hash(key);
        LinkedList<Entry<K, V>> bucket = buckets[bucketIndex];

        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                entry.value = value; // Si la clave ya existe, actualiza el valor
                return;
            }
        }

        // Si no existe, añade un nuevo Entry a la lista
        bucket.add(new Entry<>(key, value));
    }

    // Obtener un valor a partir de la clave
    public V get(K key) {
        int bucketIndex = hash(key);
        LinkedList<Entry<K, V>> bucket = buckets[bucketIndex];

        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                return entry.value; // Retorna el valor si encuentra la clave
            }
        }

        return null; // Retorna null si no se encuentra la clave
    }

    // Eliminar un elemento por su clave
    public void remove(K key) {
        int bucketIndex = hash(key);
        LinkedList<Entry<K, V>> bucket = buckets[bucketIndex];

        bucket.removeIf(entry -> entry.key.equals(key)); // Elimina si coincide la clave
    }
}
