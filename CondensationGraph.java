import java.util.*;

public class CondensationGraph {
    private List<List<Integer>> dag;
    private int numComponents;

    public CondensationGraph(List<List<GraphLoader.Edge>> graph, Map<Integer, Integer> nodeToComp, int numComponents) {
        this.numComponents = numComponents;
        dag = new ArrayList<>();
        for (int i = 0; i < numComponents; i++)
            dag.add(new ArrayList<>());

        for (int u = 0; u < graph.size(); u++) {
            int cu = nodeToComp.get(u);
            for (GraphLoader.Edge e : graph.get(u)) {
                int cv = nodeToComp.get(e.to);
                if (cu != cv && !dag.get(cu).contains(cv))
                    dag.get(cu).add(cv);
            }
        }
    }

    public List<List<Integer>> getDAG() { return dag; }

    public void print() {
        System.out.println("Condensation DAG:");
        for (int i = 0; i < dag.size(); i++)
            System.out.println("Comp " + i + " -> " + dag.get(i));
    }
}
