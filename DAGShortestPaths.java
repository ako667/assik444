import java.util.*;

public class DAGShortestPaths {
    private List<List<GraphLoader.Edge>> graph;

    private double[] dist;
    private int[] prev;

    private double[] distLongest;
    private int[] prevLongest;

    public DAGShortestPaths(List<List<GraphLoader.Edge>> graph) {
        this.graph = graph;
    }

    // Shortest paths
    public void shortestPaths(int source) {
        int n = graph.size();
        dist = new double[n];
        prev = new int[n];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        Arrays.fill(prev, -1);
        dist[source] = 0;

        List<Integer> order = topoSort();
        for (int u : order) {
            for (GraphLoader.Edge e : graph.get(u)) {
                int v = e.to;
                double w = e.w;
                if (dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    prev[v] = u;
                }
            }
        }
    }

    // Longest paths
    public void longestPaths(int source) {
        int n = graph.size();
        distLongest = new double[n];
        prevLongest = new int[n];
        Arrays.fill(distLongest, Double.NEGATIVE_INFINITY);
        Arrays.fill(prevLongest, -1);
        distLongest[source] = 0;

        List<Integer> order = topoSort();
        for (int u : order) {
            if (distLongest[u] != Double.NEGATIVE_INFINITY) {
                for (GraphLoader.Edge e : graph.get(u)) {
                    int v = e.to;
                    double w = e.w;
                    if (distLongest[u] + w > distLongest[v]) {
                        distLongest[v] = distLongest[u] + w;
                        prevLongest[v] = u;
                    }
                }
            }
        }
    }

    private List<Integer> topoSort() {
        int n = graph.size();
        boolean[] visited = new boolean[n];
        List<Integer> order = new ArrayList<>();
        for (int i = 0; i < n; i++) if (!visited[i]) dfsTopo(i, visited, order);
        Collections.reverse(order);
        return order;
    }

    private void dfsTopo(int v, boolean[] visited, List<Integer> order) {
        visited[v] = true;
        for (GraphLoader.Edge e : graph.get(v))
            if (!visited[e.to]) dfsTopo(e.to, visited, order);
        order.add(v);
    }

    public double[] getDist() { return dist; }

    public List<Integer> getPath(int to) {
        List<Integer> path = new ArrayList<>();
        for (int at = to; at != -1; at = prev[at]) path.add(at);
        Collections.reverse(path);
        return path;
    }

    public double[] getLongestDist() { return distLongest; }

    public List<Integer> getLongestPath(int to) {
        List<Integer> path = new ArrayList<>();
        for (int at = to; at != -1; at = prevLongest[at]) path.add(at);
        Collections.reverse(path);
        return path;
    }
}
