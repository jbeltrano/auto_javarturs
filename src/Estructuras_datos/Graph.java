package Estructuras_datos; 


import java.util.List;
import java.util.ArrayList;

public class Graph {
    // Clase para representar un nodo (municipio) en el grafo
    private static class Nodo implements Comparable<Nodo> {
        int id;
        int distancia;

        public Nodo(int id, int distancia) {
            this.id = id;
            this.distancia = distancia;
        }

        @Override
        public int compareTo(Nodo other) {
            return Integer.compare(this.distancia, other.distancia);
        }
    }

    private HashTable<Integer, List<Nodo>> grafo;

    public Graph() {
        //grafo = new HashMap<>();
        grafo = new HashTable<>();
    }

    public void setNodo(int origen, int destino, int distancia){
        grafo.putIfAbsent(origen, new ArrayList<>());
        grafo.get(origen).add(new Nodo(destino, distancia));

        grafo.putIfAbsent(destino, new ArrayList<>());
        grafo.get(destino).add(new Nodo(origen, distancia));
    }

    public Stack<Integer> dijkstra(int origen, int destino) {
        HashTable<Integer, Integer> distancias = new HashTable<>();
        HashTable<Integer, Integer> predecesores = new HashTable<>();
        PriorityQueue<Nodo> cola = new PriorityQueue<>(Nodo.class);
        cola.insert(new Nodo(origen, 0));
        distancias.put(origen, 0);

        while (!cola.isEmpty()) {
            Nodo actual = cola.deleteMin();
            int nodoActual = actual.id;

            if (nodoActual == destino) {
                break;
            }

            if (!grafo.containsKey(nodoActual)) continue;

            for (Nodo vecino : grafo.get(nodoActual)) {
                int nuevaDistancia = distancias.get(nodoActual) + vecino.distancia;

                if (nuevaDistancia < distancias.getOrDefault(vecino.id, Integer.MAX_VALUE)) {
                    distancias.put(vecino.id, nuevaDistancia);
                    predecesores.put(vecino.id, nodoActual);
                    cola.insert(new Nodo(vecino.id, nuevaDistancia));
                }
            }
        }

        Stack<Integer> camino = new Stack<>();
        Integer paso = destino;

        while (paso != null) {
            camino.push(paso);
            paso = predecesores.get(paso);
        }

        if (camino.peek() != origen){
            return new Stack<>();
        }

        return camino;
    }
}
