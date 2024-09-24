package Estructuras_datos;

import java.util.LinkedList;

public class HashTable<K, V> {
    private static final int INITIAL_CAPACITY = 16; // Tamaño inicial
    private LinkedList<Entrada<K, V>>[] listas;

    // Clase Entrada para almacenar pares clave-valor
    static class Entrada<K, V> {
        K key;
        V value;

        Entrada(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    @SuppressWarnings("unchecked")
    public HashTable() {
        listas = new LinkedList[INITIAL_CAPACITY]; // Inicializar las listass enlazadas
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            listas[i] = new LinkedList<>();
        }
    }

    // Función hash para calcular el índice de la clave
    private int hash(K key) {
        return Math.abs(key.hashCode() % INITIAL_CAPACITY);
    }

    // Insertar un nuevo elemento en la tabla
    public void put(K key, V value) {
        int listIndex = hash(key);
        LinkedList<Entrada<K, V>> lista = listas[listIndex];

        for (Entrada<K, V> Entrada : lista) {
            if (Entrada.key.equals(key)) {
                Entrada.value = value; // Si la clave ya existe, actualiza el valor
                return;
            }
        }

        // Si no existe, añade un nuevo Entrada a la listas
        lista.add(new Entrada<>(key, value));
    }

    // Obtener un valor a partir de la clave
    public V get(K key) {
        int listIndex = hash(key);
        LinkedList<Entrada<K, V>> lista = listas[listIndex];

        for (Entrada<K, V> Entrada : lista) {
            if (Entrada.key.equals(key)) {
                return Entrada.value; // Retorna el valor si encuentra la clave
            }
        }

        return null; // Retorna null si no se encuentra la clave
    }

    // Eliminar un elemento por su clave
    public void remove(K key) {
        int listIndex = hash(key);
        LinkedList<Entrada<K, V>> lista = listas[listIndex];

        lista.removeIf(Entrada -> Entrada.key.equals(key)); // Elimina si coincide la clave
    }

    public V putIfAbsent(K key, V value) {
        int listIndex = hash(key);
        LinkedList<Entrada<K, V>> lista = listas[listIndex];
    
        for (Entrada<K, V> Entrada : lista) {
            if (Entrada.key.equals(key)) {
                return Entrada.value; // Retorna el valor existente si la clave ya está presente
            }
        }
    
        // Si no existe, añade un nuevo Entrada a la listas
        lista.add(new Entrada<>(key, value));
        return null; // Retorna null indicando que no existía antes
    }

    public V getOrDefault(K key, V defaultValue) {
        int listIndex = hash(key);
        LinkedList<Entrada<K, V>> lista = listas[listIndex];

        for (Entrada<K, V> Entrada : lista) {
            if (Entrada.key.equals(key)) {
                return Entrada.value; // Retorna el valor si encuentra la clave
            }
        }

        return defaultValue; // Retorna el valor por defecto si no se encuentra la clave
    }

    // Verificar si la clave existe en la tabla
    public boolean containsKey(K key) {
        int listIndex = hash(key);
        LinkedList<Entrada<K, V>> lista = listas[listIndex];

        for (Entrada<K, V> Entrada : lista) {
            if (Entrada.key.equals(key)) {
                return true; // Retorna true si encuentra la clave
            }
        }
        return false; // Retorna false si no se encuentra la clave
    }
}
