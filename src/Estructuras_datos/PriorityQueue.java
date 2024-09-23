// package Estructuras_datos;

// public class PriorityQueue<T extends Comparable<T>>{
    
//     private static final int DEFAULT_SIZE = 11;
//     private int size;
//     private T[] arreglo;
//     private int ultimoElemento;

//     @SuppressWarnings("unchecked")
//     public PriorityQueue(){
//         size = DEFAULT_SIZE;
//         ultimoElemento = 0;
//         arreglo = (T[]) new Object[size];
//     }

//     @SuppressWarnings("unchecked")
//     public PriorityQueue(int size){
//         this.size = size;
//         ultimoElemento = 0;
//         arreglo = (T[]) new Object[size];
//     }

//     public void insert(T valor){
//         if(ultimoElemento == size){
//             alargarArreglo();
//         }

//         int hole = ++ultimoElemento;

//         for (arreglo[0] = valor; valor.compareTo(arreglo[hole/2]) < 0; hole = hole/2){
//             arreglo[hole] = arreglo[hole/2];
//         }
//         arreglo[hole] = valor;
//     }

//     public T findMin(){
//         if(isEmpty()){
//             return null;
//         }
//         return arreglo[1];
//     }

//     public T deleteMin(){
//         if(isEmpty()){
//             return null;
//         }

//         T item = findMin();
//         arreglo[1] = arreglo[ultimoElemento--];
//         percolateDown(1);

//         return item;
//     }

//     public boolean isEmpty(){
//         return ultimoElemento == 0;
//     }

//     private void alargarArreglo(){
//         @SuppressWarnings("unchecked")
//         T[] arreglo2 = (T[])new Object[size * 2 + 1];
//         System.arraycopy(arreglo, 0, arreglo2, 0, ultimoElemento + 1);
//         arreglo = arreglo2;
//         size = size * 2 + 1;
//     }

//     private void percolateDown(int hole) {
//         int hijo = hole * 2;
//         T temp = arreglo[hole];

//         while (hijo <= ultimoElemento) {

//             if (hijo < ultimoElemento && arreglo[hijo + 1].compareTo(arreglo[hijo]) < 0) {
//                 hijo++;
//             }

//             if (arreglo[hijo].compareTo(temp) >= 0) {
//                 break;
//             }

//             arreglo[hole] = arreglo[hijo];
//             hole = hijo;
//             hijo = hole * 2;
//         }

//         arreglo[hole] = temp;
//     }
// }

package Estructuras_datos;

import java.lang.reflect.Array;

public class PriorityQueue<T extends Comparable<T>> {
    
    private static final int DEFAULT_SIZE = 11;
    private int size;
    private T[] arreglo;
    private int ultimoElemento;
    private Class<T> type;

    @SuppressWarnings("unchecked")
    public PriorityQueue(Class<T> type) {
        this.type = type;
        size = DEFAULT_SIZE;
        ultimoElemento = 0;
        arreglo = (T[]) Array.newInstance(type, size);  // Usar Array.newInstance
    }

    @SuppressWarnings("unchecked")
    public PriorityQueue(Class<T> type, int size) {
        this.type = type;
        this.size = size;
        ultimoElemento = 0;
        arreglo = (T[]) Array.newInstance(type, size);  // Usar Array.newInstance
    }

    public void insert(T valor) {
        if (ultimoElemento == size) {
            alargarArreglo();
        }

        int hole = ++ultimoElemento;

        for (arreglo[0] = valor; valor.compareTo(arreglo[hole / 2]) < 0; hole = hole / 2) {
            arreglo[hole] = arreglo[hole / 2];
        }
        arreglo[hole] = valor;
    }

    public T findMin() {
        if (isEmpty()) {
            return null;
        }
        return arreglo[1];
    }

    public T deleteMin() {
        if (isEmpty()) {
            return null;
        }

        T item = findMin();
        arreglo[1] = arreglo[ultimoElemento--];
        percolateDown(1);

        return item;
    }

    public boolean isEmpty() {
        return ultimoElemento == 0;
    }

    @SuppressWarnings("unchecked")
    private void alargarArreglo() {
        T[] arreglo2 = (T[]) Array.newInstance(type, size * 2 + 1);  // Usar Array.newInstance
        System.arraycopy(arreglo, 0, arreglo2, 0, ultimoElemento + 1);
        arreglo = arreglo2;
        size = size * 2 + 1;
    }

    private void percolateDown(int hole) {
        int hijo = hole * 2;
        T temp = arreglo[hole];

        while (hijo <= ultimoElemento) {

            if (hijo < ultimoElemento && arreglo[hijo + 1].compareTo(arreglo[hijo]) < 0) {
                hijo++;
            }

            if (arreglo[hijo].compareTo(temp) >= 0) {
                break;
            }

            arreglo[hole] = arreglo[hijo];
            hole = hijo;
            hijo = hole * 2;
        }

        arreglo[hole] = temp;
    }
}
