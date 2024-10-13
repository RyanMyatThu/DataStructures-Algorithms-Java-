package Language.Java.DataStructures.Graphs.UndirectedGraphs.AdjancencyList;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Graph {

    LinkedList<Integer>[] adjList;
    private int v;
    private int e;

    Graph(int nodes) {
        adjList = new LinkedList[nodes];
        for (int i = 0; i < nodes; i++) {
            adjList[i] = new LinkedList<>();
        }
        this.e = 0;
        this.v = nodes;
    }

    public void link(int u, int v) {
        adjList[u].add(v);
        adjList[v].add(u);
        e++;
    }

    public void display() {
        for (int i = 0; i < adjList.length; i++) {
            System.out.print(i + ": ");
            for (int j = 0; j < adjList[i].size(); j++) {
                System.out.print(adjList[i].get(j) + " -> ");
            }
            System.out.print("null");
            System.out.println();
        }
    }

    public void bfs(int s) {
        boolean[] visited = new boolean[v];
        Queue<Integer> q = new LinkedList<>();
        q.add(s);
        visited[s] = true;
        while (!q.isEmpty()) {
            int u = q.poll();
            System.out.print(u + " ");
            for (int node : adjList[u]) {
                if (!visited[node]) {
                    visited[node] = true;
                    q.add(node);
                }
            }
        }
    }

    public void dfs(int s) {
        boolean[] visited = new boolean[v];
        Stack<Integer> stack = new Stack();
        stack.push(s);
        while (!stack.isEmpty()) {
            int u = stack.pop();
            if (!visited[u]) {
                visited[u] = true;
                System.out.print(u + " ");
                for (int node : adjList[u]) {
                    stack.push(node);
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
    }

}
