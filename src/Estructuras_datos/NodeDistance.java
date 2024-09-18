package Estructuras_datos;

public class NodeDistance implements Comparable<NodeDistance> {
    private final String node;  // Nodo puede ser una ciudad, por ejemplo
    private final int distance;

    public NodeDistance(String node, int distance) {
        this.node = node;
        this.distance = distance;
    }

    public String getNode() {
        return node;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public int compareTo(NodeDistance other) {
        return Integer.compare(this.distance, other.distance);  // Comparar por distancia
    }
}
