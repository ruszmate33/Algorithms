/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

public class TestPercolation {

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        Percolation percolation = new Percolation(N);
        percolation.printGrid();
        /*String status = testGetAdjacents(percolation);
        System.out.println(status);
        String percTestStatus = testPercolation(percolation);
        System.out.println(percTestStatus);
        String idTest = testGetIDfromGrid(percolation);
        System.out.println(idTest);*/
        testIsPercolating(percolation);
        percolation.printGrid();
        percolation.printRoots();
    }

    public static void testIsPercolating(Percolation percolation) {
        // open from 1,1 till N,1
        for (int i = 1; i <= percolation.getSize(); i++) {
            int col = 1;
            percolation.open(i, col);
        }
        if (!percolation.percolates()) {
            System.out.println("Percolation.percolates() issue");
        }
        System.out.println("percolates: " + percolation.percolates());
    }


    private static String testGetIDfromGrid(Percolation percolation) {
        int size = percolation.getSize();
        String status = "getID ok";
        if (percolation.getIDfromGrid(1, 1) != 0) {
            status = "(1,1) not 0 issue";
        }
        if (percolation.getIDfromGrid(size, size) != size * size - 1) {
            status = "(max,max) issue";
        }
        return status;
    }

    private static String testPercolation(Percolation percolation) {
        String status = "percolation ok";
        if (!percolation.percolates()) {
            status = "fully closed percolation ok";
        }
        else {
            status = "error percolation";
        }
        return status;
    }

    private static String testGetAdjacents(Percolation percolation) {

        int size = percolation.getSize();

        String status = "testGetAdjacents ok";
        int[] adj = percolation.getAdjacents(1);
        if ((adj.length != 1) && (adj[0] != 2)) {
            status = "index 1 getAdjacent issue";
            return status;
        }
        adj = percolation.getAdjacents(size);
        if ((adj.length != 1) && (adj[0] != size - 1)) {
            status = "index max N getAdjacent issue";
            return status;
        }
        adj = percolation.getAdjacents(size - 1);
        if (adj.length != 2) {
            status = "index [2, max-1] getAdjacent issue";
            return status;
        }
        return status;
    }
}
