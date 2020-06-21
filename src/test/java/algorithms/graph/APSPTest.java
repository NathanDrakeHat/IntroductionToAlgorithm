package algorithms.graph;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class APSPTest {

    @Test
    void slowAllPairsShortestPaths() {
        var res = APSP.slowAllPairsShortestPaths(buildSlowAllPairsShortestPathsGraph());
        var answer = new double[][]{
                {0,3,7,2,8},
                {1,0,4,-1,5},
                {-3,-4,0,-5,1},
                {2,1,5,0,6},
                {-4,-1,3,-2,0}
        };
        assertArrayEquals(answer,res);
    }

    @Test
    void fasterAllPairsShortestPaths() {
        var res = APSP.fasterAllPairsShortestPaths(buildSlowAllPairsShortestPathsGraph());
        var answer = new double[][]{
                {0,3,7,2,8},
                {1,0,4,-1,5},
                {-3,-4,0,-5,1},
                {2,1,5,0,6},
                {-4,-1,3,-2,0}
        };
        assertArrayEquals(answer,res);
    }
    double[][] buildSlowAllPairsShortestPathsGraph(){
        var weight = new double[][]{
                {0,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,2,Double.POSITIVE_INFINITY},
                {3,0,4,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY},
                {8,Double.POSITIVE_INFINITY,0,-5,Double.POSITIVE_INFINITY},
                {Double.POSITIVE_INFINITY,1,Double.POSITIVE_INFINITY,0,6},
                {-4,7,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,0}
        };
        return weight;
    }
}