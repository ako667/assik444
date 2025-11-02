import java.util.*;
import graph.utils.Metrics;

public class SCCFinder {
    private List<List<GraphLoader.Edge>> graph;
    private int[] ids, low;
    private boolean[] onStack;
    private Stack<Integer> stack;
    private int id = 0;
    private List<List<Integer>> sccs = new ArrayList<>();
    private Metrics metrics;

    public SCCFinder(List<List<GraphLoader.Edge>> graph, Metrics metrics) {
        this.graph = graph;
        this.metrics = metrics;
        int n = graph.size();
        ids = new int[n];
        low = new int[n];
        onStack = new boolean[n];
        stack = new Stack<>();
        Arrays.fill(ids, -1);

        for (int i = 0; i < n; i++)
            if (ids[i] == -1) dfs(i);
    }

    private void dfs(int at) {
        metrics.dfsCalls++;
        stack.push(at);
        onStack[at] = true;
        ids[at] = low[at] = id++;
        for (GraphLoader.Edge e : graph.get(at)) {
            int to = e.to;
            if (ids[to] == -1) dfs(to);
            if (onStack[to]) low[at] = Math.min(low[at], low[to]);
        }

        // Found root of SCC
        if (ids[at] == low[at]) {
            List<Integer> component = new ArrayList<>();
            int node;
            do {
                node = stack.pop();
                onStack[node] = false;
                component.add(node);
            } while (node != at);
            sccs.add(component);
        }
    }

    public List<List<Integer>> getSCCs() {
        return sccs;
    }

    // Node → Component mapping
    public Map<Integer, Integer> getNodeToComponentMap() {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < sccs.size(); i++)
            for (int node : sccs.get(i))
                map.put(node, i);
        return map;
    }
}
