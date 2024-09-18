package Estructuras_datos;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Comparator;

class Node {
    int id;
    List<Edge> edges;

    public Node(int id) {
        this.id = id;
        this.edges = new ArrayList<>();
    }
}

class Edge {
    Node target;
    int weight;

    public Edge(Node target, int weight) {
        this.target = target;
        this.weight = weight;
    }
}

class Graph {
    Map<Integer, Node> nodes;

    public Graph() {
        this.nodes = new HashMap<>();
    }

    public void addNode(int id) {
        nodes.putIfAbsent(id, new Node(id));
    }

    public void addEdge(int sourceId, int targetId, int weight) {
        Node source = nodes.get(sourceId);
        Node target = nodes.get(targetId);
        if (source != null && target != null) {
            source.edges.add(new Edge(target, weight));
        }
    }

    public static Map<Node, Integer> shortestPath(Graph graph, Node source) {
        Map<Node, Integer> distances = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        Set<Node> visited = new HashSet<>();

        for (Node node : graph.nodes.values()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(source, 0);
        pq.add(source);

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);

            for (Edge edge : current.edges) {
                Node neighbor = edge.target;
                int newDist = distances.get(current) + edge.weight;
                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    pq.add(neighbor);
                }
            }
        }
        return distances;
    }
}

