package algorithms.graph;


import org.junit.jupiter.api.Test;
import tools.Graph;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BFSTest {

    public static class Data{
        public static String names = "rstuvwxy";
        public static BFS.Vertex[] makeVertexes(){
            var vs = new BFS.Vertex[8];
            for(int i = 0; i < 8; i++){
                vs[i] = new BFS.Vertex(names.charAt(i));
            }
            return vs;
        }
        public static Graph<BFS.Vertex> makeGraph(BFS.Vertex[] vs){ ;
            var G = new Graph<>(vs);
            G.putNeighborPair(vs[0], vs[1]);
            G.putNeighborPair(vs[0], vs[4]);

            G.putNeighborPair(vs[1], vs[5]);

            G.putNeighborPair(vs[2], vs[3]);
            G.putNeighborPair(vs[2], vs[5]);
            G.putNeighborPair(vs[2], vs[6]);

            G.putNeighborPair(vs[3], vs[6]);

            G.putNeighborPair(vs[5], vs[6]);

            G.putNeighborPair(vs[6], vs[7]);

            return G;
        }
    }

    @Test
    void breathFirstSearch() {
        var vs = Data.makeVertexes();
        var t = Data.makeGraph(vs);
        BFS.breathFirstSearch(t, vs[1]);
        assertEquals(BFS.getPath(vs[1], vs[7]), List.of('s','w','x','y'));
    }
}