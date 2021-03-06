package org.nathan.algorithmsJava.graph;


import org.jetbrains.annotations.NotNull;

import java.util.*;


public final class LinkedGraph<V> {
    private final Direction graph_direction;
    private final List<V> vertices = new ArrayList<>();
    private final Map<V, List<Edge<V>>> edges_map = new HashMap<>();
    private int size;

    public LinkedGraph(@NotNull List<V> vertices, @NotNull Direction is_directed) {
        size = 0;
        for (var vertex : vertices) {
            Objects.requireNonNull(vertex);
            this.edges_map.put(vertex, new ArrayList<>());
            this.vertices.add(vertex);
            size++;
        }
        this.graph_direction = is_directed;
    }

    public LinkedGraph(@NotNull LinkedGraph<V> other_graph) {
        size = other_graph.vertices.size();
        this.graph_direction = other_graph.graph_direction;
        this.edges_map.putAll(other_graph.edges_map);
        this.vertices.addAll(other_graph.vertices);
    }

    public void setNeighbor(@NotNull V vertex, @NotNull V neighbor) {
        setNeighbor(vertex, neighbor, 1);
    }

    public void setNeighbor(@NotNull V vertex, @NotNull V neighbor, double w) {
        var edge_t = new Edge<>(vertex, neighbor, w, graph_direction);
        if (graph_direction == Direction.DIRECTED) {
            var edges_list = edges_map.get(vertex);
            edges_list.add(edge_t);
        }
        else {
            var edges_list = edges_map.get(vertex);
            edges_list.add(edge_t);

            edges_list = edges_map.get(neighbor);
            edges_list.add(edge_t);
        }
    }

    public void addNewVertex(@NotNull V vertex) {
        if (vertices.contains(vertex) || edges_map.containsKey(vertex)) {
            throw new IllegalArgumentException("repeated vertex");
        }
        size++;
        vertices.add(vertex);
        edges_map.put(vertex, new ArrayList<>());
    }

    public int getVerticesCount() {
        return size;
    }

    public List<Edge<V>> getAllEdges() {
        List<Edge<V>> res = new ArrayList<>();
        for (var vertex : vertices) {
            res.addAll(edges_map.get(vertex));
        }
        return res;
    }

    /**
     * @return unmodifiable list
     */
    public List<V> getAllVertices() {
        return Collections.unmodifiableList(vertices);
    }

    /**
     * @param vertex vertex
     * @return unmodifiable list
     */
    public List<Edge<V>> getEdgesAt(V vertex) {
        return Collections.unmodifiableList(edges_map.get(vertex));
    }

    enum Direction {
        DIRECTED, NON_DIRECTED
    }

    public static final class Edge<T> {
        private final T former_vertex;
        private final T later_vertex;
        private final Direction edge_direction;
        double weight;

        Edge(@NotNull T former, @NotNull T later, double weight, @NotNull Direction is_directed) {
            this.weight = weight;
            former_vertex = former;
            later_vertex = later;
            this.edge_direction = is_directed;
        }

        public T getFormerVertex() {
            return former_vertex;
        }

        public T getLaterVertex() {
            return later_vertex;
        }

        public T getAnotherSide(T vertex) {
            if (vertex.equals(former_vertex)) {
                return later_vertex;
            }
            else if (vertex.equals(later_vertex)) {
                return former_vertex;
            }
            else {
                throw new IllegalArgumentException();
            }
        }

        public double getWeight() {
            return weight;
        }

        @Override
        public String toString() {
            if (edge_direction == LinkedGraph.Direction.DIRECTED) {
                return String.format("[Edge(%s >>> %s)], weight:%f", former_vertex, later_vertex, weight);
            }
            else {
                return String.format("[Edge(%s <-> %s)], weight:%f", former_vertex, later_vertex, weight);
            }
        }
    }
}
