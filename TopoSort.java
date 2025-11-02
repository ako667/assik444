import java.util.*;

public class TopoSort {
    private List<List<Integer>> dag;
    private List<Integer> topoOrder;

    public TopoSort(List<List<Integer>> dag) {
        this.dag = dag;
        topoOrder = new ArrayList<>();
        boolean[] visited = new boolean[dag.size()];

        for (int i = 0; i < dag.size(); i++)
            if (!visited[i])
                dfs(i, visited);

        Collections.reverse(topoOrder);
    }

    private void dfs(int v, boolean[] visited) {
        visited[v] = true;
        if (dag.get(v) != null)
            for (int u : dag.get(v))
                if (!visited[u])
                    dfs(u, visited);
        topoOrder.add(v);
    }

    public List<Integer> getTopoOrder() {
        return topoOrder;
    }
}
