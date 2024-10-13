package Language.Java.DataStructures.Graphs.UndirectedGraphs.AdjacencyMatrix;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Graph {

    private int v; // Number of vertices
    private int e; // Number of edges
    int[][] adjMatrix;

    public Graph(int nodes) {
        adjMatrix = new int[nodes][nodes];
        this.e = 0;
        this.v = nodes;
    }

    public void link(int u, int v) {
        adjMatrix[u][v] = 1;
        adjMatrix[v][u] = 1;
        e++;
    }

    public void display() {
        System.out.print("   ");
        for (int i = 0; i < v; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < adjMatrix.length; i++) {
            int[] row = adjMatrix[i];
            System.out.print(i + ": ");
            for (int j = 0; j < row.length; j++) {
                System.out.print(row[j] + " ");
            }
            System.out.println();

        }
    }

    public void bfs(int s) {
        if (s < 0 || s >= v) {
            throw new RuntimeException("Out of bounds exception");
        }
        boolean[] visited = new boolean[v];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        visited[s] = true;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            System.out.print(u + " ");
            for (int i = 0; i < adjMatrix[u].length; i++) {
                if (adjMatrix[u][i] == 1 && !visited[i]) {
                    visited[i] = true;
                    queue.add(i);
                }
            }
        }
    }

    public void dfs(int s) {
        if (s < 0 || s >= v) {
            throw new RuntimeException("Out of bounds exception");
        }
        boolean[] visited = new boolean[v];
        Stack<Integer> stack = new Stack<>();
        stack.push(s);
        while (!stack.isEmpty()) {
            int u = stack.pop();
            if (!visited[u]) {
                visited[u] = true;
                System.out.print(u + " ");
                for (int i = 0; i < adjMatrix[u].length; i++) {
                    if (adjMatrix[u][i] == 1) {
                        stack.push(i);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Graph socialMedia = new Graph(5);
        socialMedia.link(0, 1);
        socialMedia.link(0, 3);
        socialMedia.link(3, 2);
        socialMedia.link(2, 4);
        socialMedia.display();
        socialMedia.bfs(0); // Expected OutPut : 0 1 3 2 4
        System.out.println();
        socialMedia.dfs(0); // Expected OutPut : 0 3 2 4 1
        /*
            0 1 2 3 4 
         0: 0 1 0 1 0
         1: 1 0 0 0 0
         2: 0 0 0 1 1
         3: 1 0 1 0 0 
         4: 0 0 1 0 0
         */
    }
}
