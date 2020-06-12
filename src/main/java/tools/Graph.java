package tools;

import java.util.*;
import java.util.stream.Collectors;


public class Graph<V extends Comparable<V>>  {
    public class Edge implements Comparable<Edge> {
        private final V smaller_vertex;
        private final V bigger_vertex;
        private final int weight;

        public Edge(V small, V bigger, int weight){
            this.weight = weight;
            if(small.compareTo(bigger) <= 0){
                smaller_vertex = small;
                bigger_vertex = bigger;
            }else{
                smaller_vertex = bigger;
                bigger_vertex = small;
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object other_edge){
            if(other_edge == null){
                return false;
            }else if(Edge.class.equals(other_edge.getClass())){
                return this.smaller_vertex.equals(((Edge) other_edge).smaller_vertex) &&
                        this.bigger_vertex.equals(((Edge) other_edge).bigger_vertex) &&
                        this.weight == ((Edge) other_edge).weight;
            }else{
                return false;
            }
        }

        public V getSmallerVertex() { return smaller_vertex; }

        public V getBiggerVertex() { return bigger_vertex; }

        public V getAnotherVertex(V vertex){
            if(smaller_vertex.equals(vertex)) return bigger_vertex;
            else if(bigger_vertex.equals(vertex)) return smaller_vertex;
            else throw new IllegalArgumentException("Not match.");
        }

        public int getWeight() { return weight; }

        @Override
        public int compareTo(Edge other){ 
            if(weight != other.weight){
                return weight - other.weight;
            }else{
                int small_check = smaller_vertex.compareTo(other.smaller_vertex);
                if(small_check != 0) return small_check;
                else return bigger_vertex.compareTo(other.bigger_vertex);
            }
        }

        @Override
        public String toString(){ return String.format("Edge( %s, %s ), Weight:%d",smaller_vertex,bigger_vertex,weight); }

        @Override
        public int hashCode(){ return Objects.hash(smaller_vertex, bigger_vertex, weight); }
    }
    Map<V, Set<Edge>> edge_map = new HashMap<>();

    public Graph(){}
    public Graph(V[] vertices){
        for(var v : vertices) {
            this.edge_map.put(v, new TreeSet<>());}
    }
    public Graph(List<V> vertices){
        for(var v : vertices) {
            this.edge_map.put(v, new TreeSet<>());}
    }

    public void putNeighborPair(V vertex, V neighbor){
        putNeighborPair(vertex, neighbor, 1);
    }
    public void putNeighborPair(V vertex, V neighbor, int w){
        var edge_t = new Edge(vertex, neighbor, w);
        var edges_set = edge_map.computeIfAbsent(vertex, k -> new TreeSet<>());
        edges_set.add(edge_t);
        edges_set = edge_map.computeIfAbsent(neighbor, k -> new TreeSet<>());
        edges_set.add(edge_t);
    }

    public void addOneNeighbor(V vertex, V neighbor){ addOneNeighbor(vertex, neighbor, 1); }
    public void addOneNeighbor(V vertex, V neighbor, int w){
        var edge_t = new Edge(vertex, neighbor, w);
        var edges_set = edge_map.computeIfAbsent(vertex, k -> new TreeSet<>());
        edges_set.add(edge_t);
    }

    public Set<Edge> getEdgesAt(V vertex) {
        return new TreeSet<>(edge_map.computeIfAbsent(vertex, k -> new TreeSet<>()));
    }
    public Set<Edge> getAllEdges(){
        Set<Edge> res = new TreeSet<>();
        for(var vertex : getAllVertices()){
            res.addAll(getEdgesAt(vertex));
        }
        return res;
    }

    public Set<V> getAllVertices(){
        return new HashSet<>(edge_map.keySet());
    }
    public void putVertex(V vertex) { edge_map.put(vertex, new TreeSet<>()); }
    public boolean hasVertex(V vertex) { return edge_map.containsKey(vertex); }

    public Set<V> getNeighborsAt(V vertex){
        return edge_map.get(vertex).stream().map((e) -> e.getAnotherVertex(vertex)).collect(Collectors.toCollection(TreeSet::new));
    }
    public boolean hasOneNeighbor(V vertex, V neighbor) {
        var edges = edge_map.get(vertex);
        for(var edge : edges){
            var n = edge.getAnotherVertex(vertex);
            if(neighbor.equals(n)) return true;
        }
        return false;
    }

//    public void setNeighbors(V vertex, V[] vertexes, int[] weights){
//        int len = vertexes.length;
//        if(weights.length != len) throw new IllegalArgumentException();
//
//        Set<Edge> edges_set = edge_map.get(vertex);
//        if(edges_set != null) { edges_set.clear(); }
//        else{ edges_set = new TreeSet<>(); }
//
//        for(int i = 0; i < len; i++){
//            edges_set.add(new Edge(vertex, vertexes[i], weights[i]));
//        }
//    }
//    public void setNeighborPairs(V vertex, V[] vertexes, int[] weights){
//        int len = vertexes.length;
//        if(weights.length != len) throw new IllegalArgumentException();
//        for(int i = 0; i < len; i++) putNeighborPair(vertex, vertexes[i],weights[i]);
//    }
//    public void clearNeighbors(V vertex){ edge_map.get(vertex).clear(); }
//    public boolean removeOneNeighbor(V vertex, V neighbor, int weight){
//        return edge_map.get(vertex).remove(new Edge(vertex,neighbor,weight));
//    }
}