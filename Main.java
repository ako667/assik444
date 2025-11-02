import java.util.*;
import graph.utils.Metrics;

public class Main {
    public static void main(String[] args) {
        String[] files = {
                "data/small1.json", "data/small2.json", "data/small3.json",
                "data/medium1.json", "data/medium2.json", "data/medium3.json",
                "data/large1.json", "data/large2.json", "data/large3.json"
        };

        for (String filename : files) {
            System.out.println("=== Processing " + filename + " ===");
            Metrics metrics = new Metrics();

            // 1️⃣ Load graph
            GraphLoader loader = new GraphLoader(filename);
            List<List<GraphLoader.Edge>> graph = loader.getGraph();
            int source = loader.getSource();

            // 2️⃣ SCC
            metrics.start();
            SCCFinder sccFinder = new SCCFinder(graph, metrics);
            metrics.stop();
            List<List<Integer>> sccs = sccFinder.getSCCs();
            Map<Integer, Integer> nodeToComp = sccFinder.getNodeToComponentMap();

            System.out.println("Strongly Connected Components:");
            for (int i = 0; i < sccs.size(); i++)
                System.out.println("Component " + i + ": " + sccs.get(i));
            metrics.printSummary("SCCFinder");

            // 3️⃣ Build condensation DAG
            CondensationGraph condensation = new CondensationGraph(graph, nodeToComp, sccs.size());
            condensation.print();

            // 4️⃣ Topological Sort
            metrics.start();
            TopoSort topo = new TopoSort(condensation.getDAG());
            metrics.stop();
            System.out.println("Topological order of components:");
            System.out.println(topo.getTopoOrder());
            metrics.printSummary("TopoSort");

            // 5️⃣ DAG Shortest & Longest Paths
            metrics.start();
            DAGShortestPaths dagSP = new DAGShortestPaths(graph);
            dagSP.shortestPaths(source);
            metrics.stop();
            metrics.printSummary("Shortest Paths");

            System.out.println("Shortest paths from " + source + ":");
            double[] dist = dagSP.getDist();
            for (int i = 0; i < dist.length; i++)
                System.out.println(i + " -> " + dist[i]);
            System.out.println("Shortest paths reconstructed:");
            for (int i = 0; i < dist.length; i++)
                System.out.println(dagSP.getPath(i));

            // Longest paths (critical paths)
            metrics.start();
            dagSP.longestPaths(source);
            metrics.stop();
            metrics.printSummary("Longest Paths");

            System.out.println("Longest paths from " + source + " (critical paths):");
            double[] longest = dagSP.getLongestDist();
            for (int i = 0; i < longest.length; i++)
                System.out.println(i + " -> " + longest[i]);
            System.out.println("Longest paths reconstructed:");
            for (int i = 0; i < longest.length; i++)
                System.out.println(dagSP.getLongestPath(i));

            System.out.println("=========================================\n");
        }
    }
}
