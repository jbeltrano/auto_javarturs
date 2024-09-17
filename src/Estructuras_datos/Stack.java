package Estructuras_datos;

public class Stack<T>{
    
    private class Nodo<E>{
        public E dato;
        public Nodo<E> next;

        public Nodo(E dato){
            this.dato = dato;
            next = null;
        }
    }

    private Nodo<T> head;
    private int size;

    public Stack(){
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

    public T pop(){

        if(isEmpty()){
            throw new IllegalStateException("La pila está vacia");
        }
        
        T dato = head.dato;

        head = head.next;
        size--;
        return dato;
    }

    public void push(T dato){

        Nodo<T> nuevo_nodo = new Nodo<T>(dato);
        nuevo_nodo.next = head;
        head = nuevo_nodo;
        size++;
    }

    public int size(){
        return size;
    }
    
}
