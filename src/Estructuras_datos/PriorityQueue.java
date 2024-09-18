package Estructuras_datos;

public class PriorityQueue {
    
    private static final int DEFAULT_SIZE = 11;
    private int size;
    private NodeDistance[] arreglo;
    private int ultimoElemento;

    public PriorityQueue(){
        size = DEFAULT_SIZE;
        ultimoElemento = 0;
        arreglo = new NodeDistance[size];
    }

    public PriorityQueue(int size){
        this.size = size;
        ultimoElemento = 0;
        arreglo = new NodeDistance[size];
    }

    public void insert(NodeDistance valor){
        if(ultimoElemento == size){
            alargarArreglo();
        }

        int hole = ++ultimoElemento;

        for (arreglo[0] = valor; valor.compareTo(arreglo[hole/2]) < 0; hole = hole/2){
            arreglo[hole] = arreglo[hole/2];
        }
        arreglo[hole] = valor;
    }

    public NodeDistance findMin(){
        if(isEmpty()){
            return null;
        }
        return arreglo[1];
    }

    public NodeDistance deleteMin(){
        if(isEmpty()){
            return null;
        }

        NodeDistance item = findMin();
        arreglo[1] = arreglo[ultimoElemento--];
        percolateDown(1);

        return item;
    }

    public boolean isEmpty(){
        return ultimoElemento == 0;
    }

    private void alargarArreglo(){
        NodeDistance[] arreglo2 = new NodeDistance[size * 2 + 1];
        System.arraycopy(arreglo, 0, arreglo2, 0, ultimoElemento + 1);
        arreglo = arreglo2;
        size = size * 2 + 1;
    }

    private void percolateDown(int hole) {
        int hijo = hole * 2;
        NodeDistance temp = arreglo[hole];

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
