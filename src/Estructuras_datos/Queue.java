package Estructuras_datos;

public class Queue<T>{
    
    private class Nodo<E>{
        public E dato;
        public Nodo<E> next;

        public Nodo(E dato){
            this.dato = dato;
            next = null;
        }
    }

    private Nodo<T> head;
    private Nodo<T> tail;
    private int size;

    public Queue(){
        head = null;
        size = 0;
    }

    public boolean isEmpty(){
        return head == null;
    }

    public T peek(){
        
        if(isEmpty()){
            throw new IllegalStateException("La pila está vacia");
        }
        return head.dato;
        
    }

    public int size(){
        return size;
    }
    
    public void enqueue(T dato){

        Nodo<T> nuevo_nodo = new Nodo<T>(dato);
        if(tail == null){
            tail = nuevo_nodo;
            head = nuevo_nodo;
        }else{
            tail.next = nuevo_nodo;
            tail = tail.next;
        }
        size++;
    }

    public T dequeue(){

        if(isEmpty()){
            throw new IllegalStateException("La pila está vacia");
        }

        T data = head.dato;
        head = head.next;
        if(head == null){
            tail = null;
        }
        size--;
        return data;
    }
    
}
