package graph;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class SSSPTest {

    @Test
    void algorithmBellmanFord() {
        var G = buildBellmanFordCase();
        var b = SSSP.algorithmBellmanFord(G, new BFS.BFSVertex<>("s"));
        BFS.BFSVertex<String> target = new BFS.BFSVertex<>("z");
        var vertices = G.heavilyGetAllVertices();
        for(var v : vertices){
            if(v.equals(target)) { target = v; } }
        assertEquals(-2,target.getDistance());
        List<String> res = new ArrayList<>();
        while(target != null){
            res.add(target.getContent());
            target = target.getParent();
        }
        assertTrue(b);
        assertEquals(List.of("z", "t", "x", "y","s"),res);
    }
    static LinkedGraph<BFS.BFSVertex<String>> buildBellmanFordCase(){
        String[] names = "s,t,x,y,z".split(",");
        List<BFS.BFSVertex<String>> vertices = new ArrayList<>();
        for(var n : names) vertices.add(new BFS.BFSVertex<>(n));
        var res = new LinkedGraph<>(vertices, LinkedGraph.Direction.DIRECTED);
        int[] index1 =        new int[]{0,0,1,1, 1, 2, 3,3,4,4};
        int[] index2 =        new int[]{1,3,2,3, 4, 1, 2,4,0,2};
        double[] weights = new double[]{6,7,5,8,-4,-2,-3,9,2,7};
        for(int i = 0; i < index1.length; i++){
            res.setNeighbor(vertices.get(index1[i]),vertices.get(index2[i]), weights[i]);
        }
        return res;
    }

    @Test
    void shortestPathOfDAG(){
        var two_graph = buildShortestPathOfDAGForBFS();
        var res = SSSP.shortestPathOfDAG(two_graph.DFS_G,two_graph.BFS_G,new BFS.BFSVertex<>("s"));
        var vertices = res.heavilyGetAllVertices();
        var l = vertices.stream().sorted(Comparator.comparing(BFS.BFSVertex::getContent)).collect(Collectors.toList());
        assertNull(l.get(0).getParent());
        assertNull(l.get(1).getParent());
        assertEquals(l.get(1),l.get(2).getParent());
        assertEquals(2,l.get(2).getDistance());

        assertEquals(l.get(1),l.get(3).getParent());
        assertEquals(6,l.get(3).getDistance());

        assertEquals(l.get(3),l.get(4).getParent());
        assertEquals(5,l.get(4).getDistance());

        assertEquals(l.get(4),l.get(5).getParent());
        assertEquals(3,l.get(5).getDistance());
    }
    static Result buildShortestPathOfDAGForBFS(){
        String[] names = "r,s,t,x,y,z".split(",");
        List<BFS.BFSVertex<String>> BFS_vertex = new ArrayList<>();
        for (String name : names) { BFS_vertex.add(new BFS.BFSVertex<>(name)); }
        var BFS_G = new LinkedGraph<>(BFS_vertex, LinkedGraph.Direction.DIRECTED);
        int[] index1 =        new int[]{0,0,1,1,2,2,2, 3,3, 4};
        int[] index2 =        new int[]{1,2,2,3,3,4,5, 4,5, 5};
        double[] weights = new double[]{5,3,2,6,7,4,2,-1,1,-2};
        for(int i = 0; i < index1.length; i++){
            BFS_G.setNeighbor(BFS_vertex.get(index1[i]),BFS_vertex.get(index2[i]),weights[i]);
        }
        var DFS_vertices = BFS_vertex.stream().map(DFS.DFSVertex::new).collect(Collectors.toList());
        var DFS_G = new LinkedGraph<>(DFS_vertices, LinkedGraph.Direction.DIRECTED);
        int len = DFS_vertices.size();
        for(int i = 0; i < len - 1; i++){
            DFS_G.setNeighbor(DFS_vertices.get(i), DFS_vertices.get(i+1));
        }
        var t = new Result();
        t.BFS_G = BFS_G;
        t.DFS_G = DFS_G;
        return t;
    }
    static class Result{
        public LinkedGraph<BFS.BFSVertex<String>> BFS_G;
        public LinkedGraph<DFS.DFSVertex<BFS.BFSVertex<String>>> DFS_G;
    }

    @Test
    void algorithmDijkstraTestWithFibonacciHeap(){
        var g = buildDijkstraCase();
        SSSP.algorithmDijkstraWithMinHeap(g, new BFS.BFSVertex<>("s"));
        var vertices = g.heavilyGetAllVertices().stream().sorted(Comparator.comparing(BFS.BFSVertex::getContent)).collect(Collectors.toList());
        assertNull(vertices.get(0).getParent());

        assertEquals(vertices.get(3),vertices.get(1).getParent());
        assertEquals(8,vertices.get(1).getDistance());

        assertEquals(vertices.get(1),vertices.get(2).getParent());
        assertEquals(9,vertices.get(2).getDistance());

        assertEquals(vertices.get(0),vertices.get(3).getParent());
        assertEquals(5,vertices.get(3).getDistance());

        assertEquals(vertices.get(3),vertices.get(4).getParent());
        assertEquals(7,vertices.get(4).getDistance());
    }

    @Test
    void algorithmDijkstraTestWithMinHeap(){
        var g = buildDijkstraCase();
        SSSP.algorithmDijkstraWithFibonacciHeap(g, new BFS.BFSVertex<>("s"));
        var vertices = g.heavilyGetAllVertices().stream().sorted(Comparator.comparing(BFS.BFSVertex::getContent)).collect(Collectors.toList());
        assertNull(vertices.get(0).getParent());

        assertEquals(vertices.get(3),vertices.get(1).getParent());
        assertEquals(8,vertices.get(1).getDistance());

        assertEquals(vertices.get(1),vertices.get(2).getParent());
        assertEquals(9,vertices.get(2).getDistance());

        assertEquals(vertices.get(0),vertices.get(3).getParent());
        assertEquals(5,vertices.get(3).getDistance());

        assertEquals(vertices.get(3),vertices.get(4).getParent());
        assertEquals(7,vertices.get(4).getDistance());
    }
    static LinkedGraph<BFS.BFSVertex<String>> buildDijkstraCase(){
        String[] names = "s,t,x,y,z".split(",");
        List<BFS.BFSVertex<String>> vertices = new ArrayList<>();
        for(var n : names) vertices.add(new BFS.BFSVertex<>(n));
        var graph = new LinkedGraph<>(vertices, LinkedGraph.Direction.DIRECTED);
        int[] indices1 =      new int[]{0, 0,1,1,2,3,3,3,4,4};
        int[] indices2 =      new int[]{1, 3,2,3,4,1,2,4,0,2};
        double[] weights = new double[]{10,5,1,2,4,3,9,2,7,6};
        for(int i = 0; i < indices1.length; i++){
            graph.setNeighbor(vertices.get(indices1[i]), vertices.get(indices2[i]),weights[i]);
        }
        return graph;
    }
}